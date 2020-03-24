package poke.core.gl.rendering;

import poke.core.engine.renderer.Renderer;
import poke.core.gl.config.Default;

public class StaticRenderer extends Renderer {


	public StaticRenderer() {
		super(StaticShader.getInstance(), new Default());
	}

	@Override
	public void render() {
		getConfig().enable();
		getShader().updateUniforms(getParent());
		getParent().getVbo().render();
		getConfig().disable();
	}

}
