package poke.core.engine.renderer;

import poke.core.engine.scene.Component;
import poke.core.engine.shader.Shader;

public class Renderer extends Component {

	private Shader shader;
	private RenderConfig config;

	public Renderer(Shader shader, RenderConfig config) {
		super();
		this.shader = shader;
		this.config = config;
	}

	public void render() {
		config.enable();
		shader.bind();
		shader.updateUniforms(getParent());
		getParent().getVbo().render();
		shader.unbind();
		config.disable();
	}

	public Shader getShader() {
		return shader;
	}

	public void setShader(Shader shader) {
		this.shader = shader;
	}

	public RenderConfig getConfig() {
		return config;
	}

	public void setConfig(RenderConfig config) {
		this.config = config;
	}

}
