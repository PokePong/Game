package poke.core.module.sky;

import poke.core.engine.scene.GameObject;
import poke.core.engine.shader.Shader;
import poke.core.engine.utils.Constants;

public class SkyShader extends Shader {

	private static SkyShader instance = null;

	public static SkyShader getInstance() {
		if (instance == null)
			instance = new SkyShader();
		return instance;
	}

	protected SkyShader() {
		super();

		addVertexShader("sky/sky_v.glsl");
		addFragmentShader("sky/sky_f.glsl");
		validateShader();

		addUniform("m_World");
		addUniformBlock("Camera");
	}

	@Override
	public void updateUniforms(GameObject object) {
		setUniform("m_World", object.getWorldTransform().getWorldMatrix());
		setUniformBlock("Camera", Constants.CAMERA_UBO_BINDING_INDEX);
	}

}
