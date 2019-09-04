package net.eternalconflict.www.holders.objects;

import net.eternalconflict.www.enums.ObjectTypeEnum;
import net.eternalconflict.www.holders.CoordinatesHolder;

public class MiningShipObject extends PlayerOwnedObject {

    public MiningShipObject(String OwnerID, String id, String name) {
        super(OwnerID, id, name);
        objectType = ObjectTypeEnum.STATION;
    }

    public MiningShipObject(String OwnerID, String id, String name, double x, double z) {
        super(OwnerID, id, name, x, z);
        objectType = ObjectTypeEnum.STATION;
    }

    public MiningShipObject(String OwnerID, String name) {
        super(OwnerID, name);
        objectType = ObjectTypeEnum.STATION;
    }

    public MiningShipObject(String OwnerID, String name, double x, double z) {
        super(OwnerID, name, x, z);
        objectType = ObjectTypeEnum.STATION;
    }
    public MiningShipObject(String OwnerID, String id, String name, CoordinatesHolder coordinatesHolder) {
        super(OwnerID, id, name, coordinatesHolder);
        objectType = ObjectTypeEnum.STATION;
    }
    @Override
    public void setupHud()
    {
        super.setupHud("textures/planets/hud/planet_ring_green.png");
    }
}
