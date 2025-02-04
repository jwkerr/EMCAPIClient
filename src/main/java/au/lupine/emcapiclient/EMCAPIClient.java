package au.lupine.emcapiclient;

import au.lupine.emcapiclient.manager.RequestManager;
import au.lupine.emcapiclient.object.state.DiscordType;
import au.lupine.emcapiclient.object.Location;
import au.lupine.emcapiclient.object.state.World;
import au.lupine.emcapiclient.object.apiobject.*;
import au.lupine.emcapiclient.object.identifier.*;
import au.lupine.emcapiclient.util.JSONUtil;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

/**
 * The primary class in EMCAPIClient, simply create a new instance like any other object to use it
 * <p>
 * Methods with and without a {@link World} parameter will default to the world specified by {@link #setDefaultWorld(World)} when no world is provided
 */
@SuppressWarnings("unused")
public class EMCAPIClient {

    public static final URI EARTHMC_API_URI = URI.create("https://api.earthmc.net/v3/");

    private final RequestManager requestManager = new RequestManager();
    private World world = World.AURORA;

    /**
     * Set the default world to be used in API requests when none is specified
     */
    public void setDefaultWorld(World world) {
        this.world = world;
    }

    private URI getDefaultWorldURI() {
        return EARTHMC_API_URI.resolve(world.getName() + "/");
    }

    private URI createWorldURI(World world) {
        return EARTHMC_API_URI.resolve(world.getName() + "/");
    }

    public RequestManager getRequestManager() {
        return requestManager;
    }

    public @NotNull List<PlayerIdentifier> getAllPlayers() {
        return getAllPlayers(world);
    }

    public @NotNull List<PlayerIdentifier> getAllPlayers(@NotNull World world) {
        return Identifier.createIdentifierList(
                requestManager.getURIAsJsonArray(
                        createWorldURI(world).resolve("players")
                ),
                PlayerIdentifier.class
        );
    }

    public @NotNull List<TownIdentifier> getAllTowns() {
        return getAllTowns(world);
    }

    public @NotNull List<TownIdentifier> getAllTowns(@NotNull World world) {
        return Identifier.createIdentifierList(
                requestManager.getURIAsJsonArray(
                        createWorldURI(world).resolve("towns")
                ),
                TownIdentifier.class
        );
    }

    public @NotNull List<NationIdentifier> getAllNations() {
        return getAllNations(world);
    }

    public @NotNull List<NationIdentifier> getAllNations(@NotNull World world) {
        return Identifier.createIdentifierList(
                requestManager.getURIAsJsonArray(
                        createWorldURI(world).resolve("nations")
                ),
                NationIdentifier.class
        );
    }

    public @NotNull List<QuarterIdentifier> getAllQuarters() {
        return getAllQuarters(world);
    }

    public @NotNull List<QuarterIdentifier> getAllQuarters(@NotNull World world) {
        return Identifier.createIdentifierList(
                requestManager.getURIAsJsonArray(
                        createWorldURI(world).resolve("quarters")
                ),
                QuarterIdentifier.class
        );
    }

    public @NotNull List<Player> getPlayersByStrings(@NotNull List<String> query) {
        return getPlayersByStrings(world, query);
    }

    public @NotNull List<Player> getPlayersByStrings(@NotNull World world, @NotNull List<String> query) {
        JsonArray queryArray = new JsonArray();
        for (String entry : query) {
            queryArray.add(entry);
        }

        JsonArray response = requestManager.batchPostAsJsonArray(createWorldURI(world).resolve("players"), JSONUtil.createRequestBody(queryArray));

        List<Player> players = new ArrayList<>();
        for (JsonElement element : response) {
            Player player = new Player(element.getAsJsonObject());
            players.add(player);
        }

        return players;
    }

    public @NotNull List<Town> getTownsByStrings(@NotNull List<String> query) {
        return getTownsByStrings(world, query);
    }

