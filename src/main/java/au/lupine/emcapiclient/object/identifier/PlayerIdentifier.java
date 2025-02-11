package au.lupine.emcapiclient.object.identifier;

import java.util.UUID;

public class PlayerIdentifier extends Identifier {

    public PlayerIdentifier(String name, UUID uuid) {
        super(name, uuid);
    }

    public PlayerIdentifier(String name, String uuid) {
        super(name, uuid);
    }

    public static PlayerIdentifier of(String name) {
        return new PlayerIdentifier(name, (UUID) null);
    }

    public static PlayerIdentifier of(UUID uuid) {
        return new PlayerIdentifier(null, uuid);
    }

    public static PlayerIdentifier of(String name, UUID uuid) {
        return new PlayerIdentifier(name, uuid);
    }

    public static PlayerIdentifier of(String name, String uuid) {
        return new PlayerIdentifier(name, uuid);
    }
}
