package au.lupine.emcapiclient.object.apiobjects;

import au.lupine.emcapiclient.object.Cuboid;
import au.lupine.emcapiclient.object.identifier.Identifier;
import au.lupine.emcapiclient.object.identifier.PlayerIdentifier;
import au.lupine.emcapiclient.object.identifier.TownIdentifier;
import au.lupine.emcapiclient.util.JSONUtil;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import org.jetbrains.annotations.Nullable;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@SuppressWarnings("unused")
public class Quarter extends APIObject {

    private final String name;
    private final UUID uuid;
    private final String type;
    private final PlayerIdentifier owner;
    private final TownIdentifier town;
    private final long registered;
    private final Long claimedAt;
    private final boolean isEmbassy;
    private final Integer price;
    private final int volume, numCuboids;
    private final Color colour;
    private final List<PlayerIdentifier> trusted;
    private final List<Cuboid> cuboids;

    public Quarter(JsonObject jsonObject) {
        super(jsonObject);

        this.name = jsonObject.get("name").getAsString();
        this.uuid = UUID.fromString(jsonObject.get("uuid").getAsString());
        this.type = jsonObject.get("type").getAsString();

        JsonObject owner = jsonObject.getAsJsonObject("owner");
        String ownerName = JSONUtil.getElementAsStringOrNull(owner.get("name"));
        String ownerUUID = JSONUtil.getElementAsStringOrNull(owner.get("uuid"));
        this.owner = ownerName == null || ownerUUID == null ? null : new PlayerIdentifier(
                ownerName,
                ownerUUID
        );

        JsonObject town = jsonObject.getAsJsonObject("town");
        this.town = new TownIdentifier(
                JSONUtil.getElementAsStringOrNull(town.get("name")),
                JSONUtil.getElementAsStringOrNull(town.get("uuid"))
        );

        JsonObject timestamps = jsonObject.getAsJsonObject("timestamps");
        this.registered = timestamps.get("registered").getAsLong();
        this.claimedAt = JSONUtil.getElementsAsLongOrNull(timestamps.get("claimedAt"));

        JsonObject status = jsonObject.getAsJsonObject("status");
        this.isEmbassy = status.get("isEmbassy").getAsBoolean();

        JsonObject stats = jsonObject.getAsJsonObject("stats");
        this.price = JSONUtil.getElementAsIntegerOrNull(stats.get("price"));
        this.volume = stats.get("volume").getAsInt();
        this.numCuboids = stats.get("numCuboids").getAsInt();

        JsonArray colour = jsonObject.getAsJsonArray("colour");
        this.colour = new Color(colour.get(0).getAsInt(), colour.get(1).getAsInt(), colour.get(2).getAsInt());

        JsonArray trusted = jsonObject.getAsJsonArray("trusted");
        this.trusted = Identifier.createIdentifierList(trusted, PlayerIdentifier.class);

        List<Cuboid> cuboidsList = new ArrayList<>();
        JsonArray cuboids = jsonObject.getAsJsonArray("cuboids");
        for (JsonElement cuboidElement : cuboids) {
            JsonObject cuboid = cuboidElement.getAsJsonObject();

            cuboidsList.add(new Cuboid(cuboid.getAsJsonArray("pos1"), cuboid.getAsJsonArray("pos2")));
        }
        this.cuboids = cuboidsList;
    }

    public String getName() {
        return name;
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

    public Color getColour() {
        return colour;
    }

    public List<PlayerIdentifier> getTrusted() {
        return trusted;
    }

    public List<Cuboid> getCuboids() {
        return cuboids;
    }
}
