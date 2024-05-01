package net.earthmc.emcapiclient.object.identifier;

import org.jetbrains.annotations.Nullable;

public abstract class Identifier {

    private final String name;
    private final String uuid;

    public Identifier(String name, String uuid) {
        this.name = name;
        this.uuid = uuid;
    }

    @Nullable
    public String getName() {
        return name;
    }

    @Nullable
    public String getUUID() {
        return uuid;
    }
}
