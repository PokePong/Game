package poke.instance;

import org.joml.Vector3f;

import poke.core.engine.scene.GameObject;
import poke.core.engine.utils.obj.ObjLoader;
import poke.core.gl.buffer.vbo.MeshVBO;

public class Lampadaire extends GameObject {

	@Override
	public void _init_() {
		getWorldTransform().setTranslation(new Vector3f(-5, 2, -5));
		setVbo(new MeshVBO(ObjLoader.loadMesh("geometric/", "dome.obj")));

		//Light light = new Light(new Vector4f(1, 1, 0, 1), 1f);
		//light.setParent(this);
		//addComponent("Light", light);
	}

	@Override
	public void _update_(double delta) {
		//getWorldTransform().translate(0, 0.01f, 0);
	}

}
