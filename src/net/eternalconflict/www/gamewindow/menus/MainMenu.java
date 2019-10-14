package net.eternalconflict.www.gamewindow.menus;

import java.awt.*;


public class MainMenu
{
    public Color dark_blue = new Color(20, 28, 99);
    public enum STATE{
        MENU,
        SETTINGS,
        GAME,
        PAUSE;
    }
    public STATE State = STATE.MENU;

    /*   public void init()
    {
        guiWindow = new GuiTexture(Loader.loadTexture("textures/window.png"), new Vector2f(0, 0), new Vector2f(300, 100));
        guiWindow.setVisible(false);
        //GuiManger.instance.addGuis(guiWindow);
    }*/



}
