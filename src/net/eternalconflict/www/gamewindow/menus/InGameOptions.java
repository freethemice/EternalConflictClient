package net.eternalconflict.www.gamewindow.menus;

import java.awt.*;

public class InGameOptions {

    public MainMenu.STATE State = MainMenu.STATE.SETTINGS;
    public Font fnt0;
    public Color dark_blue = new Color(20, 28, 99);

    public void renderSettings(Graphics g)
    {
        fnt0 = new Font("arial", Font.BOLD,50);
        g.setFont(fnt0);
        g.setColor(dark_blue);
        g.drawString("Game Settings", 100/2, 100);
    }
}
