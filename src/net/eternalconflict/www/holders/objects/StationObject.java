package net.eternalconflict.www.holders.objects;

import net.eternalconflict.www.enums.ObjectTypeEnum;

public class StationObject extends PlayerOwnedObject {
    public StationObject(String OwnerID, String id, String name) {
        super(OwnerID, id, name);
        objectType = ObjectTypeEnum.STATION;
    }

    public StationObject(String OwnerID, String id, String name, double x, double z) {
        super(OwnerID, id, name, x, z);
        objectType = ObjectTypeEnum.STATION;
    }

    public StationObject(String OwnerID, String name) {
        super(OwnerID, name);
        objectType = ObjectTypeEnum.STATION;
    }

    public StationObject(String OwnerID, String name, double x, double z) {
        super(OwnerID, name, x, z);
        objectType = ObjectTypeEnum.STATION;
    }
}
