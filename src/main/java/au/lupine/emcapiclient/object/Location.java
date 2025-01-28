package au.lupine.emcapiclient.object;

import au.lupine.emcapiclient.util.JSONUtil;
import com.google.gson.JsonObject;
import org.jetbrains.annotations.Nullable;

@SuppressWarnings("unused")
public class Location {

    private final String world;
    private final double x, y, z;
    private final Double pitch, yaw;

    public Location(@Nullable String world, double x, double y, double z, @Nullable Double pitch, @Nullable Double yaw) {
        this.world = world;
        this.x = x;
        this.y = y;
        this.z = z;
        this.pitch = pitch;
        this.yaw = y;
    }

    public Location(JsonObject jsonObject) {
        this.world = JSONUtil.getElementAsStringOrNull(jsonObject.get("world"));
        this.x = jsonObject.get("x").getAsDouble();
        this.y = jsonObject.get("y").getAsDouble();
        this.z = jsonObject.get("z").getAsDouble();
        this.pitch = JSONUtil.getElementAsDoubleOrNull(jsonObject.get("pitch"));
        this.yaw = JSONUtil.getElementAsDoubleOrNull(jsonObject.get("yaw"));
    }

    @Nullable
    public String getWorld() {
        return world;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double getZ() {
        return z;
    }

    @Nullable
    public Double getPitch() {
        return pitch;
    }

    @Nullable
    public Double getYaw() {
        return yaw;
    }
}
