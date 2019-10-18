package net.eternalconflict.www.holders;

import net.eternalconflict.www.ConfigFile;
import net.eternalconflict.www.holders.objects.DefaultObject;
import net.eternalconflict.www.maps.SolarSystemMap;

import java.util.ArrayList;
import java.util.List;

public class PlayerHolder {
    private ConfigFile config;

    private String name;
    private String email;
    private List<DefaultObject> myStuff;
    private SolarSystemMap viewing;
    public static PlayerHolder player;
    public PlayerHolder(ConfigFile config)
    {
        player = this;
        this.config = config;

        this.name = config.getString("name");
        this.email = config.getString("email");
        this.myStuff = config.getDefaultObjectList("objects");
        if (myStuff == null)
        {
            myStuff = new ArrayList<DefaultObject>();
        }


    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public SolarSystemMap getViewing() {
        return viewing;
    }

    public void setViewing(SolarSystemMap viewing) {
        SolarSystemMap.viewing = viewing;
        this.viewing = viewing;
    }
}
