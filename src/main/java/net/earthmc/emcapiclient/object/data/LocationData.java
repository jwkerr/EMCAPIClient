package net.earthmc.emcapiclient.object.data;

import com.google.gson.JsonObject;
import net.earthmc.emcapiclient.object.identifier.NationIdentifier;
import net.earthmc.emcapiclient.object.identifier.TownIdentifier;
import net.earthmc.emcapiclient.util.DataUtil;
import org.jetbrains.annotations.Nullable;

@SuppressWarnings("unused")
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

    /**
     *
     * @return True if the specified location has no town
     */
    public boolean isWilderness() {
        return isWilderness;
    }

    /**
     *
     * @return The {@link TownIdentifier} of the town at the specified location or null if there is no town
     */
    @Nullable
    public TownIdentifier getTown() {
        return town;
    }

    /**
     *
     * @return The {@link NationIdentifier} of the nation at the specified location or null if there is no nation
     */
    @Nullable
    public NationIdentifier getNation() {
        return nation;
    }
}
