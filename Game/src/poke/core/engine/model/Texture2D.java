package poke.core.engine.model;

import static org.lwjgl.opengl.GL11.GL_DEPTH_COMPONENT;
import static org.lwjgl.opengl.GL11.GL_FLOAT;
import static org.lwjgl.opengl.GL11.GL_LINEAR;
import static org.lwjgl.opengl.GL11.GL_NEAREST;
import static org.lwjgl.opengl.GL11.GL_REPEAT;
import static org.lwjgl.opengl.GL11.GL_RGBA;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_MAG_FILTER;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_MIN_FILTER;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_WRAP_S;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_WRAP_T;
import static org.lwjgl.opengl.GL11.GL_UNSIGNED_BYTE;
import static org.lwjgl.opengl.GL11.glBindTexture;
import static org.lwjgl.opengl.GL11.glDeleteTextures;
import static org.lwjgl.opengl.GL11.glGenTextures;
import static org.lwjgl.opengl.GL11.glTexImage2D;
import static org.lwjgl.opengl.GL11.glTexParameteri;
import static org.lwjgl.opengl.GL12.GL_CLAMP_TO_EDGE;
import static org.lwjgl.opengl.GL13.GL_CLAMP_TO_BORDER;
import static org.lwjgl.opengl.GL14.GL_DEPTH_COMPONENT32;
import static org.lwjgl.opengl.GL30.*;
import static org.lwjgl.stb.STBImage.stbi_failure_reason;
import static org.lwjgl.stb.STBImage.stbi_load;
import static org.lwjgl.stb.STBImage.stbi_set_flip_vertically_on_load;

import java.nio.ByteBuffer;
import java.nio.IntBuffer;

import org.joml.Vector2i;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL32;
import org.lwjgl.system.MemoryStack;

import poke.core.engine.utils.Constants;
import poke.core.engine.utils.ResourceLoader;

public class Texture2D {

	private int id;

	private int type;
	private int internalFormat;
	private int format;
	private int dataType;
	private int width;
	private int height;

	private boolean isDepth = false;

	public Texture2D(int type, int width, int height, int id) {
		this.type = type;
		this.width = width;
		this.height = height;
		this.id = id;
		this.dataType = GL_FLOAT;
		this.internalFormat = GL_RGBA16F;
		this.format = GL_RGBA;
	}

	public Texture2D(int type, int width, int height) {
		this(type, width, height, glGenTextures());
	}

	public Texture2D(int width, int height) {
		this(GL_TEXTURE_2D, width, height);
	}

	public void bind() {
		glBindTexture(type, id);
	}

	public void unbind() {
		glBindTexture(type, 0);
	}

	public void cleanUp() {
		unbind();
		glDeleteTextures(id);
	}

	public void noFilter() {
		glTexParameteri(type, GL_TEXTURE_MAG_FILTER, GL_NEAREST);
		glTexParameteri(type, GL_TEXTURE_MIN_FILTER, GL_NEAREST);
	}

	public void linearFilter() {
		glTexParameteri(type, GL_TEXTURE_MAG_FILTER, GL_LINEAR);
		glTexParameteri(type, GL_TEXTURE_MIN_FILTER, GL_LINEAR);
	}

	public void clampToEdge() {
		glTexParameteri(type, GL_TEXTURE_WRAP_S, GL_CLAMP_TO_EDGE);
		glTexParameteri(type, GL_TEXTURE_WRAP_T, GL_CLAMP_TO_EDGE);
	}

	public void clampToBorder() {
		glTexParameteri(type, GL_TEXTURE_WRAP_S, GL_CLAMP_TO_BORDER);
		glTexParameteri(type, GL_TEXTURE_WRAP_T, GL_CLAMP_TO_BORDER);
	}

	public void repeat() {
		glTexParameteri(type, GL_TEXTURE_WRAP_S, GL_REPEAT);
		glTexParameteri(type, GL_TEXTURE_WRAP_T, GL_REPEAT);
	}

	public Texture2D allocateImage2D(int internalFormat, int format) {
		this.internalFormat = internalFormat;
		this.format = format;
		this.dataType = GL_FLOAT;
		allocate();
		return this;
	}

	public Texture2D allocateImage2D(int internalFormat, int format, ByteBuffer buffer) {
		this.internalFormat = internalFormat;
		this.format = format;
		this.dataType = GL_UNSIGNED_BYTE;
		allocate(buffer);
		return this;
	}

	public Texture2D allocateDepth() {
		this.internalFormat = GL_DEPTH_COMPONENT32;
		this.format = GL_DEPTH_COMPONENT;
		this.dataType = GL_FLOAT;
		allocate();
		isDepth = true;
		return this;
	}

	public Texture2D allocateDepthMultisample() {
		this.internalFormat = GL_DEPTH_COMPONENT32F;
		this.format = GL_DEPTH_COMPONENT;
		this.dataType = GL_FLOAT;
		allocate();
		isDepth = true;
		return this;
	}

	public void resize(int xSize, int ySize) {
		this.width = xSize;
		this.height = ySize;
		allocate();
	}

	public void resize(Vector2i size) {
		this.width = size.x;
		this.height = size.y;
		allocate();
	}

	private void allocate() {
		if (type == GL_TEXTURE_2D) {
			glTexImage2D(type, 0, internalFormat, width, height, 0, format, dataType, 0);
		} else {
			GL32.glTexImage2DMultisample(type, Constants.MULTISAMPLES, internalFormat, width, height, false);
		}
	}

	private void allocate(ByteBuffer buffer) {
		glTexImage2D(type, 0, internalFormat, width, height, 0, format, dataType, buffer);
	}

	public boolean isMultisample() {
		return type != GL_TEXTURE_2D;
	}

	public boolean isDepth() {
		return isDepth;
	}

	public int getId() {
		return id;
	}

	public int getType() {
		return type;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public int getInternalFormat() {
		return internalFormat;
	}

	public int getFormat() {
		return format;
	}

	public int getDataType() {
		return dataType;
	}

	public static Texture2D emptyTexture() {
		return new Texture2D(GL_TEXTURE_2D, 0, 0, glGenTextures());
	}

	public static Texture2D createTexture(int width, int height, ByteBuffer data) {
		Texture2D ret = new Texture2D(width, height);
		ret.bind();
		ret.noFilter();
		ret.clampToBorder();
		ret.allocateImage2D(GL11.GL_RGBA, GL11.GL_RGBA, data);
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
