package poke.instance.terrain;

import java.util.HashMap;

import org.joml.Vector2f;
import org.joml.Vector3f;

import poke.core.engine.scene.Node;
import poke.core.gl.buffer.vbo.Patch2DVBO;

public class QuadtreeFace extends Node {

	public static final int RESOLUTION = 16;
	public static final int MAX_LOD = 3;

	private HashMap<Integer, Float> lodRanges;
	private Patch2DVBO patch;

	public QuadtreeFace() {
		this.lodRanges = new HashMap<Integer, Float>();
		this.lodRanges.put(0, 50f);
		this.lodRanges.put(1, 30f);
		this.lodRanges.put(2, 10f);
		this.lodRanges.put(3, 0f);
		this.patch = new Patch2DVBO(generatePatch());
		generateNodes();
	}

	private void generateNodes() {
		for (int i = 0; i < RESOLUTION; i++) {
			for (int j = 0; j < RESOLUTION; j++) {
				addChild(new QuadtreeNode(this, 0, new Vector2f(i - RESOLUTION / 2f, j - RESOLUTION / 2f), patch));
			}
		}
	}

	@Override
	public void init() {
		super.init();
		getLocalTransform().setScaling(new Vector3f(2f / RESOLUTION));
		setWorldTransform(getParent().getWorldTransform());
	}

	public Vector2f[] generatePatch() {
		Vector2f[] vertices = new Vector2f[4];
		int index = 0;
		vertices[index++] = new Vector2f(0f, 0f);
		vertices[index++] = new Vector2f(1f, 0f);
		vertices[index++] = new Vector2f(0f, 1f);
		vertices[index++] = new Vector2f(1f, 1f);
		return vertices;
	}

	public HashMap<Integer, Float> getLodRanges() {
		return lodRanges;
	}

}
