package poke.engine.gl;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL30.*;

import poke.engine.model.Mesh;
import poke.engine.model.Vertex;
import poke.engine.utils.Buffer;

public class MeshVBO implements VBO {

	public int vaoId;
	public int vboId;
	public int iboId;
	public int size;

	public MeshVBO(Mesh mesh) {
		this.vaoId = glGenVertexArrays();
		this.vboId = glGenBuffers();
		this.iboId = glGenBuffers();
		addData(mesh);
	}

	private void addData(Mesh mesh) {
		this.size = mesh.getIndices().length;

		glBindVertexArray(vaoId);

		glBindBuffer(GL_ARRAY_BUFFER, vboId);
		glBufferData(GL_ARRAY_BUFFER, Buffer.createFlippedBufferAOS(mesh.getVertices()), GL_STATIC_DRAW);

		glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, iboId);
		glBufferData(GL_ELEMENT_ARRAY_BUFFER, Buffer.createFlippedBuffer(mesh.getIndices()), GL_STATIC_DRAW);

		glVertexAttribPointer(0, 3, GL_FLOAT, false, Vertex.BYTES, 0);
		glVertexAttribPointer(1, 3, GL_FLOAT, false, Vertex.BYTES, Float.BYTES * 3);
		glVertexAttribPointer(2, 4, GL_FLOAT, false, Vertex.BYTES, Float.BYTES * 6);
		
		glBindBuffer(GL_ARRAY_BUFFER, 0);
		glBindVertexArray(0);
	}

	@Override
	public void draw() {
		glBindVertexArray(vaoId);
		glEnableVertexAttribArray(0);
		glEnableVertexAttribArray(1);
		glEnableVertexAttribArray(2);
		glDrawElements(GL_TRIANGLES, getVertexCount(), GL_UNSIGNED_INT, 0);
		glDrawArrays(GL_TRIANGLES, 0, getVertexCount());
		glDisableVertexAttribArray(0);
		glDisableVertexAttribArray(1);
		glDisableVertexAttribArray(2);
		glBindVertexArray(0);
	}

	@Override
	public void delete() {
		glBindVertexArray(vaoId);
		glDeleteBuffers(vboId);
		glDeleteVertexArrays(vaoId);
		glBindVertexArray(0);
	}

	@Override
	public int getVertexCount() {
		return size;
	}

}
