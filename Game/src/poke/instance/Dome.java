package poke.instance;

import poke.core.engine.utils.obj.ObjLoader;
import poke.core.gl.buffer.MeshVBO;
import poke.core.gl.rendering.StaticObject;

public class Dome extends StaticObject {

	@Override
	public void _init_() {
		setVbo(new MeshVBO(ObjLoader.loadMesh("geometric", "dome.obj")));
		getWorldTransform().translate(0, 5f, 0);
	}

	@Override
	public void _update_(double delta) {

	}

}
