package net.eternalconflict.www.enums.subtypes;

public enum StarTypeEnum {
    BLUE("Blue", 0, 2.85f, 4.32f),
    BLUE_WHITE("BlueWhite", 1, 0.778f, 2.85f),
    WHITE("White", 2, 0.605f, 0.778f),
    YELLOW_WHITE("YellowWhite", 3, 0.497f, 0.605f),
    YELLOW("Yellow", 4, 0.415f, 0.449f),
    LIGHT_ORANGE("LightOrange", 5, 0.303f, 0.415f),
    ORANGE_RED("OrangeRed", 6, 0, 0.303f),
    RED("Red", 7,0,0.445f);

    private final float minSize;
    private final float maxSize;
    private final int id;
    private final String name;

    StarTypeEnum(String name, int id, float minSize, float maxSize)
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
    public static StarTypeEnum valueOf(int id)
    {
        for (StarTypeEnum starTypeEnum: StarTypeEnum.values())
        {
            if (starTypeEnum.id == id) return starTypeEnum;
        }
        return StarTypeEnum.BLUE;
    }

}
