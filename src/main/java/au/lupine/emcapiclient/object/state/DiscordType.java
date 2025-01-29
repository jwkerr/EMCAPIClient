package au.lupine.emcapiclient.object.state;

public enum DiscordType {
    DISCORD("discord"),
    MINECRAFT("minecraft");

    private final String name;

    DiscordType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
