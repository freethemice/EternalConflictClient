package net.eternalconflict.www.enums.subtypes;

public enum MoonTypeEnum {
    BARREN("Barren", 0, 0.0003f, 0.0088f),
    LAVA("Lava", 1, 0.0003f, 0.0030f),
    OCEAN("Ocean", 2, 0.0003f, 0.0030f),
    HABITABLE("Habitable", 3, 0.0006f, 0.0009f),
    GAS("Gas", 4, 0.0030f, 0.0088f),
    ICE("Ice", 5, 0.0002f, 0.0009f),;

    private final float minSize;
    private final float maxSize;
    private final int id;
    private final String name;
    MoonTypeEnum(String name, int id, float minSize, float maxSize)
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
    public static MoonTypeEnum valueOf(int id)
    {
        for (MoonTypeEnum moonTypeEnum: MoonTypeEnum.values())
        {
            if (moonTypeEnum.id == id) return moonTypeEnum;
        }
        return MoonTypeEnum.BARREN;
    }

}
