package net.earthmc.emcapiclient;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import kotlin.Pair;
import net.earthmc.emcapiclient.exception.BadRequestException;
import net.earthmc.emcapiclient.exception.GatewayTimeoutException;
import net.earthmc.emcapiclient.exception.NotFoundException;
import net.earthmc.emcapiclient.manager.RequestManager;
import net.earthmc.emcapiclient.object.data.*;
import net.earthmc.emcapiclient.object.data.LocationData;
import net.earthmc.emcapiclient.object.identifier.*;
import net.earthmc.emcapiclient.util.DataUtils;
import net.earthmc.emcapiclient.util.RequestUtils;

import java.util.ArrayList;
import java.util.List;

public class EMCAPIClient {
    public static final String EARTHMC_API_URL = "https://api.earthmc.net/v3/aurora/";
    public static final RequestManager requestManager = new RequestManager();;

    /**
     *
     * @return A list of objects representing every player's username and UUID
     * @throws BadRequestException
     * @throws NotFoundException
     * @throws GatewayTimeoutException
     */
    public List<PlayerIdentifier> getAllPlayerIdentifiers() throws BadRequestException, NotFoundException, GatewayTimeoutException {
        return RequestUtils.getAllIdentifiers("players", PlayerIdentifier.class);
    }

    /**
     *
     * @return A list of objects representing every town's name and UUID
     * @throws BadRequestException
     * @throws NotFoundException
     * @throws GatewayTimeoutException
     */
    public List<TownIdentifier> getAllTownIdentifiers() throws BadRequestException, NotFoundException, GatewayTimeoutException {
        return RequestUtils.getAllIdentifiers("towns", TownIdentifier.class);
    }

    /**
     *
     * @return A list of objects representing every nation's name and UUID
     * @throws BadRequestException
     * @throws NotFoundException
     * @throws GatewayTimeoutException
     */
    public List<NationIdentifier> getAllNationIdentifiers() throws BadRequestException, NotFoundException, GatewayTimeoutException {
        return RequestUtils.getAllIdentifiers("nations", NationIdentifier.class);
    }

    /**
     *
     * @return A list of objects representing every quarter's name (always null) and UUID
     * @throws BadRequestException
     * @throws NotFoundException
     * @throws GatewayTimeoutException
     */
    public List<QuarterIdentifier> getAllQuarterIdentifiers() throws BadRequestException, NotFoundException, GatewayTimeoutException {
        return RequestUtils.getAllIdentifiers("quarters", QuarterIdentifier.class);
    }

    /**
     *
     * @return An object representing the server data endpoint
     * @throws BadRequestException
     * @throws NotFoundException
     * @throws GatewayTimeoutException
     */
    public ServerData getServerData() throws BadRequestException, NotFoundException, GatewayTimeoutException {
        JsonObject jsonObject = requestManager.getURLAsJsonObject(EARTHMC_API_URL);

        return new ServerData(jsonObject);
    }

    /**
     *
     * @param uuidOrName The UUID or username of a player currently registered in Towny as a string
     * @return An object representing the player's data
     * @throws BadRequestException
     * @throws NotFoundException
     * @throws GatewayTimeoutException
     */
    public PlayerData getPlayerDataByString(String uuidOrName) throws BadRequestException, NotFoundException, GatewayTimeoutException {
        JsonArray jsonArray = requestManager.getURLAsJsonArray(EARTHMC_API_URL + "players?query=" + uuidOrName);

        return new PlayerData(jsonArray.get(0).getAsJsonObject());
    }

    /**
     *
     * @param uuidOrName The UUID or name of a town currently registered in Towny as a string
     * @return An object representing the town's data
     * @throws BadRequestException
     * @throws NotFoundException
     * @throws GatewayTimeoutException
     */
    public TownData getTownDataByString(String uuidOrName) throws BadRequestException, NotFoundException, GatewayTimeoutException {
        JsonArray jsonArray = requestManager.getURLAsJsonArray(EARTHMC_API_URL + "towns?query=" + uuidOrName);

        return new TownData(jsonArray.get(0).getAsJsonObject());
    }

