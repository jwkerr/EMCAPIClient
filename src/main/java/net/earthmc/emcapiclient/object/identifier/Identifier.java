package net.earthmc.emcapiclient.object.identifier;

public abstract class Identifier {
    private final String name;
    private final String uuid;

    public Identifier(String name, String uuid) {
        this.name = name;
        this.uuid = uuid;
    }

    public String getName() {
        return name;
    }

    public String getUUID() {
        return uuid;
    }
}
