package poke.core.gl.deferred;

import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL42;
import org.lwjgl.opengl.GL43;

import poke.core.engine.core.Window;
import poke.core.engine.light.LightHandler;
import poke.core.engine.light.PointLight;
import poke.core.engine.model.Texture2D;
import poke.core.engine.scene.GameObject;
import poke.core.engine.shader.Shader;

public class DeferredShader extends Shader {

	private static DeferredShader instance;

	public static DeferredShader getInstance() {
		if (instance == null)
			instance = new DeferredShader();
		return instance;
	}

	public DeferredShader() {
		super();
		addComputeShader("deferred/deferred_c.glsl");
		validateShader();

		addUniform("num_PointLight");
		for (int i = 0; i < LightHandler.getPointLights().size(); i++) {
			addUniform("pointLights[" + i + "].diffuse");
			addUniform("pointLights[" + i + "].attenuation");
			addUniform("pointLights[" + i + "].position");
			addUniform("pointLights[" + i + "].intensity");
		}
	}

	@Override
	public void updateUniforms(GameObject object) {

	}

	public void updateUniforms(Texture2D scene, Texture2D position, Texture2D albedo, Texture2D normal) {
		GL42.glBindImageTexture(0, position.getId(), 0, false, 0, GL15.GL_READ_ONLY, position.getInternalFormat());
		GL42.glBindImageTexture(1, albedo.getId(), 0, false, 0, GL15.GL_READ_ONLY, albedo.getInternalFormat());
		GL42.glBindImageTexture(2, normal.getId(), 0, false, 0, GL15.GL_READ_ONLY, normal.getInternalFormat());
		GL42.glBindImageTexture(5, scene.getId(), 0, false, 0, GL15.GL_WRITE_ONLY, scene.getInternalFormat());
		GL43.glDispatchCompute(Window.width / 16, Window.height / 16, 1);
		GL42.glMemoryBarrier(GL42.GL_SHADER_IMAGE_ACCESS_BARRIER_BIT);

		setUniform("num_PointLight", LightHandler.getPointLights().size());
		for(int i = 0; i < LightHandler.getPointLights().size(); i++) {
			PointLight light = LightHandler.getPointLights().get(i);
			setUniform("pointLights[" + i + "].diffuse", light.getDiffuse().toVector4f());
			setUniform("pointLights[" + i + "].attenuation", light.getAttenuation());
			setUniform("pointLights[" + i + "].position", light.getWorldTransform().getTranslation());
			setUniform("pointLights[" + i + "].intensity", light.getIntensity());
		}

	}

}