    public @NotNull List<Town> getTownsByStrings(@NotNull World world, @NotNull List<String> query) {
        JsonArray queryArray = new JsonArray();
        for (String entry : query) {
            queryArray.add(entry);
        }

        JsonArray response = requestManager.batchPostAsJsonArray(createWorldURI(world).resolve("towns"), JSONUtil.createRequestBody(queryArray));

        List<Town> towns = new ArrayList<>();
        for (JsonElement element : response) {
            Town town = new Town(element.getAsJsonObject());
            towns.add(town);
        }

        return towns;
    }

    public @NotNull List<Nation> getNationsByStrings(@NotNull List<String> query) {
        return getNationsByStrings(world, query);
    }

    public @NotNull List<Nation> getNationsByStrings(@NotNull World world, @NotNull List<String> query) {
        JsonArray queryArray = new JsonArray();
        for (String entry : query) {
            queryArray.add(entry);
        }

        JsonArray response = requestManager.batchPostAsJsonArray(createWorldURI(world).resolve("nations"), JSONUtil.createRequestBody(queryArray));

        List<Nation> nations = new ArrayList<>();
        for (JsonElement element : response) {
            Nation nation = new Nation(element.getAsJsonObject());
            nations.add(nation);
        }

        return nations;
    }

    public @NotNull List<Quarter> getQuartersByStrings(@NotNull List<String> query) {
        return getQuartersByStrings(world, query);
    }

    public @NotNull List<Quarter> getQuartersByStrings(@NotNull World world, @NotNull List<String> query) {
        JsonArray queryArray = new JsonArray();
        for (String entry : query) {
            queryArray.add(entry);
        }

        JsonArray response = requestManager.batchPostAsJsonArray(createWorldURI(world).resolve("quarters"), JSONUtil.createRequestBody(queryArray));

        List<Quarter> quarters = new ArrayList<>();
        for (JsonElement element : response) {
            Quarter quarter = new Quarter(element.getAsJsonObject());
            quarters.add(quarter);
        }

        return quarters;
    }

    public @NotNull List<Discord> getDiscordsByStrings(@NotNull DiscordType type, @NotNull List<String> query) {
        return getDiscordsByStrings(world, type, query);
    }

    public @NotNull List<Discord> getDiscordsByStrings(@NotNull World world, @NotNull DiscordType type, @NotNull List<String> query) {
        JsonArray queryArray = new JsonArray();
        for (String entry : query) {
            JsonObject innerObject = new JsonObject();
            innerObject.addProperty("type", type.getName());
            innerObject.addProperty("target", entry);
            queryArray.add(innerObject);
        }

        JsonArray response = requestManager.batchPostAsJsonArray(createWorldURI(world).resolve("discord"), JSONUtil.createRequestBody(queryArray));

        List<Discord> pairs = new ArrayList<>();
        for (JsonElement element : response) {
            JsonObject jsonObject = element.getAsJsonObject();
            Discord pair = new Discord(jsonObject);

            pairs.add(pair);
        }

        return pairs;
    }

    public @NotNull List<Player> getPlayersByUUIDs(@NotNull List<UUID> query) {
        return getPlayersByUUIDs(world, query);
    }

    public @NotNull List<Player> getPlayersByUUIDs(@NotNull World world, @NotNull List<UUID> query) {
        return getPlayersByStrings(world, query.stream().map(UUID::toString).toList());
    }

    public @NotNull List<Town> getTownsByUUIDs(@NotNull List<UUID> query) {
        return getTownsByUUIDs(world, query);
    }

    public @NotNull List<Town> getTownsByUUIDs(@NotNull World world, @NotNull List<UUID> query) {
        return getTownsByStrings(world, query.stream().map(UUID::toString).toList());
    }

    public @NotNull List<Nation> getNationsByUUIDs(@NotNull List<UUID> query) {
        return getNationsByUUIDs(world, query);
    }

