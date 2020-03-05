package poke.core.engine.core;

import poke.core.engine.scene.Scenegraph;

public abstract class Game {

	private Scenegraph scenegraph;

	public Game() {
		this.scenegraph = new Scenegraph();
	}

	public void init() {
		_init_();
		scenegraph.getRoot().init();
	}

	public void update(double delta) {
		_update_(delta);
		scenegraph.getRoot().update(delta);
	}
	
	public abstract void _init_();
	
	public abstract void _update_(double delta);

	public void cleanUp() {
		scenegraph.cleanUp();
	}

	public Scenegraph getScenegraph() {
		return scenegraph;
	}

}
