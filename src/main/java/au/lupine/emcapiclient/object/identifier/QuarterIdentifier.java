package au.lupine.emcapiclient.object.identifier;

import java.util.UUID;

public class QuarterIdentifier extends Identifier {

    public QuarterIdentifier(String name, UUID uuid) {
        super(name, uuid);
    }

    public QuarterIdentifier(String name, String uuid) {
        super(name, uuid);
    }
}
