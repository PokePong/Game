package poke.instance.planet.patch;

import org.joml.Vector3f;

public class PatchInstance {

	public static final int FLOATS = 10;
	public static final int BYTES = FLOATS * Float.BYTES;

	private Vector3f A;
	private Vector3f R;
	private Vector3f S;
	private int level;

	public PatchInstance(Vector3f a, Vector3f r, Vector3f s, int level) {
		this.A = a;
		this.R = r;
		this.S = s;
		this.level = level;
	}

	public Vector3f getA() {
		return A;
	}

	public void setA(Vector3f a) {
		A = a;
	}

	public Vector3f getR() {
		return R;
	}

	public void setR(Vector3f r) {
		R = r;
	}

	public Vector3f getS() {
		return S;
	}

	public void setS(Vector3f s) {
		S = s;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

}
