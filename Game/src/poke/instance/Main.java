package poke.instance;

import poke.core.engine.core.Game;

public class Main extends Game {

	
	@Override
	public void _init_() {
		super.getScenegraph().getStaticRootObject().addChild(new Quad());
	}


	@Override
	public void _update_(double delta) {
		
	}

}