    /**
     *
     * @param uuidOrName The UUID or name of a nation currently registered in Towny as a string
     * @return An object representing the nation's data
     * @throws BadRequestException
     * @throws NotFoundException
     * @throws GatewayTimeoutException
     */
    public NationData getNationDataByString(String uuidOrName) throws BadRequestException, NotFoundException, GatewayTimeoutException {
        JsonArray jsonArray = requestManager.getURLAsJsonArray(EARTHMC_API_URL + "nations?query=" + uuidOrName);

        return new NationData(jsonArray.get(0).getAsJsonObject());
    }

    /**
     *
     * @param uuid The UUID of a quarter that currently exists
     * @return An object representing the quarter's data
     * @throws BadRequestException
     * @throws NotFoundException
     * @throws GatewayTimeoutException
     */
    public QuarterData getQuarterDataByString(String uuid) throws BadRequestException, NotFoundException, GatewayTimeoutException {
        JsonArray jsonArray = requestManager.getURLAsJsonArray(EARTHMC_API_URL + "quarters?query=" + uuid);

        return new QuarterData(jsonArray.get(0).getAsJsonObject());
    }

    /**
     *
     * @param uuidOrID The Minecraft UUID or Discord ID of the user
     * @return A {@link DiscordIdentifier} representing the user's DiscordSRV link data
     * @throws BadRequestException
     * @throws NotFoundException
     * @throws GatewayTimeoutException
     */
    public DiscordIdentifier getDiscordIdentifierByString(String uuidOrID) throws BadRequestException, NotFoundException, GatewayTimeoutException {
        JsonArray jsonArray = requestManager.getURLAsJsonArray(EARTHMC_API_URL + "discord?query=" + uuidOrID);

        return new DiscordIdentifier(jsonArray.get(0).getAsJsonObject());
    }

    /**
     *
     * @param uuidsOrNames An arbitrarily long list of player UUIDs or usernames as strings
     * @return A list of objects representing every requested player's data
     * @throws BadRequestException
     * @throws NotFoundException
     * @throws GatewayTimeoutException
     */
    public List<PlayerData> getPlayerDataByStrings(List<String> uuidsOrNames) throws BadRequestException, NotFoundException, GatewayTimeoutException {
        return RequestUtils.getDataByStrings(uuidsOrNames, "players");
    }

    /**
     *
     * @param uuidsOrNames An arbitrarily long list of town UUIDs or names as strings
     * @return A list of objects representing every requested town's data
     * @throws BadRequestException
     * @throws NotFoundException
     * @throws GatewayTimeoutException
     */
    public List<TownData> getTownDataByStrings(List<String> uuidsOrNames) throws BadRequestException, NotFoundException, GatewayTimeoutException {
        return RequestUtils.getDataByStrings(uuidsOrNames, "towns");
    }

    /**
     *
     * @param uuidsOrNames An arbitrarily long list of nation UUIDs or names as strings
     * @return A list of objects representing every requested nation's data
     * @throws BadRequestException
     * @throws NotFoundException
     * @throws GatewayTimeoutException
     */
    public List<NationData> getNationDataByStrings(List<String> uuidsOrNames) throws BadRequestException, NotFoundException, GatewayTimeoutException {
        return RequestUtils.getDataByStrings(uuidsOrNames, "nations");
    }

    /**
     *
     * @param uuidsOrNames An arbitrarily long list of quarter UUIDs as strings
     * @return A list of objects representing every requested quarter's data
     * @throws BadRequestException
     * @throws NotFoundException
     * @throws GatewayTimeoutException
     */
    public List<QuarterData> getQuarterDataByStrings(List<String> uuidsOrNames) throws BadRequestException, NotFoundException, GatewayTimeoutException {
        return RequestUtils.getDataByStrings(uuidsOrNames, "quarters");
    }

    /**
     *
     * @param identifiers An arbitrarily long list of PlayerIdentifiers
     * @return A list of objects representing every requested player's data
     * @throws BadRequestException
     * @throws NotFoundException
     * @throws GatewayTimeoutException
     */
    public List<PlayerData> getPlayerDataByIdentifiers(List<PlayerIdentifier> identifiers) throws BadRequestException, NotFoundException, GatewayTimeoutException {
        return getPlayerDataByStrings(identifiers.stream()
                .map(Identifier::getUUID)
                .toList());
    }

    /**
     *
     * @param identifiers An arbitrarily long list of TownIdentifiers
     * @return A list of objects representing every requested town's data
     * @throws BadRequestException
     * @throws NotFoundException
     * @throws GatewayTimeoutException
     */
    public List<TownData> getTownDataByIdentifiers(List<TownIdentifier> identifiers) throws BadRequestException, NotFoundException, GatewayTimeoutException {
        return getTownDataByStrings(identifiers.stream()
                .map(Identifier::getUUID)
                .toList());
    }

