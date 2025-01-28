package au.lupine.emcapiclient.object;

/**
 * Represents a discrete world on EarthMC as in Terra Nova, Terra Aurora, etc.
 * This is not to be used for in-game worlds/dimensions such as the nether
 */
public class World {

    private final String name;

    /**
     * Nova no longer exists and never had an API implementation
     */
    public static final World NOVA = new World("nova");
    public static final World AURORA = new World("aurora");

    public World(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
