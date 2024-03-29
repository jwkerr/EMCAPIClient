package net.earthmc.emcapiclient.util;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import net.earthmc.emcapiclient.EMCAPIClient;
import net.earthmc.emcapiclient.exception.GatewayTimeoutException;
import net.earthmc.emcapiclient.object.data.*;
import net.earthmc.emcapiclient.object.identifier.DiscordIdentifier;
import net.earthmc.emcapiclient.object.identifier.Identifier;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class RequestUtils {
    private static final int BATCH_SIZE = 100;

    public static <T extends Identifier> List<T> getAllIdentifiers(String endpoint, Class<T> identiferClass) {
        JsonArray jsonArray = EMCAPIClient.requestManager.getURLAsJsonArray(EMCAPIClient.EARTHMC_API_URL + endpoint);

        return DataUtils.getIdentifierList(jsonArray, identiferClass);
    }

    public static <T extends Data> List<T> getDataByStrings(List<String> uuidsOrNames, String endpoint) throws GatewayTimeoutException {
        List<T> data = new ArrayList<>();
        ExecutorService executor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

        List<Future<List<T>>> futures = new ArrayList<>();

        for (int i = 0; i < uuidsOrNames.size(); i += BATCH_SIZE) {
            List<String> batch = uuidsOrNames.subList(i, Math.min(i + BATCH_SIZE, uuidsOrNames.size()));
            Future<List<T>> future = executor.submit(() -> {
                List<T> batchData = new ArrayList<>();

                String requestString = String.join(",", batch);
                JsonArray batchArray = EMCAPIClient.requestManager.getURLAsJsonArray(EMCAPIClient.EARTHMC_API_URL + endpoint + "?query=" + requestString);

                for (JsonElement element : batchArray) {
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

    public static List<DiscordIdentifier> getDiscordIdentifiers(List<String> uuidsOrIDs) {
        List<DiscordIdentifier> data = new ArrayList<>();
        for (int i = 0; i < uuidsOrIDs.size(); i += BATCH_SIZE) {
            List<String> batch = uuidsOrIDs.subList(i, BATCH_SIZE);

            String requestString = String.join(",", batch);
            JsonArray jsonArray = EMCAPIClient.requestManager.getURLAsJsonArray(EMCAPIClient.EARTHMC_API_URL + "discord?query=" + requestString);

            for (JsonElement element : jsonArray) {
                data.add(new DiscordIdentifier(element.getAsJsonObject()));
            }
        }

        return data;
    }
}
