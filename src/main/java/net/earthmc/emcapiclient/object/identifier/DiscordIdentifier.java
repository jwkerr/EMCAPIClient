package net.earthmc.emcapiclient.object.identifier;

import com.google.gson.JsonObject;
import net.earthmc.emcapiclient.util.DataUtil;
import org.jetbrains.annotations.Nullable;

@SuppressWarnings("unused")
public class DiscordIdentifier {

    private final String uuid;
    private final String id;

    public DiscordIdentifier(JsonObject jsonObject) {
        this.uuid = DataUtil.getElementAsStringOrNull(jsonObject.get("uuid"));
        this.id = DataUtil.getElementAsStringOrNull(jsonObject.get("id"));
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
