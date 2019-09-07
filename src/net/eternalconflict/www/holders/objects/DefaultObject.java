package net.eternalconflict.www.holders.objects;

import net.eternalconflict.www.ConfigFile;
import net.eternalconflict.www.enums.ObjectTypeEnum;
import org.lwjglb.engine.graph.gui.rendering.GuiManger;
import net.eternalconflict.www.holders.CoordinatesHolder;
import org.joml.Vector2f;
import org.lwjglb.engine.MouseInput;
import org.lwjglb.engine.items.GameItem;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class DefaultObject {
    private static List<String> allids = new ArrayList<String>();
    public static boolean hasID(String id)
    {
        return allids.contains(id);
    }
    public static void addID(String id)
    {
        allids.add(id);
    }

    public static String getNewID()
    {
        String SALTCHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz1234567890";
        StringBuilder salt = new StringBuilder();
        Random rnd = new Random(System.currentTimeMillis());
        while (salt.length() < 36) { // length of the random string.
            int index = (int) (rnd.nextFloat() * SALTCHARS.length());
            salt.append(SALTCHARS.charAt(index));
        }
        String saltStr = salt.toString();


        if (DefaultObject.hasID(saltStr)) {
            return getNewID();
        }
        DefaultObject.addID(saltStr);
        return saltStr;
    }
    public void save(ConfigFile configFile)
    {

    }
    protected CoordinatesHolder position;
    protected String name;
    protected String id;
    protected double size = -1; // size is radius
    protected ObjectTypeEnum objectType;
    public GuiManger hudOverlay = null;
    public GameItem gameItem;

    public DefaultObject(String id, String name)
    {
        this.id = id;
        this.name = name;
        this.position = new CoordinatesHolder(0,0);
        objectType = ObjectTypeEnum.DEFAULT;
    }
    public DefaultObject(String id, String name, double x,double z)
    {
        this.id = id;
        this.name = name;
        this.position = new CoordinatesHolder(x, z);
        objectType = ObjectTypeEnum.DEFAULT;
    }
    public DefaultObject(String id, String name, CoordinatesHolder coordinatesHolder)
    {
        this.id = id;
        this.name = name;
        this.position = coordinatesHolder.clone();
        objectType = ObjectTypeEnum.DEFAULT;
    }
    public DefaultObject(String name)
    {
        this.id = DefaultObject.getNewID();
        this.name = name;
        this.position = new CoordinatesHolder(0, 0);
        objectType = ObjectTypeEnum.DEFAULT;
    }
    public DefaultObject(String name, double x, double z)
    {
        this.id = DefaultObject.getNewID();
        this.name = name;
        this.position = new CoordinatesHolder(x, z);
        objectType = ObjectTypeEnum.DEFAULT;
    }
    public DefaultObject(String name, CoordinatesHolder coordinatesHolder)
    {
        this.id = DefaultObject.getNewID();
        this.name = name;
        this.position = coordinatesHolder.clone();
        objectType = ObjectTypeEnum.DEFAULT;
    }
    public ObjectTypeEnum getObjectType() {
        return objectType;
    }
    public CoordinatesHolder getPosition() {
        return position;
    }
    public double getSize() {
        return size;
    }

    public void setSize(double size) {
        this.size = size;
    }
    public void setPosition(double x, double z) {
        this.position = new CoordinatesHolder(x, z);
    }
    public void setPosition(CoordinatesHolder position) {
        this.position = position.clone();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }


    public boolean isMouseOnMe(MouseInput mouseInput)
    {
        if (hudOverlay != null) {
            if (hudOverlay.isVisible()) {
                float where = hudOverlay.getPosition().distance(new Vector2f((float) mouseInput.getCurrentPos().x - hudOverlay.getSize().x/2, (float) mouseInput.getCurrentPos().y- hudOverlay.getSize().y/2));
                if (where < 25) {
                    return true;
                }
            }
        }
        return false;
    }
    public void setupHud()
    {

    }
    protected void setupHud(String texture, float sizex, float sizez)
    {
        this.hudOverlay =  new GuiManger(texture, "", new Vector2f(0, 0), new Vector2f(sizex, sizez), true);
        this.hudOverlay.setVisible(false);
        GuiManger.instance.addGuis(this.hudOverlay);
    }
    public void setHudPosition(Vector2f position)
    {
        this.hudOverlay.setPosition(new Vector2f(position.x - this.hudOverlay.getSize().x/2, position.y - this.hudOverlay.getSize().y/2));
    }
    public void setHudVisible(boolean visible)
    {
        this.hudOverlay.setVisible(visible);
    }
    public GuiManger getHudOverlay() {
        return hudOverlay;
    }

    public GameItem getGameItem() {
        return gameItem;
    }

    public void setGameItem(GameItem gameItem) {
        this.gameItem = gameItem;
    }
}
