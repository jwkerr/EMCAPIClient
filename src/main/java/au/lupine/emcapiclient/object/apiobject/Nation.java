package au.lupine.emcapiclient.object.apiobject;

import au.lupine.emcapiclient.object.Location;
import au.lupine.emcapiclient.object.identifier.Identifier;
import au.lupine.emcapiclient.object.identifier.NationIdentifier;
import au.lupine.emcapiclient.object.identifier.PlayerIdentifier;
import au.lupine.emcapiclient.object.identifier.TownIdentifier;
import au.lupine.emcapiclient.util.JSONUtil;
import com.google.gson.JsonObject;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.List;
import java.util.UUID;

@SuppressWarnings("unused")
public class Nation extends APIObject {

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
    private final HashMap<String, List<PlayerIdentifier>> ranks;

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
                JSONUtil.getElementAsStringOrNull(king.get("name")),
                JSONUtil.getElementAsStringOrNull(king.get("uuid"))
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

        this.ranks = createRanksMap();
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
    public @Nullable String getBoard() {
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
    public @Nullable String getWiki() {
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
    public @Nullable Location getSpawn() {
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

    public HashMap<String, List<PlayerIdentifier>> getRanks() {
        return ranks;
    }
}
