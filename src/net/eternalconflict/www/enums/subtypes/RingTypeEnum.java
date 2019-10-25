package net.eternalconflict.www.enums.subtypes;

public enum RingTypeEnum {
    BARREN("Barren_Ring", 0, 0.0035f, 0.0858f),
    LAVA("Lava_Ring", 1, 0.0035f, 0.0350f),
    OCEAN("Ocean_Ring", 2, 0.0035f, 0.0350f),
    HABITABLE("Habitable_Ring", 3, 0.0056f, 0.0059f),
    GAS("Gas_Ring", 4, 0.0530f, 0.0858f),
    ICE("Ice_Ring", 5, 0.0052f, 0.0059f),;

    private final float minSize;
    private final float maxSize;
    private final int id;
    private final String name;
    RingTypeEnum(String name, int id, float minSize, float maxSize)
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
    public static RingTypeEnum valueOf(int id)
    {
        for (RingTypeEnum ringTypeEnum: RingTypeEnum.values())
        {
            if (ringTypeEnum.id == id) return ringTypeEnum;
        }
        return RingTypeEnum.BARREN;
    }

}
