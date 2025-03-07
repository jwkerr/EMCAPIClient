package au.lupine.emcapiclient.object.apiobject;

import au.lupine.emcapiclient.object.identifier.Identifier;
import au.lupine.emcapiclient.object.identifier.PlayerIdentifier;
import com.google.gson.JsonObject;

import java.util.HashMap;
import java.util.List;

public abstract class APIObject {

    private final JsonObject jsonObject;

    public APIObject(JsonObject jsonObject) {
        this.jsonObject = jsonObject;
    }

    protected HashMap<String, List<PlayerIdentifier>> createRanksMap() {
        JsonObject ranksObject = jsonObject.getAsJsonObject("ranks");
        HashMap<String, List<PlayerIdentifier>> ranksMap = new HashMap<>();

        for (String rank : ranksObject.keySet()) {
            List<PlayerIdentifier> residentsWithRank = Identifier.createIdentifierList(ranksObject.getAsJsonArray(rank), PlayerIdentifier.class);
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
     * @return True if the object is an {@link APIObject} and if the object's internal {@link JsonObject} variable is equal
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
