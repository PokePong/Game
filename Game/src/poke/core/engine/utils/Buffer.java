package poke.core.engine.utils;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import org.joml.Matrix4f;
import org.lwjgl.BufferUtils;

import poke.core.engine.model.Vertex;

public class Buffer {

	public static FloatBuffer createFloatBuffer(int size) {
		return BufferUtils.createFloatBuffer(size);
	}

	public static IntBuffer createIntBuffer(int size) {
		return BufferUtils.createIntBuffer(size);
	}

	public static FloatBuffer createFlippedBuffer(float... values) {
		FloatBuffer ret = createFloatBuffer(values.length);
		ret.put(values);
		ret.flip();
		return ret;
	}

	public static IntBuffer createFlippedBuffer(int... values) {
		IntBuffer ret = createIntBuffer(values.length);
		ret.put(values);
		ret.flip();
		return ret;
	}

	public static FloatBuffer createFlippedBuffer(Matrix4f matrix) {
		FloatBuffer buffer = createFloatBuffer(4 * 4);
		buffer.put(matrix.m00());
		buffer.put(matrix.m01());
		buffer.put(matrix.m02());
		buffer.put(matrix.m03());
		buffer.put(matrix.m10());
		buffer.put(matrix.m11());
		buffer.put(matrix.m12());
		buffer.put(matrix.m13());
		buffer.put(matrix.m20());
		buffer.put(matrix.m21());
		buffer.put(matrix.m22());
		buffer.put(matrix.m23());
		buffer.put(matrix.m30());
		buffer.put(matrix.m31());
		buffer.put(matrix.m32());
		buffer.put(matrix.m33());
		buffer.flip();
		return buffer;
	}

	public static FloatBuffer createFlippedBufferAOS(Vertex[] vertices) {
		FloatBuffer ret = createFloatBuffer(vertices.length * Vertex.FLOATS);
		for (int i = 0; i < vertices.length; i++) {
			ret.put(vertices[i].getPosition().x);
			ret.put(vertices[i].getPosition().y);
			ret.put(vertices[i].getPosition().z);

			ret.put(vertices[i].getNormal().x);
			ret.put(vertices[i].getNormal().y);
			ret.put(vertices[i].getNormal().z);

			ret.put(vertices[i].getColor().x);
			ret.put(vertices[i].getColor().y);
			ret.put(vertices[i].getColor().z);
			ret.put(vertices[i].getColor().w);
		}
		ret.flip();
		return ret;
	}

}
