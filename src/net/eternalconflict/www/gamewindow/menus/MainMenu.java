package net.eternalconflict.www.gamewindow.menus;

import java.awt.*;


public class MainMenu
{
    public Color dark_blue = new Color(20, 28, 99);
    public enum STATE{
        MENU,
        SETTINGS,
        GAME,
        PAUSE,
        CHAT;
    }
    public STATE State = STATE.MENU;


}
