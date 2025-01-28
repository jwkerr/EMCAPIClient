package net.earthmc.emcapiclient.object.identifier;

import java.util.UUID;

public class PlayerIdentifier extends Identifier {
    public PlayerIdentifier(String name, UUID uuid) {
        super(name, uuid);
    }

    public PlayerIdentifier(String name, String uuid) {
        super(name, uuid);
    }
}
