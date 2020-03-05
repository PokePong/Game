package poke.core.engine.core;

import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.glClear;
import static org.lwjgl.opengl.GL11.glClearColor;

import poke.core.engine.core.engine.Engine;
import poke.core.engine.core.engine.EngineSystem;

public class RenderingEngine {

	private EngineSystem system;
	private Game game;

	public RenderingEngine() {
		this.system = Engine.getInstance().getEngineSystem();
		this.game = system.getGame();
	}

	public void init() {

	}

	public void update(double delta) {

	}

	public void render() {
		glClearColor(1, 0, 0, 1);
		glClear(GL_COLOR_BUFFER_BIT);

		game.getScenegraph().getRoot().render();
	}

	public void cleanUp() {

	}

}
