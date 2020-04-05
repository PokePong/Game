package poke.core.module.gui;

import java.util.ArrayList;
import java.util.List;

import poke.core.engine.renderer.RenderConfig;
import poke.core.engine.shader.Shader;

public class GUI {

	private Shader shader;
	private RenderConfig config;
	private List<GuiScreen> screens;

	public GUI() {
		this.screens = new ArrayList<GuiScreen>();
		this.shader = GuiShader.getInstance();
		this.config = new GuiConfig();
	}

	public void init() {
		for (GuiScreen screen : screens) {
			screen.init();
		}
	}

	public void update(double delta) {
		for (GuiScreen screen : screens) {
			if (screen.isEnable()) {
				screen.update(delta);
			}
		}
	}

	public void render() {
		shader.bind();
		config.enable();
		for (GuiScreen screen : screens) {
			if (screen.isEnable())
				screen.render();
		}
		config.disable();
		shader.unbind();
	}

	public void cleanUp() {
		for (GuiScreen screen : screens) {
			screen.cleanUp();
		}
	}

	public List<GuiScreen> getGuiScreens() {
		return screens;
	}

}
