package au.lupine.emcapiclient.object;

import org.jetbrains.annotations.Nullable;

import java.util.UUID;

public class DiscordPair {

    private final String id;
    private final UUID uuid;

    public DiscordPair(@Nullable String id, @Nullable UUID uuid) {
        this.id = id;
        this.uuid = uuid;
    }

    public DiscordPair(@Nullable String id, @Nullable String uuid) {
        this.id = id;
        this.uuid = uuid == null ? null : UUID.fromString(uuid);
    }

    public @Nullable String getID() {
        return id;
    }

    public @Nullable UUID getUUID() {
        return uuid;
    }
}
