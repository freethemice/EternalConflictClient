package net.eternalconflict.www.enums;

public enum ObjectTypeEnum {
    DEFAULT("default"),
    PLANET("planets"),
    STAR("stars"),
    STATION("stations"),
    SHIP("ships");
    String folder;
    ObjectTypeEnum(String folder)
    {
        this.folder = folder;
    }

    public String getFolder() {
        return folder;
    }
}
