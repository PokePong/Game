package poke.core.gl.renderer;

import poke.core.engine.renderer.Renderer;
import poke.core.gl.config.Default;
import poke.core.gl.shader.StaticShader;

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
