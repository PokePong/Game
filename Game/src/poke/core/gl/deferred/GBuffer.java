package poke.core.gl.deferred;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL30;

import poke.core.engine.model.Texture2D;

public class GBuffer {

	private Texture2D position;
	private Texture2D albedo;
	private Texture2D normal;
	private Texture2D depth;

	public GBuffer(int width, int height) {
		this.position = new Texture2D(width, height);
		position.bind();
		position.noFilter();
		position.allocateImage2D(GL30.GL_RGBA32F, GL11.GL_RGBA);

		this.albedo = new Texture2D(width, height);
		albedo.bind();
		albedo.noFilter();
		albedo.allocateImage2D(GL30.GL_RGBA16F, GL11.GL_RGBA);

		this.normal = new Texture2D(width, height);
		normal.bind();
		normal.noFilter();
		normal.allocateImage2D(GL30.GL_RGBA32F, GL11.GL_RGBA);

		this.depth = new Texture2D(width, height);
		depth.bind();
		depth.noFilter();
		depth.allocateDepth();
	}

	public void cleanUp() {
		position.cleanUp();
		albedo.cleanUp();
		normal.cleanUp();
		depth.cleanUp();
	}

	public Texture2D getPosition() {
		return position;
	}

	public Texture2D getAlbedo() {
		return albedo;
	}

	public Texture2D getNormal() {
		return normal;
	}

	public Texture2D getDepth() {
		return depth;
	}
	
	public void resize(int width, int height) {
		position.bind();
		position.resize(width, height);
		
		albedo.bind();
		albedo.resize(width, height);
		
		normal.bind();
		normal.resize(width, height);
		
		depth.bind();
		depth.resize(width, height);
		
		depth.unbind();
	}

}
