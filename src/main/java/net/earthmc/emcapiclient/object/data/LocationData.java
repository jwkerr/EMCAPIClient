package net.earthmc.emcapiclient.object.data;

import com.google.gson.JsonObject;
import net.earthmc.emcapiclient.object.identifier.NationIdentifier;
import net.earthmc.emcapiclient.object.identifier.TownIdentifier;
import net.earthmc.emcapiclient.util.DataUtil;
import org.jetbrains.annotations.Nullable;

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
        String townName = DataUtil.getElementAsStringOrNull(town.get("name"));
        String townUUID = DataUtil.getElementAsStringOrNull(town.get("uuid"));
        this.town = townName != null || townUUID != null ? new TownIdentifier(townName, townUUID) : null;

        JsonObject nation = jsonObject.getAsJsonObject("nation");
        String nationName = DataUtil.getElementAsStringOrNull(nation.get("name"));
        String nationUUID = DataUtil.getElementAsStringOrNull(nation.get("uuid"));
        this.nation = nationName != null || nationUUID != null ? new NationIdentifier(nationName, nationUUID) : null;
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

    @Nullable
    public TownIdentifier getTown() {
        return town;
    }

    @Nullable
    public NationIdentifier getNation() {
        return nation;
    }
}
