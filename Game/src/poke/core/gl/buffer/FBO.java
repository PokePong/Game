package poke.core.gl.buffer;

import static org.lwjgl.opengl.GL30.*;

import java.nio.IntBuffer;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL32;

import poke.core.engine.core.Window;

public class FBO {

	private int id;
	private int width;
	private int height;

	public FBO() {
		this(Window.width, Window.height);
	}

	public FBO(int width, int height) {
		this.id = glGenFramebuffers();
		this.width = width;
		this.height = height;
	}

	public void bind() {
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, 0);
		glBindFramebuffer(GL_FRAMEBUFFER, id);
		GL11.glViewport(0, 0, width, height);
	}

	public void unbind() {
		glBindFramebuffer(GL_FRAMEBUFFER, 0);
		GL11.glViewport(0, 0, Window.width, Window.height);
	}

	public void cleanUp() {
		glDeleteFramebuffers(id);
	}

	public void createColorTextureAttachment(int textureId, int channel) {
		glFramebufferTexture2D(GL_DRAW_FRAMEBUFFER, GL_COLOR_ATTACHMENT0 + channel, GL11.GL_TEXTURE_2D, textureId, 0);
	}

	public void createDepthTextureAttachment(int textureId) {
		GL32.glFramebufferTexture(GL_FRAMEBUFFER, GL_DEPTH_ATTACHMENT, textureId, 0);
	}

	public void createDepthBufferAttachment() {
		int depthBuffer = glGenRenderbuffers();
		glBindRenderbuffer(GL_RENDERBUFFER, depthBuffer);
		glRenderbufferStorage(GL_RENDERBUFFER, GL_DEPTH_COMPONENT32F, width, height);
		glFramebufferRenderbuffer(GL_DRAW_FRAMEBUFFER, GL_DEPTH_ATTACHMENT, GL_RENDERBUFFER, depthBuffer);
	}

	public void createColorTextureMultisampleAttachment(int textureId, int channel) {
		glFramebufferTexture2D(GL_FRAMEBUFFER, GL_COLOR_ATTACHMENT0 + channel, GL32.GL_TEXTURE_2D_MULTISAMPLE,
				textureId, 0);
	}

	public void createDepthTextureMultisampleAttachment(int textureId) {
		glFramebufferTexture2D(GL_FRAMEBUFFER, GL_DEPTH_ATTACHMENT, GL32.GL_TEXTURE_2D_MULTISAMPLE, textureId, 0);
	}

	public void setDrawBuffers(IntBuffer buffer) {
		GL20.glDrawBuffers(buffer);
	}

	/** Check all openGL framebuffer errors */
	public void checkStatus() {
		if (glCheckFramebufferStatus(GL_FRAMEBUFFER) == GL_FRAMEBUFFER_COMPLETE) {
			return;
		} else if (glCheckFramebufferStatus(GL_FRAMEBUFFER) == GL_FRAMEBUFFER_UNDEFINED) {
			System.err.println("Framebuffer creation failed with GL_FRAMEBUFFER_UNDEFINED error");
			System.exit(1);
		} else if (glCheckFramebufferStatus(GL_FRAMEBUFFER) == GL_FRAMEBUFFER_INCOMPLETE_ATTACHMENT) {
			System.err.println("Framebuffer creation failed with GL_FRAMEBUFFER_INCOMPLETE_ATTACHMENT error");
			System.exit(1);
		} else if (glCheckFramebufferStatus(GL_FRAMEBUFFER) == GL_FRAMEBUFFER_INCOMPLETE_MISSING_ATTACHMENT) {
			System.err.println("Framebuffer creation failed with GL_FRAMEBUFFER_INCOMPLETE_MISSING_ATTACHMENT error");
			System.exit(1);
		} else if (glCheckFramebufferStatus(GL_FRAMEBUFFER) == GL_FRAMEBUFFER_INCOMPLETE_DRAW_BUFFER) {
			System.err.println("Framebuffer creation failed with GL_FRAMEBUFFER_INCOMPLETE_DRAW_BUFFER error");
			System.exit(1);
		} else if (glCheckFramebufferStatus(GL_FRAMEBUFFER) == GL_FRAMEBUFFER_INCOMPLETE_READ_BUFFER) {
			System.err.println("Framebuffer creation failed with GL_FRAMEBUFFER_INCOMPLETE_READ_BUFFER error");
			System.exit(1);
		} else if (glCheckFramebufferStatus(GL_FRAMEBUFFER) == GL_FRAMEBUFFER_UNSUPPORTED) {
			System.err.println("Framebuffer creation failed with GL_FRAMEBUFFER_UNSUPPORTED error");
			System.exit(1);
		} else if (glCheckFramebufferStatus(GL_FRAMEBUFFER) == GL_FRAMEBUFFER_INCOMPLETE_MULTISAMPLE) {
			System.err.println("Framebuffer creation failed with GL_FRAMEBUFFER_INCOMPLETE_MULTISAMPLE error");
			System.exit(1);
		} else if (glCheckFramebufferStatus(GL_FRAMEBUFFER) == GL32.GL_FRAMEBUFFER_INCOMPLETE_LAYER_TARGETS) {
			System.err.println("Framebuffer creation failed with GL_FRAMEBUFFER_INCOMPLETE_LAYER_TARGETS error");
			System.exit(1);
		}
	}

	public int getId() {
		return id;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

}
