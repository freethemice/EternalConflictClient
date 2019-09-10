package net.eternalconflict.www.enums.subtypes;

public enum PlanetTypeEnum {
    BARREN("Barren", 0, 0.003f, 0.088f),
    LAVA("Lava", 1, 0.003f, 0.030f),
    OCEAN("Ocean", 2, 0.003f, 0.030f),
    HABITABLE("Habitable", 3, 0.006f, 0.009f),
    GAS("Gas", 4, 0.030f, 0.088f),
    ICE("Ice", 5, 0.002f, 0.009f),;

    private final float minSize;
    private final float maxSize;
    private final int id;
    private final String name;
    PlanetTypeEnum(String name, int id, float minSize, float maxSize)
    {
        this.name = name;
        this.id = id;
        this.minSize = minSize;
        this.maxSize = maxSize;
    }

    public String getName() {
        return name;
    }

    public int getID()
    {
        return this.id;
    }
    public static PlanetTypeEnum valueOf(int id)
    {
        for (PlanetTypeEnum planetTypeEnum: PlanetTypeEnum.values())
        {
            if (planetTypeEnum.id == id) return planetTypeEnum;
        }
        return PlanetTypeEnum.BARREN;
    }

}
