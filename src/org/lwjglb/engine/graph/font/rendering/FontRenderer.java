package org.lwjglb.engine.graph.font.rendering;

import org.joml.Vector2f;
import org.joml.Vector3f;
import org.lwjglb.engine.Utils;
import org.lwjglb.engine.Window;
import org.lwjglb.engine.graph.ShaderProgram;
import org.lwjglb.engine.graph.font.meshcreator.FontType;
import org.lwjglb.engine.graph.font.meshcreator.GuiLabels;
import org.lwjglb.engine.graph.other.Maths;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL13.GL_TEXTURE0;
import static org.lwjgl.opengl.GL13.glActiveTexture;
import static org.lwjgl.opengl.GL20.glDisableVertexAttribArray;
import static org.lwjgl.opengl.GL20.glEnableVertexAttribArray;
import static org.lwjgl.opengl.GL30.glBindVertexArray;

public class FontRenderer {

	private ShaderProgram shader;
	public static FontRenderer instance;
	private static final String VERTEX_FILE = "resources/fonts/fontVertex.txt";
	private static final String FRAGMENT_FILE = "resources/fonts/fontFragment.txt";

	public FontRenderer() {
		try {
			shader = new ShaderProgram();
			shader.createVertexShader(Utils.loadResource(VERTEX_FILE));
			shader.createFragmentShader(Utils.loadResource(FRAGMENT_FILE));
			shader.bindAttribute(0, "position");
			shader.bindAttribute(1, "textureCoords");
			shader.link();
			shader.createUniform("colour");
			shader.createUniform("translation");

		} catch (Exception e) {
			e.printStackTrace();
		}
		instance = this;
	}
	public void render(Window window, GuiLabels text)
	{
		if (text.getTextString().length() > 0 && text.isVisible()) {
			prepare();
			FontType font = text.getFont();
			glActiveTexture(GL_TEXTURE0);
			glBindTexture(GL_TEXTURE_2D, font.getTextureAtlas());
			renderText(window, text);
			endRendering();
		}
	}
	/*public void render(Map<FontType, List<GuiLabels>> texts)
	{
		prepare();
		for(FontType font: texts.keySet())
		{
			glActiveTexture(GL_TEXTURE0);
			glBindTexture(GL_TEXTURE_2D, font.getTextureAtlas());
			for(GuiLabels text: texts.get(font))
			{
				if (text.isVisible()) renderText(text);
			}
		}
		endRendering();
	}*/
	protected void loadColour(Vector3f colour)
	{
		shader.setUniform("colour", colour);
	}
	protected void loadTranslation(Vector2f translation)
	{
		shader.setUniform("translation", translation);
	}

	public void cleanUp(){
		shader.cleanup();
	}

	public void prepare()
	{
		glEnable(GL_BLEND);
		glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
		glDisable(GL_DEPTH_TEST);
		shader.bind();
	}
	
	private void renderText(Window window, GuiLabels text){
		Vector2f position = Maths.getDrawScreenPositionsText(window, text.getPosition());
		glBindVertexArray(text.getMesh());
		glEnableVertexAttribArray(0);
		glEnableVertexAttribArray(1);
		loadColour(text.getColour());
		loadTranslation(position);
		glDrawArrays(GL_TRIANGLES, 0, text.getVertexCount());
		glDisableVertexAttribArray(0);
		glDisableVertexAttribArray(1);
		glBindVertexArray(0);

	}
	
	private void endRendering(){
		shader.unbind();
		glDisable(GL_BLEND);
		glEnable(GL_DEPTH_TEST);

	}

}
