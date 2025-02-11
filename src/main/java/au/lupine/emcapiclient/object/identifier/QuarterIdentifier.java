package au.lupine.emcapiclient.object.identifier;

import java.util.UUID;

public class QuarterIdentifier extends Identifier {

    public QuarterIdentifier(String name, UUID uuid) {
        super(name, uuid);
    }

    public QuarterIdentifier(String name, String uuid) {
        super(name, uuid);
    }

    public static QuarterIdentifier of(String name) {
        return new QuarterIdentifier(name, (UUID) null);
    }

    public static QuarterIdentifier of(UUID uuid) {
        return new QuarterIdentifier(null, uuid);
    }

    public static QuarterIdentifier of(String name, UUID uuid) {
        return new QuarterIdentifier(name, uuid);
    }

    public static QuarterIdentifier of(String name, String uuid) {
        return new QuarterIdentifier(name, uuid);
    }
}
