package net.earthmc.emcapiclient.util;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import net.earthmc.emcapiclient.object.identifier.Identifier;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DataUtil {

    public static <T extends Identifier> List<T> getIdentifierList(JsonArray jsonArray, Class<T> identifierClass) {
        List<T> identifiers = new ArrayList<>();
        for (JsonElement element : jsonArray) {
            JsonObject jsonObject = element.getAsJsonObject();
            String name = DataUtil.getElementAsStringOrNull(jsonObject.get("name"));
            String uuid = DataUtil.getElementAsStringOrNull(jsonObject.get("uuid"));

            try {
                T identifier = identifierClass.getConstructor(String.class, String.class).newInstance(name, uuid);
                identifiers.add(identifier);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return identifiers;
    }

    public static HashMap<String, List<String>> getRanksMap(JsonObject jsonObject) {
        HashMap<String, List<String>> ranksMap = new HashMap<>();
        for (String rank : jsonObject.keySet()) {
            List<String> residentsWithRank = new ArrayList<>();
            JsonArray jsonArray = jsonObject.getAsJsonArray(rank);
            for (JsonElement element : jsonArray) {
                residentsWithRank.add(element.getAsString());
            }

            ranksMap.put(rank, residentsWithRank);
        }

        return ranksMap;
    }

    public static String getElementAsStringOrNull(JsonElement element) {
        if (element.isJsonNull()) return null;

        return element.getAsString();
    }

    public static Integer getElementAsIntegerOrNull(JsonElement element) {
        if (element.isJsonNull()) return null;

        return element.getAsInt();
    }

    public static Long getElementsAsLongOrNull(JsonElement element) {
        if (element.isJsonNull()) return null;

        return element.getAsLong();
    }

    public static Double getElementAsDoubleOrNull(JsonElement element) {
        if (element.isJsonNull()) return null;

        return element.getAsDouble();
    }
}
