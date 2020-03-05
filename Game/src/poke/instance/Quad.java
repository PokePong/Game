package poke.instance;

import poke.core.engine.model.Mesh;
import poke.core.engine.scene.GameObject;
import poke.core.engine.utils.MeshGenerator;
import poke.core.gl.buffer.MeshVBO;

public class Quad extends GameObject {

	@Override
	public void _init_() {
		Mesh m = MeshGenerator.createQuad();
		MeshVBO vbo = new MeshVBO(m);
		super.setVbo(vbo);

	}

	@Override
	public void _update_(double delta) {

	}

}