    /**
     *
     * @param identifiers An arbitrarily long list of NationIdentifiers
     * @return A list of objects representing every requested nation's data
     * @throws BadRequestException
     * @throws NotFoundException
     * @throws GatewayTimeoutException
     */
    public List<NationData> getNationDataByIdentifiers(List<NationIdentifier> identifiers) throws BadRequestException, NotFoundException, GatewayTimeoutException {
        return getNationDataByStrings(identifiers.stream()
                .map(Identifier::getUUID)
                .toList());
    }

    /**
     *
     * @param identifiers An arbitrarily long list of QuarterIdentifiers
     * @return A list of objects representing every requested quarter's data
     * @throws BadRequestException
     * @throws NotFoundException
     * @throws GatewayTimeoutException
     */
    public List<QuarterData> getQuarterDataByIdentifiers(List<QuarterIdentifier> identifiers) throws BadRequestException, NotFoundException, GatewayTimeoutException {
        return getQuarterDataByStrings(identifiers.stream()
                .map(Identifier::getUUID)
                .toList());
    }

    /**
     *
     * @param uuidsOrIDs A list of Minecraft UUIDs or Discord IDs as strings
     * @return A list of {@link DiscordIdentifier}
     * @throws BadRequestException
     * @throws NotFoundException
     * @throws GatewayTimeoutException
     */
    public List<DiscordIdentifier> getDiscordIdentifiersByStrings(List<String> uuidsOrIDs) throws BadRequestException, NotFoundException, GatewayTimeoutException {
        return RequestUtils.getDiscordIdentifiers(uuidsOrIDs);
    }

    /**
     *
     * @param uuidOrName The town UUID or name that you want to search near
     * @param radius The block radius to search around, measured in blocks from homeblock to homeblock
     * @return A list of {@link TownIdentifier} representing all the nearby towns excluding the specified town
     * @throws BadRequestException
     * @throws NotFoundException
     * @throws GatewayTimeoutException
     */
    public List<TownIdentifier> getTownsNearbyTown(String uuidOrName, int radius) throws BadRequestException, NotFoundException, GatewayTimeoutException {
        return DataUtils.getIdentifierList(requestManager.getURLAsJsonArray(EARTHMC_API_URL + "nearby/town?town=" + uuidOrName + "&radius=" + radius), TownIdentifier.class);
    }

    /**
     *
     * @param x The X coordinate to search near
     * @param z The Z coordinate to search near
     * @param radius radius The block radius to search around, measured in blocks from townblock to homeblock
     * @return A list of {@link TownIdentifier} representing all the nearby towns
     * @throws BadRequestException
     * @throws NotFoundException
     * @throws GatewayTimeoutException
     */
    public List<TownIdentifier> getTownsNearbyCoordinate(int x, int z, int radius) throws BadRequestException, NotFoundException, GatewayTimeoutException {
        return DataUtils.getIdentifierList(requestManager.getURLAsJsonArray(EARTHMC_API_URL + "nearby/coordinate?x=" + x + "&z=" + z + "&radius=" + radius), TownIdentifier.class);
    }

    /**
     *
     * @param x The X coordinate to search
     * @param z The Z coordinate to search
     * @return An object representing basic information such as the town and nation at that location
     * @throws BadRequestException
     * @throws NotFoundException
     * @throws GatewayTimeoutException
     */
    public LocationData getLocationData(int x, int z) throws BadRequestException, NotFoundException, GatewayTimeoutException {
        JsonArray jsonArray = requestManager.getURLAsJsonArray(EARTHMC_API_URL + "location?query=" + x + ";" + z);

        return new LocationData(jsonArray.get(0).getAsJsonObject());
    }

    /**
     *
     * @param coordinates A list of pairs representing in-game coordinates, the first entry is the X coordinate and the second entry is the Z coordinate
     * @return A list of objects representing basic information at each specified location such as the town and nation
     * @throws BadRequestException
     * @throws NotFoundException
     * @throws GatewayTimeoutException
     */
    public List<LocationData> getLocationData(List<Pair<Integer, Integer>> coordinates) throws BadRequestException, NotFoundException, GatewayTimeoutException {
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
}
