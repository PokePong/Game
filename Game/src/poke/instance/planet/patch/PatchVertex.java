package poke.instance.planet.patch;

import org.joml.Vector2f;

public class PatchVertex {

	public static final int FLOATS = 4;
	public static final int BYTES = FLOATS * Float.BYTES;

	private Vector2f position;
	private Vector2f morphism;

	public PatchVertex(Vector2f position, Vector2f morphism) {
		this.position = position;
		this.morphism = morphism;
	}

	public Vector2f getPosition() {
		return position;
	}

	public void setPosition(Vector2f position) {
		this.position = position;
	}

	public Vector2f getMorphism() {
		return morphism;
	}

	public void setMorphism(Vector2f morphism) {
		this.morphism = morphism;
	}

}
