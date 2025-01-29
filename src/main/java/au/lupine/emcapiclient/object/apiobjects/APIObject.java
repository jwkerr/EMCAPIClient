package au.lupine.emcapiclient.object.apiobjects;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public abstract class APIObject {

    private final JsonObject jsonObject;

    public APIObject(JsonObject jsonObject) {
        this.jsonObject = jsonObject;
    }

    public HashMap<String, List<String>> getRanksMap() {
        JsonObject ranksObject = jsonObject.getAsJsonObject("ranks");
        HashMap<String, List<String>> ranksMap = new HashMap<>();

        for (String rank : ranksObject.keySet()) {
            List<String> residentsWithRank = new ArrayList<>();

            JsonArray jsonArray = ranksObject.getAsJsonArray(rank);
            for (JsonElement element : jsonArray) {
                residentsWithRank.add(element.getAsString());
            }

            ranksMap.put(rank, residentsWithRank);
        }

        return ranksMap;
    }

    /**
     * Use this to access any values that may not be parsed by EMCAPIClient
     * @return The {@link JsonObject} that was parsed to create this object
     */
    public JsonObject getJsonObject() {
        return jsonObject;
    }

    /**
     * @param obj Any object
     * @return True if the object is an instance of Data and if the object's internal {@link JsonObject} variable is equal
     */
    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof APIObject apiObject)) return false;

        return this.jsonObject.equals(apiObject.getJsonObject());
    }

    @Override
    public String toString() {
        return jsonObject.toString();
    }
}
