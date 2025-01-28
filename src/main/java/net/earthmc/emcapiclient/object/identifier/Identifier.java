package net.earthmc.emcapiclient.object.identifier;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public abstract class Identifier {

    private final String name;
    private final UUID uuid;

    public Identifier(String name, UUID uuid) {
        this.name = name;
        this.uuid = uuid;
    }

    public Identifier(String name, String uuid) {
        this.name = name;
        this.uuid = uuid == null ? null : UUID.fromString(uuid);
    }

    public static <T extends Identifier> @NotNull List<T> createIdentifierList(JsonArray array, Class<T> type) {
        List<T> identifiers = new ArrayList<>();

        Constructor<T> constructor;
        try {
            constructor = type.getConstructor(String.class, UUID.class);
        } catch (NoSuchMethodException ignored) {
            return identifiers;
        }

        for (JsonElement element : array) {
            JsonObject jsonObject = element.getAsJsonObject();

            try {
                T identifier = constructor.newInstance(
                        jsonObject.get("name").getAsString(),
                        UUID.fromString(jsonObject.get("uuid").getAsString())
                );

                identifiers.add(identifier);
            } catch (Exception ignored) {}
        }

        return identifiers;
    }

    public String getName() {
        return name;
    }

    public UUID getUUID() {
        return uuid;
    }
}
