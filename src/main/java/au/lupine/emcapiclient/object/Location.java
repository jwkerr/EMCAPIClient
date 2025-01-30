package au.lupine.emcapiclient.object;

import au.lupine.emcapiclient.util.JSONUtil;
import com.google.gson.JsonObject;
import org.jetbrains.annotations.Nullable;

@SuppressWarnings("unused")
public class Location {

    private final String world;
    private final double x, z;
    private final Double pitch, y, yaw;

    public Location(double x, double z) {
        this.world = null;
        this.x = x;
        this.y = null;
        this.z = z;
        this.pitch = null;
        this.yaw = null;
    }

    public Location(@Nullable String world, double x, @Nullable Double y, double z, @Nullable Double pitch, @Nullable Double yaw) {
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

    public @Nullable String getWorld() {
        return world;
    }

    public double getX() {
        return x;
    }

    public @Nullable Double getY() {
        return y;
    }

    public double getZ() {
        return z;
    }

    public @Nullable Double getPitch() {
        return pitch;
    }

    public @Nullable Double getYaw() {
        return yaw;
    }
}
