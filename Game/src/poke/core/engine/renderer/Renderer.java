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
		shader.bind();
		config.enable();
		shader.updateUniforms(getParent());
		getParent().getVbo().render();
		config.disable();
		shader.unbind();
	}
	
	public void cleanUp() {
		shader.cleanUp();
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
