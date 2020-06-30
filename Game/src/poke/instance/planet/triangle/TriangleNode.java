package poke.instance.planet.triangle;

import org.joml.Vector3f;

public class TriangleNode {

	private Vector3f A;
	private Vector3f B;
	private Vector3f C;
	private TriangleNodeType type;
	
	public TriangleNode(Vector3f A, Vector3f B, Vector3f C) {
		this.A = A;
		this.B = B;
		this.C = C;
	}

	public Vector3f getA() {
		return A;
	}

	public void setA(Vector3f a) {
		this.A = a;
	}

	public Vector3f getB() {
		return B;
	}

	public void setB(Vector3f b) {
		this.B = b;
	}

	public Vector3f getC() {
		return C;
	}

	public void setC(Vector3f c) {
		this.C = c;
	}

	public TriangleNodeType getType() {
		return type;
	}

	public void setType(TriangleNodeType type) {
		this.type = type;
	}
	
	
	
	
	
}
