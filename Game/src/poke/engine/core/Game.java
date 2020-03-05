package poke.engine.core;

import java.util.ArrayList;
import java.util.List;

import poke.engine.model.Mesh;

public abstract class Game {

	// SceneGraphe
	private List<Mesh> object;

	public Game() {
		this.object = new ArrayList<Mesh>();

	}

	public abstract void init();

	public abstract void update(double delta);

	public abstract void cleanUp();

	public List<Mesh> getMeshes() {
		return object;
	}

}
