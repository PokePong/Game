package poke.instance;

import poke.core.engine.core.Game;

public class Main extends Game {

	private Quad q;
	
	public Main() {
		q = new Quad();
	}

	@Override
	public void _init_() {
		super.getScenegraph().getRoot().addChild(q);
	}


	@Override
	public void _update_(double delta) {
		
	}

}
