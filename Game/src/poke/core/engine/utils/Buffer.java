package poke.core.engine.utils;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.joml.Vector4f;
import org.lwjgl.system.MemoryStack;
import org.lwjgl.system.MemoryUtil;

import poke.core.engine.model.Vertex;

public class Buffer {

	public static FloatBuffer createFloatBuffer(int size) {
		return MemoryUtil.memAllocFloat(size);
	}

	public static IntBuffer createIntBuffer(int size) {
		return MemoryUtil.memAllocInt(size);
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

	public static FloatBuffer createFlippedBuffer(Vector3f vector) {
		FloatBuffer buffer = createFloatBuffer(Float.BYTES * 3);
		buffer.put(vector.x);
		buffer.put(vector.y);
		buffer.put(vector.z);
		buffer.flip();
		return buffer;
	}
	
	public static FloatBuffer createFlippedBuffer(Vector4f vector) {
		FloatBuffer buffer = createFloatBuffer(Float.BYTES * 4);
		buffer.put(vector.x);
		buffer.put(vector.y);
		buffer.put(vector.z);
		buffer.put(vector.w);
		buffer.flip();
		return buffer;
	}

	public static FloatBuffer createFlippedBuffer(Matrix4f matrix) {
		try (MemoryStack stack = MemoryStack.stackPush()) {
			FloatBuffer buffer = stack.mallocFloat(4 * 4);
			matrix.get(buffer);
			return buffer;
		}
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
	
	public static FloatBuffer createFlippedBufferAOSTex(Vertex[] vertices) {
		FloatBuffer ret = createFloatBuffer(vertices.length * Vertex.FLOATS_TEX);
		for (int i = 0; i < vertices.length; i++) {
			ret.put(vertices[i].getPosition().x);
			ret.put(vertices[i].getPosition().y);
			ret.put(vertices[i].getPosition().z);
			
			ret.put(vertices[i].getTexture().x);
			ret.put(vertices[i].getTexture().y);
			
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
