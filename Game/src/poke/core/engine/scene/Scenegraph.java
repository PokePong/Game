package poke.core.engine.scene;

import poke.core.module.sky.SkyDome;

public class Scenegraph extends Node {

	private Node root;
	private SkyDome sky;
	private Camera camera;

	public Scenegraph() {
		this.root = new Node();
	}

	public void init() {
		if (camera == null)
			throw new IllegalStateException("[Scenegraph] Camera is null!");
		camera.init();
		root.init();
		sky.init();
	}

	public void render() {
		root.render();
		sky.render();
	}

	public void update(double delta) {
		camera.update(delta);
		root.update(delta);
		sky.update(delta);
	}

	public void cleanUp() {
		camera.cleanUp();
		root.cleanUp();
		sky.cleanUp();
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

	public SkyDome getSky() {
		return sky;
	}

	public void setSky(SkyDome sky) {
		this.sky = sky;
	}

}
