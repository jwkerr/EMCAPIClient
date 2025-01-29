package au.lupine.emcapiclient;

import au.lupine.emcapiclient.object.Location;
import au.lupine.emcapiclient.object.apiobjects.LocationInfo;
import au.lupine.emcapiclient.object.identifier.TownIdentifier;

import java.util.List;

public class Main {

    public static void main(String[] args) {
        EMCAPIClient api = new EMCAPIClient();

        List<LocationInfo> locations = api.getLocationDataByLocations(
                new Location(500, 500),
                new Location(5000, 500),
                new Location(0, 0),
                new Location(-27000, -12400)
        );

        for (LocationInfo location : locations) {
            TownIdentifier town = location.getTown();
            if (town != null) System.out.println(town.getName());
        }
    }
}
