package poke.engine.kernel;

import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_DEPTH_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.glClear;
import static org.lwjgl.opengl.GL11.glClearColor;
import static org.lwjgl.opengl.GL11.glClearDepth;

import poke.engine.model.Mesh;

public class RenderingEngine {

	private EngineSystem system;
	private Game game;

	public RenderingEngine(EngineSystem system) {
		this.system = system;
		this.game = system.getGame();
	}

	public void init() {

	}

	public void update(double delta) {

	}

	public void render() {
		glClearColor(1, 0, 0, 1);
		glClearDepth(1.0);
		glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
		
	}

	public void cleanUp() {

	}

}
