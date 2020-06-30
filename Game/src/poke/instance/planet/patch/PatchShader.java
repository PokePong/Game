package poke.instance.planet.patch;

import poke.core.engine.scene.GameObject;
import poke.core.engine.shader.Shader;
import poke.core.engine.utils.Constants;
import poke.instance.planet.Planet;

public class PatchShader extends Shader {

	private static PatchShader instance;

	public static PatchShader getInstance() {
		if (instance == null) {
			instance = new PatchShader();
		}
		return instance;
	}

	public PatchShader() {
		addVertexShader("patch/patch_v.glsl");
		addFragmentShader("patch/patch_f.glsl");
		validateShader();

		addUniform("m_World");
		addUniform("radius");
		addUniform("maxLevel");
		addUniform("morphRange");
		addUniformBlock("Camera");
		for (int i = 0; i < 32; i++) {
			addUniform("distanceLUT[" + i + "]");
		}
	}

	@Override
	public void updateUniforms(GameObject object) {
		Planet planet = ((Planet) object);
		setUniform("m_World", planet.getWorldTransform().getWorldMatrix());
		setUniform("radius", planet.getRadius());
		setUniform("maxLevel", planet.getTriangulator().getMaxLevel());
		setUniform("morphRange", PatchVBO.MORPHRANGE);
		setUniformBlock("Camera", Constants.CAMERA_UBO_BINDING_INDEX);
		float[] distanceLUT = planet.getTriangulator().getDistanceLUT();
		for (int i = 0; i < 32; i++) {
			if (i < distanceLUT.length) {
				setUniform("distanceLUT[" + i + "]", distanceLUT[i]);
			} else {
				setUniform("distanceLUT[" + i + "]", 0.5f);
			}
			
		}
	}

}
