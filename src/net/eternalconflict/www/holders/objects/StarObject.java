package net.eternalconflict.www.holders.objects;

import net.eternalconflict.www.enums.ObjectTypeEnum;
import net.eternalconflict.www.enums.subtypes.StarTypeEnum;
import net.eternalconflict.www.holders.CoordinatesHolder;

public class StarObject extends DefaultObject{


    private float size = -1; // size is radius
    private StarTypeEnum starType;

    public StarObject(String name) {
        super(name);
        objectType = ObjectTypeEnum.STAR;
    }
    public StarObject(String name, double x, double z) {
        super(name, x, z);
        objectType = ObjectTypeEnum.STAR;
    }
    public StarObject(String name, CoordinatesHolder coordinatesHolder) {
        super(name, coordinatesHolder);
        objectType = ObjectTypeEnum.STAR;
    }
    public StarObject(String id, String name) {
        super(id, name);
        objectType = ObjectTypeEnum.STAR;
    }
    public StarObject(String id, String name, double x, double z) {
        super(id, name, x, z);
        objectType = ObjectTypeEnum.STAR;
    }
    public StarObject(String id, String name, CoordinatesHolder coordinatesHolder) {
        super(id, name, coordinatesHolder);
        objectType = ObjectTypeEnum.STAR;
    }

    public void setSize(double size) {
        this.size = (float) size;
    }

    @Override
    public void setupHud()
    {
        super.setupHud("textures/planets/hud/planet_ring_purple.png");
    }

    public double getSize() {
        return size;
    }

    public StarTypeEnum getStarType() {
        return starType;
    }

    public void setStarType(StarTypeEnum starType) {
        this.starType = starType;
    }
}
