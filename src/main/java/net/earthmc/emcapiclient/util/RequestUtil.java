package net.earthmc.emcapiclient.util;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import net.earthmc.emcapiclient.EMCAPIClient;
import net.earthmc.emcapiclient.exception.GatewayTimeoutException;
import net.earthmc.emcapiclient.object.DiscordType;
import net.earthmc.emcapiclient.object.data.*;
import net.earthmc.emcapiclient.object.identifier.DiscordIdentifier;
import net.earthmc.emcapiclient.object.identifier.Identifier;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class RequestUtil {

    public static int batchSize = 100;

    public static <T extends Identifier> List<T> getAllIdentifiers(String endpoint, Class<T> identiferClass) {
        JsonArray jsonArray = EMCAPIClient.REQUEST_MANAGER.getURLAsJsonArray(EMCAPIClient.EARTHMC_API_URL + endpoint);

        return DataUtil.getIdentifierList(jsonArray, identiferClass);
    }

    public static <T extends Data> List<T> getDataByStrings(List<String> uuidsOrNames, String endpoint) throws GatewayTimeoutException {
        List<T> data = new ArrayList<>();
        ExecutorService executor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

        List<Future<List<T>>> futures = new ArrayList<>();

        for (int i = 0; i < uuidsOrNames.size(); i += batchSize) {
            List<String> batch = uuidsOrNames.subList(i, Math.min(i + batchSize, uuidsOrNames.size()));
            Future<List<T>> future = executor.submit(() -> {
                List<T> batchData = new ArrayList<>();

                JsonObject bodyObject = JSONUtil.createRequestBody(batch);
                JsonArray batchArray = EMCAPIClient.REQUEST_MANAGER.postURLAsJsonArray(EMCAPIClient.EARTHMC_API_URL + endpoint, bodyObject);

                for (JsonElement element : batchArray) {
                    if (element == null) batchData.add(null);

                    switch (endpoint) {
                        case "players" -> batchData.add((T) new PlayerData(element.getAsJsonObject()));
                        case "towns" -> batchData.add((T) new TownData(element.getAsJsonObject()));
                        case "nations" -> batchData.add((T) new NationData(element.getAsJsonObject()));
                        case "quarters" -> batchData.add((T) new QuarterData(element.getAsJsonObject()));
                    }
                }

                return batchData;
            });

            futures.add(future);
        }

        for (Future<List<T>> future : futures) {
            try {
                data.addAll(future.get());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        executor.shutdown();

        return data;
    }

    public static List<DiscordIdentifier> getDiscordIdentifiers(List<String> uuidsOrIDs, DiscordType type) {
        List<DiscordIdentifier> data = new ArrayList<>();
        for (int i = 0; i < uuidsOrIDs.size(); i += batchSize) {
            List<String> batch = uuidsOrIDs.subList(i, batchSize);

            JsonObject bodyObject = JSONUtil.createDiscordRequestBody(batch, type);
            JsonArray jsonArray = EMCAPIClient.REQUEST_MANAGER.postURLAsJsonArray(EMCAPIClient.EARTHMC_API_URL + "discord", bodyObject);

            for (JsonElement element : jsonArray) {
                data.add(new DiscordIdentifier(element.getAsJsonObject()));
            }
        }

        return data;
    }
}
