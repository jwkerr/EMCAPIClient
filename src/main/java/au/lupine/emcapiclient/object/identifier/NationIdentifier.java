package au.lupine.emcapiclient.object.identifier;

import java.util.UUID;

public class NationIdentifier extends Identifier {

    public NationIdentifier(String name, UUID uuid) {
        super(name, uuid);
    }

    public NationIdentifier(String name, String uuid) {
        super(name, uuid);
    }

    public static NationIdentifier of(String name) {
        return new NationIdentifier(name, (UUID) null);
    }

    public static NationIdentifier of(UUID uuid) {
        return new NationIdentifier(null, uuid);
    }

    public static NationIdentifier of(String name, UUID uuid) {
        return new NationIdentifier(name, uuid);
    }

    public static NationIdentifier of(String name, String uuid) {
        return new NationIdentifier(name, uuid);
    }
}
