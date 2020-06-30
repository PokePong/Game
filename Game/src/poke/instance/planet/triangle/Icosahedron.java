package poke.instance.planet.triangle;

import java.util.ArrayList;
import java.util.List;

import org.joml.Vector3f;

public class Icosahedron {

	private List<Vector3f> ortho;
	private List<TriangleNode> faces;
	private float lengthTriangle;

	public Icosahedron() {
		this.ortho = new ArrayList<>();
		this.faces = new ArrayList<>();
		build();
	}

	private void build() {
		float phi = (float) ((1 + Math.sqrt(5)) / 2f);

		// Construction du plan orthonormé
		// Plan X
		ortho.add(new Vector3f(phi, 0, -1)); // 0
		ortho.add(new Vector3f(-phi, 0, -1)); // 1
		ortho.add(new Vector3f(phi, 0, 1)); // 2
		ortho.add(new Vector3f(-phi, 0, 1)); // 3
		// Plan Y
		ortho.add(new Vector3f(0, -1, phi)); // 4
		ortho.add(new Vector3f(0, -1, -phi)); // 5
		ortho.add(new Vector3f(0, 1, phi)); // 6
		ortho.add(new Vector3f(0, 1, -phi)); // 7

		// Plan Z
		ortho.add(new Vector3f(-1, phi, 0)); // 8
		ortho.add(new Vector3f(-1, -phi, 0)); // 9
		ortho.add(new Vector3f(1, phi, 0)); // 10
		ortho.add(new Vector3f(1, -phi, 0)); // 11

		// Construction icosahedron 20 faces
		faces.add(new TriangleNode(ortho.get(1), ortho.get(3), ortho.get(8)));
		faces.add(new TriangleNode(ortho.get(1), ortho.get(3), ortho.get(9)));
		faces.add(new TriangleNode(ortho.get(0), ortho.get(2), ortho.get(10)));
		faces.add(new TriangleNode(ortho.get(0), ortho.get(2), ortho.get(11)));

		faces.add(new TriangleNode(ortho.get(5), ortho.get(7), ortho.get(0)));
		faces.add(new TriangleNode(ortho.get(5), ortho.get(7), ortho.get(1)));
		faces.add(new TriangleNode(ortho.get(4), ortho.get(6), ortho.get(2)));
		faces.add(new TriangleNode(ortho.get(4), ortho.get(6), ortho.get(3)));

		faces.add(new TriangleNode(ortho.get(9), ortho.get(11), ortho.get(4)));
		faces.add(new TriangleNode(ortho.get(9), ortho.get(11), ortho.get(5)));
		faces.add(new TriangleNode(ortho.get(8), ortho.get(10), ortho.get(6)));
		faces.add(new TriangleNode(ortho.get(8), ortho.get(10), ortho.get(7)));

		faces.add(new TriangleNode(ortho.get(1), ortho.get(7), ortho.get(8)));
		faces.add(new TriangleNode(ortho.get(1), ortho.get(5), ortho.get(9)));
		faces.add(new TriangleNode(ortho.get(0), ortho.get(7), ortho.get(10)));
		faces.add(new TriangleNode(ortho.get(0), ortho.get(5), ortho.get(11)));

		faces.add(new TriangleNode(ortho.get(3), ortho.get(6), ortho.get(8)));
		faces.add(new TriangleNode(ortho.get(3), ortho.get(4), ortho.get(9)));
		faces.add(new TriangleNode(ortho.get(2), ortho.get(6), ortho.get(10)));
		faces.add(new TriangleNode(ortho.get(2), ortho.get(4), ortho.get(11)));

		this.lengthTriangle = faces.get(0).getA().distance(faces.get(0).getB());
	}

	public float getLengthTriangle() {
		return lengthTriangle;
	}

	public List<Vector3f> getOrtho() {
		return ortho;
	}

	public List<TriangleNode> getFaces() {
		return faces;
	}

}