    public @NotNull List<Nation> getNationsByUUIDs(@NotNull World world, @NotNull List<UUID> query) {
        return getNationsByStrings(world, query.stream().map(UUID::toString).toList());
    }

    public @NotNull List<Quarter> getQuartersByUUIDs(@NotNull List<UUID> query) {
        return getQuartersByUUIDs(world, query);
    }

    public @NotNull List<Quarter> getQuartersByUUIDs(@NotNull World world, @NotNull List<UUID> query) {
        return getQuartersByStrings(world, query.stream().map(UUID::toString).toList());
    }

    public @NotNull List<Discord> getDiscordsByUUIDs(@NotNull List<UUID> query) {
        return getDiscordsByUUIDs(world, query);
    }

    public @NotNull List<Discord> getDiscordsByUUIDs(@NotNull World world, @NotNull List<UUID> query) {
        return getDiscordsByStrings(world, DiscordType.MINECRAFT, query.stream().map(UUID::toString).toList());
    }

    public @NotNull List<Player> getPlayersByStrings(@NotNull String... query) {
        return getPlayersByStrings(world, query);
    }

    public @NotNull List<Player> getPlayersByStrings(@NotNull World world, @NotNull String... query) {
        return getPlayersByStrings(world, Arrays.stream(query).toList());
    }

    public @NotNull List<Town> getTownsByStrings(@NotNull String... query) {
        return getTownsByStrings(world, query);
    }

    public @NotNull List<Town> getTownsByStrings(@NotNull World world, @NotNull String... query) {
        return getTownsByStrings(world, Arrays.stream(query).toList());
    }

    public @NotNull List<Nation> getNationsByStrings(@NotNull String... query) {
        return getNationsByStrings(world, query);
    }

    public @NotNull List<Nation> getNationsByStrings(@NotNull World world, @NotNull String... query) {
        return getNationsByStrings(world, Arrays.stream(query).toList());
    }

    public @NotNull List<Quarter> getQuartersByStrings(@NotNull String... query) {
        return getQuartersByStrings(world, query);
    }

    public @NotNull List<Quarter> getQuartersByStrings(@NotNull World world, @NotNull String... query) {
        return getQuartersByStrings(world, Arrays.stream(query).toList());
    }

    public @NotNull List<Discord> getDiscordsByStrings(@NotNull DiscordType type, @NotNull String... query) {
        return getDiscordsByStrings(world, type, query);
    }

    public @NotNull List<Discord> getDiscordsByStrings(@NotNull World world, @NotNull DiscordType type, @NotNull String... query) {
        return getDiscordsByStrings(world, type, Arrays.stream(query).toList());
    }

    public @NotNull List<Player> getPlayersByIdentifiers(@NotNull List<PlayerIdentifier> query) {
        return getPlayersByIdentifiers(world, query);
    }

    public @NotNull List<Player> getPlayersByIdentifiers(@NotNull World world, @NotNull List<PlayerIdentifier> query) {
        return getPlayersByUUIDs(world, query.stream().map(Identifier::getUUID).toList());
    }

    public @NotNull List<Town> getTownsByIdentifiers(@NotNull List<TownIdentifier> query) {
        return getTownsByIdentifiers(world, query);
    }

    public @NotNull List<Town> getTownsByIdentifiers(@NotNull World world, @NotNull List<TownIdentifier> query) {
        return getTownsByUUIDs(world, query.stream().map(Identifier::getUUID).toList());
    }

    public @NotNull List<Nation> getNationsByIdentifiers(@NotNull List<NationIdentifier> query) {
        return getNationsByIdentifiers(world, query);
    }

    public @NotNull List<Nation> getNationsByIdentifiers(@NotNull World world, @NotNull List<NationIdentifier> query) {
        return getNationsByUUIDs(world, query.stream().map(Identifier::getUUID).toList());
    }

    public @NotNull List<Quarter> getQuartersByIdentifiers(@NotNull List<QuarterIdentifier> query) {
        return getQuartersByIdentifiers(world, query);
    }

