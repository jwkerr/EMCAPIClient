package net.earthmc.emcapiclient.util;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import net.earthmc.emcapiclient.object.DiscordType;

import java.util.List;

public class JSONUtil {

    public static JsonObject createRequestBody(String string) {
        JsonObject bodyObject = new JsonObject();
        JsonArray queryArray = new JsonArray();

        queryArray.add(string);
        bodyObject.add("query", queryArray);

        return bodyObject;
    }

    public static JsonObject createRequestBody(List<String> strings) {
        JsonObject bodyObject = new JsonObject();
        JsonArray queryArray = new JsonArray();

        for (String string : strings) {
            queryArray.add(string);
        }

        bodyObject.add("query", queryArray);

        return bodyObject;
    }

    public static JsonObject createDiscordRequestBody(List<String> strings, DiscordType type) {
        JsonObject bodyObject = new JsonObject();
        JsonArray queryArray = new JsonArray();
        String typeString = type.toString();

        for (String string : strings) {
            JsonObject innerObject = new JsonObject();
            innerObject.addProperty("type", typeString);
            innerObject.addProperty("target", string);

            queryArray.add(innerObject);
        }

        bodyObject.add("query", queryArray);

        return bodyObject;
    }
}
