package au.lupine.emcapiclient.object.wrapper;

/**
 * Represents an EarthMC server as in Terra Nova, Terra Aurora, etc.
 */
public class Server {

    private final String name;

    /**
     * Nova no longer exists and never had an API implementation
     */
    public static final Server NOVA = new Server("nova");
    public static final Server AURORA = new Server("aurora");

    public Server(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
