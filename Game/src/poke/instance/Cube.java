package poke.instance;

import poke.core.engine.model.Mesh;
import poke.core.engine.scene.GameObject;
import poke.core.engine.utils.obj.ObjLoader;
import poke.core.gl.buffer.vbo.MeshVBO;

public class Cube extends GameObject {

	@Override
	public void _init_() {
		Mesh m = ObjLoader.loadMesh("geometric/", "cube.obj");
		setVbo(new MeshVBO(m));
		getWorldTransform().translate(0, 3, 0);
	}

	@Override
	public void _update_(double delta) {

	}

}
