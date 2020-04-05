package poke.core.gl.buffer.vbo;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL15.GL_ARRAY_BUFFER;
import static org.lwjgl.opengl.GL15.GL_STATIC_DRAW;
import static org.lwjgl.opengl.GL15.glBindBuffer;
import static org.lwjgl.opengl.GL15.glBufferData;
import static org.lwjgl.opengl.GL15.glDeleteBuffers;
import static org.lwjgl.opengl.GL15.glGenBuffers;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL30.glBindVertexArray;
import static org.lwjgl.opengl.GL30.glDeleteVertexArrays;
import static org.lwjgl.opengl.GL30.glGenVertexArrays;

import java.nio.FloatBuffer;

import org.joml.Vector3f;
import org.lwjgl.system.MemoryUtil;

import poke.core.engine.gl.VBO;
import poke.core.engine.utils.Buffer;

public class PointVBO implements VBO {

	private int vaoId;
	private int vboId;
	private int size;

	public PointVBO(Vector3f position) {
		this.vaoId = glGenVertexArrays();
		this.vboId = glGenBuffers();
		this.size = 3;
		addData(position);
	}

	private void addData(Vector3f position) {
		FloatBuffer buffer;
		glBindVertexArray(vaoId);
		buffer = Buffer.createFlippedBuffer(position);
		glBindBuffer(GL_ARRAY_BUFFER, vboId);
		glBufferData(GL_ARRAY_BUFFER, buffer, GL_STATIC_DRAW);
		MemoryUtil.memFree(buffer);

		glVertexAttribPointer(0, 3, GL_FLOAT, false, Float.BYTES * 3, 0);

		glBindBuffer(GL_ARRAY_BUFFER, 0);
		glBindVertexArray(0);
	}

	@Override
	public void render() {
		glBindVertexArray(vaoId);
		glEnableVertexAttribArray(0);
		glDrawArrays(GL_POINT, 0, getVertexCount());
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
