package poke.instance.planet.patch;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL11.GL_TRIANGLES;
import static org.lwjgl.opengl.GL11.GL_UNSIGNED_INT;
import static org.lwjgl.opengl.GL15.GL_ARRAY_BUFFER;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL15.GL_STATIC_DRAW;
import static org.lwjgl.opengl.GL15.glBindBuffer;
import static org.lwjgl.opengl.GL15.glBufferData;
import static org.lwjgl.opengl.GL15.glDeleteBuffers;
import static org.lwjgl.opengl.GL15.glGenBuffers;
import static org.lwjgl.opengl.GL20.glDisableVertexAttribArray;
import static org.lwjgl.opengl.GL20.glEnableVertexAttribArray;
import static org.lwjgl.opengl.GL20.glVertexAttribPointer;
import static org.lwjgl.opengl.GL30.glBindVertexArray;
import static org.lwjgl.opengl.GL30.*;
import static org.lwjgl.opengl.GL31.*;
import static org.lwjgl.opengl.GL33.*;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.List;

import org.joml.Vector2f;
import org.lwjgl.system.MemoryUtil;

import poke.core.engine.gl.VBO;
import poke.core.engine.utils.Buffer;

public class PatchVBO implements VBO {

	public static float MORPHRANGE = 0.3f;
	
	public int vaoId;
	public int vboId;
	public int iboId;
	public int instanceId;

	private int size;
	private int numInstances;

	private List<PatchVertex> vertices;
	private List<Integer> indices;

	public PatchVBO() {
		this.vaoId = glGenVertexArrays();
		this.vboId = glGenBuffers();
		this.iboId = glGenBuffers();
		this.instanceId = glGenBuffers();

		this.vertices = new ArrayList<>();
		this.indices = new ArrayList<>();
	}

	public void uploadPatch(int level) {
		buildPatch(level);

		PatchVertex[] verticesArray = new PatchVertex[vertices.size()];
		vertices.toArray(verticesArray);

		int[] indicesArray = new int[indices.size()];
		for (int i = 0; i < indices.size(); i++) {
			indicesArray[i] = indices.get(i);
		}
		this.size = indices.size();

		glBindVertexArray(vaoId);

		FloatBuffer verticesBuffer = Buffer.createFlippedBufferAOSPatchVertex(verticesArray);
		glBindBuffer(GL_ARRAY_BUFFER, vboId);
		glBufferData(GL_ARRAY_BUFFER, verticesBuffer, GL_STATIC_DRAW);
		MemoryUtil.memFree(verticesBuffer);

		IntBuffer indicesBuffer = Buffer.createFlippedBuffer(indicesArray);
		glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, iboId);
		glBufferData(GL_ELEMENT_ARRAY_BUFFER, indicesBuffer, GL_STATIC_DRAW);
		MemoryUtil.memFree(indicesBuffer);

		glBindBuffer(GL_ARRAY_BUFFER, vboId);
		glVertexAttribPointer(0, 2, GL_FLOAT, false, PatchVertex.BYTES, 0);
		glVertexAttribPointer(1, 2, GL_FLOAT, false, PatchVertex.BYTES, 2 * Float.BYTES);

		glBindBuffer(GL_ARRAY_BUFFER, 0);
		glBindVertexArray(0);
	}

	public void uploadData(PatchInstance[] instances) {
		this.numInstances = instances.length;

		glBindVertexArray(vaoId);

		FloatBuffer verticesBuffer = Buffer.createFlippedBufferAOSPatchInstance(instances);
		glBindBuffer(GL_ARRAY_BUFFER, instanceId);
		glBufferData(GL_ARRAY_BUFFER, verticesBuffer, GL_DYNAMIC_DRAW);
		MemoryUtil.memFree(verticesBuffer);

		glVertexAttribPointer(2, 3, GL_FLOAT, false, PatchInstance.BYTES, 0);
		glVertexAttribPointer(3, 3, GL_FLOAT, false, PatchInstance.BYTES, 3 * Float.BYTES);
		glVertexAttribPointer(4, 3, GL_FLOAT, false, PatchInstance.BYTES, 6 * Float.BYTES);
		glVertexAttribPointer(5, 1, GL_FLOAT, false, PatchInstance.BYTES, 9 * Float.BYTES);
		glVertexAttribDivisor(2, 1);
		glVertexAttribDivisor(3, 1);
		glVertexAttribDivisor(4, 1);
		glVertexAttribDivisor(5, 1);

		glBindBuffer(GL_ARRAY_BUFFER, 0);
		glBindVertexArray(0);
	}

	@Override
	public void render() {
		glBindVertexArray(vaoId);
		glEnableVertexAttribArray(0);
		glEnableVertexAttribArray(1);
		glEnableVertexAttribArray(2);
		glEnableVertexAttribArray(3);
		glEnableVertexAttribArray(4);
		glEnableVertexAttribArray(5);
		glDrawElementsInstanced(GL_TRIANGLES, getVertexCount(), GL_UNSIGNED_INT, 0, numInstances);
		glDisableVertexAttribArray(0);
		glDisableVertexAttribArray(1);
		glDisableVertexAttribArray(2);
		glDisableVertexAttribArray(3);
		glDisableVertexAttribArray(4);
		glDisableVertexAttribArray(5);
		glBindVertexArray(0);
	}

	@Override
	public void cleanUp() {
		glDisableVertexAttribArray(0);
		glDisableVertexAttribArray(1);
		glDisableVertexAttribArray(2);
		glDisableVertexAttribArray(3);
		glDisableVertexAttribArray(4);
		glDisableVertexAttribArray(5);
		glBindBuffer(GL_ARRAY_BUFFER, 0);
		glDeleteBuffers(vboId);
		glDeleteBuffers(iboId);
		glDeleteBuffers(instanceId);
		glBindVertexArray(0);
		glDeleteVertexArrays(vaoId);
	}

	private void buildPatch(int level) {
		vertices.clear();
		indices.clear();

		int RC = (int) (1 + Math.pow(2, level));
		int rowIndex = 0;
		int nextIndex = 0;

		float delta = (float)1 / (RC - 1);

		for (int row = 0; row < RC; row++) {
			int numCols = RC - row;
			nextIndex += numCols;
			for (int column = 0; column < numCols; column++) {
				float x = column / (float) (RC - 1);
				float y = row / (float) (RC - 1);

				Vector2f position = new Vector2f(x, y);
				Vector2f morphism = new Vector2f(0, 0);
				
				if (row % 2 == 0) {
					if (column % 2 == 1)
						morphism = new Vector2f(-delta, 0);
				} else {
					if (column % 2 == 0)
						morphism = new Vector2f(0, delta);
					else
						morphism = new Vector2f(delta, -delta);
				}
				
				vertices.add(new PatchVertex(position, morphism));

				if (row < RC - 1 && column < numCols - 1) {
					indices.add(rowIndex + column);
					indices.add(nextIndex + column);
					indices.add(1 + rowIndex + column);
					if (column < numCols - 2) {
						indices.add(nextIndex + column);
						indices.add(1 + nextIndex + column);
						indices.add(1 + rowIndex + column);
					}
				}
			}
			rowIndex = nextIndex;
		}
	}

	@Override
	public int getVertexCount() {
		return size;
	}

}
