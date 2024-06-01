package net.earthmc.emcapiclient;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import kotlin.Pair;
import net.earthmc.emcapiclient.manager.RequestManager;
import net.earthmc.emcapiclient.object.data.*;
import net.earthmc.emcapiclient.object.data.LocationData;
import net.earthmc.emcapiclient.object.identifier.*;
import net.earthmc.emcapiclient.util.DataUtil;
import net.earthmc.emcapiclient.util.RequestUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@SuppressWarnings("unused")
public class EMCAPIClient {

    public static final String EARTHMC_API_URL = "https://api.earthmc.net/v3/aurora/";
    public static final RequestManager REQUEST_MANAGER = new RequestManager();

    /**
     * Do not set this value higher than the default of 52 if you are using identifiers or UUIDs as it will max out the URL length
     * If you wish to increase the size to request more at once you should be using usernames, such workarounds will not be necessary in the future with updates to the API
     *
     * @param batchSize The amount of objects that will be requested in one request
     */
    public void setBatchSize(int batchSize) {
        if (batchSize <= 0) batchSize = 1;
        if (batchSize > 100) batchSize = 100; // The max objects that can be looked up at once in the API

        RequestUtil.batchSize = batchSize;
    }

    /**
     *
     * @return A list of objects representing every player's username and UUID
     */
    public List<PlayerIdentifier> getAllPlayerIdentifiers() {
        return RequestUtil.getAllIdentifiers("players", PlayerIdentifier.class);
    }

    /**
     *
     * @return A list of objects representing every town's name and UUID
     */
    public List<TownIdentifier> getAllTownIdentifiers() {
        return RequestUtil.getAllIdentifiers("towns", TownIdentifier.class);
    }

    /**
     *
     * @return A list of objects representing every nation's name and UUID
     */
    public List<NationIdentifier> getAllNationIdentifiers() {
        return RequestUtil.getAllIdentifiers("nations", NationIdentifier.class);
    }

    /**
     *
     * @return A list of objects representing every quarter's name (always null) and UUID
     */
    public List<QuarterIdentifier> getAllQuarterIdentifiers() {
        return RequestUtil.getAllIdentifiers("quarters", QuarterIdentifier.class);
    }

    /**
     *
     * @return A list of objects representing every player's data
     */
    public List<PlayerData> getAllPlayerData() {
        return getPlayerDataByIdentifiers(getAllPlayerIdentifiers());
    }

    /**
     *
     * @return A list of objects representing every town's data
     */
    public List<TownData> getAllTownData() {
        return getTownDataByIdentifiers(getAllTownIdentifiers());
    }

    /**
     *
     * @return A list of objects representing every nation's data
     */
    public List<NationData> getAllNationData() {
        return getNationDataByIdentifiers(getAllNationIdentifiers());
    }

    /**
     *
     * @return A list of objects representing every quarter's data
     */
    public List<QuarterData> getAllQuarterData() {
        return getQuarterDataByIdentifiers(getAllQuarterIdentifiers());
    }

    /**
     *
     * @return An object representing the server data endpoint
     */
    public ServerData getServerData() {
        JsonObject jsonObject = REQUEST_MANAGER.getURLAsJsonObject(EARTHMC_API_URL);

        return new ServerData(jsonObject);
    }

    /**
     *
     * @param uuidOrName The UUID or username of a player currently registered in Towny as a string
     * @return An object representing the player's data
     */
    public PlayerData getPlayerDataByString(String uuidOrName) {
        JsonArray jsonArray = REQUEST_MANAGER.getURLAsJsonArray(EARTHMC_API_URL + "players?query=" + uuidOrName);

        return new PlayerData(jsonArray.get(0).getAsJsonObject());
    }

    /**
     *
     * @param uuidOrName The UUID or name of a town currently registered in Towny as a string
     * @return An object representing the town's data
     */
    public TownData getTownDataByString(String uuidOrName) {
        JsonArray jsonArray = REQUEST_MANAGER.getURLAsJsonArray(EARTHMC_API_URL + "towns?query=" + uuidOrName);

        return new TownData(jsonArray.get(0).getAsJsonObject());
    }

    /**
     *
     * @param uuidOrName The UUID or name of a nation currently registered in Towny as a string
     * @return An object representing the nation's data
     */
    public NationData getNationDataByString(String uuidOrName) {
        JsonArray jsonArray = REQUEST_MANAGER.getURLAsJsonArray(EARTHMC_API_URL + "nations?query=" + uuidOrName);

        return new NationData(jsonArray.get(0).getAsJsonObject());
    }

    /**
     *
     * @param uuid The UUID of a quarter that currently exists
     * @return An object representing the quarter's data
     */
    public QuarterData getQuarterDataByString(String uuid) {
        JsonArray jsonArray = REQUEST_MANAGER.getURLAsJsonArray(EARTHMC_API_URL + "quarters?query=" + uuid);

        return new QuarterData(jsonArray.get(0).getAsJsonObject());
    }

