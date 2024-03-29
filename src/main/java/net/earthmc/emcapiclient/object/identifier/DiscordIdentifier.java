package net.earthmc.emcapiclient.object.identifier;

import com.google.gson.JsonObject;
import net.earthmc.emcapiclient.util.DataUtils;
import org.jetbrains.annotations.Nullable;

public class DiscordIdentifier {
    private final String uuid;
    private final String id;

    public DiscordIdentifier(JsonObject jsonObject) {
        this.uuid = DataUtils.getElementAsStringOrNull(jsonObject.get("uuid"));
        this.id = DataUtils.getElementAsStringOrNull(jsonObject.get("id"));
    }

    @Nullable
    public String getUUID() {
        return uuid;
    }

    @Nullable
    public String getID() {
        return id;
    }
}
