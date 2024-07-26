package net.earthmc.emcapiclient;

import net.earthmc.emcapiclient.object.data.TownData;

import java.util.List;

public class Main {

    public static void main(String[] args) {
        EMCAPIClient api = new EMCAPIClient();

        long startTime = System.currentTimeMillis();
        List<TownData> townData = api.getAllTownData();
        long endTime = System.currentTimeMillis();

        long totalTimeSeconds = (endTime - startTime) / 1000;

        System.out.println("Requested " + townData.size() + " towns' data in " + totalTimeSeconds + " seconds");
    }
}
