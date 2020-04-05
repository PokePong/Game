package poke.core.module.gui;


import org.lwjgl.opengl.GL11;

import poke.core.engine.renderer.RenderConfig;

public class GuiConfig implements RenderConfig {

	@Override
	public void enable() {
		GL11.glDisable(GL11.GL_DEPTH_TEST);
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
	}

	@Override
	public void disable() {
		GL11.glDisable(GL11.GL_BLEND);
		GL11.glEnable(GL11.GL_DEPTH_TEST);
		
	}

}
