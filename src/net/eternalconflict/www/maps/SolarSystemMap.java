package net.eternalconflict.www.maps;

import net.eternalconflict.www.ConfigFile;
import net.eternalconflict.www.holders.objects.DefaultObject;
import net.eternalconflict.www.holders.objects.PlayerOwnedObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class SolarSystemMap {

    private HashMap<String, DefaultObject> objects;
    private ConfigFile config;
    private String key;
    public static SolarSystemMap viewing;
    public SolarSystemMap(ConfigFile configFile)
    {
        this.objects = new HashMap<String, DefaultObject>();
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
        //this.config.setFile("systems", key + ".txt");
    }
    public void updateFromConfig(SolarSystemMap solarSystemMap)
    {
        try {
            for(DefaultObject object: objects.values())
            {
                if (object != null) {
                    DefaultObject upObject = solarSystemMap.getObject(object.getId());
                    if (upObject != null) {
                        object.setSize(upObject.getSize());
                        object.setPosition(upObject.getPosition());
                        if (object instanceof PlayerOwnedObject && upObject instanceof PlayerOwnedObject) {
                            PlayerOwnedObject pOO = (PlayerOwnedObject) object;
                            PlayerOwnedObject upPOO = (PlayerOwnedObject) upObject;
                            pOO.setDestination(upPOO.getDestination());
                            pOO.setVelocity(upPOO.getVelocity());
                            pOO.setOwnerID(upPOO.getOwnerID());
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
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
    }
    public void removeObject(DefaultObject defaultObject)
    {
        objects.remove(defaultObject.getId());
    }
    public List<DefaultObject> getObjects()
    {
        return new ArrayList<DefaultObject>(objects.values());
    }
    public DefaultObject getObject(String id)
    {
        return objects.get(id);
    }
}
