package net.earthmc.emcapiclient.object.permissions;

import com.google.gson.JsonObject;

public class Flags {
    private final boolean pvp, explosion, fire, mobs;

    public Flags(JsonObject jsonObject) {
        this.pvp = jsonObject.get("pvp").getAsBoolean();
        this.explosion = jsonObject.get("explosion").getAsBoolean();
        this.fire = jsonObject.get("fire").getAsBoolean();
        this.mobs = jsonObject.get("mobs").getAsBoolean();
    }

    public boolean getPvP() {
        return pvp;
    }

    public boolean getExplosion() {
        return explosion;
    }

    public boolean getFire() {
        return fire;
    }

    public boolean getMobs() {
        return mobs;
    }
}
