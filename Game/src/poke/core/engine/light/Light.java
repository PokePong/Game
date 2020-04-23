package poke.core.engine.light;

import poke.core.engine.scene.Component;
import poke.core.module.color.Color4f;

public class Light extends Component {

	private Color4f diffuse;
	private float intensity;

	public Light() {
		this.diffuse = Color4f.WHITE;
		this.intensity = 1f;
	}

	public Light(Color4f diffuse) {
		this.diffuse = diffuse;
		this.intensity = 1f;
	}

	public Light(Color4f diffuse, float intensity) {
		this.diffuse = diffuse;
		this.intensity = intensity;
	}

	public Color4f getDiffuse() {
		return diffuse;
	}

	public void setDiffuse(Color4f diffuse) {
		this.diffuse = diffuse;
	}

	public float getIntensity() {
		return intensity;
	}

	public void setIntensity(float intensity) {
		this.intensity = intensity;
	}

}
