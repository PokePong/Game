package poke.instance;

import poke.core.engine.core.Game;
import poke.core.module.color.Color4f;

public class Main extends Game {

	int numCube = 100;
	
	@Override
	public void _init_() {
		for(int i = 0; i < numCube; i++) {
			Cube cube = new Cube(Color4f.random());
			getScenegraph().getRoot().addChild(cube);
		}
		getScenegraph().setCamera(new GameCamera());
	}

	@Override
	public void _update_(double delta) {

	}

}
