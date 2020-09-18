package poke.instance.camera;

import org.joml.Vector2f;
import org.lwjgl.glfw.GLFW;

import poke.core.engine.input.Input;
import poke.core.engine.scene.Camera;

public class CameraFree implements CameraControl {

	private float speed = 100f;
	private float speedRotate = 5f;
	private float mouseSensibility = 3f;

	private boolean rotateMode = false;

	@Override
	public void init(Camera camera) {
		
	}
	
	@Override
	public void update(Camera camera, double delta) {
		Input input = Input.getInstance();
		if (input.isKeyHolding(GLFW.GLFW_KEY_W)) {
			camera.move(camera.getForward(), speed * (float) delta);
		}
		if (input.isKeyHolding(GLFW.GLFW_KEY_S)) {
			camera.move(camera.getForward(), -speed * (float) delta);
		}
		if (input.isKeyHolding(GLFW.GLFW_KEY_A)) {
			camera.move(camera.getRight(), -speed * (float) delta);
		}
		if (input.isKeyHolding(GLFW.GLFW_KEY_D)) {
			camera.move(camera.getRight(), speed * (float) delta);
		}

		if (input.isKeyPushed(GLFW.GLFW_KEY_C)) {
			if (rotateMode) {
				rotateMode = false;
				Input.getInstance().unlockCursor();
			} else {
				rotateMode = true;
				Input.getInstance().lockCursor();
			}
		}

		if (rotateMode) {
			updateRotate(camera, delta);
		} else {
			if (input.isButtonHolding(2)) {
				updateRotate(camera, delta);
			}
		}

	}
	
	private void updateRotate(Camera camera, double delta) {
		Vector2f rot = Input.getInstance().getDisplVec();
		float angX = (rot.x * speedRotate * mouseSensibility * (float) delta);
		float angY = (rot.y * speedRotate * mouseSensibility * (float) delta);
		camera.rotateX(angX);
		camera.rotateY(angY);
	}

}
