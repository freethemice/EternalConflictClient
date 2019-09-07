package net.eternalconflict.www.holders.objects;

import net.eternalconflict.www.enums.ObjectTypeEnum;
import net.eternalconflict.www.enums.subtypes.PlanetTypeEnum;
import net.eternalconflict.www.holders.CoordinatesHolder;

public class PlanetObject extends DefaultObject {

    private PlanetTypeEnum planetType;

    public PlanetObject(String name) {
        super(name);
        objectType = ObjectTypeEnum.PLANET;
    }
    public PlanetObject(String name, double x, double z) {
        super(name, x, z);
        objectType = ObjectTypeEnum.PLANET;
    }
    public PlanetObject(String name, CoordinatesHolder coordinatesHolder) {
        super(name, coordinatesHolder);
        objectType = ObjectTypeEnum.PLANET;
    }
    public PlanetObject(String id, String name) {
        super(id, name);
        objectType = ObjectTypeEnum.PLANET;
    }
    public PlanetObject(String id, String name, double x, double z) {
        super(id, name, x, z);
        objectType = ObjectTypeEnum.PLANET;
    }
    public PlanetObject(String id, String name, CoordinatesHolder coordinatesHolder) {
        super(id, name, coordinatesHolder);
        objectType = ObjectTypeEnum.PLANET;
    }
    @Override
    public void setupHud()
    {
        super.setupHud("textures/icons/spot.png", 4, 4);
    }

    public PlanetTypeEnum getPlanetType() {
        return planetType;
    }

    public void setPlanetType(PlanetTypeEnum planetType) {
        this.planetType = planetType;
    }
}
