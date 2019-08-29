package org.lwjglb.engine.graph.gui.rendering;

import net.eternalconflict.www.holders.objects.DefaultObject;
import org.joml.Matrix4f;
import org.joml.Vector2f;
import org.lwjglb.engine.Utils;
import org.lwjglb.engine.Window;
import org.lwjglb.engine.graph.ShaderProgram;
import org.lwjglb.engine.graph.gui.meshcreator.GuiTexture;
import org.lwjglb.engine.graph.other.Loader;
import org.lwjglb.engine.graph.other.Maths;
import org.lwjglb.engine.graph.other.RawMesh;

import static org.lwjgl.opengl.GL11.GL_BLEND;
import static org.lwjgl.opengl.GL11.GL_DEPTH_TEST;
import static org.lwjgl.opengl.GL11.GL_ONE_MINUS_SRC_ALPHA;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL11.GL_TRIANGLE_STRIP;
import static org.lwjgl.opengl.GL11.glBindTexture;
import static org.lwjgl.opengl.GL11.glBlendFunc;
import static org.lwjgl.opengl.GL11.glDisable;
import static org.lwjgl.opengl.GL11.glDrawArrays;
import static org.lwjgl.opengl.GL11.glEnable;
import static org.lwjgl.opengl.GL13.GL_SRC_ALPHA;
import static org.lwjgl.opengl.GL13.*;
import static org.lwjgl.opengl.GL20.glDisableVertexAttribArray;
import static org.lwjgl.opengl.GL20.glEnableVertexAttribArray;
import static org.lwjgl.opengl.GL30.glBindVertexArray;

public class GuiRenderer {
    private final RawMesh quad;
    private ShaderProgram shader;
    private GuiTexture guiWindow;
    public static GuiRenderer instance;
    public static DefaultObject mouseOver = null;
    private static final String VERTEX_FILE = "resources/shaders/guiVertexShader.txt";
    private static final String FRAGMENT_FILE = "resources/shaders/guiFragmentShader.txt";

    public GuiRenderer()
    {
        float[] positions = {-1, 1, -1, -1, 1, 1, 1, -1};
        quad = Loader.loadToVAO(positions);
        try {
            shader = new ShaderProgram();
            shader.createVertexShader(Utils.loadResource(VERTEX_FILE));
            shader.createFragmentShader(Utils.loadResource(FRAGMENT_FILE));
            shader.link();
            shader.createUniform("transformationMatrix");
            shader.bindAttribute(0, "position");
            instance = this;
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    public void render(Window window, GuiTexture gui)
    {
        shader.bind();
        glBindVertexArray(quad.getVaoID());
        glEnableVertexAttribArray(0);
        glEnable(GL_BLEND);
        glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
        glDisable(GL_DEPTH_TEST);
        //render
        if (gui.isVisible()) {
            Vector2f drawP = Maths.getDrawScreenPositions(window, gui.getPosition(), gui.getSize());
            Vector2f drawS = Maths.getDrawScreenSizes(window, gui.getSize());
            glActiveTexture(GL_TEXTURE0);
            glBindTexture(GL_TEXTURE_2D, gui.getTexture());
            Matrix4f matrix = Maths.createTransformationMatrix(drawP, drawS);
            shader.setUniform("transformationMatrix", matrix);
            glDrawArrays(GL_TRIANGLE_STRIP, 0, quad.getVertexCount());
        }
        glEnable(GL_DEPTH_TEST);
        glDisable(GL_BLEND);
        glDisableVertexAttribArray(0);
        glBindVertexArray(0);
        shader.unbind();
    }
    public void cleanUp()

    {
        shader.cleanup();
    }
}
