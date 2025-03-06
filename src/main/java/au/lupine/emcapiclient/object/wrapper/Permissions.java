package au.lupine.emcapiclient.object.wrapper;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

@SuppressWarnings("unused")
public class Permissions {

    private final JsonObject jsonObject;
    private final Build buildPermissions;
    private final Destroy destroyPermissions;
    private final Switch switchPermissions;
    private final ItemUse itemUsePermissions;

    private final Flags flags;

    public Permissions(JsonObject permsObject) {
        this.jsonObject = permsObject;

        this.buildPermissions = new Build(permsObject.getAsJsonArray("build"));
        this.destroyPermissions = new Destroy(permsObject.getAsJsonArray("destroy"));
        this.switchPermissions = new Switch(permsObject.getAsJsonArray("switch"));
        this.itemUsePermissions = new ItemUse(permsObject.getAsJsonArray("itemUse"));

        this.flags = new Flags(permsObject.getAsJsonObject("flags"));
    }

    private static class TownyAction {

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

    public static class Build extends TownyAction {
        public Build(JsonArray jsonArray) {
            super(jsonArray);
        }
    }

    public static class Destroy extends TownyAction {
        public Destroy(JsonArray jsonArray) {
            super(jsonArray);
        }
    }

    public static class Switch extends TownyAction {
        public Switch(JsonArray jsonArray) {
            super(jsonArray);
        }
    }

    public static class ItemUse extends TownyAction {
        public ItemUse(JsonArray jsonArray) {
            super(jsonArray);
        }
    }

    public static class Flags {

        private final boolean pvp, explosion, fire, mobs;

        public Flags(JsonObject jsonObject) {
            this.pvp = jsonObject.get("pvp").getAsBoolean();
            this.explosion = jsonObject.get("explosion").getAsBoolean();
            this.fire = jsonObject.get("fire").getAsBoolean();
            this.mobs = jsonObject.get("mobs").getAsBoolean();
        }

        public boolean hasPvP() {
            return pvp;
        }

        public boolean hasExplosion() {
            return explosion;
        }

        public boolean hasFire() {
            return fire;
        }

        public boolean hasMobs() {
            return mobs;
        }
    }

    public JsonObject getJsonObject() {
        return jsonObject;
    }

    public Build getBuild() {
        return buildPermissions;
    }

    public Destroy getDestroy() {
        return destroyPermissions;
    }

    public Switch getSwitch() {
        return switchPermissions;
    }

    public ItemUse getItemUse() {
        return itemUsePermissions;
    }

    public Flags getFlags() {
        return flags;
    }
}
