package poke.core.gl.deferred;

import org.lwjgl.opengl.GL11;

import poke.core.engine.renderer.RenderConfig;

public class DeferredConfig implements RenderConfig {

	@Override
	public void enable() {
		GL11.glDisable(GL11.GL_DEPTH_TEST);
	}

	@Override
	public void disable() {
		GL11.glEnable(GL11.GL_DEPTH_TEST);
	}

}
