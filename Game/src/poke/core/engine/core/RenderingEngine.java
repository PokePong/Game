package poke.core.engine.core;


import poke.core.engine.core.engine.Engine;
import poke.core.engine.core.engine.EngineSystem;
import poke.core.gl.config.Default;

public class RenderingEngine {

	private EngineSystem system;
	private Game game;

	public RenderingEngine() {
		this.system = Engine.getInstance().getEngineSystem();
		this.game = system.getGame();
	}

	public void init() {
		Default.init();

	}

	public void render() {
		Default.clearScreen();

		game.getScenegraph().render();

		game.getGui().render();

	}

	public void cleanUp() {

	}

}
