package net.earthmc.emcapiclient.object.data;

import com.google.gson.JsonObject;

public abstract class Data {

    private final JsonObject jsonObject;

    public Data(JsonObject jsonObject) {
        this.jsonObject = jsonObject;
    }

    /**
     * Use this to access any values that may not be parsed by EMCAPIClient
     *
     * @return The {@link JsonObject} that was parsed to create this object
     */
    public JsonObject getJsonObject() {
        return jsonObject;
    }

    /**
     *
     * @param obj Any object
     * @return True if the object is an instance of Data and if the object's internal {@link JsonObject} variable is equal
     */
    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Data data)) return false;

        return this.jsonObject.equals(data.getJsonObject());
    }
}
