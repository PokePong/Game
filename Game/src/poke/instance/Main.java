package poke.instance;

import poke.core.engine.core.Game;
import poke.core.module.gui.GuiScreen;
import poke.core.module.gui.element.GuiButton;
import poke.core.module.sky.SkyDome;

public class Main extends Game {

	@Override
	public void _init_() {
		getScenegraph().setCamera(new GameCamera());
		getScenegraph().setSky(new SkyDome());
		getScenegraph().getRoot().addChild(new Terrain());
		getScenegraph().getRoot().addChild(new Cube());
		getScenegraph().getRoot().addChild(new Lampadaire());

		GuiScreen screen = new GuiScreen();
		screen.addChild(new GuiButton());
		screen.setEnable(true);
		addGuiScreen(screen);
	}

	@Override
	public void _update_(double delta) {

	}

}
