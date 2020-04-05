package poke.core.engine.model;

import static org.lwjgl.stb.STBImage.stbi_failure_reason;
import static org.lwjgl.stb.STBImage.stbi_load;
import static org.lwjgl.stb.STBImage.stbi_set_flip_vertically_on_load;

import java.nio.ByteBuffer;
import java.nio.IntBuffer;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.system.MemoryStack;

import poke.core.engine.utils.ResourceLoader;

public class Texture2D {

	private int id;
	private int width;
	private int height;

	public Texture2D() {
		this.id = GL11.glGenTextures();
	}

	public void delete() {
		GL11.glDeleteTextures(id);
	}

	public void bind() {
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, id);
	}

	public void unbind() {
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, 0);
	}

	public void noFilter() {
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_NEAREST);
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_NEAREST);
	}

	public void clampToBorder() {
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_S, GL13.GL_CLAMP_TO_BORDER);
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_T, GL13.GL_CLAMP_TO_BORDER);
	}

	public void uploadData(int internalformat, int width, int height, int format, ByteBuffer data) {
		GL11.glTexImage2D(GL11.GL_TEXTURE_2D, 0, internalformat, width, height, 0, format, GL11.GL_UNSIGNED_BYTE, data);
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

	public void setWidth(int width) {
		this.width = width;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public static Texture2D createTexture(int width, int height, ByteBuffer data) {
		Texture2D ret = new Texture2D();
		ret.setWidth(width);
		ret.setHeight(height);
		ret.bind();
		ret.noFilter();
		ret.clampToBorder();
		ret.uploadData(GL11.GL_RGBA, width, height, GL11.GL_RGBA, data);
		ret.unbind();
		return ret;
	}

	public static Texture2D loadTexture(String dir, String fileName) {
		ByteBuffer data;
		String path = ResourceLoader.getAbsoluPath("texture/" + dir + fileName);
		int width, height;

		try (MemoryStack stack = MemoryStack.stackPush()) {
			IntBuffer w = stack.mallocInt(1);
			IntBuffer h = stack.mallocInt(1);
			IntBuffer comp = stack.mallocInt(1);

			stbi_set_flip_vertically_on_load(true);
			data = stbi_load(path, w, h, comp, 4);
			if (data == null)
				throw new RuntimeException(
						"Failed to load a image file!" + System.lineSeparator() + stbi_failure_reason());

			width = w.get();
			height = h.get();
		}
		return createTexture(width, height, data);
	}

}
