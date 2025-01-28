package net.earthmc.emcapiclient.object.data;

import com.google.gson.JsonObject;
import net.earthmc.emcapiclient.object.Location;
import net.earthmc.emcapiclient.object.identifier.Identifier;
import net.earthmc.emcapiclient.object.identifier.NationIdentifier;
import net.earthmc.emcapiclient.object.identifier.PlayerIdentifier;
import net.earthmc.emcapiclient.object.identifier.TownIdentifier;
import net.earthmc.emcapiclient.util.JSONUtil;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.List;
import java.util.UUID;

@SuppressWarnings("unused")
public class Nation extends Data {

    private final String name, board, dynmapColour, dynmapOutline, wiki;
    private final UUID uuid;
    private final PlayerIdentifier king;
    private final TownIdentifier capital;
    private final long registered;
    private final boolean isPublic, isOpen, isNeutral;
    private final int numTownBlocks, numResidents, numTowns, numAllies, numEnemies, balance;
    private final Location spawn;
    private final List<PlayerIdentifier> residents;
    private final List<TownIdentifier> towns, sanctioned;
    private final List<NationIdentifier> allies, enemies;
    private final HashMap<String, List<String>> ranks;

    public Nation(JsonObject jsonObject) {
        super(jsonObject);

        this.name = jsonObject.get("name").getAsString();
        this.uuid = UUID.fromString(jsonObject.get("uuid").getAsString());
        this.board = JSONUtil.getElementAsStringOrNull(jsonObject.get("board"));
        this.dynmapColour = jsonObject.get("dynmapColour").getAsString();
        this.dynmapOutline = jsonObject.get("dynmapOutline").getAsString();
        this.wiki = JSONUtil.getElementAsStringOrNull(jsonObject.get("wiki"));

        JsonObject king = jsonObject.getAsJsonObject("king");
        this.king = new PlayerIdentifier(
                JSONUtil.getElementAsStringOrNull(jsonObject.get("name")),
                JSONUtil.getElementAsStringOrNull(jsonObject.get("uuid"))
        );

        JsonObject capital = jsonObject.getAsJsonObject("capital");
        this.capital = new TownIdentifier(
                JSONUtil.getElementAsStringOrNull(capital.get("name")),
                JSONUtil.getElementAsStringOrNull(capital.get("uuid"))
        );

        JsonObject timestamps = jsonObject.getAsJsonObject("timestamps");
        this.registered = timestamps.get("registered").getAsLong();

        JsonObject status = jsonObject.getAsJsonObject("status");
        this.isPublic = status.get("isPublic").getAsBoolean();
        this.isOpen = status.get("isOpen").getAsBoolean();
        this.isNeutral = status.get("isNeutral").getAsBoolean();

        JsonObject stats = jsonObject.getAsJsonObject("stats");
        this.numTownBlocks = stats.get("numTownBlocks").getAsInt();
        this.numResidents = stats.get("numResidents").getAsInt();
        this.numTowns = stats.get("numTowns").getAsInt();
        this.numAllies = stats.get("numAllies").getAsInt();
        this.numEnemies = stats.get("numEnemies").getAsInt();
        this.balance = stats.get("balance").getAsInt();

        JsonObject coordinates = jsonObject.getAsJsonObject("coordinates");
        JsonObject spawn = coordinates.getAsJsonObject("spawn");
        this.spawn = spawn.get("world").isJsonNull() ? null : new Location(spawn);

        this.residents = Identifier.createIdentifierList(jsonObject.getAsJsonArray("residents"), PlayerIdentifier.class);
        this.towns = Identifier.createIdentifierList(jsonObject.getAsJsonArray("towns"), TownIdentifier.class);
        this.allies = Identifier.createIdentifierList(jsonObject.getAsJsonArray("allies"), NationIdentifier.class);
        this.enemies = Identifier.createIdentifierList(jsonObject.getAsJsonArray("enemies"), NationIdentifier.class);
        this.sanctioned = Identifier.createIdentifierList(jsonObject.getAsJsonArray("sanctioned"), TownIdentifier.class);

        this.ranks = getRanksMap();
    }

    public String getName() {
        return name;
    }

    public UUID getUUID() {
        return uuid;
    }

    /**
     * @return A string representing the nation's board as seen on /n, null if the nation has no board
     */
    @Nullable
    public String getBoard() {
        return board;
    }

    public String getDynmapColour() {
        return dynmapColour;
    }

    public String getDynmapOutline() {
        return dynmapOutline;
    }

    /**
     * @return A string representing the nation's linked wiki URL, null if the nation has not set a wiki URL
     */
    @Nullable
    public String getWiki() {
        return wiki;
    }

    public PlayerIdentifier getKing() {
        return king;
    }

    public TownIdentifier getCapital() {
        return capital;
    }

    public long getRegistered() {
        return registered;
    }

    public boolean isPublic() {
        return isPublic;
    }

    public boolean isOpen() {
        return isOpen;
    }

    public boolean isNeutral() {
        return isNeutral;
    }

    public int getNumTownBlocks() {
        return numTownBlocks;
    }

    public int getNumResidents() {
        return numResidents;
    }

    public int getNumTowns() {
        return numTowns;
    }

    public int getNumAllies() {
        return numAllies;
    }

    public int getNumEnemies() {
        return numEnemies;
    }

    public int getBalance() {
        return balance;
    }

    /**
     * @return An object representing the values of where the nation's spawn is set, null if there is no spawn set
     */
    @Nullable
    public Location getSpawn() {
        return spawn;
    }

    public List<PlayerIdentifier> getResidents() {
        return residents;
    }

    public List<TownIdentifier> getTowns() {
        return towns;
    }

    public List<NationIdentifier> getAllies() {
        return allies;
    }

    public List<NationIdentifier> getEnemies() {
        return enemies;
    }

    public List<TownIdentifier> getSanctioned() {
        return sanctioned;
    }

    public HashMap<String, List<String>> getRanks() {
        return ranks;
    }
}
