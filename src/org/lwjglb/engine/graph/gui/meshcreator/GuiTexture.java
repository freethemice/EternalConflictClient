package org.lwjglb.engine.graph.gui.meshcreator;

import org.joml.Vector2f;

public class GuiTexture {
    private int texture;
    private Vector2f position;
    private Vector2f size;
    private boolean visible;
    public GuiTexture(int texture, Vector2f position, Vector2f size) {
        this.texture = texture;
        this.position = position;
        this.size = size;
        this.visible = true;
    }

    public void setSize(Vector2f size) {
        this.size = size;
    }

    public void setPosition(Vector2f position) {
        this.position = position;
    }

    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    public int getTexture() {
        return texture;
    }

    public Vector2f getPosition() {
        return position;
    }

    public Vector2f getSize() {
        return size;
    }
}
