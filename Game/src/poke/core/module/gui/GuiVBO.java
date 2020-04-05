package poke.core.module.gui;

import static org.lwjgl.opengl.GL11.GL_FLOAT;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL15.GL_ARRAY_BUFFER;
import static org.lwjgl.opengl.GL15.GL_STATIC_DRAW;
import static org.lwjgl.opengl.GL15.glBindBuffer;
import static org.lwjgl.opengl.GL15.glBufferData;
import static org.lwjgl.opengl.GL15.glDeleteBuffers;
import static org.lwjgl.opengl.GL15.glGenBuffers;
import static org.lwjgl.opengl.GL20.glDisableVertexAttribArray;
import static org.lwjgl.opengl.GL20.glEnableVertexAttribArray;
import static org.lwjgl.opengl.GL20.glVertexAttribPointer;
import static org.lwjgl.opengl.GL30.glBindVertexArray;
import static org.lwjgl.opengl.GL30.glDeleteVertexArrays;
import static org.lwjgl.opengl.GL30.glGenVertexArrays;

import java.nio.FloatBuffer;

import org.lwjgl.system.MemoryUtil;

import poke.core.engine.gl.VBO;
import poke.core.engine.utils.Buffer;

public class GuiVBO implements VBO {

	public int vaoId;
	public int vboId;
	private int size;

	private float[] positions = { -0.5f, 0.5f, 0.5f, 0.5f, -0.5f, -0.5f, 0.5f, -0.5f };

	public GuiVBO() {
		this.vaoId = glGenVertexArrays();
		this.vboId = glGenBuffers();
		this.size = positions.length / 2;
		addData();
	}

	private void addData() {
		FloatBuffer verticesBuffer;
		glBindVertexArray(vaoId);
		verticesBuffer = Buffer.createFlippedBuffer(positions);
		glBindBuffer(GL_ARRAY_BUFFER, vboId);
		glBufferData(GL_ARRAY_BUFFER, verticesBuffer, GL_STATIC_DRAW);
		MemoryUtil.memFree(verticesBuffer);

		glVertexAttribPointer(0, 2, GL_FLOAT, false, 0, 0);

		glBindBuffer(GL_ARRAY_BUFFER, 0);
		glBindVertexArray(0);
	}

	@Override
	public void render() {
		glBindVertexArray(vaoId);
		glEnableVertexAttribArray(0);
		glDrawArrays(GL_TRIANGLE_STRIP, 0, getVertexCount());
		glDisableVertexAttribArray(0);
		glBindVertexArray(0);
	}

	@Override
	public void cleanUp() {
		glDisableVertexAttribArray(0);
		glBindBuffer(GL_ARRAY_BUFFER, 0);
		glDeleteBuffers(vboId);
		glBindVertexArray(0);
		glDeleteVertexArrays(vaoId);
	}

	@Override
	public int getVertexCount() {
		return size;
	}

}
