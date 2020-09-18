package poke.instance.camera;

import org.joml.Vector2f;
import org.joml.Vector3f;
import org.lwjgl.glfw.GLFW;

import poke.core.engine.input.Input;
import poke.core.engine.scene.Camera;
import poke.instance.planet.Planet;

public class CameraCelestialBody implements CameraControl {

	private Planet planet;

	private float zoomSensibility = 2f;
	private float rotateSensibility = 5f;

	public CameraCelestialBody(Planet planet) {
		this.planet = planet;
	}

	@Override
	public void init(Camera camera) {
		camera.move(planet.getWorldTransform().getTranslation());
		camera.move(new Vector3f(0, 0, 1), planet.getRadius() * 2);
	}

	@Override
	public void update(Camera camera, double delta) {

		Input input = Input.getInstance();

		// fix pos
		camera.move(planet.getDeltaPos());

		// scroll
		Vector3f dir = new Vector3f(planet.getWorldTransform().getTranslation())
				.add(new Vector3f(camera.getPosition()).mul(-1));
		dir.normalize();
		float scroll = input.getScrollOffSet();
		if (scroll != 0) {
			camera.move(dir, scroll * zoomSensibility);
		}

		// rotation
		Vector2f rot = Input.getInstance().getDisplVec();
		if (input.isButtonHolding(GLFW.GLFW_MOUSE_BUTTON_LEFT)) {
			camera.rotateAroundRight(planet.getWorldTransform().getTranslation(), -rot.x * rotateSensibility * 0.01f);
			//camera.rotateAroundUp(planet.getWorldTransform().getTranslation(), -rot.y * rotateSensibility * 0.01f);
		}
		

	}

	public Planet getPlanet() {
		return planet;
	}

	public void setPlanet(Planet planet) {
		this.planet = planet;
	}

}
