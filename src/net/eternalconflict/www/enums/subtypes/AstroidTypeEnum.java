package net.eternalconflict.www.enums.subtypes;

public enum AstroidTypeEnum {
    SMALL("Small", 0, 0.075f, 0.900f),
    MEDIUM("Medium", 1, 1.000f, 1.900f),
    LARGE("Large", 2, 1.996f, 2.778f),
    VERYLARGE("Very Large", 3, 2.779f, 3.995f);

    private final float minSize;
    private final float maxSize;
    private final int id;
    private final String name;

    AstroidTypeEnum(String name, int id, float minSize, float maxSize)    {
        this.name = name;
        this.id = id;
        this.minSize = minSize;
        this.maxSize = maxSize;
    }

    public String getName() { return name;}

    public int getID()
    {
        return this.id;
    }
    public static AstroidTypeEnum valueOf(int id)
    {
        for (AstroidTypeEnum astroidTypeEnum: AstroidTypeEnum.values())
        {
            if (astroidTypeEnum.id == id) return astroidTypeEnum;
        }
        return net.eternalconflict.www.enums.subtypes.AstroidTypeEnum.SMALL;
    }
}
