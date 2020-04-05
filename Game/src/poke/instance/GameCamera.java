package poke.instance;

import org.joml.Vector3f;
import org.lwjgl.glfw.GLFW;

import poke.core.engine.input.Input;
import poke.core.engine.scene.Camera;

public class GameCamera extends Camera {

	private float speed = 5f;

	@Override
	public void _init_() {
		setPosition(new Vector3f(0, 15, 15));
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
		
		if (Input.getInstance().isKeyHolding(GLFW.GLFW_KEY_UP)) {
			rotateY(-speed*20f * (float) delta);
		}
		if (Input.getInstance().isKeyHolding(GLFW.GLFW_KEY_DOWN)) {
			rotateY(speed*20f * (float) delta);
		}
		if (Input.getInstance().isKeyHolding(GLFW.GLFW_KEY_LEFT)) {
			rotateX(-speed*20f * (float) delta);
		}
		if (Input.getInstance().isKeyHolding(GLFW.GLFW_KEY_RIGHT)) {
			rotateX(speed*20f * (float) delta);
		}

		if (Input.getInstance().isButtonHolding(2)) {

		}
	}
}
