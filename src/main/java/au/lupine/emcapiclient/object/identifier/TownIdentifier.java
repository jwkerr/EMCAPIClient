package au.lupine.emcapiclient.object.identifier;

import java.util.UUID;

public class TownIdentifier extends Identifier {

    public TownIdentifier(String name, UUID uuid) {
        super(name, uuid);
    }

    public TownIdentifier(String name, String uuid) {
        super(name, uuid);
    }
}
