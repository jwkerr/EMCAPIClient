package au.lupine.emcapiclient.object.data;

import au.lupine.emcapiclient.object.identifier.Identifier;
import au.lupine.emcapiclient.object.identifier.NationIdentifier;
import au.lupine.emcapiclient.object.identifier.PlayerIdentifier;
import au.lupine.emcapiclient.object.identifier.TownIdentifier;
import au.lupine.emcapiclient.object.permissions.Permissions;
import au.lupine.emcapiclient.util.JSONUtil;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@SuppressWarnings("unused")
public class Player extends APIObject {

    private final String name, title, surname, formattedName, about;
    private final UUID uuid;
    private final TownIdentifier town;
    private final NationIdentifier nation;
    private final long registered;
    private final Long joinedTownAt, lastOnline;
    private final boolean isOnline, isNPC, isMayor, isKing, hasTown, hasNation;
    private final int balance, numFriends;
    private final Permissions permissions;
    private final List<String> townRanks, nationRanks;
    private final List<PlayerIdentifier> friends;


    public Player(JsonObject jsonObject) {
        super(jsonObject);

        this.name = jsonObject.get("name").getAsString();
        this.uuid = UUID.fromString(jsonObject.get("uuid").getAsString());
        this.title = JSONUtil.getElementAsStringOrNull(jsonObject.get("title"));
        this.surname = JSONUtil.getElementAsStringOrNull(jsonObject.get("surname"));
        this.formattedName = jsonObject.get("formattedName").getAsString();
        this.about = JSONUtil.getElementAsStringOrNull(jsonObject.get("about"));

        JsonObject town = jsonObject.getAsJsonObject("town");
        String townName = JSONUtil.getElementAsStringOrNull(town.get("name"));
        String townUUID = JSONUtil.getElementAsStringOrNull(town.get("uuid"));
        this.town = townName == null || townUUID == null ? null : new TownIdentifier(
                townName,
                townUUID
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
        this.joinedTownAt = JSONUtil.getElementsAsLongOrNull(timestamps.get("joinedTownAt"));
        this.lastOnline = JSONUtil.getElementsAsLongOrNull(timestamps.get("lastOnline"));

        JsonObject status = jsonObject.getAsJsonObject("status");
        this.isOnline = status.get("isOnline").getAsBoolean();
        this.isNPC = status.get("isNPC").getAsBoolean();
        this.isMayor = status.get("isMayor").getAsBoolean();
        this.isKing = status.get("isKing").getAsBoolean();
        this.hasTown = status.get("hasTown").getAsBoolean();
        this.hasNation = status.get("hasNation").getAsBoolean();

        JsonObject stats = jsonObject.getAsJsonObject("stats");
        this.balance = stats.get("balance").getAsInt();
        this.numFriends = stats.get("numFriends").getAsInt();

        this.permissions = new Permissions(jsonObject.getAsJsonObject("perms"));

        JsonObject ranks = jsonObject.getAsJsonObject("ranks");
        this.townRanks = ranks.get("townRanks").getAsJsonArray().asList().stream().map(JsonElement::getAsString).collect(Collectors.toList());
        this.nationRanks = ranks.get("nationRanks").getAsJsonArray().asList().stream().map(JsonElement::getAsString).collect(Collectors.toList());

        JsonArray friends = jsonObject.getAsJsonArray("friends");
        this.friends = Identifier.createIdentifierList(friends, PlayerIdentifier.class);
    }

    public String getName() {
        return name;
    }

    public UUID getUUID() {
        return uuid;
    }

    @Nullable
    public String getTitle() {
        return title;
    }

    @Nullable
    public String getSurname() {
        return surname;
    }

    public String getFormattedName() {
        return formattedName;
    }

    /**
     * @return A string representing the player's about as seen on /res, null if the player has no about
     */
    @Nullable
    public String getAbout() {
        return about;
    }

    /**
     * @return A {@link Town} representing the player's town or null if they have no town
     */
    @Nullable
    public TownIdentifier getTown() {
        return town;
    }

    /**
     * @return A {@link Nation} representing the player's nation or null if they have no nation
     */
    @Nullable
    public NationIdentifier getNation() {
        return nation;
    }

    public long getRegistered() {
        return registered;
    }

    @Nullable
    public Long getJoinedTownAt() {
        return joinedTownAt;
    }

    @Nullable
    public Long getLastOnline() {
        return lastOnline;
    }

    public boolean isOnline() {
        return isOnline;
    }

    public boolean isNPC() {
        return isNPC;
    }

    public boolean isMayor() {
        return isMayor;
    }

    public boolean isKing() {
        return isKing;
    }

    public boolean hasTown() {
        return hasTown;
    }

    public boolean hasNation() {
        return hasNation;
    }

    public int getBalance() {
        return balance;
    }

    public int getNumFriends() {
        return numFriends;
    }

    public Permissions getPermissions() {
        return permissions;
    }

    public List<String> getTownRanks() {
        return townRanks;
    }

    public List<String> getNationRanks() {
        return nationRanks;
    }

    public List<PlayerIdentifier> getFriends() {
        return friends;
    }
}
