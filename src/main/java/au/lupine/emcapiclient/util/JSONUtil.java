package au.lupine.emcapiclient.util;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class JSONUtil {

    public static @NotNull JsonObject createRequestBody(@NotNull JsonArray queryArray) {
        JsonObject body = new JsonObject();
        body.add("query", queryArray);
        return body;
    }

    public static @Nullable String getElementAsStringOrNull(JsonElement element) {
        if (element.isJsonNull()) return null;

        return element.getAsString();
    }

    public static @Nullable Integer getElementAsIntegerOrNull(JsonElement element) {
        if (element.isJsonNull()) return null;

        return element.getAsInt();
    }

    public static @Nullable Long getElementAsLongOrNull(JsonElement element) {
        if (element.isJsonNull()) return null;

        return element.getAsLong();
    }

    public static @Nullable Double getElementAsDoubleOrNull(JsonElement element) {
        if (element.isJsonNull()) return null;

        return element.getAsDouble();
    }
}
