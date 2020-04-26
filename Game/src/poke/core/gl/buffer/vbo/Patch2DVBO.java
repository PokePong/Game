package poke.core.gl.buffer.vbo;

import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL30.*;

import java.nio.FloatBuffer;

import org.joml.Vector2f;
import static org.lwjgl.opengl.GL40.*;
import org.lwjgl.system.MemoryUtil;

import poke.core.engine.gl.VBO;
import poke.core.engine.utils.Buffer;

public class Patch2DVBO implements VBO {

	public int vaoId;
	public int vboId;
	private int size;

	public Patch2DVBO(Vector2f[] vertices) {
		this.vaoId = glGenVertexArrays();
		this.vboId = glGenBuffers();
		this.size = vertices.length;
		addData(vertices);
	}

	private void addData(Vector2f[] vertices) {
		FloatBuffer verticesBuffer;
		glBindVertexArray(vaoId);

		verticesBuffer = Buffer.createFlippedBuffer(vertices);
		glBindBuffer(GL_ARRAY_BUFFER, vboId);
		glBufferData(GL_ARRAY_BUFFER, verticesBuffer, GL_STATIC_DRAW);
		MemoryUtil.memFree(verticesBuffer);

		glVertexAttribPointer(0, 2, GL_FLOAT, false, Float.BYTES * 2, 0);
		glPatchParameteri(GL_PATCH_VERTICES, size);

		glBindBuffer(GL_ARRAY_BUFFER, 0);
		glBindVertexArray(0);
	}

	@Override
	public void render() {
		glBindVertexArray(vaoId);
		glEnableVertexAttribArray(0);
		glDrawArrays(GL_PATCHES, 0, size);
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
