package au.lupine.emcapiclient.util;

import com.google.gson.JsonElement;
import org.jetbrains.annotations.Nullable;

public class JSONUtil {

    @Nullable
    public static String getElementAsStringOrNull(JsonElement element) {
        if (element.isJsonNull()) return null;

        return element.getAsString();
    }

    @Nullable
    public static Integer getElementAsIntegerOrNull(JsonElement element) {
        if (element.isJsonNull()) return null;

        return element.getAsInt();
    }

    @Nullable
    public static Long getElementsAsLongOrNull(JsonElement element) {
        if (element.isJsonNull()) return null;

        return element.getAsLong();
    }

    @Nullable
    public static Double getElementAsDoubleOrNull(JsonElement element) {
        if (element.isJsonNull()) return null;

        return element.getAsDouble();
    }
}
