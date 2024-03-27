package net.earthmc.emcapiclient;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import kotlin.Pair;
import net.earthmc.emcapiclient.exception.APIUnavailableException;
import net.earthmc.emcapiclient.manager.RequestManager;
import net.earthmc.emcapiclient.object.data.*;
import net.earthmc.emcapiclient.object.data.LocationData;
import net.earthmc.emcapiclient.object.identifier.*;
import net.earthmc.emcapiclient.util.DataUtils;

import java.util.ArrayList;
import java.util.List;

public class EMCAPIClient {
    public static final String EARTHMC_API_URL = "https://api.earthmc.net/v3/aurora/";
    private static final int BATCH_SIZE = 50;
    public static RequestManager requestManager;

    public EMCAPIClient() {
        requestManager = new RequestManager();
    }

    public ServerData getServerData() throws APIUnavailableException {
        JsonObject jsonObject = requestManager.getURLAsJsonObject(EARTHMC_API_URL);

        return new ServerData(jsonObject);
    }

    public PlayerData getPlayerData(String uuidOrName) throws APIUnavailableException {
        JsonArray jsonArray = requestManager.getURLAsJsonArray(EARTHMC_API_URL + "players?query=" + uuidOrName);

        return new PlayerData(jsonArray.get(0).getAsJsonObject());
    }

    public List<PlayerData> getPlayerData(List<PlayerIdentifier> identifiers) throws APIUnavailableException {
        List<String> uuidStrings = identifiers.stream()
                .map(Identifier::getUUID)
                .toList();

        List<PlayerData> data = new ArrayList<>();
        for (int i = 0; i < uuidStrings.size(); i += BATCH_SIZE) {
            List<String> batch = uuidStrings.subList(i, BATCH_SIZE);

            String requestString = String.join(",", batch);
            JsonArray jsonArray = EMCAPIClient.requestManager.getURLAsJsonArray(EMCAPIClient.EARTHMC_API_URL + "players?query=" + requestString);

            for (JsonElement element : jsonArray) {
                data.add(new PlayerData(element.getAsJsonObject()));
            }
        }

        return data;
    }

    public TownData getTownData(String uuidOrName) throws APIUnavailableException {
        JsonArray jsonArray = requestManager.getURLAsJsonArray(EARTHMC_API_URL + "towns?query=" + uuidOrName);

        return new TownData(jsonArray.get(0).getAsJsonObject());
    }

    public List<TownData> getTownData(List<TownIdentifier> identifiers) throws APIUnavailableException {
        List<String> uuidStrings = identifiers.stream()
                .map(Identifier::getUUID)
                .toList();

        List<TownData> data = new ArrayList<>();
        for (int i = 0; i < uuidStrings.size(); i += BATCH_SIZE) {
            List<String> batch = uuidStrings.subList(i, BATCH_SIZE);

            String requestString = String.join(",", batch);
            JsonArray jsonArray = EMCAPIClient.requestManager.getURLAsJsonArray(EMCAPIClient.EARTHMC_API_URL + "towns?query=" + requestString);

            for (JsonElement element : jsonArray) {
                data.add(new TownData(element.getAsJsonObject()));
            }
        }

        return data;
    }

    public NationData getNationData(String uuidOrName) throws APIUnavailableException {
        JsonArray jsonArray = requestManager.getURLAsJsonArray(EARTHMC_API_URL + "nations?query=" + uuidOrName);

        return new NationData(jsonArray.get(0).getAsJsonObject());
    }

    public List<NationData> getNationData(List<NationIdentifier> identifiers) throws APIUnavailableException {
        List<String> uuidStrings = identifiers.stream()
                .map(Identifier::getUUID)
                .toList();

        List<NationData> data = new ArrayList<>();
        for (int i = 0; i < uuidStrings.size(); i += BATCH_SIZE) {
            List<String> batch = uuidStrings.subList(i, BATCH_SIZE);

            String requestString = String.join(",", batch);
            JsonArray jsonArray = EMCAPIClient.requestManager.getURLAsJsonArray(EMCAPIClient.EARTHMC_API_URL + "nations?query=" + requestString);

            for (JsonElement element : jsonArray) {
                data.add(new NationData(element.getAsJsonObject()));
            }
        }

        return data;
    }

