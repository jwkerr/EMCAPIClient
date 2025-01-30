package au.lupine.emcapiclient.object;

import com.google.gson.JsonArray;

@SuppressWarnings("unused")
public class Cuboid {

    private final Location cornerOne, cornerTwo;

    public Cuboid(JsonArray cornerOne, JsonArray cornerTwo) {
        this.cornerOne = new Location(null, cornerOne.get(0).getAsDouble(), cornerOne.get(1).getAsDouble(), cornerOne.get(2).getAsDouble(), null, null);
        this.cornerTwo = new Location(null, cornerTwo.get(0).getAsDouble(), cornerTwo.get(1).getAsDouble(), cornerTwo.get(2).getAsDouble(), null, null);
    }

    public Location getCornerOne() {
        return cornerOne;
    }

    public Location getCornerTwo() {
        return cornerTwo;
    }
}
