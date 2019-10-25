package net.eternalconflict.www.enums;

public enum ObjectTypeEnum {
    DEFAULT("default"),
    PLANET("planets"),
    STAR("stars"),
    STATION("stations"),
    SHIP("stations"),
    ASTROID("astroids"),
    MOONS("moons"),
    RINGS("rings");
    String folder;
    ObjectTypeEnum(String folder)
    {
        this.folder = folder;
    }

    public String getFolder() {
        return folder;
    }
}
