package poke.engine.model;

import org.joml.Vector3f;
import org.joml.Vector4f;

public class Vertex {

	public static final int FLOATS = 10;
	public static final int BYTES = FLOATS * Float.BYTES;

	public Vector3f position;
	public Vector3f normal;
	public Vector4f color;

	public Vertex(Vector3f position) {
		new Vertex(position, new Vector3f(0, 0, 0), new Vector4f(1, 1, 1, 1));
	}

	public Vertex(Vector3f position, Vector4f color) {
		new Vertex(position, new Vector3f(0, 0, 0), color);
	}

	public Vertex(Vector3f position, Vector3f normal, Vector4f color) {
		this.position = position;
		this.normal = normal;
		this.color = color;
	}

	public Vector3f getPosition() {
		return position;
	}

	public void setPosition(Vector3f position) {
		this.position = position;
	}

	public Vector3f getNormal() {
		return normal;
	}

	public void setNormal(Vector3f normal) {
		this.normal = normal;
	}

	public Vector4f getColor() {
		return color;
	}

	public void setColor(Vector4f color) {
		this.color = color;
	}

}
