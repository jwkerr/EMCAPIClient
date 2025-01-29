package au.lupine.emcapiclient.object.apiobjects;

import au.lupine.emcapiclient.object.wrapper.Location;
import au.lupine.emcapiclient.object.identifier.NationIdentifier;
import au.lupine.emcapiclient.object.identifier.TownIdentifier;
import au.lupine.emcapiclient.util.JSONUtil;
import com.google.gson.JsonObject;
import org.jetbrains.annotations.Nullable;


public class LocationInfo extends APIObject {

    private final Location location;
    private final boolean isWilderness;
    private final TownIdentifier town;
    private final NationIdentifier nation;

    public LocationInfo(JsonObject jsonObject) {
        super(jsonObject);

        JsonObject locationObject = jsonObject.get("location").getAsJsonObject();
        double x = locationObject.get("x").getAsDouble();
        double z = locationObject.get("z").getAsDouble();
        this.location = new Location(null, x, 0D, z, null, null);

        this.isWilderness = jsonObject.get("isWilderness").getAsBoolean();

        JsonObject townObject = jsonObject.get("town").getAsJsonObject();
        String townName = JSONUtil.getElementAsStringOrNull(townObject.get("name"));
        String townUUID = JSONUtil.getElementAsStringOrNull(townObject.get("uuid"));
        this.town = townName == null || townUUID == null ? null : new TownIdentifier(
                townName,
                townUUID
        );

        JsonObject nationObject = jsonObject.get("nation").getAsJsonObject();
        String nationName = JSONUtil.getElementAsStringOrNull(nationObject.get("name"));
        String nationUUID = JSONUtil.getElementAsStringOrNull(nationObject.get("uuid"));
        this.nation = nationName == null || nationUUID == null ? null : new NationIdentifier(
                nationName,
                nationUUID
        );
    }

    public Location getLocation() {
        return location;
    }

    public boolean isWilderness() {
        return isWilderness;
    }

    public @Nullable TownIdentifier getTown() {
        return town;
    }

    public @Nullable NationIdentifier getNation() {
        return nation;
    }
}
