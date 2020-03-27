package poke.core.module.sky;

import poke.core.engine.model.Mesh;
import poke.core.engine.renderer.Renderer;
import poke.core.engine.scene.GameObject;
import poke.core.engine.utils.Constants;
import poke.core.engine.utils.obj.ObjLoader;
import poke.core.gl.buffer.MeshVBO;
import poke.core.gl.config.CCW;
import poke.core.gl.config.Default;

public class SkyDome extends GameObject {

	@Override
	public void _init_() {
		Mesh m = ObjLoader.loadMesh("geometric", "dome.obj");
		setVbo(new MeshVBO(m));
		getWorldTransform().scale(1000);

		Renderer renderer = new Renderer(SkyShader.getInstance(), new CCW());
		renderer.setParent(this);
		addComponent(Constants.RENDERER_COMPONENT, renderer);
	}

	@Override
	public void _update_(double delta) {

	}

}
