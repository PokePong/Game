package poke.core.engine.scene;


public class Scenegraph extends Node {

	private Node root;
	private Camera camera;

	public Scenegraph() {
		this.root = new Node();
	}

	public void init() {
		if (camera == null)
			throw new IllegalStateException("[Scenegraph] Camera is null!");
		camera.init();
		root.init();
	}

	public void render() {
		root.render();
	}

	public void update(double delta) {
		camera.update(delta);
		root.update(delta);
	}

	public void cleanUp() {
		camera.cleanUp();
		root.cleanUp();
	}

	public Node getRoot() {
		return root;
	}

	public Camera getCamera() {
		return camera;
	}

	public void setCamera(Camera camera) {
		this.camera = camera;
	}

}
