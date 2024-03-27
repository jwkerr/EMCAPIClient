package net.earthmc.emcapiclient.object;

import com.google.gson.JsonArray;

public class Cuboid {
    private final int[] pos1, pos2;

    public Cuboid(JsonArray pos1, JsonArray pos2) {
        this.pos1 = new int[]{pos1.get(0).getAsInt(), pos1.get(1).getAsInt(), pos1.get(2).getAsInt()};
        this.pos2 = new int[]{pos2.get(0).getAsInt(), pos2.get(1).getAsInt(), pos2.get(2).getAsInt()};
    }

    public int[] getPos1() {
        return pos1;
    }

    public int[] getPos2() {
        return pos2;
    }
}
