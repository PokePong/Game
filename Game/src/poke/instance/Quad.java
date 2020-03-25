package poke.instance;

import poke.core.engine.model.Mesh;
import poke.core.engine.utils.MeshGenerator;
import poke.core.gl.buffer.MeshVBO;
import poke.core.gl.rendering.StaticObject;

public class Quad extends StaticObject {

	@Override
	public void _init_() {
		Mesh m = MeshGenerator.createQuad();
		super.setVbo(new MeshVBO(m));
		getWorldTransform().translate(0, 0, -5.5f);
	}

	@Override
	public void _update_(double delta) {
		//getWorldTransform().translate((float) (0.1f * delta) , 0, 0);
	}
	
	

}
