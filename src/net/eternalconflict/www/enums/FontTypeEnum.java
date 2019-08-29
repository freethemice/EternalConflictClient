package net.eternalconflict.www.enums;

import org.lwjglb.engine.Window;
import org.lwjglb.engine.graph.font.meshcreator.FontType;
import org.lwjglb.engine.graph.other.Loader;

import java.io.File;

public enum FontTypeEnum {
    Verdana("Verdana"), Umbrage("Umbrage");
    public final String name;

    FontTypeEnum(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
    public FontType getFontType(Window window)
    {
        String lName = this.name.toLowerCase();
        return new FontType(Loader.loadTexture("fonts/" + lName + ".png"), new File("resources/fonts/" + lName + ".fnt"), window);
    }
    public static FontType getFontType(FontTypeEnum fontTypeEnum, Window window)
    {
        String lName = fontTypeEnum.getName().toLowerCase();
        return new FontType(Loader.loadTexture("fonts/" + lName + ".png"), new File("resources/fonts/" + lName + ".fnt"), window);
    }
}
