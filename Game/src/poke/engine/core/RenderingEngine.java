package poke.engine.core;

import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_DEPTH_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.glClear;
import static org.lwjgl.opengl.GL11.glClearColor;
import static org.lwjgl.opengl.GL11.glClearDepth;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;

import poke.engine.gl.MeshVBO;
import poke.engine.gl.VBO;
import poke.engine.model.Mesh;
import poke.instance.StaticShader;

public class RenderingEngine {

	private EngineSystem system;
	private Game game;

	public List<MeshVBO> vbos;

	public RenderingEngine(EngineSystem system) {
		this.system = system;
		this.game = system.getGame();
		this.vbos = new ArrayList<MeshVBO>();
	}

	public void init() {
		for (Mesh m : game.getMeshes()) {
			vbos.add(new MeshVBO(m));
		}

	}

	public void update(double delta) {
		
	}

	public void render() {
		glClearColor(1, 0, 0, 1);
		glClear(GL_COLOR_BUFFER_BIT);
		
		for(MeshVBO vbo : vbos) {
			StaticShader.getInstance().bind();
			vbo.draw();
			StaticShader.getInstance().unbind();
		}
	}

	public void cleanUp() {

	}

}
