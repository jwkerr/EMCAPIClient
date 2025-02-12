package au.lupine.emcapiclient;

import au.lupine.emcapiclient.manager.RequestManager;
import au.lupine.emcapiclient.object.Location;
import au.lupine.emcapiclient.object.apiobject.*;
import au.lupine.emcapiclient.object.identifier.*;
import au.lupine.emcapiclient.object.state.DiscordType;
import au.lupine.emcapiclient.object.wrapper.Server;
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
 * Methods with and without a {@link Server} parameter will default to the server specified by {@link #setDefaultServer(Server)} when no server is provided
 */
@SuppressWarnings("unused")
public class EMCAPIClient {

    public static final URI EARTHMC_API_URI = URI.create("https://api.earthmc.net/v3/");

    private final RequestManager requestManager = new RequestManager();
    private Server server = Server.AURORA;

    /**
     * Create a new instance of EMCAPIClient with the default server unchanged
     */
    public EMCAPIClient() {}

    /**
     * Create a new instance of EMCAPIClient with a specific server as the default
     * @param server The default ser for this EMCAPIClient instance
     */
    public EMCAPIClient(Server server) {
        this.server = server;
    }

    /**
     * Set the default server to be used in API requests when none is specified
     */
    public void setDefaultServer(Server server) {
        this.server = server;
    }

    private URI createWorldURI(Server server) {
        return EARTHMC_API_URI.resolve(server.getName() + "/");
    }

    public RequestManager getRequestManager() {
        return requestManager;
    }

    public @NotNull List<PlayerIdentifier> getAllPlayerIdentifiers() {
        return getAllPlayerIdentifiers(server);
    }

    public @NotNull List<PlayerIdentifier> getAllPlayerIdentifiers(@NotNull Server server) {
        return Identifier.createIdentifierList(
                requestManager.getURIAsJsonArray(
                        createWorldURI(server).resolve("players")
                ),
                PlayerIdentifier.class
        );
    }

    public @NotNull List<TownIdentifier> getAllTownIdentifiers() {
        return getAllTownIdentifiers(server);
    }

    public @NotNull List<TownIdentifier> getAllTownIdentifiers(@NotNull Server server) {
        return Identifier.createIdentifierList(
                requestManager.getURIAsJsonArray(
                        createWorldURI(server).resolve("towns")
                ),
                TownIdentifier.class
        );
    }

    public @NotNull List<NationIdentifier> getAllNationIdentifiers() {
        return getAllNationIdentifiers(server);
    }

    public @NotNull List<NationIdentifier> getAllNationIdentifiers(@NotNull Server server) {
        return Identifier.createIdentifierList(
                requestManager.getURIAsJsonArray(
                        createWorldURI(server).resolve("nations")
                ),
                NationIdentifier.class
        );
    }

    public @NotNull List<QuarterIdentifier> getAllQuarterIdentifiers() {
        return getAllQuarterIdentifiers(server);
    }

    public @NotNull List<QuarterIdentifier> getAllQuarterIdentifiers(@NotNull Server server) {
        return Identifier.createIdentifierList(
                requestManager.getURIAsJsonArray(
                        createWorldURI(server).resolve("quarters")
                ),
                QuarterIdentifier.class
        );
    }

    public @NotNull List<Player> getAllPlayers() {
        return getAllPlayers(server);
    }

    public @NotNull List<Player> getAllPlayers(@NotNull Server server) {
        return getPlayersByIdentifiers(getAllPlayerIdentifiers());
    }

    public @NotNull List<Town> getAllTowns() {
        return getAllTowns(server);
    }

    public @NotNull List<Town> getAllTowns(@NotNull Server server) {
        return getTownsByIdentifiers(getAllTownIdentifiers());
    }

    public @NotNull List<Nation> getAllNations() {
        return getAllNations(server);
    }

    public @NotNull List<Nation> getAllNations(@NotNull Server server) {
        return getNationsByIdentifiers(getAllNationIdentifiers());
    }

    public @NotNull List<Quarter> getAllQuarters() {
        return getAllQuarters(server);
    }

    public @NotNull List<Quarter> getAllQuarters(@NotNull Server server) {
        return getQuartersByIdentifiers(getAllQuarterIdentifiers());
    }

    public @NotNull List<Player> getPlayersByStrings(@NotNull List<String> query) {
        return getPlayersByStrings(server, query);
    }

    public @NotNull List<Player> getPlayersByStrings(@NotNull Server server, @NotNull List<String> query) {
        JsonArray queryArray = new JsonArray();
        for (String entry : query) {
            queryArray.add(entry);
        }

        JsonArray response = requestManager.batchPostAsJsonArray(createWorldURI(server).resolve("players"), JSONUtil.createRequestBody(queryArray));

        List<Player> players = new ArrayList<>();
        for (JsonElement element : response) {
            Player player = new Player(element.getAsJsonObject());
            players.add(player);
        }

        return players;
    }

    public @NotNull List<Town> getTownsByStrings(@NotNull List<String> query) {
        return getTownsByStrings(server, query);
    }

    public @NotNull List<Town> getTownsByStrings(@NotNull Server server, @NotNull List<String> query) {
        JsonArray queryArray = new JsonArray();
        for (String entry : query) {
            queryArray.add(entry);
        }

        JsonArray response = requestManager.batchPostAsJsonArray(createWorldURI(server).resolve("towns"), JSONUtil.createRequestBody(queryArray));

        List<Town> towns = new ArrayList<>();
        for (JsonElement element : response) {
            Town town = new Town(element.getAsJsonObject());
            towns.add(town);
        }

        return towns;
    }

    public @NotNull List<Nation> getNationsByStrings(@NotNull List<String> query) {
        return getNationsByStrings(server, query);
    }

    public @NotNull List<Nation> getNationsByStrings(@NotNull Server server, @NotNull List<String> query) {
        JsonArray queryArray = new JsonArray();
        for (String entry : query) {
            queryArray.add(entry);
        }

        JsonArray response = requestManager.batchPostAsJsonArray(createWorldURI(server).resolve("nations"), JSONUtil.createRequestBody(queryArray));

        List<Nation> nations = new ArrayList<>();
        for (JsonElement element : response) {
            Nation nation = new Nation(element.getAsJsonObject());
            nations.add(nation);
        }

        return nations;
    }

    public @NotNull List<Quarter> getQuartersByStrings(@NotNull List<String> query) {
        return getQuartersByStrings(server, query);
    }

    public @NotNull List<Quarter> getQuartersByStrings(@NotNull Server server, @NotNull List<String> query) {
        JsonArray queryArray = new JsonArray();
        for (String entry : query) {
            queryArray.add(entry);
        }

        JsonArray response = requestManager.batchPostAsJsonArray(createWorldURI(server).resolve("quarters"), JSONUtil.createRequestBody(queryArray));

        List<Quarter> quarters = new ArrayList<>();
        for (JsonElement element : response) {
            Quarter quarter = new Quarter(element.getAsJsonObject());
            quarters.add(quarter);
        }

        return quarters;
    }

    public @NotNull List<Discord> getDiscordsByStrings(@NotNull DiscordType type, @NotNull List<String> query) {
        return getDiscordsByStrings(server, type, query);
    }

    public @NotNull List<Discord> getDiscordsByStrings(@NotNull Server server, @NotNull DiscordType type, @NotNull List<String> query) {
        JsonArray queryArray = new JsonArray();
        for (String entry : query) {
            JsonObject innerObject = new JsonObject();
            innerObject.addProperty("type", type.getName());
            innerObject.addProperty("target", entry);
            queryArray.add(innerObject);
        }

        JsonArray response = requestManager.batchPostAsJsonArray(createWorldURI(server).resolve("discord"), JSONUtil.createRequestBody(queryArray));

        List<Discord> pairs = new ArrayList<>();
        for (JsonElement element : response) {
            JsonObject jsonObject = element.getAsJsonObject();
            Discord pair = new Discord(jsonObject);

            pairs.add(pair);
        }

        return pairs;
    }

    public @NotNull List<Player> getPlayersByUUIDs(@NotNull List<UUID> query) {
        return getPlayersByUUIDs(server, query);
    }

    public @NotNull List<Player> getPlayersByUUIDs(@NotNull Server server, @NotNull List<UUID> query) {
        return getPlayersByStrings(server, query.stream().map(UUID::toString).toList());
    }

    public @NotNull List<Town> getTownsByUUIDs(@NotNull List<UUID> query) {
        return getTownsByUUIDs(server, query);
    }

    public @NotNull List<Town> getTownsByUUIDs(@NotNull Server server, @NotNull List<UUID> query) {
        return getTownsByStrings(server, query.stream().map(UUID::toString).toList());
    }

    public @NotNull List<Nation> getNationsByUUIDs(@NotNull List<UUID> query) {
        return getNationsByUUIDs(server, query);
    }

    public @NotNull List<Nation> getNationsByUUIDs(@NotNull Server server, @NotNull List<UUID> query) {
        return getNationsByStrings(server, query.stream().map(UUID::toString).toList());
    }

    public @NotNull List<Quarter> getQuartersByUUIDs(@NotNull List<UUID> query) {
        return getQuartersByUUIDs(server, query);
    }

    public @NotNull List<Quarter> getQuartersByUUIDs(@NotNull Server server, @NotNull List<UUID> query) {
        return getQuartersByStrings(server, query.stream().map(UUID::toString).toList());
    }

    public @NotNull List<Discord> getDiscordsByUUIDs(@NotNull List<UUID> query) {
        return getDiscordsByUUIDs(server, query);
    }

    public @NotNull List<Discord> getDiscordsByUUIDs(@NotNull Server server, @NotNull List<UUID> query) {
        return getDiscordsByStrings(server, DiscordType.MINECRAFT, query.stream().map(UUID::toString).toList());
    }

    public @NotNull List<Player> getPlayersByStrings(@NotNull String... query) {
        return getPlayersByStrings(server, query);
    }

    public @NotNull List<Player> getPlayersByStrings(@NotNull Server server, @NotNull String... query) {
        return getPlayersByStrings(server, Arrays.stream(query).toList());
    }

    public @NotNull List<Town> getTownsByStrings(@NotNull String... query) {
        return getTownsByStrings(server, query);
    }

    public @NotNull List<Town> getTownsByStrings(@NotNull Server server, @NotNull String... query) {
        return getTownsByStrings(server, Arrays.stream(query).toList());
    }

    public @NotNull List<Nation> getNationsByStrings(@NotNull String... query) {
        return getNationsByStrings(server, query);
    }

    public @NotNull List<Nation> getNationsByStrings(@NotNull Server server, @NotNull String... query) {
        return getNationsByStrings(server, Arrays.stream(query).toList());
    }

    public @NotNull List<Quarter> getQuartersByStrings(@NotNull String... query) {
        return getQuartersByStrings(server, query);
    }

    public @NotNull List<Quarter> getQuartersByStrings(@NotNull Server server, @NotNull String... query) {
        return getQuartersByStrings(server, Arrays.stream(query).toList());
    }

    public @NotNull List<Discord> getDiscordsByStrings(@NotNull DiscordType type, @NotNull String... query) {
        return getDiscordsByStrings(server, type, query);
    }

    public @NotNull List<Discord> getDiscordsByStrings(@NotNull Server server, @NotNull DiscordType type, @NotNull String... query) {
        return getDiscordsByStrings(server, type, Arrays.stream(query).toList());
    }

    public @NotNull List<Player> getPlayersByIdentifiers(@NotNull List<PlayerIdentifier> query) {
        return getPlayersByIdentifiers(server, query);
    }

    public @NotNull List<Player> getPlayersByIdentifiers(@NotNull Server server, @NotNull List<PlayerIdentifier> query) {
        return getPlayersByStrings(server, query.stream().map(Identifier::getUUIDOrNameAsString).toList());
    }

    public @NotNull List<Town> getTownsByIdentifiers(@NotNull List<TownIdentifier> query) {
        return getTownsByIdentifiers(server, query);
    }

    public @NotNull List<Town> getTownsByIdentifiers(@NotNull Server server, @NotNull List<TownIdentifier> query) {
        return getTownsByStrings(server, query.stream().map(Identifier::getUUIDOrNameAsString).toList());
    }

    public @NotNull List<Nation> getNationsByIdentifiers(@NotNull List<NationIdentifier> query) {
        return getNationsByIdentifiers(server, query);
    }

    public @NotNull List<Nation> getNationsByIdentifiers(@NotNull Server server, @NotNull List<NationIdentifier> query) {
        return getNationsByStrings(server, query.stream().map(Identifier::getUUIDOrNameAsString).toList());
    }

    public @NotNull List<Quarter> getQuartersByIdentifiers(@NotNull List<QuarterIdentifier> query) {
        return getQuartersByIdentifiers(server, query);
    }

    public @NotNull List<Quarter> getQuartersByIdentifiers(@NotNull Server server, @NotNull List<QuarterIdentifier> query) {
        return getQuartersByStrings(server, query.stream().map(Identifier::getUUIDOrNameAsString).toList());
    }

    public @NotNull List<Discord> getDiscordsByIdentifiers(@NotNull List<PlayerIdentifier> query) {
        return getDiscordsByIdentifiers(server, query);
    }

    public @NotNull List<Discord> getDiscordsByIdentifiers(@NotNull Server server, @NotNull List<PlayerIdentifier> query) {
        return getDiscordsByUUIDs(server, query.stream().map(Identifier::getUUID).toList());
    }

    public @NotNull ServerInfo getServer() {
        return getServer(server);
    }

    public @NotNull ServerInfo getServer(@NotNull Server server) {
        JsonObject response = requestManager.getURIAsJsonObject(createWorldURI(server));
        return new ServerInfo(response);
    }

    public @Nullable Player getPlayerByString(@NotNull String query) {
        return getPlayerByString(server, query);
    }

    public @Nullable Player getPlayerByString(@NotNull Server server, @NotNull String query) {
        List<Player> players = getPlayersByStrings(server, query);
        return players.isEmpty() ? null : players.get(0);
    }

    public @Nullable Town getTownByString(@NotNull String query) {
        return getTownByString(server, query);
    }

    public @Nullable Town getTownByString(@NotNull Server server, @NotNull String query) {
        List<Town> towns = getTownsByStrings(server, query);
        return towns.isEmpty() ? null : towns.get(0);
    }

    public @Nullable Nation getNationByString(@NotNull String query) {
        return getNationByString(server, query);
    }

    public @Nullable Nation getNationByString(@NotNull Server server, @NotNull String query) {
        List<Nation> nations = getNationsByStrings(server, query);
        return nations.isEmpty() ? null : nations.get(0);
    }

    public @Nullable Quarter getQuarterByString(@NotNull String query) {
        return getQuarterByString(server, query);
    }

    public @Nullable Quarter getQuarterByString(@NotNull Server server, @NotNull String query) {
        List<Quarter> quarters = getQuartersByStrings(server, query);
        return quarters.isEmpty() ? null : quarters.get(0);
    }

    public @NotNull Discord getDiscordByString(@NotNull DiscordType type, @NotNull String query) {
        return getDiscordByString(server, type, query);
    }

    public @NotNull Discord getDiscordByString(@NotNull Server server, @NotNull DiscordType type, @NotNull String query) {
        List<Discord> identifiers = getDiscordsByStrings(server, type, query);
        return identifiers.get(0);
    }

    public @Nullable Player getPlayerByUUID(@NotNull UUID query) {
        return getPlayerByUUID(server, query);
    }

    public @Nullable Player getPlayerByUUID(@NotNull Server server, @NotNull UUID query) {
        return getPlayerByString(server, query.toString());
    }

    public @Nullable Town getTownByUUID(@NotNull UUID query) {
        return getTownByUUID(server, query);
    }

    public @Nullable Town getTownByUUID(@NotNull Server server, @NotNull UUID query) {
        return getTownByString(server, query.toString());
    }

    public @Nullable Nation getNationByUUID(@NotNull UUID query) {
        return getNationByUUID(server, query);
    }

    public @Nullable Nation getNationByUUID(@NotNull Server server, @NotNull UUID query) {
        return getNationByString(server, query.toString());
    }

    public @Nullable Quarter getQuarterByUUID(@NotNull UUID query) {
        return getQuarterByUUID(server, query);
    }

    public @Nullable Quarter getQuarterByUUID(@NotNull Server server, @NotNull UUID query) {
        return getQuarterByString(server, query.toString());
    }

    public @Nullable Discord getDiscordByUUID(@NotNull UUID query) {
        return getDiscordByUUID(server, query);
    }

    public @Nullable Discord getDiscordByUUID(@NotNull Server server, @NotNull UUID query) {
        return getDiscordByString(server, DiscordType.MINECRAFT, query.toString());
    }

    public @Nullable Player getPlayerByIdentifier(@NotNull PlayerIdentifier identifier) {
        return getPlayerByIdentifier(server, identifier);
    }

    public @Nullable Player getPlayerByIdentifier(@NotNull Server server, @NotNull PlayerIdentifier identifier) {
        return getPlayerByString(server, identifier.getUUIDOrNameAsString());
    }

    public @Nullable Town getTownByIdentifier(@NotNull TownIdentifier identifier) {
        return getTownByIdentifier(server, identifier);
    }

    public @Nullable Town getTownByIdentifier(@NotNull Server server, @NotNull TownIdentifier identifier) {
        return getTownByString(server, identifier.getUUIDOrNameAsString());
    }

    public @Nullable Nation getNationByIdentifier(@NotNull NationIdentifier identifier) {
        return getNationByIdentifier(server, identifier);
    }

    public @Nullable Nation getNationByIdentifier(@NotNull Server server, @NotNull NationIdentifier identifier) {
        return getNationByString(server, identifier.getUUIDOrNameAsString());
    }

    public @Nullable Quarter getQuarterByIdentifier(@NotNull QuarterIdentifier identifier) {
        return getQuarterByIdentifier(server, identifier);
    }

    public @Nullable Quarter getQuarterByIdentifier(@NotNull Server server, @NotNull QuarterIdentifier identifier) {
        return getQuarterByUUID(server, identifier.getUUID());
    }

    public @Nullable Discord getDiscordByIdentifier(@NotNull PlayerIdentifier identifier) {
        return getDiscordByIdentifier(server, identifier);
    }

    public @Nullable Discord getDiscordByIdentifier(@NotNull Server server, @NotNull PlayerIdentifier identifier) {
        return getDiscordByUUID(server, identifier.getUUID());
    }

    public @Nullable TownIdentifier getTownIdentifierByIdentifier(@NotNull PlayerIdentifier identifier) {
        return getTownIdentifierByIdentifier(server, identifier);
    }

    public @Nullable TownIdentifier getTownIdentifierByIdentifier(@NotNull Server server, @NotNull PlayerIdentifier identifier) {
        Player player = getPlayerByIdentifier(server, identifier);
        if (player == null) return null;

        return player.getTown();
    }

    public @Nullable Town getTownByIdentifier(@NotNull PlayerIdentifier identifier) {
        return getTownByIdentifier(server, identifier);
    }

    public @Nullable Town getTownByIdentifier(@NotNull Server server, @NotNull PlayerIdentifier identifier) {
        TownIdentifier town = getTownIdentifierByIdentifier(server, identifier);
        if (town == null) return null;

        return getTownByIdentifier(server, town);
    }

    public @Nullable NationIdentifier getNationIdentifierByIdentifier(@NotNull PlayerIdentifier identifier) {
        return getNationIdentifierByIdentifier(server, identifier);
    }

    public @Nullable NationIdentifier getNationIdentifierByIdentifier(@NotNull Server server, @NotNull PlayerIdentifier identifier) {
        Player player = getPlayerByIdentifier(server, identifier);
        if (player == null) return null;

        return player.getNation();
    }

    public @Nullable Nation getNationByIdentifier(@NotNull PlayerIdentifier identifier) {
        return getNationByIdentifier(server, identifier);
    }

    public @Nullable Nation getNationByIdentifier(@NotNull Server server, @NotNull PlayerIdentifier identifier) {
        NationIdentifier nation = getNationIdentifierByIdentifier(server, identifier);
        if (nation == null) return null;

        return getNationByIdentifier(server, nation);
    }

    public @Nullable List<PlayerIdentifier> getFriendIdentifiersByIdentifier(@NotNull PlayerIdentifier identifier) {
        return getFriendIdentifiersByIdentifier(server, identifier);
    }

    public @Nullable List<PlayerIdentifier> getFriendIdentifiersByIdentifier(@NotNull Server server, @NotNull PlayerIdentifier identifier) {
        Player player = getPlayerByIdentifier(server, identifier);
        if (player == null) return null;

        return player.getFriends();
    }

    public @Nullable List<Player> getFriendsByIdentifier(@NotNull PlayerIdentifier identifier) {
        return getFriendsByIdentifier(server, identifier);
    }

    public @Nullable List<Player> getFriendsByIdentifier(@NotNull Server server, @NotNull PlayerIdentifier identifier) {
        List<PlayerIdentifier> friends = getFriendIdentifiersByIdentifier(server, identifier);
        if (friends == null) return null;

        return getPlayersByIdentifiers(server, friends);
    }

    public @Nullable PlayerIdentifier getMayorIdentifierByIdentifier(@NotNull TownIdentifier identifier) {
         return getMayorIdentifierByIdentifier(server, identifier);
    }

    public @Nullable PlayerIdentifier getMayorIdentifierByIdentifier(@NotNull Server server, @NotNull TownIdentifier identifier) {
        Town town = getTownByIdentifier(server, identifier);
        if (town == null) return null;

        return town.getMayor();
    }

    public @Nullable Player getMayorByIdentifier(@NotNull TownIdentifier identifier) {
        return getMayorByIdentifier(server, identifier);
    }

    public @Nullable Player getMayorByIdentifier(@NotNull Server server, @NotNull TownIdentifier identifier) {
        PlayerIdentifier mayor = getMayorIdentifierByIdentifier(server, identifier);
        if (mayor == null) return null;

        return getPlayerByIdentifier(server, mayor);
    }

    public @Nullable NationIdentifier getNationIdentifierByIdentifier(@NotNull TownIdentifier identifier) {
        return getNationIdentifierByIdentifier(server, identifier);
    }

    public @Nullable NationIdentifier getNationIdentifierByIdentifier(@NotNull Server server, @NotNull TownIdentifier identifier) {
        Town town = getTownByIdentifier(server, identifier);
        if (town == null) return null;

        return town.getNation();
    }

    public @Nullable Nation getNationByIdentifier(@NotNull TownIdentifier identifier) {
        return getNationByIdentifier(server, identifier);
    }

    public @Nullable Nation getNationByIdentifier(@NotNull Server server, @NotNull TownIdentifier identifier) {
        NationIdentifier nation = getNationIdentifierByIdentifier(server, identifier);
        if (nation == null) return null;

        return getNationByIdentifier(server, nation);
    }

    public @Nullable List<PlayerIdentifier> getResidentIdentifiersByIdentifier(@NotNull TownIdentifier identifier) {
        return getResidentIdentifiersByIdentifier(server, identifier);
    }

    public @Nullable List<PlayerIdentifier> getResidentIdentifiersByIdentifier(@NotNull Server server, @NotNull TownIdentifier identifier) {
        Town town = getTownByIdentifier(server, identifier);
        if (town == null) return null;

        return town.getResidents();
    }

    public @Nullable List<Player> getResidentsByIdentifier(@NotNull TownIdentifier identifier) {
        return getResidentsByIdentifier(server, identifier);
    }

    public @Nullable List<Player> getResidentsByIdentifier(@NotNull Server server, @NotNull TownIdentifier identifier) {
        List<PlayerIdentifier> residents = getResidentIdentifiersByIdentifier(server, identifier);
        if (residents == null) return null;

        return getPlayersByIdentifiers(server, residents);
    }

    public @Nullable List<PlayerIdentifier> getTrustedIdentifiersByIdentifier(@NotNull TownIdentifier identifier) {
        return getTrustedIdentifiersByIdentifier(server, identifier);
    }

    public @Nullable List<PlayerIdentifier> getTrustedIdentifiersByIdentifier(@NotNull Server server, @NotNull TownIdentifier identifier) {
        Town town = getTownByIdentifier(server, identifier);
        if (town == null) return null;

        return town.getTrusted();
    }

    public @Nullable List<Player> getTrustedByIdentifier(@NotNull TownIdentifier identifier) {
        return getResidentsByIdentifier(server, identifier);
    }

    public @Nullable List<Player> getTrustedByIdentifier(@NotNull Server server, @NotNull TownIdentifier identifier) {
        List<PlayerIdentifier> trusted = getTrustedIdentifiersByIdentifier(server, identifier);
        if (trusted == null) return null;

        return getPlayersByIdentifiers(server, trusted);
    }

    public @Nullable List<PlayerIdentifier> getOutlawIdentifiersByIdentifier(@NotNull TownIdentifier identifier) {
        return getOutlawIdentifiersByIdentifier(server, identifier);
    }

    public @Nullable List<PlayerIdentifier> getOutlawIdentifiersByIdentifier(@NotNull Server server, @NotNull TownIdentifier identifier) {
        Town town = getTownByIdentifier(server, identifier);
        if (town == null) return null;

        return town.getOutlaws();
    }

    public @Nullable List<Player> getOutlawsByIdentifier(@NotNull TownIdentifier identifier) {
        return getOutlawsByIdentifier(server, identifier);
    }

    public @Nullable List<Player> getOutlawsByIdentifier(@NotNull Server server, @NotNull TownIdentifier identifier) {
        List<PlayerIdentifier> outlaws = getOutlawIdentifiersByIdentifier(server, identifier);
        if (outlaws == null) return null;

        return getPlayersByIdentifiers(server, outlaws);
    }

    public @Nullable List<QuarterIdentifier> getQuarterIdentifiersByIdentifier(@NotNull TownIdentifier identifier) {
        return getQuarterIdentifiersByIdentifier(server, identifier);
    }

    public @Nullable List<QuarterIdentifier> getQuarterIdentifiersByIdentifier(@NotNull Server server, @NotNull TownIdentifier identifier) {
        Town town = getTownByIdentifier(server, identifier);
        if (town == null) return null;

        return town.getQuarters();
    }

    public @Nullable List<Quarter> getQuartersByIdentifier(@NotNull TownIdentifier identifier) {
        return getQuartersByIdentifier(server, identifier);
    }

    public @Nullable List<Quarter> getQuartersByIdentifier(@NotNull Server server, @NotNull TownIdentifier identifier) {
        List<QuarterIdentifier> quarters = getQuarterIdentifiersByIdentifier(server, identifier);
        if (quarters == null) return null;

        return getQuartersByIdentifiers(server, quarters);
    }

    public @Nullable PlayerIdentifier getKingIdentifierByIdentifier(@NotNull NationIdentifier identifier) {
        return getKingIdentifierByIdentifier(server, identifier);
    }

    public @Nullable PlayerIdentifier getKingIdentifierByIdentifier(@NotNull Server server, @NotNull NationIdentifier identifier) {
        Nation nation = getNationByIdentifier(server, identifier);
        if (nation == null) return null;

        return nation.getKing();
    }

    public @Nullable Player getKingByIdentifier(@NotNull NationIdentifier identifier) {
        return getKingByIdentifier(server, identifier);
    }

    public @Nullable Player getKingByIdentifier(@NotNull Server server, @NotNull NationIdentifier identifier) {
        PlayerIdentifier king = getKingIdentifierByIdentifier(server, identifier);
        if (king == null) return null;

        return getPlayerByIdentifier(server, king);
    }

    public @Nullable TownIdentifier getCapitalIdentifierByIdentifier(@NotNull NationIdentifier identifier) {
        return getCapitalIdentifierByIdentifier(server, identifier);
    }

    public @Nullable TownIdentifier getCapitalIdentifierByIdentifier(@NotNull Server server, @NotNull NationIdentifier identifier) {
        Nation nation = getNationByIdentifier(server, identifier);
        if (nation == null) return null;

        return nation.getCapital();
    }

    public @Nullable Town getCapitalByIdentifier(@NotNull NationIdentifier identifier) {
        return getCapitalByIdentifier(server, identifier);
    }

    public @Nullable Town getCapitalByIdentifier(@NotNull Server server, @NotNull NationIdentifier identifier) {
        TownIdentifier capital = getCapitalIdentifierByIdentifier(server, identifier);
        if (capital == null) return null;

        return getTownByIdentifier(server, capital);
    }

    public @Nullable List<PlayerIdentifier> getResidentIdentifiersByIdentifier(@NotNull NationIdentifier identifier) {
        return getResidentIdentifiersByIdentifier(server, identifier);
    }

    public @Nullable List<PlayerIdentifier> getResidentIdentifiersByIdentifier(@NotNull Server server, NationIdentifier identifier) {
        Nation nation = getNationByIdentifier(server, identifier);
        if (nation == null) return null;

        return nation.getResidents();
    }

    public @Nullable List<Player> getResidentsByIdentifier(@NotNull NationIdentifier identifier) {
        return getResidentsByIdentifier(server, identifier);
    }

    public @Nullable List<Player> getResidentsByIdentifier(@NotNull Server server, @NotNull NationIdentifier identifier) {
        List<PlayerIdentifier> residents = getResidentIdentifiersByIdentifier(server, identifier);
        if (residents == null) return null;

        return getPlayersByIdentifiers(server, residents);
    }

    public @Nullable List<TownIdentifier> getTownIdentifiersByIdentifier(@NotNull NationIdentifier identifier) {
        return getTownIdentifiersByIdentifier(server, identifier);
    }

    public @Nullable List<TownIdentifier> getTownIdentifiersByIdentifier(@NotNull Server server, NationIdentifier identifier) {
        Nation nation = getNationByIdentifier(server, identifier);
        if (nation == null) return null;

        return nation.getTowns();
    }

    public @Nullable List<Town> getTownsByIdentifier(@NotNull NationIdentifier identifier) {
        return getTownsByIdentifier(server, identifier);
    }

    public @Nullable List<Town> getTownsByIdentifier(@NotNull Server server, @NotNull NationIdentifier identifier) {
        List<TownIdentifier> towns = getTownIdentifiersByIdentifier(server, identifier);
        if (towns == null) return null;

        return getTownsByIdentifiers(server, towns);
    }

    public @Nullable List<TownIdentifier> getSanctionedIdentifiersByIdentifier(@NotNull NationIdentifier identifier) {
        return getSanctionedIdentifiersByIdentifier(server, identifier);
    }

    public @Nullable List<TownIdentifier> getSanctionedIdentifiersByIdentifier(@NotNull Server server, NationIdentifier identifier) {
        Nation nation = getNationByIdentifier(server, identifier);
        if (nation == null) return null;

        return nation.getSanctioned();
    }

    public @Nullable List<Town> getSanctionedByIdentifier(@NotNull NationIdentifier identifier) {
        return getSanctionedByIdentifier(server, identifier);
    }

    public @Nullable List<Town> getSanctionedByIdentifier(@NotNull Server server, @NotNull NationIdentifier identifier) {
        List<TownIdentifier> sanctioned = getSanctionedIdentifiersByIdentifier(server, identifier);
        if (sanctioned == null) return null;

        return getTownsByIdentifiers(server, sanctioned);
    }

    public @Nullable List<NationIdentifier> getAllyIdentifiersByIdentifier(@NotNull NationIdentifier identifier) {
        return getAllyIdentifiersByIdentifier(server, identifier);
    }

    public @Nullable List<NationIdentifier> getAllyIdentifiersByIdentifier(@NotNull Server server, NationIdentifier identifier) {
        Nation nation = getNationByIdentifier(server, identifier);
        if (nation == null) return null;

        return nation.getAllies();
    }

    public @Nullable List<Nation> getAlliesByIdentifier(@NotNull NationIdentifier identifier) {
        return getAlliesByIdentifier(server, identifier);
    }

    public @Nullable List<Nation> getAlliesByIdentifier(@NotNull Server server, @NotNull NationIdentifier identifier) {
        List<NationIdentifier> allies = getAllyIdentifiersByIdentifier(server, identifier);
        if (allies == null) return null;

        return getNationsByIdentifiers(server, allies);
    }

    public @Nullable List<NationIdentifier> getEnemyIdentifiersByIdentifier(@NotNull NationIdentifier identifier) {
        return getEnemyIdentifiersByIdentifier(server, identifier);
    }

    public @Nullable List<NationIdentifier> getEnemyIdentifiersByIdentifier(@NotNull Server server, NationIdentifier identifier) {
        Nation nation = getNationByIdentifier(server, identifier);
        if (nation == null) return null;

        return nation.getEnemies();
    }

    public @Nullable List<Nation> getEnemiesByIdentifier(@NotNull NationIdentifier identifier) {
        return getEnemiesByIdentifier(server, identifier);
    }

    public @Nullable List<Nation> getEnemiesByIdentifier(@NotNull Server server, @NotNull NationIdentifier identifier) {
        List<NationIdentifier> enemies = getEnemyIdentifiersByIdentifier(server, identifier);
        if (enemies == null) return null;

        return getNationsByIdentifiers(server, enemies);
    }

    public @Nullable PlayerIdentifier getOwnerIdentifierByIdentifier(@NotNull QuarterIdentifier identifier) {
        return getOwnerIdentifierByIdentifier(server, identifier);
    }

    public @Nullable PlayerIdentifier getOwnerIdentifierByIdentifier(@NotNull Server server, @NotNull QuarterIdentifier identifier) {
        Quarter quarter = getQuarterByIdentifier(identifier);
        if (quarter == null) return null;

        return quarter.getOwner();
    }

    public @Nullable Player getOwnerByIdentifier(@NotNull QuarterIdentifier identifier) {
        return getOwnerByIdentifier(server, identifier);
    }

    public @Nullable Player getOwnerByIdentifier(@NotNull Server server, @NotNull QuarterIdentifier identifier) {
        PlayerIdentifier owner = getOwnerIdentifierByIdentifier(server, identifier);
        if (owner == null) return null;

        return getPlayerByIdentifier(server, owner);
    }

    public @Nullable TownIdentifier getTownIdentifierByIdentifier(@NotNull QuarterIdentifier identifier) {
        return getTownIdentifierByIdentifier(server, identifier);
    }

    public @Nullable TownIdentifier getTownIdentifierByIdentifier(@NotNull Server server, @NotNull QuarterIdentifier identifier) {
        Quarter quarter = getQuarterByIdentifier(identifier);
        if (quarter == null) return null;

        return quarter.getTown();
    }

    public @Nullable Town getTownByIdentifier(@NotNull QuarterIdentifier identifier) {
        return getTownByIdentifier(server, identifier);
    }

    public @Nullable Town getTownByIdentifier(@NotNull Server server, @NotNull QuarterIdentifier identifier) {
        TownIdentifier town = getTownIdentifierByIdentifier(server, identifier);
        if (town == null) return null;

        return getTownByIdentifier(server, town);
    }

    public @Nullable List<PlayerIdentifier> getTrustedIdentifiersByIdentifier(@NotNull QuarterIdentifier identifier) {
        return getTrustedIdentifiersByIdentifier(server, identifier);
    }

    public @Nullable List<PlayerIdentifier> getTrustedIdentifiersByIdentifier(@NotNull Server server, @NotNull QuarterIdentifier identifier) {
        Quarter quarter = getQuarterByIdentifier(identifier);
        if (quarter == null) return null;

        return quarter.getTrusted();
    }

    public @Nullable List<Player> getTrustedByIdentifier(@NotNull QuarterIdentifier identifier) {
        return getTrustedByIdentifier(server, identifier);
    }

    public @Nullable List<Player> getTrustedByIdentifier(@NotNull Server server, @NotNull QuarterIdentifier identifier) {
        List<PlayerIdentifier> trusted = getTrustedIdentifiersByIdentifier(server, identifier);
        if (trusted == null) return null;

        return getPlayersByIdentifiers(server, trusted);
    }

    public @NotNull List<LocationInfo> getLocationInfoByLocations(@NotNull List<Location> query) {
        return getLocationInfoByLocations(server, query);
    }

    public @NotNull List<LocationInfo> getLocationInfoByLocations(@NotNull Server server, @NotNull List<Location> query) {
        JsonArray queryArray = new JsonArray();
        for (Location entry : query) {
            JsonArray innerArray = new JsonArray();
            innerArray.add(entry.getX());
            innerArray.add(entry.getZ());
            queryArray.add(innerArray);
        }

        JsonArray response = requestManager.batchPostAsJsonArray(createWorldURI(server).resolve("location"), JSONUtil.createRequestBody(queryArray));

        List<LocationInfo> locations = new ArrayList<>();
        for (JsonElement element : response) {
            JsonObject jsonObject = element.getAsJsonObject();
            LocationInfo data = new LocationInfo(jsonObject);
            locations.add(data);
        }

        return locations;
    }

    public @NotNull List<LocationInfo> getLocationInfoByLocations(@NotNull Location... query) {
        return getLocationInfoByLocations(server, query);
    }

    public @NotNull List<LocationInfo> getLocationInfoByLocations(@NotNull Server server, @NotNull Location... query) {
        return getLocationInfoByLocations(server, Arrays.stream(query).toList());
    }

    public @NotNull LocationInfo getLocationInfoByLocation(@NotNull Location query) {
        return getLocationInfoByLocation(server, query);
    }

    public @NotNull LocationInfo getLocationInfoByLocation(@NotNull Server server, @NotNull Location query) {
        List<LocationInfo> data = getLocationInfoByLocations(query);
        return data.get(0);
    }
}
