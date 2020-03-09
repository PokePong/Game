package poke.core.engine.scene;

public class Scenegraph extends Node {

	private Camera camera;
	private Node staticRootObject;

	public Scenegraph() {
		this.staticRootObject = new Node();
		this.camera = new Camera();
	}

	public void init() {
		camera.init();
		staticRootObject.init();
	}

	public void udpate(double delta) {
		camera.update(delta);
		staticRootObject.update(delta);
	}

	public void render() {
		staticRootObject.render();
	}

	public void cleanUp() {
		staticRootObject.cleanUp();
	}

	public Node getStaticRootObject() {
		return staticRootObject;
	}

}
