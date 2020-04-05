package poke.core.engine.core;

import poke.core.engine.scene.Scenegraph;
import poke.core.module.gui.GUI;
import poke.core.module.gui.GuiScreen;

public abstract class Game {

	private Scenegraph scenegraph;
	private GUI gui;

	public Game() {
		this.scenegraph = new Scenegraph();
		this.gui = new GUI();
	}

	public void init() {
		_init_();
		scenegraph.init();
		gui.init();
	}

	public void update(double delta) {
		_update_(delta);
		scenegraph.update(delta);
		gui.update(delta);
	}

	public abstract void _init_();

	public abstract void _update_(double delta);

	public void cleanUp() {
		scenegraph.cleanUp();
		gui.cleanUp();
	}

	public void addGuiScreen(GuiScreen screen) {
		this.gui.getGuiScreens().add(screen);
	}

	public GUI getGui() {
		return gui;
	}

	public Scenegraph getScenegraph() {
		return scenegraph;
	}

}
