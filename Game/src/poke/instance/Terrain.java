package poke.instance;

import org.joml.Vector4f;

import poke.core.engine.model.Mesh;
import poke.core.engine.utils.MeshGenerator;
import poke.core.gl.buffer.MeshVBO;
import poke.core.gl.rendering.StaticObject;

public class Terrain extends StaticObject {

	private Vector4f color;
	
	@Override
	public void _init_() {
		this.color = new Vector4f(35/255f, 142/255f, 35/255f, 1);
		Mesh m = MeshGenerator.createQuad();
		m.getVertices()[0].setColor(color);
		m.getVertices()[1].setColor(color);
		m.getVertices()[2].setColor(color);
		m.getVertices()[3].setColor(color);
		super.setVbo(new MeshVBO(m));
		getWorldTransform().scale(50f);
		getWorldTransform().rotate(-(float) (Math.PI / 2), 0, 0);
	}

	@Override
	public void _update_(double delta) {
		//getWorldTransform().translate((float) (1f * delta) , 0, 0);
	}
	
	

}
