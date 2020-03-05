package poke.engine.utils;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import org.lwjgl.BufferUtils;

import poke.engine.model.Vertex;

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
