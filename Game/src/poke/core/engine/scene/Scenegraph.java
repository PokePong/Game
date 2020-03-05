package poke.core.engine.scene;

public class Scenegraph extends Node {

	private Node rootObject;

	public Scenegraph() {
		this.rootObject = new Node();
	}

	public void init() {
		rootObject.init();
	}

	public void udpate(double delta) {
		rootObject.update(delta);
	}

	public void render() {
		rootObject.render();
	}

	public void cleanUp() {
		rootObject.cleanUp();
	}

	public Node getRoot() {
		return rootObject;
	}

}
