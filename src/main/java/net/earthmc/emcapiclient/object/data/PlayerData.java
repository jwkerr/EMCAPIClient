package net.earthmc.emcapiclient.object.data;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import net.earthmc.emcapiclient.object.permissions.Permissions;
import net.earthmc.emcapiclient.object.identifier.NationIdentifier;
import net.earthmc.emcapiclient.object.identifier.PlayerIdentifier;
import net.earthmc.emcapiclient.object.identifier.TownIdentifier;
import net.earthmc.emcapiclient.util.DataUtil;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.stream.Collectors;

public class PlayerData extends Data {
    private final String name, uuid, title, surname, formattedName, about;
    private final TownIdentifier town;
    private final NationIdentifier nation;
    private final long registered;
    private final Long joinedTownAt, lastOnline;
    private final boolean isOnline, isNPC, isMayor, isKing, hasTown, hasNation;
    private final int balance, numFriends;
    private final Permissions permissions;
    private final List<String> townRanks, nationRanks;
    private final List<PlayerIdentifier> friends;


    public PlayerData(JsonObject jsonObject) {
        super(jsonObject);

        this.name = jsonObject.get("name").getAsString();
        this.uuid = jsonObject.get("uuid").getAsString();
        this.title = DataUtil.getElementAsStringOrNull(jsonObject.get("title"));
        this.surname = DataUtil.getElementAsStringOrNull(jsonObject.get("surname"));
        this.formattedName = jsonObject.get("formattedName").getAsString();
        this.about = DataUtil.getElementAsStringOrNull(jsonObject.get("about"));

        JsonObject town = jsonObject.getAsJsonObject("town");
        this.town = new TownIdentifier(DataUtil.getElementAsStringOrNull(town.get("name")), DataUtil.getElementAsStringOrNull(town.get("uuid")));

        JsonObject nation = jsonObject.getAsJsonObject("nation");
        this.nation = new NationIdentifier(DataUtil.getElementAsStringOrNull(nation.get("name")), DataUtil.getElementAsStringOrNull(nation.get("uuid")));

        JsonObject timestamps = jsonObject.getAsJsonObject("timestamps");
        this.registered = timestamps.get("registered").getAsLong();
        this.joinedTownAt = DataUtil.getElementsAsLongOrNull(timestamps.get("joinedTownAt"));
        this.lastOnline = DataUtil.getElementsAsLongOrNull(timestamps.get("lastOnline"));

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
        this.friends = DataUtil.getIdentifierList(friends, PlayerIdentifier.class);
    }

    public String getName() {
        return name;
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

    @Nullable
    public String getAbout() {
        return about;
    }

    public String getUUID() {
        return uuid;
    }

    public TownIdentifier getTown() {
        return town;
    }

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
