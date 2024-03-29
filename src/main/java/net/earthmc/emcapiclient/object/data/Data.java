package net.earthmc.emcapiclient.object.data;

import com.google.gson.JsonObject;

public abstract class Data {
    private final JsonObject jsonObject;

    public Data(JsonObject jsonObject) {
        this.jsonObject = jsonObject;
    }

    public JsonObject getJsonObject() {
        return jsonObject;
    }
}
