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
		root.init();
		camera.init();
	}

	public void render() {
		root.render();
	}

	public void update(double delta) {
		root.update(delta);
		camera.update(delta);
	}

	public void cleanUp() {
		root.cleanUp();
		camera.cleanUp();
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
