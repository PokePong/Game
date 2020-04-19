package poke.instance;

import java.util.Random;

import poke.core.engine.model.Mesh;
import poke.core.engine.scene.GameObject;
import poke.core.engine.utils.obj.ObjLoader;
import poke.core.gl.buffer.vbo.MeshVBO;
import poke.core.module.color.Color4f;

public class Cube extends GameObject {

	private Color4f color;
	private int size = 15;

	public Cube(Color4f color) {
		this.color = color;
	}

	@Override
	public void _init_() {
		Mesh m = ObjLoader.loadMesh("geometric/", "cube.obj");
		for (int i = 0; i < m.getVertices().length; i++) {
			m.getVertices()[i].setColor(color.toVector4f());
		}
		setVbo(new MeshVBO(m));
		int x = new Random().nextInt(size) - size/2;
		int y = new Random().nextInt(size) - size/2;
		int z = new Random().nextInt(size) - size/2;
		getWorldTransform().translate(x, y, z);
	}

	@Override
	public void _update_(double delta) {

	}

}
