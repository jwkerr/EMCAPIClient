package au.lupine.emcapiclient.object.identifier;

import java.util.UUID;

public class TownIdentifier extends Identifier {

    public TownIdentifier(String name, UUID uuid) {
        super(name, uuid);
    }

    public TownIdentifier(String name, String uuid) {
        super(name, uuid);
    }

    public static TownIdentifier of(String name) {
        return new TownIdentifier(name, (UUID) null);
    }

    public static TownIdentifier of(UUID uuid) {
        return new TownIdentifier(null, uuid);
    }

    public static TownIdentifier of(String name, UUID uuid) {
        return new TownIdentifier(name, uuid);
    }

    public static TownIdentifier of(String name, String uuid) {
        return new TownIdentifier(name, uuid);
    }
}