    public @NotNull List<Quarter> getQuartersByIdentifiers(@NotNull World world, @NotNull List<QuarterIdentifier> query) {
        return getQuartersByUUIDs(world, query.stream().map(Identifier::getUUID).toList());
    }

    public @NotNull List<Discord> getDiscordsByIdentifiers(@NotNull List<PlayerIdentifier> query) {
        return getDiscordsByIdentifiers(world, query);
    }

    public @NotNull List<Discord> getDiscordsByIdentifiers(@NotNull World world, @NotNull List<PlayerIdentifier> query) {
        return getDiscordsByUUIDs(world, query.stream().map(Identifier::getUUID).toList());
    }

    public @NotNull Server getServer() {
        return getServer(world);
    }

    public @NotNull Server getServer(@NotNull World world) {
        JsonObject response = requestManager.getURIAsJsonObject(createWorldURI(world));
        return new Server(response);
    }

    public @Nullable Player getPlayerByString(@NotNull String query) {
        return getPlayerByString(world, query);
    }

    public @Nullable Player getPlayerByString(@NotNull World world, @NotNull String query) {
        List<Player> players = getPlayersByStrings(world, query);
        return players.isEmpty() ? null : players.get(0);
    }

    public @Nullable Town getTownByString(@NotNull String query) {
        return getTownByString(world, query);
    }

    public @Nullable Town getTownByString(@NotNull World world, @NotNull String query) {
        List<Town> towns = getTownsByStrings(world, query);
        return towns.isEmpty() ? null : towns.get(0);
    }

    public @Nullable Nation getNationByString(@NotNull String query) {
        return getNationByString(world, query);
    }

    public @Nullable Nation getNationByString(@NotNull World world, @NotNull String query) {
        List<Nation> nations = getNationsByStrings(world, query);
        return nations.isEmpty() ? null : nations.get(0);
    }

    public @Nullable Quarter getQuarterByString(@NotNull String query) {
        return getQuarterByString(world, query);
    }

    public @Nullable Quarter getQuarterByString(@NotNull World world, @NotNull String query) {
        List<Quarter> quarters = getQuartersByStrings(world, query);
        return quarters.isEmpty() ? null : quarters.get(0);
    }

    public @NotNull Discord getDiscordByString(@NotNull DiscordType type, @NotNull String query) {
        return getDiscordByString(world, type, query);
    }

    public @NotNull Discord getDiscordByString(@NotNull World world, @NotNull DiscordType type, @NotNull String query) {
        List<Discord> identifiers = getDiscordsByStrings(world, type, query);
        return identifiers.get(0);
    }

    public @Nullable Player getPlayerByUUID(@NotNull UUID query) {
        return getPlayerByUUID(world, query);
    }

    public @Nullable Player getPlayerByUUID(@NotNull World world, @NotNull UUID query) {
        return getPlayerByString(world, query.toString());
    }

    public @Nullable Town getTownByUUID(@NotNull UUID query) {
        return getTownByUUID(world, query);
    }

    public @Nullable Town getTownByUUID(@NotNull World world, @NotNull UUID query) {
        return getTownByString(world, query.toString());
    }

    public @Nullable Nation getNationByUUID(@NotNull UUID query) {
        return getNationByUUID(world, query);
    }

    public @Nullable Nation getNationByUUID(@NotNull World world, @NotNull UUID query) {
        return getNationByString(world, query.toString());
    }

    public @Nullable Quarter getQuarterByUUID(@NotNull UUID query) {
        return getQuarterByUUID(world, query);
    }

    public @Nullable Quarter getQuarterByUUID(@NotNull World world, @NotNull UUID query) {
        return getQuarterByString(world, query.toString());
    }

    public @Nullable Discord getDiscordByUUID(@NotNull UUID query) {
        return getDiscordByUUID(world, query);
    }

    public @Nullable Discord getDiscordByUUID(@NotNull World world, @NotNull UUID query) {
        return getDiscordByString(world, DiscordType.MINECRAFT, query.toString());
    }

