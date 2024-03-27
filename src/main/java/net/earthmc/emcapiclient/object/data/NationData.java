package net.earthmc.emcapiclient.object.data;

import com.google.gson.JsonObject;
import net.earthmc.emcapiclient.object.Spawn;
import net.earthmc.emcapiclient.object.identifier.NationIdentifier;
import net.earthmc.emcapiclient.object.identifier.PlayerIdentifier;
import net.earthmc.emcapiclient.object.identifier.TownIdentifier;
import net.earthmc.emcapiclient.util.DataUtils;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.List;

public class NationData {
    private final JsonObject jsonObject;
    private final String name, uuid, board, dynmapColour, dynmapOutline, wiki;
    private final PlayerIdentifier king;
    private final TownIdentifier capital;
    private final long registered;
    private final boolean isPublic, isOpen, isNeutral;
    private final int numTownBlocks, numResidents, numTowns, numAllies, numEnemies, balance;
    private final Spawn spawn;
    private final List<PlayerIdentifier> residents;
    private final List<TownIdentifier> towns, sanctioned;
    private final List<NationIdentifier> allies, enemies;
    private final HashMap<String, List<String>> ranks;

    public NationData(JsonObject jsonObject) {
        this.jsonObject = jsonObject;

        this.name = jsonObject.get("name").getAsString();
        this.uuid = jsonObject.get("uuid").getAsString();
        this.board = DataUtils.getElementAsStringOrNull(jsonObject.get("board"));
        this.dynmapColour = jsonObject.get("dynmapColour").getAsString();
        this.dynmapOutline = jsonObject.get("dynmapOutline").getAsString();
        this.wiki = DataUtils.getElementAsStringOrNull(jsonObject.get("wiki"));

        JsonObject king = jsonObject.getAsJsonObject("king");
        this.king = new PlayerIdentifier(DataUtils.getElementAsStringOrNull(king.get("name")), DataUtils.getElementAsStringOrNull(king.get("uuid")));

        JsonObject capital = jsonObject.getAsJsonObject("capital");
        this.capital = new TownIdentifier(DataUtils.getElementAsStringOrNull(capital.get("name")), DataUtils.getElementAsStringOrNull(capital.get("uuid")));

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
        this.spawn = spawn.get("world").isJsonNull() ? null : new Spawn(spawn);

        this.residents = DataUtils.getIdentifierList(jsonObject.getAsJsonArray("residents"), PlayerIdentifier.class);
        this.towns = DataUtils.getIdentifierList(jsonObject.getAsJsonArray("towns"), TownIdentifier.class);
        this.allies = DataUtils.getIdentifierList(jsonObject.getAsJsonArray("allies"), NationIdentifier.class);
        this.enemies = DataUtils.getIdentifierList(jsonObject.getAsJsonArray("enemies"), NationIdentifier.class);
        this.sanctioned = DataUtils.getIdentifierList(jsonObject.getAsJsonArray("sanctioned"), TownIdentifier.class);

        this.ranks = DataUtils.getRanksMap(jsonObject.getAsJsonObject("ranks"));
    }

    public JsonObject getJsonObject() {
        return jsonObject;
    }

    public String getName() {
        return name;
    }

    public String getUUID() {
        return uuid;
    }

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

    @Nullable
    public Spawn getSpawn() {
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
