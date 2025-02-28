package au.lupine.emcapiclient.object.apiobject;

import au.lupine.emcapiclient.util.JSONUtil;
import com.google.gson.JsonObject;
import org.jetbrains.annotations.Nullable;

import java.util.UUID;

/**
 * Represents one (if account is not linked) or both of a Discord ID and a Minecraft UUID
 */
public class Discord extends APIObject {

    private final String id;
    private final UUID uuid;

    public Discord(JsonObject jsonObject) {
        super(jsonObject);

        this.id = JSONUtil.getElementAsStringOrNull(jsonObject.get("id"));

        String uuidString = JSONUtil.getElementAsStringOrNull(jsonObject.get("uuid"));
        this.uuid = uuidString == null ? null : UUID.fromString(uuidString);
    }

    /**
     * @return Discord account's ID
     */
    public @Nullable String getDiscord() {
        return id;
    }

    /**
     * @return Minecraft account's UUID
     */
    public @Nullable UUID getMinecraft() {
        return uuid;
    }
}
