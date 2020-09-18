package poke.instance.planet.triangle;

import java.util.ArrayList;
import java.util.List;

import org.joml.Vector3f;
import org.joml.Vector4f;

import poke.core.engine.core.Window;
import poke.instance.camera.GameCamera;
import poke.instance.planet.Planet;
import poke.instance.planet.patch.PatchInstance;

public class Triangulator {

	public final float allowedTriPx = 400;

	private Planet planet;
	private Icosahedron icosahedron;
	private int maxLevel;
	private float[] triLevelSize;
	private float[] distanceLUT;

	private List<PatchInstance> patchInstances;
	private PatchInstance[] patchInstancesArray;

	public Triangulator(Planet planet, int maxLevel) {
		this.planet = planet;
		this.maxLevel = maxLevel;
		this.icosahedron = new Icosahedron();
		this.triLevelSize = new float[maxLevel];
		this.distanceLUT = new float[maxLevel];

		this.patchInstances = new ArrayList<>();
	}

	public void init() {
		precalculate();
		updateGeometry();
	}

	public void precalculate() {
		triLevelSize[0] = icosahedron.getLengthTriangle();
		for (int i = 1; i < maxLevel; i++) {
			triLevelSize[i] = triLevelSize[i - 1] / 2;
		}

		float frac = (float) Math.tan(Math.toRadians(allowedTriPx * GameCamera.getInstance().getFov() / Window.width));
		for (int i = 0; i < maxLevel; i++) {
			distanceLUT[i] = (triLevelSize[i] / frac) * planet.getRadius();
		}

	}

	public void updateGeometry() {
		precalculate();
		patchInstances.clear();

		for (TriangleNode node : icosahedron.getFaces()) {
			buildTriangle(node.getA(), node.getB(), node.getC(), 0);
		}

		patchInstancesArray = new PatchInstance[patchInstances.size()];
		patchInstances.toArray(patchInstancesArray);
	}

	private void buildTriangle(Vector3f a, Vector3f b, Vector3f c, int level) {
		boolean sub = subdivision(a, b, c, level);
		if (sub) {
			Vector3f A = new Vector3f(b).add(new Vector3f(c)).mul(0.5f);
			Vector3f B = new Vector3f(c).add(new Vector3f(a)).mul(0.5f);
			Vector3f C = new Vector3f(a).add(new Vector3f(b)).mul(0.5f);

			int nextLevel = level + 1;
			buildTriangle(a, B, C, nextLevel);
			buildTriangle(A, b, C, nextLevel);
			buildTriangle(A, B, c, nextLevel);
			buildTriangle(A, B, C, nextLevel);
		} else {
			Vector3f A = new Vector3f(a).normalize().mul(planet.getRadius());
			Vector3f B = new Vector3f(b).normalize().mul(planet.getRadius());
			Vector3f C = new Vector3f(c).normalize().mul(planet.getRadius());

			Vector3f R = new Vector3f(B).add(new Vector3f(A).mul(-1f));
			Vector3f S = new Vector3f(C).add(new Vector3f(A).mul(-1f));

			patchInstances.add(new PatchInstance(A, R, S, level));
		}
	}

	private boolean subdivision(Vector3f a, Vector3f b, Vector3f c, int level) {
		if (level == maxLevel)
			return false;

		Vector3f A = new Vector3f(a);
		Vector3f B = new Vector3f(b);
		Vector3f C = new Vector3f(c);
		Vector3f center = new Vector3f(A).add(new Vector3f(B)).add(new Vector3f(C));
		center.div(3f);
		center.normalize();
		center.mul(planet.getRadius());

		Vector3f worldPos = planet.getWorldPosition(center);
		Vector3f camPos = GameCamera.getInstance().getPosition();

		float distance = worldPos.distance(camPos);
		

		if (distance < distanceLUT[level])
			return true;
		return false;
	}
	

	public Planet getPlanet() {
		return planet;
	}

	public int getMaxLevel() {
		return maxLevel;
	}

	public void setMaxLevel(int maxLevel) {
		this.maxLevel = maxLevel;
	}

	public PatchInstance[] getPatchInstancesArray() {
		return patchInstancesArray;
	}

	public float[] getDistanceLUT() {
		return distanceLUT;
	}

}
