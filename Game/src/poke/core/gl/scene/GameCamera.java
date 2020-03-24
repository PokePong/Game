package poke.core.gl.scene;

import org.lwjgl.glfw.GLFW;

import poke.core.engine.input.Input;
import poke.core.engine.scene.Camera;

public class GameCamera extends Camera {

	private float speed = 0.5f;

	@Override
	public void _init_() {

	}

	@Override
	public void _update_(double delta) {
		if (Input.getInstance().isKeyHolding(GLFW.GLFW_KEY_W)) {
			move(getForward(), speed * (float) delta);
		}
		if (Input.getInstance().isKeyHolding(GLFW.GLFW_KEY_S)) {
			move(getForward(), -speed * (float) delta);
		}
		if (Input.getInstance().isKeyHolding(GLFW.GLFW_KEY_A)) {
			move(getRight(), -speed * (float) delta);
		}
		if (Input.getInstance().isKeyHolding(GLFW.GLFW_KEY_D)) {
			move(getRight(), speed * (float) delta);
		}
	}

}