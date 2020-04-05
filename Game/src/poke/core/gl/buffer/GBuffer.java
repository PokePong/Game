package poke.core.gl.buffer;

import static org.lwjgl.opengl.GL11.*;

import org.lwjgl.opengl.GL30;

import poke.core.engine.model.Texture2D;

public class GBuffer {

	private Texture2D position;
	private Texture2D normal;
	private Texture2D albedo;
	private Texture2D specular;
	private Texture2D depth;

	public GBuffer(int width, int height) {
		this.position = new Texture2D();
		this.position.bind();
		glTexImage2D(GL_TEXTURE_2D, 0, GL30.GL_RGB16F, width, height, 0, GL_RGB, GL_FLOAT, 0);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);

		this.normal = new Texture2D();
		this.normal.bind();
		glTexImage2D(GL_TEXTURE_2D, 0, GL30.GL_RGB16F, width, height, 0, GL_RGB, GL_FLOAT, 0);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);

		this.albedo = new Texture2D();
		this.albedo.bind();
		glTexImage2D(GL_TEXTURE_2D, 0, GL30.GL_RGB16F, width, height, 0, GL_RGB, GL_FLOAT, 0);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);

		this.specular = new Texture2D();
		this.specular.bind();
		glTexImage2D(GL_TEXTURE_2D, 0, GL30.GL_RGB16F, width, height, 0, GL_RGB, GL_FLOAT, 0);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);
		this.specular.unbind();
	}

	public Texture2D getPosition() {
		return position;
	}

	public Texture2D getNormal() {
		return normal;
	}

	public Texture2D getAlbedo() {
		return albedo;
	}

	public Texture2D getSpecular() {
		return specular;
	}

	public Texture2D getDepth() {
		return depth;
	}

}
