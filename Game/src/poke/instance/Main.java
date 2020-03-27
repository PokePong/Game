package poke.instance;

import poke.core.engine.core.Game;
import poke.core.gl.scene.GameCamera;
import poke.core.module.sky.SkyDome;

public class Main extends Game {

	@Override
	public void _init_() {
		super.getScenegraph().setCamera(new GameCamera());
		super.getScenegraph().getStaticRootObject().addChild(new Terrain());
		super.getScenegraph().getStaticRootObject().addChild(new SkyDome());
	}

	@Override
	public void _update_(double delta) {
		
	}

}