    public @Nullable Player getPlayerByIdentifier(@NotNull PlayerIdentifier identifier) {
        return getPlayerByIdentifier(world, identifier);
    }

    public @Nullable Player getPlayerByIdentifier(@NotNull World world, @NotNull PlayerIdentifier identifier) {
        return getPlayerByUUID(world, identifier.getUUID());
    }

    public @Nullable Town getTownByIdentifier(@NotNull TownIdentifier identifier) {
        return getTownByIdentifier(world, identifier);
    }

    public @Nullable Town getTownByIdentifier(@NotNull World world, @NotNull TownIdentifier identifier) {
        return getTownByUUID(world, identifier.getUUID());
    }

    public @Nullable Nation getNationByIdentifier(@NotNull NationIdentifier identifier) {
        return getNationByIdentifier(world, identifier);
    }

    public @Nullable Nation getNationByIdentifier(@NotNull World world, @NotNull NationIdentifier identifier) {
        return getNationByUUID(world, identifier.getUUID());
    }

    public @Nullable Quarter getQuarterByIdentifier(@NotNull QuarterIdentifier identifier) {
        return getQuarterByIdentifier(world, identifier);
    }

    public @Nullable Quarter getQuarterByIdentifier(@NotNull World world, @NotNull QuarterIdentifier identifier) {
        return getQuarterByUUID(world, identifier.getUUID());
    }

    public @Nullable Discord getDiscordByIdentifier(@NotNull PlayerIdentifier identifier) {
        return getDiscordByIdentifier(world, identifier);
    }

    public @Nullable Discord getDiscordByIdentifier(@NotNull World world, @NotNull PlayerIdentifier identifier) {
        return getDiscordByUUID(world, identifier.getUUID());
    }

    public @Nullable TownIdentifier getTownByIdentifier(@NotNull PlayerIdentifier identifier) {
        return getTownByIdentifier(world, identifier);
    }

    public @Nullable TownIdentifier getTownByIdentifier(@NotNull World world, @NotNull PlayerIdentifier identifier) {
        Player player = getPlayerByIdentifier(world, identifier);
        if (player == null) return null;

        return player.getTown();
    }

    public @Nullable NationIdentifier getNationByIdentifier(@NotNull PlayerIdentifier identifier) {
        return getNationByIdentifier(world, identifier);
    }

    public @Nullable NationIdentifier getNationByIdentifier(@NotNull World world, @NotNull PlayerIdentifier identifier) {
        Player player = getPlayerByIdentifier(world, identifier);
        if (player == null) return null;

        return player.getNation();
    }

    public @Nullable List<PlayerIdentifier> getFriendsByIdentifier(@NotNull PlayerIdentifier identifier) {
        return getFriendsByIdentifier(world, identifier);
    }

    public @Nullable List<PlayerIdentifier> getFriendsByIdentifier(@NotNull World world, @NotNull PlayerIdentifier identifier) {
        Player player = getPlayerByIdentifier(world, identifier);
        if (player == null) return null;

        return player.getFriends();
    }

    public @Nullable PlayerIdentifier getMayorByIdentifier(@NotNull TownIdentifier identifier) {
         return getMayorByIdentifier(world, identifier);
    }

    public @Nullable PlayerIdentifier getMayorByIdentifier(@NotNull World world, @NotNull TownIdentifier identifier) {
        Town town = getTownByIdentifier(world, identifier);
        if (town == null) return null;

        return town.getMayor();
    }

    public @Nullable NationIdentifier getNationByIdentifier(@NotNull TownIdentifier identifier) {
        return getNationByIdentifier(world, identifier);
    }

    public @Nullable NationIdentifier getNationByIdentifier(@NotNull World world, @NotNull TownIdentifier identifier) {
        Town town = getTownByIdentifier(world, identifier);
        if (town == null) return null;

        return town.getNation();
    }

