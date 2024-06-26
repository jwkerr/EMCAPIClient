package net.earthmc.emcapiclient.object.data;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import net.earthmc.emcapiclient.object.Cuboid;
import net.earthmc.emcapiclient.object.identifier.PlayerIdentifier;
import net.earthmc.emcapiclient.object.identifier.TownIdentifier;
import net.earthmc.emcapiclient.util.DataUtil;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@SuppressWarnings("unused")
public class QuarterData extends Data {

    private final UUID uuid;
    private final String type;
    private final PlayerIdentifier owner;
    private final TownIdentifier town;
    private final long registered;
    private final Long claimedAt;
    private final boolean isEmbassy;
    private final Integer price;
    private final int volume, numCuboids;
    private final int[] colour;
    private final List<PlayerIdentifier> trusted;
    private final List<Cuboid> cuboids;

    public QuarterData(JsonObject jsonObject) {
        super(jsonObject);

        this.uuid = UUID.fromString(jsonObject.get("uuid").getAsString());
        this.type = jsonObject.get("type").getAsString();

        JsonObject owner = jsonObject.getAsJsonObject("owner");
        String ownerName = DataUtil.getElementAsStringOrNull(owner.get("name"));
        String ownerUUID = DataUtil.getElementAsStringOrNull(owner.get("uuid"));
        this.owner = ownerName != null || ownerUUID != null ? new PlayerIdentifier(ownerName, ownerUUID) : null;

        JsonObject town = jsonObject.getAsJsonObject("town");
        this.town = new TownIdentifier(DataUtil.getElementAsStringOrNull(town.get("name")), DataUtil.getElementAsStringOrNull(town.get("uuid")));

        JsonObject timestamps = jsonObject.getAsJsonObject("timestamps");
        this.registered = timestamps.get("registered").getAsLong();
        this.claimedAt = DataUtil.getElementsAsLongOrNull(timestamps.get("claimedAt"));

        JsonObject status = jsonObject.getAsJsonObject("status");
        this.isEmbassy = status.get("isEmbassy").getAsBoolean();

        JsonObject stats = jsonObject.getAsJsonObject("stats");
        this.price = DataUtil.getElementAsIntegerOrNull(stats.get("price"));
        this.volume = stats.get("volume").getAsInt();
        this.numCuboids = stats.get("numCuboids").getAsInt();

        JsonArray colour = jsonObject.getAsJsonArray("colour");
        this.colour = new int[]{colour.get(0).getAsInt(), colour.get(1).getAsInt(), colour.get(2).getAsInt()};

        JsonArray trusted = jsonObject.getAsJsonArray("trusted");
        this.trusted = DataUtil.getIdentifierList(trusted, PlayerIdentifier.class);

        List<Cuboid> cuboidsList = new ArrayList<>();
        JsonArray cuboids = jsonObject.getAsJsonArray("cuboids");
        for (JsonElement cuboidElement : cuboids) {
            JsonObject cuboid = cuboidElement.getAsJsonObject();

            cuboidsList.add(new Cuboid(cuboid.getAsJsonArray("pos1"), cuboid.getAsJsonArray("pos2")));
        }
        this.cuboids = cuboidsList;
    }

    public UUID getUUID() {
        return uuid;
    }

    public String getType() {
        return type;
    }

    @Nullable
    public PlayerIdentifier getOwner() {
        return owner;
    }

    public TownIdentifier getTown() {
        return town;
    }

    public long getRegistered() {
        return registered;
    }

    @Nullable
    public Long getClaimedAt() {
        return claimedAt;
    }

    public boolean isEmbassy() {
        return isEmbassy;
    }

    @Nullable
    public Integer getPrice() {
        return price;
    }

    public int getVolume() {
        return volume;
    }

    public int getNumCuboids() {
        return numCuboids;
    }

    public int[] getColour() {
        return colour;
    }

    public List<PlayerIdentifier> getTrusted() {
        return trusted;
    }

    public List<Cuboid> getCuboids() {
        return cuboids;
    }
}
