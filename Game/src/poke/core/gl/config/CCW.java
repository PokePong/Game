package poke.core.gl.config;

import static org.lwjgl.opengl.GL11.GL_CCW;
import static org.lwjgl.opengl.GL11.GL_CW;
import static org.lwjgl.opengl.GL11.glFrontFace;

import poke.core.engine.renderer.RenderConfig;

public class CCW implements RenderConfig {

	@Override
	public void enable() {
		glFrontFace(GL_CCW);
	}

	@Override
	public void disable() {
		glFrontFace(GL_CW);
	}

}
