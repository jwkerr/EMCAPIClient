package net.earthmc.emcapiclient.object.data;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import kotlin.Pair;
import net.earthmc.emcapiclient.object.Location;
import net.earthmc.emcapiclient.object.identifier.Identifier;
import net.earthmc.emcapiclient.object.identifier.NationIdentifier;
import net.earthmc.emcapiclient.object.identifier.PlayerIdentifier;
import net.earthmc.emcapiclient.object.identifier.QuarterIdentifier;
import net.earthmc.emcapiclient.object.permissions.Permissions;
import net.earthmc.emcapiclient.util.JSONUtil;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

@SuppressWarnings("unused")
public class Town extends Data {

    private final String name, board, founder, wiki;
    private final UUID uuid;
    private final PlayerIdentifier mayor;
    private final NationIdentifier nation;
    private final long registered;
    private final Long joinedNationAt, ruinedAt;
    private final boolean isPublic, isOpen, isNeutral, isCapital, isOverClaimed, isRuined, isForSale, hasNation, hasOverclaimShield, canOutsidersSpawn;
    private final int numTownBlocks, maxTownBlocks, bonusBlocks, numResidents, numTrusted, numOutlaws, balance;
    private final Double forSalePrice;
    private final Permissions permissions;
    private final Location spawn;
    private final Pair<Integer, Integer> homeBlock;
    private final List<Pair<Integer, Integer>> townBlocks;
    private final List<PlayerIdentifier> residents, trusted, outlaws;
    private final List<QuarterIdentifier> quarters;
    private final HashMap<String, List<String>> ranks;

    public Town(JsonObject jsonObject) {
        super(jsonObject);

        this.name = jsonObject.get("name").getAsString();
        this.uuid =  UUID.fromString(jsonObject.get("uuid").getAsString());
        this.board = JSONUtil.getElementAsStringOrNull(jsonObject.get("board"));
        this.founder = jsonObject.get("founder").getAsString();
        this.wiki = JSONUtil.getElementAsStringOrNull(jsonObject.get("wiki"));

        JsonObject mayor = jsonObject.getAsJsonObject("mayor");
        this.mayor = new PlayerIdentifier(
                JSONUtil.getElementAsStringOrNull(mayor.get("name")),
                JSONUtil.getElementAsStringOrNull(mayor.get("uuid"))
        );

        JsonObject nation = jsonObject.getAsJsonObject("nation");
        String nationName = JSONUtil.getElementAsStringOrNull(nation.get("name"));
        String nationUUID = JSONUtil.getElementAsStringOrNull(nation.get("uuid"));
        this.nation = nationName == null || nationUUID == null ? null : new NationIdentifier(
                nationName,
                nationUUID
        );

        JsonObject timestamps = jsonObject.getAsJsonObject("timestamps");
        this.registered = timestamps.get("registered").getAsLong();
        this.joinedNationAt = JSONUtil.getElementsAsLongOrNull(timestamps.get("joinedNationAt"));
        this.ruinedAt = JSONUtil.getElementsAsLongOrNull(timestamps.get("ruinedAt"));

        JsonObject status = jsonObject.getAsJsonObject("status");
        this.isPublic = status.get("isPublic").getAsBoolean();
        this.isOpen = status.get("isOpen").getAsBoolean();
        this.isNeutral = status.get("isNeutral").getAsBoolean();
        this.isCapital = status.get("isCapital").getAsBoolean();
        this.isOverClaimed = status.get("isOverClaimed").getAsBoolean();
        this.isRuined = status.get("isRuined").getAsBoolean();
        this.isForSale = status.get("isForSale").getAsBoolean();
        this.hasNation = status.get("hasNation").getAsBoolean();
        this.hasOverclaimShield = status.get("hasOverclaimShield").getAsBoolean();
        this.canOutsidersSpawn = status.get("canOutsidersSpawn").getAsBoolean();

        JsonObject stats = jsonObject.getAsJsonObject("stats");
        this.numTownBlocks = stats.get("numTownBlocks").getAsInt();
        this.maxTownBlocks = stats.get("maxTownBlocks").getAsInt();
        this.bonusBlocks = stats.get("bonusBlocks").getAsInt();
        this.numResidents = stats.get("numResidents").getAsInt();
        this.numTrusted = stats.get("numTrusted").getAsInt();
        this.numOutlaws = stats.get("numOutlaws").getAsInt();
        this.balance = stats.get("balance").getAsInt();
        this.forSalePrice = JSONUtil.getElementAsDoubleOrNull(stats.get("forSalePrice"));

        this.permissions = new Permissions(jsonObject.getAsJsonObject("perms"));

        JsonObject coordinates = jsonObject.getAsJsonObject("coordinates");
        JsonObject spawn = coordinates.getAsJsonObject("spawn");
        this.spawn = spawn.get("world").isJsonNull() ? null : new Location(spawn);

        JsonArray homeBlock = coordinates.getAsJsonArray("homeBlock");
        this.homeBlock = homeBlock.get(0).isJsonNull() ? null : new Pair<>(homeBlock.get(0).getAsInt(), homeBlock.get(1).getAsInt());

        List<Pair<Integer, Integer>> townBlocksList = new ArrayList<>();
        JsonArray townBlocks = coordinates.getAsJsonArray("townBlocks");
        for (JsonElement townBlockElement : townBlocks) {
            JsonArray townBlockArray = townBlockElement.getAsJsonArray();
            townBlocksList.add(new Pair<>(townBlockArray.get(0).getAsInt(), townBlockArray.get(1).getAsInt()));
        }
        this.townBlocks = townBlocksList;

        this.residents = Identifier.createIdentifierList(jsonObject.getAsJsonArray("residents"), PlayerIdentifier.class);
        this.trusted = Identifier.createIdentifierList(jsonObject.getAsJsonArray("trusted"), PlayerIdentifier.class);
        this.outlaws = Identifier.createIdentifierList(jsonObject.getAsJsonArray("outlaws"), PlayerIdentifier.class);

        this.quarters = Identifier.createIdentifierList(jsonObject.getAsJsonArray("quarters"), QuarterIdentifier.class);

        this.ranks = getRanksMap();
    }

