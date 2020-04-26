package poke.instance.terrain;

import poke.core.engine.scene.GameObject;
import poke.core.engine.shader.Shader;
import poke.core.engine.utils.Constants;

public class QuadtreeShader extends Shader {

	private static QuadtreeShader instance;

	public static QuadtreeShader getInstance() {
		if (instance == null) {
			instance = new QuadtreeShader();
		}
		return instance;
	}

	public QuadtreeShader() {
		addVertexShader("terrain/terrain_v.glsl");
		addTessellationControlShader("terrain/terrain_tc.glsl");
		addTesselationEvaludationShader("terrain/terrain_te.glsl");
		addGeometryShader("terrain/terrain_g.glsl");
		addFragmentShader("terrain/terrain_f.glsl");
		validateShader();

		addUniform("m_Local");
		addUniform("m_Face");
		addUniform("m_World");
		addUniform("lod");
		addUniformBlock("Camera");
	}

	@Override
	public void updateUniforms(GameObject object) {
		setUniform("m_Local", object.getLocalTransform().getWorldMatrix());
		setUniform("m_Face", object.getWorldTransform().getWorldMatrix());
		setUniform("m_World", ((QuadtreeNode) object).getFace().getWorldTransform().getWorldMatrix());
		setUniform("lod", ((QuadtreeNode) object).getLod());
		setUniformBlock("Camera", Constants.CAMERA_UBO_BINDING_INDEX);
	}

}
