package poke.core.engine.scene;

public class Scenegraph extends Node {

	private Node staticRootObject;
	private Camera camera;

	public Scenegraph() {
		this.staticRootObject = new Node();
	}

	public void init() {
		if (camera == null)
			throw new IllegalStateException("[Scenegraph] Camera is null!");
		camera.init();
		staticRootObject.init();
	}

	public void update(double delta) {
		camera.update(delta);
		staticRootObject.update(delta);
	}

	public void render() {
		staticRootObject.render();
	}

	public void cleanUp() {
		camera.cleanUp();
		staticRootObject.cleanUp();
	}

	public Node getStaticRootObject() {
		return staticRootObject;
	}

	public Camera getCamera() {
		return camera;
	}

	public void setCamera(Camera camera) {
		this.camera = camera;
	}

}
