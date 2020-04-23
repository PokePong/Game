package poke.instance;


import org.joml.Vector3f;

import poke.core.engine.light.PointLight;
import poke.core.engine.scene.GameObject;
import poke.core.engine.utils.Constants;
import poke.core.gl.buffer.vbo.PointVBO;
import poke.core.module.color.Color4f;

public class Lampe extends GameObject {

	@Override
	public void _init_() {
		setVbo(new PointVBO());
		PointLight light = new PointLight();
		light.setDiffuse(Color4f.WHITE);
		light.setAttenuation(new Vector3f(1f, 0.1f, 0.05f));
		light.setIntensity(1f);
		getWorldTransform().setTranslation(new Vector3f(0, 0, 0));
		addComponent(Constants.LIGHT_COMPONENT0, light);
	}

	@Override
	public void _update_(double delta) {

	}

}
