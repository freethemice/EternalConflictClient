package net.eternalconflict.www.maps;

import net.eternalconflict.www.ConfigFile;
import net.eternalconflict.www.holders.objects.DefaultObject;
import net.eternalconflict.www.holders.objects.PlayerOwnedObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class SolarSystemMap {

    private HashMap<String, DefaultObject> objects;
    private HashMap<String, PlayerOwnedObject> objectsPOO;
    private ConfigFile config;
    private String key;
    public static SolarSystemMap viewing;
    public SolarSystemMap(ConfigFile configFile)
    {
        this.objects = new HashMap<String, DefaultObject>();
        this.objectsPOO = new HashMap<String, PlayerOwnedObject>();
        this.config = configFile.clone();
        if (config.containsKey("system.key")) {
            this.key = config.getString("system.key");
            if (config.containsKey("system.planets")) {
                List<DefaultObject> loading = config.getDefaultObjectList("system.planets");
                for (int i = 0; i < loading.size(); i++) {
                    addObject(loading.get(i));
                }
            }
        }
        viewing = this;
        //this.config.setFile("systems", key + ".txt");
    }

    public String getKey() {
        return key;
    }

    public ConfigFile getAsConfig()
    {
        return config.clone();
    }
    public void addObject(DefaultObject defaultObject)
    {
        objects.put(defaultObject.getId(), defaultObject);
        if (defaultObject instanceof PlayerOwnedObject)
        {
            objectsPOO.put(defaultObject.getId(), (PlayerOwnedObject) defaultObject);
        }
    }
    public void removeObject(DefaultObject defaultObject)
    {
        objects.remove(defaultObject.getId());
        objectsPOO.remove(defaultObject.getId());
    }
    public List<DefaultObject> getObjects()
    {
        return new ArrayList<DefaultObject>(objects.values());
    }
    public List<PlayerOwnedObject> getPlayerObjects()
    {
        return new ArrayList<PlayerOwnedObject>(objectsPOO.values());
    }
    public DefaultObject getObject(String id)
    {
        return objects.get(id);
    }
    public void tick()
    {
        /*
                The distance between Start and End point is given by d^2=(x1−x0)^2+(y1−y0)^2
                Let the ratio of distances, t=dt/d
                Then the point (xt,yt)=(((1−t)x0+tx1),((1−t)y0+ty1))
         */
        for(PlayerOwnedObject playerOwnedObject: objectsPOO.values())
        {
            if (playerOwnedObject.getVelocity() > 0)
            {
                //needs to be checked
                double d = playerOwnedObject.getPosition().distance(playerOwnedObject.getDestination());
                double t = playerOwnedObject.getVelocity()/d;
                double xt = ((1-t)*playerOwnedObject.getDestination().getX() + t*playerOwnedObject.getPosition().getX());
                double zt = ((1-t)*playerOwnedObject.getDestination().getZ() + t*playerOwnedObject.getPosition().getZ());
                playerOwnedObject.setPosition(xt, zt);
            }
        }
    }
}
