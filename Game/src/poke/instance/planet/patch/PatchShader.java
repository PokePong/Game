package poke.instance.planet.patch;

import org.lwjgl.opengl.GL13;

import poke.core.engine.scene.GameObject;
import poke.core.engine.shader.Shader;
import poke.core.engine.utils.Constants;
import poke.instance.planet.Planet;
import poke.instance.planet.noise.Noise;

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

		addUniformBlock("Camera");

		addUniform("m_World");
		addUniform("radius");
		addUniform("maxLevel");
		addUniform("morphRange");
		for (int i = 0; i < 32; i++) {
			addUniform("distanceLUT[" + i + "]");
		}

		for (int i = 0; i < Noise.NOISE_SIZE; i++) {
			addUniform("random[" + i + "]");
		}
		addUniform("strength");
		addUniform("roughness");
		addUniform("center");
	}

	@Override
	public void updateUniforms(GameObject object) {
		Planet planet = ((Planet) object);

		setUniformBlock("Camera", Constants.CAMERA_UBO_BINDING_INDEX);

		setUniform("m_World", planet.getWorldTransform().getWorldMatrix());
		setUniform("radius", planet.getRadius());
		setUniform("maxLevel", planet.getTriangulator().getMaxLevel());
		setUniform("morphRange", PatchVBO.MORPHRANGE);
		float[] distanceLUT = planet.getTriangulator().getDistanceLUT();
		for (int i = 0; i < 32; i++) {
			if (i < distanceLUT.length) {
				setUniform("distanceLUT[" + i + "]", distanceLUT[i]);
			} else {
				setUniform("distanceLUT[" + i + "]", 0f);
			}
		}

		int[] random = planet.getNoiseFilter().getNoise().getRandom();
		for (int i = 0; i < Noise.NOISE_SIZE; i++) {
			setUniform("random[" + i + "]", random[i]);
		}
		setUniform("strength", planet.getNoiseFilter().getSettings().strength);
		setUniform("roughness", planet.getNoiseFilter().getSettings().roughness);
		setUniform("center", planet.getNoiseFilter().getSettings().center);

		GL13.glActiveTexture(GL13.GL_TEXTURE0);
		planet.getTexture().bind();
	}

}
