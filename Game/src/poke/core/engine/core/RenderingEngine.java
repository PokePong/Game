package poke.core.engine.core;

import poke.core.engine.core.engine.Engine;
import poke.core.engine.core.engine.EngineSystem;
import poke.core.gl.config.Default;
import poke.core.gl.shader.StaticShader;

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

	public void update(double delta) {

	}

	public void render() {
		Default.clearScreen();

		StaticShader.getInstance().bind();
		game.getScenegraph().getStaticRootObject().render();
		StaticShader.getInstance().unbind();
	}

	public void cleanUp() {

	}

}
