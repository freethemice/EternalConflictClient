package net.eternalconflict.www.gamewindow.hud.gui;

import net.eternalconflict.www.gamewindow.GameWindow;
import net.eternalconflict.www.gamewindow.MouseBoxSelectionDetector;
import org.joml.Vector2f;
import org.lwjglb.engine.MouseInput;
import org.lwjglb.engine.graph.gui.rendering.GuiManger;
import org.lwjglb.engine.items.GameItem;

public class CursorGui {
    private GuiManger cursor;
    private GuiManger cursor_ring;
    private long lasttime = 0;
    private float size = 1;
    private MouseBoxSelectionDetector detector;
    private GameItem selected = null;
    public CursorGui() {
        GuiManger guiTexture = new GuiManger("textures/cursor.png", "", new Vector2f(100, 100), new Vector2f(80, 60), false);
        cursor = guiTexture;
        guiTexture = new GuiManger("textures/cursor_ring.png","" , new Vector2f(100, 100), new Vector2f(80*size, 60*size), false);
        cursor_ring = guiTexture;
        cursor_ring.setVisible(false);

        GuiManger.addGuis(cursor);
        GuiManger.addGuis(cursor_ring);
        detector = new MouseBoxSelectionDetector();
    }
    public void update(MouseInput mouseInput)
    {
        if (System.currentTimeMillis() - lasttime > 10)
        {
            lasttime = System.currentTimeMillis();
            size += 0.01f;
            if (size > 2) size = 1;
            cursor_ring.setSize(new Vector2f(80*size, 60*size));
        }

        Vector2f position = new Vector2f((float)mouseInput.getCurrentPos().x - cursor.getSize().x / 2, (float)mouseInput.getCurrentPos().y- cursor.getSize().y / 2);
        Vector2f position_ring = new Vector2f((float)mouseInput.getCurrentPos().x - cursor_ring.getSize().x / 2, (float)mouseInput.getCurrentPos().y- cursor_ring.getSize().y / 2);

        cursor.setPosition(position);
        cursor_ring.setPosition(position_ring);
        selected = detector.selectGameItem(GameWindow.instance.getGameItems(), GameWindow.instance.getWindow(), mouseInput.getCurrentPos(), GameWindow.instance.getCamera());
        if (selected == null && GuiManger.mouseOver != null) selected = GuiManger.mouseOver.gameItem;
        if (selected != null)
        {
            cursor_ring.setVisible(true);
        }
        else
        {
            cursor_ring.setVisible(false);
            size = 1;
        }
    }

    public GameItem getSelected() {
        return selected;
    }
}