    public @Nullable List<PlayerIdentifier> getResidentsByIdentifier(@NotNull TownIdentifier identifier) {
        return getResidentsByIdentifier(world, identifier);
    }

    public @Nullable List<PlayerIdentifier> getResidentsByIdentifier(@NotNull World world, TownIdentifier identifier) {
        Town town = getTownByIdentifier(world, identifier);
        if (town == null) return null;

        return town.getResidents();
    }

    public @Nullable List<PlayerIdentifier> getTrustedByIdentifier(@NotNull TownIdentifier identifier) {
        return getTrustedByIdentifier(world, identifier);
    }

    public @Nullable List<PlayerIdentifier> getTrustedByIdentifier(@NotNull World world, @NotNull TownIdentifier identifier) {
        Town town = getTownByIdentifier(world, identifier);
        if (town == null) return null;

        return town.getTrusted();
    }

    public @Nullable List<PlayerIdentifier> getOutlawsByIdentifier(@NotNull TownIdentifier identifier) {
        return getOutlawsByIdentifier(world, identifier);
    }

    public @Nullable List<PlayerIdentifier> getOutlawsByIdentifier(@NotNull World world, @NotNull TownIdentifier identifier) {
        Town town = getTownByIdentifier(world, identifier);
        if (town == null) return null;

        return town.getOutlaws();
    }

    public @Nullable List<QuarterIdentifier> getQuartersByIdentifier(@NotNull TownIdentifier identifier) {
        return getQuartersByIdentifier(world, identifier);
    }

    public @Nullable List<QuarterIdentifier> getQuartersByIdentifier(@NotNull World world, @NotNull TownIdentifier identifier) {
        Town town = getTownByIdentifier(world, identifier);
        if (town == null) return null;

        return town.getQuarters();
    }

    public @Nullable PlayerIdentifier getKingByIdentifier(@NotNull NationIdentifier identifier) {
        return getKingByIdentifier(world, identifier);
    }

    public @Nullable PlayerIdentifier getKingByIdentifier(@NotNull World world, @NotNull NationIdentifier identifier) {
        Nation nation = getNationByIdentifier(world, identifier);
        if (nation == null) return null;

        return nation.getKing();
    }

    public @Nullable TownIdentifier getCapitalByIdentifier(@NotNull NationIdentifier identifier) {
        return getCapitalByIdentifier(world, identifier);
    }

    public @Nullable TownIdentifier getCapitalByIdentifier(@NotNull World world, @NotNull NationIdentifier identifier) {
        Nation nation = getNationByIdentifier(world, identifier);
        if (nation == null) return null;

        return nation.getCapital();
    }

    public @Nullable List<PlayerIdentifier> getResidentsByIdentifier(@NotNull NationIdentifier identifier) {
        return getResidentsByIdentifier(world, identifier);
    }

    public @Nullable List<PlayerIdentifier> getResidentsByIdentifier(@NotNull World world, NationIdentifier identifier) {
        Nation nation = getNationByIdentifier(world, identifier);
        if (nation == null) return null;

        return nation.getResidents();
    }

    public @Nullable List<TownIdentifier> getTownsByIdentifier(@NotNull NationIdentifier identifier) {
        return getTownsByIdentifier(world, identifier);
    }

    public @Nullable List<TownIdentifier> getTownsByIdentifier(@NotNull World world, NationIdentifier identifier) {
        Nation nation = getNationByIdentifier(world, identifier);
        if (nation == null) return null;

        return nation.getTowns();
    }

    public @Nullable List<TownIdentifier> getSanctionedByIdentifier(@NotNull NationIdentifier identifier) {
        return getSanctionedByIdentifier(world, identifier);
    }

    public @Nullable List<TownIdentifier> getSanctionedByIdentifier(@NotNull World world, NationIdentifier identifier) {
        Nation nation = getNationByIdentifier(world, identifier);
        if (nation == null) return null;

        return nation.getSanctioned();
    }

