package net.eternalconflict.www.enums.subtypes;

public enum NebulaTypeEnum {
    STABLE("Stable", 1, 1.003f, 2.030f),
    UNSTABLE("Unstable", 2, 3.003f, 4.030f),
    DANGROUS("Namgrous", 3, 3.006f, 5.009f);

    private final float minSize;
    private final float maxSize;
    private final int id;
    private final String name;
    NebulaTypeEnum(String name, int id, float minSize, float maxSize)
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
    public static NebulaTypeEnum valueOf(int id)
    {
        for (NebulaTypeEnum nebulaTypeEnum: NebulaTypeEnum.values())
        {
            if (nebulaTypeEnum.id == id) return nebulaTypeEnum;
        }
        return NebulaTypeEnum.STABLE;
    }

}
