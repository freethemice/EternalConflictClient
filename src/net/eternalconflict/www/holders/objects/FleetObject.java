package net.eternalconflict.www.holders.objects;

public class FleetObject extends PlayerOwnedObject {

    public FleetObject(String OwnerID, String id, String name) {
        super(OwnerID, id, name);
    }

    public FleetObject(String OwnerID, String id, String name, double x, double z) {
        super(OwnerID, id, name, x, z);
    }

    public FleetObject(String OwnerID, String name) {
        super(OwnerID, name);
    }

    public FleetObject(String OwnerID, String name, double x, double z) {
        super(OwnerID, name, x, z);
    }
}
