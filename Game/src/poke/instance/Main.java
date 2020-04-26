package poke.instance;

import poke.core.engine.core.Game;
import poke.core.module.color.Color4f;

public class Main extends Game {

	int numCube = 20;

	@Override
	public void _init_() {
		getScenegraph().setCamera(new GameCamera());

		for (int i = 0; i < numCube; i++) {
			Cube cube = new Cube(Color4f.random());
			getScenegraph().getRoot().addChild(cube);
		}
		getScenegraph().getRoot().addChild(new Lampe());
	}

	@Override
	public void _update_(double delta) {

	}

}
