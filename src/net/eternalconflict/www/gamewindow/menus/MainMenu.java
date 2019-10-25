package net.eternalconflict.www.gamewindow.menus;

import javafx.scene.input.KeyEvent;
import org.lwjgl.opengl.GL11;

import java.awt.*;


public class MainMenu
{
    public Color dark_blue = new Color(20, 28, 99);
    public enum STATE{
        MENU,
        SETTINGS,
        GAME,
        PAUSE,
        CHAT, Game;
    }
    private STATE State = STATE.MENU;
}
