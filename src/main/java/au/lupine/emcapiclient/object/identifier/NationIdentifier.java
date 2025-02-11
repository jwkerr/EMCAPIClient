package au.lupine.emcapiclient.object.identifier;

import java.util.UUID;

public class NationIdentifier extends Identifier {

    public NationIdentifier(String name, UUID uuid) {
        super(name, uuid);
    }

    public NationIdentifier(String name, String uuid) {
        super(name, uuid);
    }
}