    /**
     *
     * @param uuidOrID The Minecraft UUID or Discord ID of the user
     * @return A {@link DiscordIdentifier} representing the user's DiscordSRV link data
     */
    public DiscordIdentifier getDiscordIdentifierByString(String uuidOrID) {
        JsonArray jsonArray = REQUEST_MANAGER.getURLAsJsonArray(EARTHMC_API_URL + "discord?query=" + uuidOrID);

        return new DiscordIdentifier(jsonArray.get(0).getAsJsonObject());
    }

    /**
     *
     * @param uuid The UUID of a player currently registered in Towny
     * @return An object representing the player's data
     */
    public PlayerData getPlayerDataByUUID(UUID uuid) {
        return getPlayerDataByString(uuid.toString());
    }

    /**
     *
     * @param uuid The UUID of a town currently registered in Towny
     * @return An object representing the town's data
     */
    public TownData getTownDataByUUID(UUID uuid) {
        return getTownDataByString(uuid.toString());
    }

    /**
     *
     * @param uuid The UUID of a nation currently registered in Towny
     * @return An object representing the nation's data
     */
    public NationData getNationDataByUUID(UUID uuid) {
        return getNationDataByString(uuid.toString());
    }

    /**
     *
     * @param uuid The UUID of a quarter that currently exists
     * @return An object representing the quarter's data
     */
    public QuarterData getQuarterDataByUUID(UUID uuid) {
        return getQuarterDataByString(uuid.toString());
    }

    /**
     *
     * @param uuid The Minecraft UUID of the user
     * @return A {@link DiscordIdentifier} representing the user's DiscordSRV link data
     */
    public DiscordIdentifier getDiscordIdentifierByUUID(UUID uuid) {
        return getDiscordIdentifierByString(uuid.toString());
    }

    /**
     *
     * @param uuidsOrNames An arbitrarily long list of player UUIDs or usernames as strings
     * @return A list of objects representing every requested player's data
     */
    public List<PlayerData> getPlayerDataByStrings(List<String> uuidsOrNames) {
        return RequestUtil.getDataByStrings(uuidsOrNames, "players");
    }

    /**
     *
     * @param uuidsOrNames An arbitrarily long list of town UUIDs or names as strings
     * @return A list of objects representing every requested town's data
     */
    public List<TownData> getTownDataByStrings(List<String> uuidsOrNames) {
        return RequestUtil.getDataByStrings(uuidsOrNames, "towns");
    }

    /**
     *
     * @param uuidsOrNames An arbitrarily long list of nation UUIDs or names as strings
     * @return A list of objects representing every requested nation's data
     */
    public List<NationData> getNationDataByStrings(List<String> uuidsOrNames) {
        return RequestUtil.getDataByStrings(uuidsOrNames, "nations");
    }

    /**
     *
     * @param uuidsOrNames An arbitrarily long list of quarter UUIDs as strings
     * @return A list of objects representing every requested quarter's data
     */
    public List<QuarterData> getQuarterDataByStrings(List<String> uuidsOrNames) {
        return RequestUtil.getDataByStrings(uuidsOrNames, "quarters");
    }

    /**
     *
     * @param uuidsOrNames An arbitrarily long list of player UUIDs or usernames as strings
     * @return A list of objects representing every requested player's data
     */
    public List<PlayerData> getPlayerDataByStrings(String[] uuidsOrNames) {
        return getPlayerDataByStrings(List.of(uuidsOrNames));
    }

    /**
     *
     * @param uuidsOrNames An arbitrarily long list of town UUIDs or names as strings
     * @return A list of objects representing every requested town's data
     */
    public List<TownData> getTownDataByStrings(String[] uuidsOrNames) {
        return getTownDataByStrings(List.of(uuidsOrNames));
    }

    /**
     *
     * @param uuidsOrNames An arbitrarily long list of nation UUIDs or names as strings
     * @return A list of objects representing every requested nation's data
     */
    public List<NationData> getNationDataByStrings(String[] uuidsOrNames) {
        return getNationDataByStrings(List.of(uuidsOrNames));
    }

    /**
     *
     * @param uuidsOrNames An arbitrarily long list of quarter UUIDs as strings
     * @return A list of objects representing every requested quarter's data
     */
    public List<QuarterData> getQuarterDataByStrings(String[] uuidsOrNames) {
        return getQuarterDataByStrings(List.of(uuidsOrNames));
    }

    /**
     *
     * @param uuids An arbitrarily long list of player UUIDs
     * @return A list of objects representing every requested player's data
     */
    public List<PlayerData> getPlayerDataByUUIDs(List<UUID> uuids) {
        return getPlayerDataByStrings(uuids.stream()
                .map(UUID::toString)
                .toList());
    }

    /**
     *
     * @param uuids An arbitrarily long list of town UUIDs
     * @return A list of objects representing every requested town's data
     */
    public List<TownData> getTownDataByUUIDs(List<UUID> uuids) {
        return getTownDataByStrings(uuids.stream()
                .map(UUID::toString)
                .toList());
    }

