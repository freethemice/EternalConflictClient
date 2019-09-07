package org.lwjglb.engine.graph.gui.rendering;

import net.eternalconflict.www.enums.FontTypeEnum;
import net.eternalconflict.www.gamewindow.GameWindow;
import net.eternalconflict.www.holders.objects.DefaultObject;
import org.joml.Vector2f;
import org.lwjglb.engine.Window;
import org.lwjglb.engine.graph.font.meshcreator.FontType;
import org.lwjglb.engine.graph.font.meshcreator.GuiLabels;
import org.lwjglb.engine.graph.font.rendering.FontRenderer;
import org.lwjglb.engine.graph.gui.meshcreator.GuiTexture;
import org.lwjglb.engine.graph.other.Loader;

import java.util.ArrayList;
import java.util.List;

public class GuiManger {


    private static List<GuiManger> guis = new ArrayList<GuiManger>();
    public static GuiManger instance;
    public static DefaultObject mouseOver = null;
    public static void addGuis(GuiManger texture)
    {
        guis.add(texture);
    }
    public static void removeGui(GuiManger texture)
    {
        guis.remove(texture);
    }
    public static void removeGui(int index)
    {
        guis.remove(index);
    }
    public static int sizeGuis()
    {
        return guis.size();
    }
    public static GuiManger getGui(int index)
    {
        return guis.get(index);
    }
    public static FontType font;
    public static void render(Window window, boolean renderBackGround)
    {
        for(GuiManger guiManger : guis)
        {
            if (guiManger.visible) {
                if (guiManger.isRenderBackGround() == renderBackGround) {
                    GuiRenderer.instance.render(window, guiManger.background);
                    FontRenderer.instance.render(window, guiManger.label);
                }
            }
        }
    }
    private boolean renderBackGround;
    private GuiTexture background;
    private GuiLabels label;
    private Vector2f position;
    private Vector2f size;
    private boolean visible;
    public GuiManger(String background, String text, Vector2f position, Vector2f size)
    {
        this.renderBackGround = false;
        this.position = position;
        this.size = size;
        this.visible = true;
        this.background = new GuiTexture(Loader.loadTexture(background), position, size);

        font = FontTypeEnum.getFontType(FontTypeEnum.Verdana, GameWindow.instance.getWindow());
        label = new GuiLabels(text, 1, font, position, size.x, false);
        label.setColour(0,1,0);
    }
    public GuiManger(String background, String text, Vector2f position, Vector2f size, boolean renderBackGround)
    {
        this.renderBackGround = renderBackGround;
        this.position = position;
        this.size = size;
        this.visible = true;
        this.background = new GuiTexture(Loader.loadTexture(background), position, size);

        font = FontTypeEnum.getFontType(FontTypeEnum.Verdana, GameWindow.instance.getWindow());
        label = new GuiLabels(text, 1, font, position, size.x, false);
        label.setColour(0,1,0);
    }

    public boolean isRenderBackGround() {
        return renderBackGround;
    }

    public void setRenderBackGround(boolean renderBackGround) {
        this.renderBackGround = renderBackGround;
    }

    public void setFontColour(float r, float g, float b) {
        label.setColour(r, g, b);
    }

    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    public String getText() {
        return label.getTextString();
    }

    public void setText(String text) {
        this.label.setTextString(text);
    }

    public Vector2f getPosition() {
        return position;
    }

    public void setPosition(Vector2f position) {
        this.position = position;
        this.background.setPosition(this.position);
        this.label.setPosition(this.position);
    }

    public Vector2f getSize() {
        return size;
    }

    public void setSize(Vector2f size) {
        this.size = size;
        this.background.setSize(size);
    }
/*   public void init()
    {
        guiWindow = new GuiTexture(Loader.loadTexture("textures/window.png"), new Vector2f(0, 0), new Vector2f(300, 100));
        guiWindow.setVisible(false);
        //GuiManger.instance.addGuis(guiWindow);
    }*/



}