    public String getName() {
        return name;
    }

    public UUID getUUID() {
        return uuid;
    }

    /**
     * @return A string representing the town's board as seen on /t, null if the town has no board
     */
    @Nullable
    public String getBoard() {
        return board;
    }

    public String getFounder() {
        return founder;
    }

    /**
     * @return A string representing the town's linked wiki URL, null if the town has not set a wiki URL
     */
    @Nullable
    public String getWiki() {
        return wiki;
    }

    public PlayerIdentifier getMayor() {
        return mayor;
    }

    @Nullable
    public NationIdentifier getNation() {
        return nation;
    }

    public long getRegistered() {
        return registered;
    }

    @Nullable
    public Long getJoinedNationAt() {
        return joinedNationAt;
    }

    @Nullable
    public Long getRuinedAt() {
        return ruinedAt;
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

    public boolean isCapital() {
        return isCapital;
    }

    public boolean isOverClaimed() {
        return isOverClaimed;
    }

    public boolean isRuined() {
        return isRuined;
    }

    public boolean isForSale() {
        return isForSale;
    }

    public boolean hasNation() {
        return hasNation;
    }

    public boolean hasOverclaimShield() {
        return hasOverclaimShield;
    }

    public boolean canOutsidersSpawn() {
        return canOutsidersSpawn;
    }

    public int getNumTownBlocks() {
        return numTownBlocks;
    }

    public int getMaxTownBlocks() {
        return maxTownBlocks;
    }

    public int getBonusBlocks() {
        return bonusBlocks;
    }

    public int getNumResidents() {
        return numResidents;
    }

    public int getNumTrusted() {
        return numTrusted;
    }

    public int getNumOutlaws() {
        return numOutlaws;
    }

    public int getBalance() {
        return balance;
    }

    /**
     * @return A double representing the town's sale price, null if the town is not for sale
     * <p>
     * See {@link #isForSale()}
     */
    @Nullable
    public Double getForSalePrice() {
        return forSalePrice;
    }

    public Permissions getPermissions() {
        return permissions;
    }

    @Nullable
    public Location getSpawn() {
        return spawn;
    }

    @Nullable
    public Pair<Integer, Integer> getHomeBlock() {
        return homeBlock;
    }

    public List<Pair<Integer, Integer>> getTownBlocks() {
        return townBlocks;
    }

    public List<PlayerIdentifier> getResidents() {
        return residents;
    }

    public List<PlayerIdentifier> getTrusted() {
        return trusted;
    }

    public List<PlayerIdentifier> getOutlaws() {
        return outlaws;
    }

    public List<QuarterIdentifier> getQuarters() {
        return quarters;
    }

    public HashMap<String, List<String>> getRanks() {
        return ranks;
    }
}
