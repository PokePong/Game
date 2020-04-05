package poke.core.gl.buffer.vbo;

import static org.lwjgl.opengl.GL11.GL_FLOAT;
import static org.lwjgl.opengl.GL11.GL_TRIANGLES;
import static org.lwjgl.opengl.GL11.GL_UNSIGNED_INT;
import static org.lwjgl.opengl.GL11.glDrawElements;
import static org.lwjgl.opengl.GL15.GL_ARRAY_BUFFER;
import static org.lwjgl.opengl.GL15.GL_ELEMENT_ARRAY_BUFFER;
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
import java.nio.IntBuffer;

import org.lwjgl.system.MemoryUtil;

import poke.core.engine.gl.VBO;
import poke.core.engine.utils.Buffer;

public class ScreenVBO implements VBO {

	public int vaoId;
	public int vboId;
	public int iboId;
	private float[] positions = { -1, 1, 1, 1, -1, -1, 1, -1 };
	private int[] indices = { 0, 1, 2, 2, 1, 3 };

	public ScreenVBO() {
		this.vaoId = glGenVertexArrays();
		this.vboId = glGenBuffers();
		this.iboId = glGenBuffers();
		addData();
	}

	private void addData() {
		FloatBuffer verticesBuffer;
		IntBuffer indicesBuffer;
		glBindVertexArray(vaoId);
		verticesBuffer = Buffer.createFloatBuffer(getVertexCount() * 2);
		verticesBuffer.put(positions);
		verticesBuffer.flip();
		glBindBuffer(GL_ARRAY_BUFFER, vboId);
		glBufferData(GL_ARRAY_BUFFER, verticesBuffer, GL_STATIC_DRAW);
		MemoryUtil.memFree(verticesBuffer);

		indicesBuffer = Buffer.createFlippedBuffer(indices);
		glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, iboId);
		glBufferData(GL_ELEMENT_ARRAY_BUFFER, indicesBuffer, GL_STATIC_DRAW);
		MemoryUtil.memFree(indicesBuffer);

		glVertexAttribPointer(0, 2, GL_FLOAT, false, Float.BYTES * 2, 0);

		glBindBuffer(GL_ARRAY_BUFFER, 0);
		glBindVertexArray(0);
	}

	@Override
	public void render() {
		glBindVertexArray(vaoId);
		glEnableVertexAttribArray(0);
		glDrawElements(GL_TRIANGLES, getVertexCount(), GL_UNSIGNED_INT, 0);
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
		return indices.length;
	}

}
