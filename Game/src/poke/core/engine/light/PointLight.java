package poke.core.engine.light;

import org.joml.Vector3f;

import poke.core.module.color.Color4f;

public class PointLight extends Light {

	private Vector3f attenuation;

	public PointLight() {
		this(Color4f.WHITE, new Vector3f(1, 0.7f, 0.5f), 1f);
	}

	public PointLight(Color4f diffuse) {
		this(diffuse, new Vector3f(1, 0.7f, 0.5f), 1f);
	}

	public PointLight(Color4f diffuse, Vector3f attenuation) {
		this(diffuse, attenuation, 1f);
	}

	public PointLight(Color4f diffuse, Vector3f attenuation, float intensity) {
		super(diffuse, intensity);
		this.attenuation = attenuation;
		LightHandler.addPointLight(this);
	}

	public Vector3f getAttenuation() {
		return attenuation;
	}

	public void setAttenuation(Vector3f attenuation) {
		this.attenuation = attenuation;
	}

}