    public @Nullable List<NationIdentifier> getAlliesByIdentifier(@NotNull NationIdentifier identifier) {
        return getAlliesByIdentifier(world, identifier);
    }

    public @Nullable List<NationIdentifier> getAlliesByIdentifier(@NotNull World world, NationIdentifier identifier) {
        Nation nation = getNationByIdentifier(world, identifier);
        if (nation == null) return null;

        return nation.getAllies();
    }

    public @Nullable List<NationIdentifier> getEnemiesByIdentifier(@NotNull NationIdentifier identifier) {
        return getEnemiesByIdentifier(world, identifier);
    }

    public @Nullable List<NationIdentifier> getEnemiesByIdentifier(@NotNull World world, NationIdentifier identifier) {
        Nation nation = getNationByIdentifier(world, identifier);
        if (nation == null) return null;

        return nation.getEnemies();
    }

    public @Nullable PlayerIdentifier getOwnerByIdentifier(@NotNull QuarterIdentifier identifier) {
        return getOwnerByIdentifier(world, identifier);
    }

    public @Nullable PlayerIdentifier getOwnerByIdentifier(@NotNull World world, @NotNull QuarterIdentifier identifier) {
        Quarter quarter = getQuarterByIdentifier(identifier);
        if (quarter == null) return null;

        return quarter.getOwner();
    }

    public @Nullable TownIdentifier getTownByIdentifier(@NotNull QuarterIdentifier identifier) {
        return getTownByIdentifier(world, identifier);
    }

    public @Nullable TownIdentifier getTownByIdentifier(@NotNull World world, @NotNull QuarterIdentifier identifier) {
        Quarter quarter = getQuarterByIdentifier(identifier);
        if (quarter == null) return null;

        return quarter.getTown();
    }

    public @Nullable List<PlayerIdentifier> getTrustedByIdentifier(@NotNull QuarterIdentifier identifier) {
        return getTrustedByIdentifier(world, identifier);
    }

    public @Nullable List<PlayerIdentifier> getTrustedByIdentifier(@NotNull World world, @NotNull QuarterIdentifier identifier) {
        Quarter quarter = getQuarterByIdentifier(identifier);
        if (quarter == null) return null;

        return quarter.getTrusted();
    }

    public @NotNull List<LocationInfo> getLocationInfoByLocations(@NotNull List<Location> query) {
        return getLocationInfoByLocations(world, query);
    }

    public @NotNull List<LocationInfo> getLocationInfoByLocations(@NotNull World world, @NotNull List<Location> query) {
        JsonArray queryArray = new JsonArray();
        for (Location entry : query) {
            JsonArray innerArray = new JsonArray();
            innerArray.add(entry.getX());
            innerArray.add(entry.getZ());
            queryArray.add(innerArray);
        }

        JsonArray response = requestManager.batchPostAsJsonArray(createWorldURI(world).resolve("location"), JSONUtil.createRequestBody(queryArray));

        List<LocationInfo> locations = new ArrayList<>();
        for (JsonElement element : response) {
            JsonObject jsonObject = element.getAsJsonObject();
            LocationInfo data = new LocationInfo(jsonObject);
            locations.add(data);
        }

        return locations;
    }

    public @NotNull List<LocationInfo> getLocationInfoByLocations(@NotNull Location... query) {
        return getLocationInfoByLocations(world, query);
    }

    public @NotNull List<LocationInfo> getLocationInfoByLocations(@NotNull World world, @NotNull Location... query) {
        return getLocationInfoByLocations(world, Arrays.stream(query).toList());
    }

    public @NotNull LocationInfo getLocationInfoByLocation(@NotNull Location query) {
        return getLocationInfoByLocation(world, query);
    }

    public @NotNull LocationInfo getLocationInfoByLocation(@NotNull World world, @NotNull Location query) {
        List<LocationInfo> data = getLocationInfoByLocations(query);
        return data.get(0);
    }
}
