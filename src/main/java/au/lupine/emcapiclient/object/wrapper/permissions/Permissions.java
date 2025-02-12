package au.lupine.emcapiclient.object.wrapper.permissions;

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

    public JsonObject getJsonObject() {
        return jsonObject;
    }

    public Build getBuildPermissions() {
        return buildPermissions;
    }

    public Destroy getDestroyPermissions() {
        return destroyPermissions;
    }

    public Switch getSwitchPermissions() {
        return switchPermissions;
    }

    public ItemUse getItemUsePermissions() {
        return itemUsePermissions;
    }

    public Flags getFlags() {
        return flags;
    }
}
