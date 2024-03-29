package net.earthmc.emcapiclient.object.data;

import com.google.gson.JsonObject;
import net.earthmc.emcapiclient.object.identifier.NationIdentifier;
import net.earthmc.emcapiclient.object.identifier.TownIdentifier;
import net.earthmc.emcapiclient.util.DataUtil;

public class LocationData extends Data {
    private final int x, z;
    private final boolean isWilderness;
    private final TownIdentifier town;
    private final NationIdentifier nation;

    public LocationData(JsonObject jsonObject) {
        super(jsonObject);

        JsonObject location = jsonObject.getAsJsonObject("location");
        this.x = location.get("x").getAsInt();
        this.z = location.get("z").getAsInt();

        this.isWilderness = jsonObject.get("isWilderness").getAsBoolean();

        JsonObject town = jsonObject.getAsJsonObject("town");
        this.town = new TownIdentifier(DataUtil.getElementAsStringOrNull(town.get("name")), DataUtil.getElementAsStringOrNull(town.get("uuid")));

        JsonObject nation = jsonObject.getAsJsonObject("nation");
        this.nation = new NationIdentifier(DataUtil.getElementAsStringOrNull(nation.get("name")), DataUtil.getElementAsStringOrNull(nation.get("uuid")));
    }

    public int getX() {
        return x;
    }

    public int getZ() {
        return z;
    }

    public boolean isWilderness() {
        return isWilderness;
    }

    public TownIdentifier getTown() {
        return town;
    }

    public NationIdentifier getNation() {
        return nation;
    }
}
