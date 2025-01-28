package net.earthmc.emcapiclient.object.data;

import com.google.gson.JsonObject;

@SuppressWarnings("unused")
public class Server extends Data {

    private final String version, moonPhase;
    private final long newDayTime, serverTimeOfDay, time, fullTime;
    private final boolean hasStorm, isThundering;
    private final int maxPlayers, numOnlinePlayers, numOnlineNomads, numResidents, numNomads, numTowns, numTownBlocks, numNations, numQuarters, numCuboids, votePartyTarget, numVotesRemaining;

    public Server(JsonObject jsonObject) {
        super(jsonObject);

        this.version = jsonObject.get("version").getAsString();
        this.moonPhase = jsonObject.get("moonPhase").getAsString();

        JsonObject timestamps = jsonObject.getAsJsonObject("timestamps");
        this.newDayTime = timestamps.get("newDayTime").getAsLong();
        this.serverTimeOfDay = timestamps.get("serverTimeOfDay").getAsLong();

        JsonObject status = jsonObject.getAsJsonObject("status");
        this.hasStorm = status.get("hasStorm").getAsBoolean();
        this.isThundering = status.get("isThundering").getAsBoolean();

        JsonObject stats = jsonObject.getAsJsonObject("stats");
        this.time = stats.get("time").getAsLong();
        this.fullTime = stats.get("fullTime").getAsLong();
        this.maxPlayers = stats.get("maxPlayers").getAsInt();
        this.numOnlinePlayers = stats.get("numOnlinePlayers").getAsInt();
        this.numOnlineNomads = stats.get("numOnlineNomads").getAsInt();
        this.numResidents = stats.get("numResidents").getAsInt();
        this.numNomads = stats.get("numNomads").getAsInt();
        this.numTowns = stats.get("numTowns").getAsInt();
        this.numTownBlocks = stats.get("numTownBlocks").getAsInt();
        this.numNations = stats.get("numNations").getAsInt();
        this.numQuarters = stats.get("numQuarters").getAsInt();
        this.numCuboids = stats.get("numCuboids").getAsInt();

        JsonObject voteParty = jsonObject.getAsJsonObject("voteParty");
        this.votePartyTarget = voteParty.get("target").getAsInt();
        this.numVotesRemaining = voteParty.get("numRemaining").getAsInt();
    }

    public String getVersion() {
        return version;
    }

    public String getMoonPhase() {
        return moonPhase;
    }

    public long getNewDayTime() {
        return newDayTime;
    }

    public long getServerTimeOfDay() {
        return serverTimeOfDay;
    }

    public boolean hasStorm() {
        return hasStorm;
    }

    public boolean isThundering() {
        return isThundering;
    }

    public long getTime() {
        return time;
    }

    public long getFullTime() {
        return fullTime;
    }

    public int getMaxPlayers() {
        return maxPlayers;
    }

    public int getNumOnlinePlayers() {
        return numOnlinePlayers;
    }

    public int getNumOnlineNomads() {
        return numOnlineNomads;
    }

    public int getNumResidents() {
        return numResidents;
    }

    public int getNumNomads() {
        return numNomads;
    }

    public int getNumTowns() {
        return numTowns;
    }

    public int getNumTownBlocks() {
        return numTownBlocks;
    }

    public int getNumNations() {
        return numNations;
    }

    public int getNumQuarters() {
        return numQuarters;
    }

    public int getNumCuboids() {
        return numCuboids;
    }

    public int getVotePartyTarget() {
        return votePartyTarget;
    }

    public int getNumVotesRemaining() {
        return numVotesRemaining;
    }
}
