package net.eternalconflict.www.enums;

public enum ServerInfoEnum {

    LOGIN("Login", "localhost"),
    WEB("Web", "http://www.firesoftitan.com/ec"),
    REGISTER("Register", "http://www.firesoftitan.com/ec"),
    VERSIONS("Versions", "http://www.firesoftitan.com/ec/info.data");
    private final String name;
    private final String address;

    ServerInfoEnum(String name, String address) {
        this.name = name;
        this.address = address;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }
}