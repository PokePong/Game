package poke.instance;

import poke.engine.shader.Shader;

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
	}

}
