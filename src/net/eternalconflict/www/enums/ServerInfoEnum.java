package net.eternalconflict.www.enums;

public enum ServerInfoEnum {

    LOGIN("Login", "51.79.120.233"),
    WEB("Web", "http://www.firesoftitan.com/ec"),
    REGISTER("Register", "http://www.firesoftitan.com/ec"),
    VERSIONS("Versions", "http://www.firesoftitan.com/ec/info.data"),
    ISSUES("Issues", "https://github.com/freethemice/EternalConflictClient/issues"),
    ACCOUNT("Account","http://www.firesoftitan.com/ec/mylogin.html");
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
