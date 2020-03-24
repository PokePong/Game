package poke.core.engine.scene;

public class Scenegraph extends Node {

	private Node staticRootObject;

	public Scenegraph() {
		this.staticRootObject = new Node();
	}

	public void init() {
		staticRootObject.init();
	}

	public void udpate(double delta) {
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
