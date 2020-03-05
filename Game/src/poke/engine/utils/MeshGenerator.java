package poke.engine.utils;

import org.joml.Vector3f;

import poke.engine.model.Mesh;
import poke.engine.model.Vertex;

public class MeshGenerator {

	public static Mesh createQuad() {
		Vertex[] vertices = new Vertex[4];
		int[] indices = {0, 1, 2, 2, 1, 3};
		vertices[0] = new Vertex(new Vector3f(-0.5f, 0.5f, 0));
		vertices[1] = new Vertex(new Vector3f(0.5f, 0.5f, 0));
		vertices[2] = new Vertex(new Vector3f(-0.5f, -0.5f, 0));
		vertices[3] = new Vertex(new Vector3f(0.5f, -0.5f, 0));
		Mesh ret = new Mesh(vertices, indices);
		return ret;

	}

}
