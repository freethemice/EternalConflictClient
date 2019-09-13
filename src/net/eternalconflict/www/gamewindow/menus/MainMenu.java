package net.eternalconflict.www.gamewindow.menus;

import java.awt.*;

import static net.eternalconflict.www.EternalConflict.gameEng;

public class MainMenu
{
    public Color dark_blue = new Color(20, 28, 99);
    public enum STATE{
        MENU,
        SETTINGS,
        Game,
    }
    public STATE State = STATE.MENU;
    public Font fnt0;

    public void render (Graphics g)
    {
        fnt0 = new Font("arial", Font.BOLD,50);
        g.setFont(fnt0);
        g.setColor(dark_blue);
        g.drawString("Eternal Conflict",1000/ 2,100);
    }
}
