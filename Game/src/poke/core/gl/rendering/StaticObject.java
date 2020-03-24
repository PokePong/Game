package poke.core.gl.rendering;

import poke.core.engine.renderer.Renderer;
import poke.core.engine.scene.GameObject;
import poke.core.engine.utils.Constants;

public abstract class StaticObject extends GameObject {

	@Override
	public void init() {
		super.init();
		if (!getComponents().containsKey(Constants.RENDERER_COMPONENT)) {
			Renderer renderer = new StaticRenderer();
			renderer.setParent(this);
			getComponents().put(Constants.RENDERER_COMPONENT, renderer);
		}
	}


}