    /**
     *
     * @param uuids An arbitrarily long list of nation UUIDs
     * @return A list of objects representing every requested nation's data
     */
    public List<NationData> getNationDataByUUIDs(List<UUID> uuids) {
        return getNationDataByStrings(uuids.stream()
                .map(UUID::toString)
                .toList());
    }

    /**
     *
     * @param uuids An arbitrarily long list of quarter UUIDs
     * @return A list of objects representing every requested quarter's data
     */
    public List<QuarterData> getQuarterDataByUUIDs(List<UUID> uuids) {
        return getQuarterDataByStrings(uuids.stream()
                .map(UUID::toString)
                .toList());
    }

    /**
     *
     * @param identifiers An arbitrarily long list of PlayerIdentifiers
     * @return A list of objects representing every requested player's data
     */
    public List<PlayerData> getPlayerDataByIdentifiers(List<PlayerIdentifier> identifiers) {
        return getPlayerDataByStrings(identifiers.stream()
                .map(Identifier::getUUID)
                .toList());
    }

    /**
     *
     * @param identifiers An arbitrarily long list of TownIdentifiers
     * @return A list of objects representing every requested town's data
     */
    public List<TownData> getTownDataByIdentifiers(List<TownIdentifier> identifiers) {
        return getTownDataByStrings(identifiers.stream()
                .map(Identifier::getUUID)
                .toList());
    }

    /**
     *
     * @param identifiers An arbitrarily long list of NationIdentifiers
     * @return A list of objects representing every requested nation's data
     */
    public List<NationData> getNationDataByIdentifiers(List<NationIdentifier> identifiers) {
        return getNationDataByStrings(identifiers.stream()
                .map(Identifier::getUUID)
                .toList());
    }

    /**
     *
     * @param identifiers An arbitrarily long list of QuarterIdentifiers
     * @return A list of objects representing every requested quarter's data
     */
    public List<QuarterData> getQuarterDataByIdentifiers(List<QuarterIdentifier> identifiers) {
        return getQuarterDataByStrings(identifiers.stream()
                .map(Identifier::getUUID)
                .toList());
    }

    /**
     *
     * @param uuidsOrIDs A list of Minecraft UUIDs or Discord IDs as strings
     * @return A list of {@link DiscordIdentifier}
     */
    public List<DiscordIdentifier> getDiscordIdentifiersByStrings(List<String> uuidsOrIDs) {
        return RequestUtil.getDiscordIdentifiers(uuidsOrIDs);
    }

    /**
     *
     * @param uuidOrName The town UUID or name that you want to search near
     * @param radius The block radius to search around, measured in blocks from homeblock to homeblock
     * @return A list of {@link TownIdentifier} representing all the nearby towns excluding the specified town
     */
    public List<TownIdentifier> getTownsNearbyTown(String uuidOrName, int radius) {
        return DataUtil.getIdentifierList(REQUEST_MANAGER.getURLAsJsonArray(EARTHMC_API_URL + "nearby/town?town=" + uuidOrName + "&radius=" + radius), TownIdentifier.class);
    }

    /**
     *
     * @param x The X coordinate to search near
     * @param z The Z coordinate to search near
     * @param radius radius The block radius to search around, measured in blocks from townblock to homeblock
     * @return A list of {@link TownIdentifier} representing all the nearby towns
     */
    public List<TownIdentifier> getTownsNearbyCoordinate(int x, int z, int radius) {
        return DataUtil.getIdentifierList(REQUEST_MANAGER.getURLAsJsonArray(EARTHMC_API_URL + "nearby/coordinate?x=" + x + "&z=" + z + "&radius=" + radius), TownIdentifier.class);
    }

    /**
     *
     * @param x The X coordinate to search
     * @param z The Z coordinate to search
     * @return An object representing basic information such as the town and nation at that location
     */
    public LocationData getLocationData(int x, int z) {
        JsonArray jsonArray = REQUEST_MANAGER.getURLAsJsonArray(EARTHMC_API_URL + "location?query=" + x + ";" + z);

        return new LocationData(jsonArray.get(0).getAsJsonObject());
    }

    /**
     *
     * @param coordinates A list of pairs representing in-game coordinates, the first entry is the X coordinate and the second entry is the Z coordinate
     * @return A list of objects representing basic information at each specified location such as the town and nation
     */
    public List<LocationData> getLocationData(List<Pair<Integer, Integer>> coordinates) {
        List<String> coordinateStrings = new ArrayList<>();
        for (Pair<Integer, Integer> coordinate : coordinates) {
            coordinateStrings.add(coordinate.getFirst() + ";" + coordinate.getSecond());
        }

        String requestString = String.join(",", coordinateStrings);
        JsonArray jsonArray = REQUEST_MANAGER.getURLAsJsonArray(EARTHMC_API_URL + "location?query=" + requestString);

        List<LocationData> locations = new ArrayList<>();
        for (JsonElement element : jsonArray) {
            locations.add(new LocationData(element.getAsJsonObject()));
        }

        return locations;
    }
}
