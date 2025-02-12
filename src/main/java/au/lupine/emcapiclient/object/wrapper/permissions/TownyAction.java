package au.lupine.emcapiclient.object.wrapper.permissions;

import com.google.gson.JsonArray;

@SuppressWarnings("unused")
public class TownyAction {
    
    private final boolean resident, nation, ally, outsider;

    public TownyAction(JsonArray jsonArray) {
        this.resident = jsonArray.get(0).getAsBoolean();
        this.nation = jsonArray.get(1).getAsBoolean();
        this.ally = jsonArray.get(2).getAsBoolean();
        this.outsider = jsonArray.get(3).getAsBoolean();
    }

    public boolean isResident() {
        return resident;
    }

    public boolean isNation() {
        return nation;
    }

    public boolean isAlly() {
        return ally;
    }

    public boolean isOutsider() {
        return outsider;
    }
}
