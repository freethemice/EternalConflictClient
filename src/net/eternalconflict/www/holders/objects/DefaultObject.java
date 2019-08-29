package net.eternalconflict.www.holders.objects;

import net.eternalconflict.www.ConfigFile;
import net.eternalconflict.www.enums.ObjectTypeEnum;
import net.eternalconflict.www.gamewindow.GameWindow;
import net.eternalconflict.www.gamewindow.hud.gui.GuiWindow;
import net.eternalconflict.www.holders.CoordinatesHolder;
import org.joml.Vector2f;
import org.joml.Vector3f;
import org.lwjglb.engine.MouseInput;
import org.lwjglb.engine.Utils;
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
    public GuiWindow hudOverlay = null;
    public GuiWindow overlay = null;
    public GameItem gameItem;

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
    protected void setupHud(String texture)
    {
        this.hudOverlay =  new GuiWindow(texture, "", new Vector2f(0, 0), new Vector2f(80, 60));
        this.hudOverlay.setVisible(false);
        GuiWindow.instance.addGuis(this.hudOverlay);
        this.overlay =  new GuiWindow("textures/planets/hud/spot.png", "", new Vector2f(0, 0), new Vector2f(4, 4));
        this.overlay.setVisible(false);
        GuiWindow.instance.addGuis(this.overlay);
    }
    public void setHudPosition(Vector2f position)
    {
        this.hudOverlay.setPosition(new Vector2f(position.x - this.hudOverlay.getSize().x/2, position.y - this.hudOverlay.getSize().y/2));
        this.overlay.setPosition(new Vector2f(position.x - this.overlay.getSize().x/2, position.y - this.overlay.getSize().y/2));
    }
    public void setHudVisible(boolean visible)
    {
        this.hudOverlay.setVisible(visible);
        Vector3f vector3f = Utils.getScreenCoords(GameWindow.instance.getCamera(), GameWindow.instance.getWindow(), this.gameItem.getPosition().add(this.gameItem.getScale(), 0, 0));
        Vector3f vector3f2 = Utils.getScreenCoords(GameWindow.instance.getCamera(), GameWindow.instance.getWindow(), this.gameItem.getPosition().add(-1*this.gameItem.getScale(), 0, 0));
        if (vector3f != null && vector3f2 != null) {
            if (vector3f.distance(vector3f2) < 1) {
                this.overlay.setVisible(visible);
            } else {
                this.overlay.setVisible(false);
            }
        } else {
            this.overlay.setVisible(false);
        }
    }
    public GuiWindow getHudOverlay() {
        return hudOverlay;
    }

    public GameItem getGameItem() {
        return gameItem;
    }

    public void setGameItem(GameItem gameItem) {
        this.gameItem = gameItem;
    }

    protected CoordinatesHolder position;
    protected String name;
    protected String id;
    protected ObjectTypeEnum objectType;
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
}
