package poke.core.gl.rendering;

import poke.core.engine.scene.GameObject;
import poke.core.engine.shader.Shader;

public class StaticShader extends Shader {

	private static StaticShader instance = null;

	public static StaticShader getInstance() {
		if (instance == null)
			instance = new StaticShader();
		return instance;
	}

	protected StaticShader() {
		super();

		addVertexShader("static/vertex_static.glsl");
		addFragmentShader("static/fragment_static.glsl");
		validateShader();

		
		addUniform("m_World");
	}

	@Override
	public void updateUniforms(GameObject object) {
		setUniform("m_World", object.getWorldTransform().getWorldMatrix());
	}

}
