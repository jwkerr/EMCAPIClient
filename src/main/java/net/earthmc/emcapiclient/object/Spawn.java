package net.earthmc.emcapiclient.object;

import com.google.gson.JsonObject;

public class Spawn {
    private final String world;
    private final double x, y, z, pitch, yaw;

    public Spawn(JsonObject jsonObject) {
        this.world = jsonObject.get("world").getAsString();

        this.x = jsonObject.get("x").getAsDouble();
        this.y = jsonObject.get("y").getAsDouble();
        this.z = jsonObject.get("z").getAsDouble();
        this.pitch = jsonObject.get("pitch").getAsDouble();
        this.yaw = jsonObject.get("yaw").getAsDouble();
    }

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

    public double getPitch() {
        return pitch;
    }

    public double getYaw() {
        return yaw;
    }
}