    public QuarterData getQuarterData(String uuid) throws APIUnavailableException {
        JsonArray jsonArray = requestManager.getURLAsJsonArray(EARTHMC_API_URL + "quarters?query=" + uuid);

        return new QuarterData(jsonArray.get(0).getAsJsonObject());
    }

    public List<QuarterData> getQuarterData(List<QuarterIdentifier> identifiers) throws APIUnavailableException {
        List<String> uuidStrings = identifiers.stream()
                .map(Identifier::getUUID)
                .toList();

        List<QuarterData> data = new ArrayList<>();
        for (int i = 0; i < uuidStrings.size(); i += BATCH_SIZE) {
            List<String> batch = uuidStrings.subList(i, BATCH_SIZE);

            String requestString = String.join(",", batch);
            JsonArray jsonArray = EMCAPIClient.requestManager.getURLAsJsonArray(EMCAPIClient.EARTHMC_API_URL + "quarters?query=" + requestString);

            for (JsonElement element : jsonArray) {
                data.add(new QuarterData(element.getAsJsonObject()));
            }
        }

        return data;
    }

    public List<TownIdentifier> getTownsNearbyTown(String uuidOrName, int radius) throws APIUnavailableException {
        return DataUtils.getIdentifierList(requestManager.getURLAsJsonArray(EARTHMC_API_URL + "nearby/town?town=" + uuidOrName + "&radius=" + radius), TownIdentifier.class);
    }

    public List<TownIdentifier> getTownsNearbyCoordinate(int x, int z, int radius) throws APIUnavailableException {
        return DataUtils.getIdentifierList(requestManager.getURLAsJsonArray(EARTHMC_API_URL + "nearby/coordinate?x=" + x + "&z=" + z + "&radius=" + radius), TownIdentifier.class);
    }

    public LocationData getLocationData(int x, int z) throws APIUnavailableException {
        JsonArray jsonArray = requestManager.getURLAsJsonArray(EARTHMC_API_URL + "location?query=" + x + ";" + z);

        return new LocationData(jsonArray.get(0).getAsJsonObject());
    }

    public List<LocationData> getLocationData(List<Pair<Integer, Integer>> coordinates) throws APIUnavailableException {
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < coordinates.size(); i++) {
            sb.append(coordinates.get(i).getFirst());
            sb.append(";");
            sb.append(coordinates.get(i).getSecond());

            if (i != coordinates.size() - 1)
                sb.append(",");
        }

        JsonArray jsonArray = requestManager.getURLAsJsonArray(EARTHMC_API_URL + "location?query=" + sb);

        List<LocationData> locations = new ArrayList<>();
        for (JsonElement element : jsonArray) {
            locations.add(new LocationData(element.getAsJsonObject()));
        }

        return locations;
    }

    public List<PlayerIdentifier> getAllPlayerIdentifiers() throws APIUnavailableException {
        return getAllIdentifiers("players", PlayerIdentifier.class);
    }

    public List<TownIdentifier> getAllTownIdentifiers() throws APIUnavailableException {
        return getAllIdentifiers("towns", TownIdentifier.class);
    }

    public List<NationIdentifier> getAllNationIdentifiers() throws APIUnavailableException {
        return getAllIdentifiers("nations", NationIdentifier.class);
    }

    public List<QuarterIdentifier> getAllQuarterIdentifiers() throws APIUnavailableException {
        return getAllIdentifiers("quarters", QuarterIdentifier.class);
    }

    public List<DiscordIdentifier> getDiscordIdentifiers(List<String> uuidsOrIDs) throws APIUnavailableException {
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

    private <T extends Identifier> List<T> getAllIdentifiers(String endpoint, Class<T> identiferClass) {
        JsonArray jsonArray = requestManager.getURLAsJsonArray(EARTHMC_API_URL + endpoint);

        return DataUtils.getIdentifierList(jsonArray, identiferClass);
    }
}
