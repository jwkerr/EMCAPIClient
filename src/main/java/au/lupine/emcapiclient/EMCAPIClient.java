package au.lupine.emcapiclient;

import au.lupine.emcapiclient.manager.RequestManager;
import au.lupine.emcapiclient.object.Location;
import au.lupine.emcapiclient.object.apiobject.*;
import au.lupine.emcapiclient.object.identifier.*;
import au.lupine.emcapiclient.object.state.DiscordType;
import au.lupine.emcapiclient.object.state.World;
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
     * Create a new instance of EMCAPIClient with the default world unchanged
     */
    public EMCAPIClient() {}

    /**
     * Create a new instance of EMCAPIClient with a specific world as the default
     * @param world The default world for this EMCAPIClient instance
     */
    public EMCAPIClient(World world) {
        this.world = world;
    }

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

    public @NotNull List<PlayerIdentifier> getAllPlayerIdentifiers() {
        return getAllPlayerIdentifiers(world);
    }

    public @NotNull List<PlayerIdentifier> getAllPlayerIdentifiers(@NotNull World world) {
        return Identifier.createIdentifierList(
                requestManager.getURIAsJsonArray(
                        createWorldURI(world).resolve("players")
                ),
                PlayerIdentifier.class
        );
    }

    public @NotNull List<TownIdentifier> getAllTownIdentifiers() {
        return getAllTownIdentifiers(world);
    }

    public @NotNull List<TownIdentifier> getAllTownIdentifiers(@NotNull World world) {
        return Identifier.createIdentifierList(
                requestManager.getURIAsJsonArray(
                        createWorldURI(world).resolve("towns")
                ),
                TownIdentifier.class
        );
    }

    public @NotNull List<NationIdentifier> getAllNationIdentifiers() {
        return getAllNationIdentifiers(world);
    }

    public @NotNull List<NationIdentifier> getAllNationIdentifiers(@NotNull World world) {
        return Identifier.createIdentifierList(
                requestManager.getURIAsJsonArray(
                        createWorldURI(world).resolve("nations")
                ),
                NationIdentifier.class
        );
    }

    public @NotNull List<QuarterIdentifier> getAllQuarterIdentifiers() {
        return getAllQuarterIdentifiers(world);
    }

    public @NotNull List<QuarterIdentifier> getAllQuarterIdentifiers(@NotNull World world) {
        return Identifier.createIdentifierList(
                requestManager.getURIAsJsonArray(
                        createWorldURI(world).resolve("quarters")
                ),
                QuarterIdentifier.class
        );
    }

    public @NotNull List<Player> getAllPlayers() {
        return getAllPlayers(world);
    }

    public @NotNull List<Player> getAllPlayers(@NotNull World world) {
        return getPlayersByIdentifiers(getAllPlayerIdentifiers());
    }

    public @NotNull List<Town> getAllTowns() {
        return getAllTowns(world);
    }

    public @NotNull List<Town> getAllTowns(@NotNull World world) {
        return getTownsByIdentifiers(getAllTownIdentifiers());
    }

    public @NotNull List<Nation> getAllNations() {
        return getAllNations(world);
    }

    public @NotNull List<Nation> getAllNations(@NotNull World world) {
        return getNationsByIdentifiers(getAllNationIdentifiers());
    }

    public @NotNull List<Quarter> getAllQuarters() {
        return getAllQuarters(world);
    }

    public @NotNull List<Quarter> getAllQuarters(@NotNull World world) {
        return getQuartersByIdentifiers(getAllQuarterIdentifiers());
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
        return getPlayersByStrings(world, query.stream().map(Identifier::getUUIDOrNameAsString).toList());
    }

    public @NotNull List<Town> getTownsByIdentifiers(@NotNull List<TownIdentifier> query) {
        return getTownsByIdentifiers(world, query);
    }

    public @NotNull List<Town> getTownsByIdentifiers(@NotNull World world, @NotNull List<TownIdentifier> query) {
        return getTownsByStrings(world, query.stream().map(Identifier::getUUIDOrNameAsString).toList());
    }

    public @NotNull List<Nation> getNationsByIdentifiers(@NotNull List<NationIdentifier> query) {
        return getNationsByIdentifiers(world, query);
    }

    public @NotNull List<Nation> getNationsByIdentifiers(@NotNull World world, @NotNull List<NationIdentifier> query) {
        return getNationsByStrings(world, query.stream().map(Identifier::getUUIDOrNameAsString).toList());
    }

    public @NotNull List<Quarter> getQuartersByIdentifiers(@NotNull List<QuarterIdentifier> query) {
        return getQuartersByIdentifiers(world, query);
    }

    public @NotNull List<Quarter> getQuartersByIdentifiers(@NotNull World world, @NotNull List<QuarterIdentifier> query) {
        return getQuartersByStrings(world, query.stream().map(Identifier::getUUIDOrNameAsString).toList());
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
        return getPlayerByString(world, identifier.getUUIDOrNameAsString());
    }

    public @Nullable Town getTownByIdentifier(@NotNull TownIdentifier identifier) {
        return getTownByIdentifier(world, identifier);
    }

    public @Nullable Town getTownByIdentifier(@NotNull World world, @NotNull TownIdentifier identifier) {
        return getTownByString(world, identifier.getUUIDOrNameAsString());
    }

    public @Nullable Nation getNationByIdentifier(@NotNull NationIdentifier identifier) {
        return getNationByIdentifier(world, identifier);
    }

    public @Nullable Nation getNationByIdentifier(@NotNull World world, @NotNull NationIdentifier identifier) {
        return getNationByString(world, identifier.getUUIDOrNameAsString());
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

    public @Nullable TownIdentifier getTownIdentifierByIdentifier(@NotNull PlayerIdentifier identifier) {
        return getTownIdentifierByIdentifier(world, identifier);
    }

    public @Nullable TownIdentifier getTownIdentifierByIdentifier(@NotNull World world, @NotNull PlayerIdentifier identifier) {
        Player player = getPlayerByIdentifier(world, identifier);
        if (player == null) return null;

        return player.getTown();
    }

    public @Nullable Town getTownByIdentifier(@NotNull PlayerIdentifier identifier) {
        return getTownByIdentifier(world, identifier);
    }

    public @Nullable Town getTownByIdentifier(@NotNull World world, @NotNull PlayerIdentifier identifier) {
        TownIdentifier town = getTownIdentifierByIdentifier(world, identifier);
        if (town == null) return null;

        return getTownByIdentifier(world, town);
    }

    public @Nullable NationIdentifier getNationIdentifierByIdentifier(@NotNull PlayerIdentifier identifier) {
        return getNationIdentifierByIdentifier(world, identifier);
    }

    public @Nullable NationIdentifier getNationIdentifierByIdentifier(@NotNull World world, @NotNull PlayerIdentifier identifier) {
        Player player = getPlayerByIdentifier(world, identifier);
        if (player == null) return null;

        return player.getNation();
    }

    public @Nullable Nation getNationByIdentifier(@NotNull PlayerIdentifier identifier) {
        return getNationByIdentifier(world, identifier);
    }

    public @Nullable Nation getNationByIdentifier(@NotNull World world, @NotNull PlayerIdentifier identifier) {
        NationIdentifier nation = getNationIdentifierByIdentifier(world, identifier);
        if (nation == null) return null;

        return getNationByIdentifier(world, nation);
    }

    public @Nullable List<PlayerIdentifier> getFriendIdentifiersByIdentifier(@NotNull PlayerIdentifier identifier) {
        return getFriendIdentifiersByIdentifier(world, identifier);
    }

    public @Nullable List<PlayerIdentifier> getFriendIdentifiersByIdentifier(@NotNull World world, @NotNull PlayerIdentifier identifier) {
        Player player = getPlayerByIdentifier(world, identifier);
        if (player == null) return null;

        return player.getFriends();
    }

    public @Nullable List<Player> getFriendsByIdentifier(@NotNull PlayerIdentifier identifier) {
        return getFriendsByIdentifier(world, identifier);
    }

    public @Nullable List<Player> getFriendsByIdentifier(@NotNull World world, @NotNull PlayerIdentifier identifier) {
        List<PlayerIdentifier> friends = getFriendIdentifiersByIdentifier(world, identifier);
        if (friends == null) return null;

        return getPlayersByIdentifiers(world, friends);
    }

    public @Nullable PlayerIdentifier getMayorIdentifierByIdentifier(@NotNull TownIdentifier identifier) {
         return getMayorIdentifierByIdentifier(world, identifier);
    }

    public @Nullable PlayerIdentifier getMayorIdentifierByIdentifier(@NotNull World world, @NotNull TownIdentifier identifier) {
        Town town = getTownByIdentifier(world, identifier);
        if (town == null) return null;

        return town.getMayor();
    }

    public @Nullable Player getMayorByIdentifier(@NotNull TownIdentifier identifier) {
        return getMayorByIdentifier(world, identifier);
    }

    public @Nullable Player getMayorByIdentifier(@NotNull World world, @NotNull TownIdentifier identifier) {
        PlayerIdentifier mayor = getMayorIdentifierByIdentifier(world, identifier);
        if (mayor == null) return null;

        return getPlayerByIdentifier(world, mayor);
    }

    public @Nullable NationIdentifier getNationIdentifierByIdentifier(@NotNull TownIdentifier identifier) {
        return getNationIdentifierByIdentifier(world, identifier);
    }

    public @Nullable NationIdentifier getNationIdentifierByIdentifier(@NotNull World world, @NotNull TownIdentifier identifier) {
        Town town = getTownByIdentifier(world, identifier);
        if (town == null) return null;

        return town.getNation();
    }

    public @Nullable Nation getNationByIdentifier(@NotNull TownIdentifier identifier) {
        return getNationByIdentifier(world, identifier);
    }

    public @Nullable Nation getNationByIdentifier(@NotNull World world, @NotNull TownIdentifier identifier) {
        NationIdentifier nation = getNationIdentifierByIdentifier(world, identifier);
        if (nation == null) return null;

        return getNationByIdentifier(world, nation);
    }

    public @Nullable List<PlayerIdentifier> getResidentIdentifiersByIdentifier(@NotNull TownIdentifier identifier) {
        return getResidentIdentifiersByIdentifier(world, identifier);
    }

    public @Nullable List<PlayerIdentifier> getResidentIdentifiersByIdentifier(@NotNull World world, @NotNull TownIdentifier identifier) {
        Town town = getTownByIdentifier(world, identifier);
        if (town == null) return null;

        return town.getResidents();
    }

    public @Nullable List<Player> getResidentsByIdentifier(@NotNull TownIdentifier identifier) {
        return getResidentsByIdentifier(world, identifier);
    }

    public @Nullable List<Player> getResidentsByIdentifier(@NotNull World world, @NotNull TownIdentifier identifier) {
        List<PlayerIdentifier> residents = getResidentIdentifiersByIdentifier(world, identifier);
        if (residents == null) return null;

        return getPlayersByIdentifiers(world, residents);
    }

    public @Nullable List<PlayerIdentifier> getTrustedIdentifiersByIdentifier(@NotNull TownIdentifier identifier) {
        return getTrustedIdentifiersByIdentifier(world, identifier);
    }

    public @Nullable List<PlayerIdentifier> getTrustedIdentifiersByIdentifier(@NotNull World world, @NotNull TownIdentifier identifier) {
        Town town = getTownByIdentifier(world, identifier);
        if (town == null) return null;

        return town.getTrusted();
    }

    public @Nullable List<Player> getTrustedByIdentifier(@NotNull TownIdentifier identifier) {
        return getResidentsByIdentifier(world, identifier);
    }

    public @Nullable List<Player> getTrustedByIdentifier(@NotNull World world, @NotNull TownIdentifier identifier) {
        List<PlayerIdentifier> trusted = getTrustedIdentifiersByIdentifier(world, identifier);
        if (trusted == null) return null;

        return getPlayersByIdentifiers(world, trusted);
    }

    public @Nullable List<PlayerIdentifier> getOutlawIdentifiersByIdentifier(@NotNull TownIdentifier identifier) {
        return getOutlawIdentifiersByIdentifier(world, identifier);
    }

    public @Nullable List<PlayerIdentifier> getOutlawIdentifiersByIdentifier(@NotNull World world, @NotNull TownIdentifier identifier) {
        Town town = getTownByIdentifier(world, identifier);
        if (town == null) return null;

        return town.getOutlaws();
    }

    public @Nullable List<Player> getOutlawsByIdentifier(@NotNull TownIdentifier identifier) {
        return getOutlawsByIdentifier(world, identifier);
    }

    public @Nullable List<Player> getOutlawsByIdentifier(@NotNull World world, @NotNull TownIdentifier identifier) {
        List<PlayerIdentifier> outlaws = getOutlawIdentifiersByIdentifier(world, identifier);
        if (outlaws == null) return null;

        return getPlayersByIdentifiers(world, outlaws);
    }

    public @Nullable List<QuarterIdentifier> getQuarterIdentifiersByIdentifier(@NotNull TownIdentifier identifier) {
        return getQuarterIdentifiersByIdentifier(world, identifier);
    }

    public @Nullable List<QuarterIdentifier> getQuarterIdentifiersByIdentifier(@NotNull World world, @NotNull TownIdentifier identifier) {
        Town town = getTownByIdentifier(world, identifier);
        if (town == null) return null;

        return town.getQuarters();
    }

    public @Nullable List<Quarter> getQuartersByIdentifier(@NotNull TownIdentifier identifier) {
        return getQuartersByIdentifier(world, identifier);
    }

    public @Nullable List<Quarter> getQuartersByIdentifier(@NotNull World world, @NotNull TownIdentifier identifier) {
        List<QuarterIdentifier> quarters = getQuarterIdentifiersByIdentifier(world, identifier);
        if (quarters == null) return null;

        return getQuartersByIdentifiers(world, quarters);
    }

    public @Nullable PlayerIdentifier getKingIdentifierByIdentifier(@NotNull NationIdentifier identifier) {
        return getKingIdentifierByIdentifier(world, identifier);
    }

    public @Nullable PlayerIdentifier getKingIdentifierByIdentifier(@NotNull World world, @NotNull NationIdentifier identifier) {
        Nation nation = getNationByIdentifier(world, identifier);
        if (nation == null) return null;

        return nation.getKing();
    }

    public @Nullable Player getKingByIdentifier(@NotNull NationIdentifier identifier) {
        return getKingByIdentifier(world, identifier);
    }

    public @Nullable Player getKingByIdentifier(@NotNull World world, @NotNull NationIdentifier identifier) {
        PlayerIdentifier king = getKingIdentifierByIdentifier(world, identifier);
        if (king == null) return null;

        return getPlayerByIdentifier(world, king);
    }

    public @Nullable TownIdentifier getCapitalIdentifierByIdentifier(@NotNull NationIdentifier identifier) {
        return getCapitalIdentifierByIdentifier(world, identifier);
    }

    public @Nullable TownIdentifier getCapitalIdentifierByIdentifier(@NotNull World world, @NotNull NationIdentifier identifier) {
        Nation nation = getNationByIdentifier(world, identifier);
        if (nation == null) return null;

        return nation.getCapital();
    }

    public @Nullable Town getCapitalByIdentifier(@NotNull NationIdentifier identifier) {
        return getCapitalByIdentifier(world, identifier);
    }

    public @Nullable Town getCapitalByIdentifier(@NotNull World world, @NotNull NationIdentifier identifier) {
        TownIdentifier capital = getCapitalIdentifierByIdentifier(world, identifier);
        if (capital == null) return null;

        return getTownByIdentifier(world, capital);
    }

    public @Nullable List<PlayerIdentifier> getResidentIdentifiersByIdentifier(@NotNull NationIdentifier identifier) {
        return getResidentIdentifiersByIdentifier(world, identifier);
    }

    public @Nullable List<PlayerIdentifier> getResidentIdentifiersByIdentifier(@NotNull World world, NationIdentifier identifier) {
        Nation nation = getNationByIdentifier(world, identifier);
        if (nation == null) return null;

        return nation.getResidents();
    }

    public @Nullable List<Player> getResidentsByIdentifier(@NotNull NationIdentifier identifier) {
        return getResidentsByIdentifier(world, identifier);
    }

    public @Nullable List<Player> getResidentsByIdentifier(@NotNull World world, @NotNull NationIdentifier identifier) {
        List<PlayerIdentifier> residents = getResidentIdentifiersByIdentifier(world, identifier);
        if (residents == null) return null;

        return getPlayersByIdentifiers(world, residents);
    }

    public @Nullable List<TownIdentifier> getTownIdentifiersByIdentifier(@NotNull NationIdentifier identifier) {
        return getTownIdentifiersByIdentifier(world, identifier);
    }

    public @Nullable List<TownIdentifier> getTownIdentifiersByIdentifier(@NotNull World world, NationIdentifier identifier) {
        Nation nation = getNationByIdentifier(world, identifier);
        if (nation == null) return null;

        return nation.getTowns();
    }

    public @Nullable List<Town> getTownsByIdentifier(@NotNull NationIdentifier identifier) {
        return getTownsByIdentifier(world, identifier);
    }

    public @Nullable List<Town> getTownsByIdentifier(@NotNull World world, @NotNull NationIdentifier identifier) {
        List<TownIdentifier> towns = getTownIdentifiersByIdentifier(world, identifier);
        if (towns == null) return null;

        return getTownsByIdentifiers(world, towns);
    }

    public @Nullable List<TownIdentifier> getSanctionedIdentifiersByIdentifier(@NotNull NationIdentifier identifier) {
        return getSanctionedIdentifiersByIdentifier(world, identifier);
    }

    public @Nullable List<TownIdentifier> getSanctionedIdentifiersByIdentifier(@NotNull World world, NationIdentifier identifier) {
        Nation nation = getNationByIdentifier(world, identifier);
        if (nation == null) return null;

        return nation.getSanctioned();
    }

    public @Nullable List<Town> getSanctionedByIdentifier(@NotNull NationIdentifier identifier) {
        return getSanctionedByIdentifier(world, identifier);
    }

    public @Nullable List<Town> getSanctionedByIdentifier(@NotNull World world, @NotNull NationIdentifier identifier) {
        List<TownIdentifier> sanctioned = getSanctionedIdentifiersByIdentifier(world, identifier);
        if (sanctioned == null) return null;

        return getTownsByIdentifiers(world, sanctioned);
    }

    public @Nullable List<NationIdentifier> getAllyIdentifiersByIdentifier(@NotNull NationIdentifier identifier) {
        return getAllyIdentifiersByIdentifier(world, identifier);
    }

    public @Nullable List<NationIdentifier> getAllyIdentifiersByIdentifier(@NotNull World world, NationIdentifier identifier) {
        Nation nation = getNationByIdentifier(world, identifier);
        if (nation == null) return null;

        return nation.getAllies();
    }

    public @Nullable List<Nation> getAlliesByIdentifier(@NotNull NationIdentifier identifier) {
        return getAlliesByIdentifier(world, identifier);
    }

    public @Nullable List<Nation> getAlliesByIdentifier(@NotNull World world, @NotNull NationIdentifier identifier) {
        List<NationIdentifier> allies = getAllyIdentifiersByIdentifier(world, identifier);
        if (allies == null) return null;

        return getNationsByIdentifiers(world, allies);
    }

    public @Nullable List<NationIdentifier> getEnemyIdentifiersByIdentifier(@NotNull NationIdentifier identifier) {
        return getEnemyIdentifiersByIdentifier(world, identifier);
    }

    public @Nullable List<NationIdentifier> getEnemyIdentifiersByIdentifier(@NotNull World world, NationIdentifier identifier) {
        Nation nation = getNationByIdentifier(world, identifier);
        if (nation == null) return null;

        return nation.getEnemies();
    }

    public @Nullable List<Nation> getEnemiesByIdentifier(@NotNull NationIdentifier identifier) {
        return getEnemiesByIdentifier(world, identifier);
    }

    public @Nullable List<Nation> getEnemiesByIdentifier(@NotNull World world, @NotNull NationIdentifier identifier) {
        List<NationIdentifier> enemies = getEnemyIdentifiersByIdentifier(world, identifier);
        if (enemies == null) return null;

        return getNationsByIdentifiers(world, enemies);
    }

    public @Nullable PlayerIdentifier getOwnerIdentifierByIdentifier(@NotNull QuarterIdentifier identifier) {
        return getOwnerIdentifierByIdentifier(world, identifier);
    }

    public @Nullable PlayerIdentifier getOwnerIdentifierByIdentifier(@NotNull World world, @NotNull QuarterIdentifier identifier) {
        Quarter quarter = getQuarterByIdentifier(identifier);
        if (quarter == null) return null;

        return quarter.getOwner();
    }

    public @Nullable Player getOwnerByIdentifier(@NotNull QuarterIdentifier identifier) {
        return getOwnerByIdentifier(world, identifier);
    }

    public @Nullable Player getOwnerByIdentifier(@NotNull World world, @NotNull QuarterIdentifier identifier) {
        PlayerIdentifier owner = getOwnerIdentifierByIdentifier(world, identifier);
        if (owner == null) return null;

        return getPlayerByIdentifier(world, owner);
    }

    public @Nullable TownIdentifier getTownIdentifierByIdentifier(@NotNull QuarterIdentifier identifier) {
        return getTownIdentifierByIdentifier(world, identifier);
    }

    public @Nullable TownIdentifier getTownIdentifierByIdentifier(@NotNull World world, @NotNull QuarterIdentifier identifier) {
        Quarter quarter = getQuarterByIdentifier(identifier);
        if (quarter == null) return null;

        return quarter.getTown();
    }

    public @Nullable Town getTownByIdentifier(@NotNull QuarterIdentifier identifier) {
        return getTownByIdentifier(world, identifier);
    }

    public @Nullable Town getTownByIdentifier(@NotNull World world, @NotNull QuarterIdentifier identifier) {
        TownIdentifier town = getTownIdentifierByIdentifier(world, identifier);
        if (town == null) return null;

        return getTownByIdentifier(world, town);
    }

    public @Nullable List<PlayerIdentifier> getTrustedIdentifiersByIdentifier(@NotNull QuarterIdentifier identifier) {
        return getTrustedIdentifiersByIdentifier(world, identifier);
    }

    public @Nullable List<PlayerIdentifier> getTrustedIdentifiersByIdentifier(@NotNull World world, @NotNull QuarterIdentifier identifier) {
        Quarter quarter = getQuarterByIdentifier(identifier);
        if (quarter == null) return null;

        return quarter.getTrusted();
    }

    public @Nullable List<Player> getTrustedByIdentifier(@NotNull QuarterIdentifier identifier) {
        return getTrustedByIdentifier(world, identifier);
    }

    public @Nullable List<Player> getTrustedByIdentifier(@NotNull World world, @NotNull QuarterIdentifier identifier) {
        List<PlayerIdentifier> trusted = getTrustedIdentifiersByIdentifier(world, identifier);
        if (trusted == null) return null;

        return getPlayersByIdentifiers(world, trusted);
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
