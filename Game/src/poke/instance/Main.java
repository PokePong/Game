package poke.instance;

import poke.engine.core.Game;
import poke.engine.model.Mesh;
import poke.engine.utils.MeshGenerator;

public class Main extends Game {

	public Mesh quad;

	@Override
	public void init() {
		this.quad = MeshGenerator.createQuad();
		super.getMeshes().add(quad);
	}

	@Override
	public void update(double delta) {

	}

	@Override
	public void cleanUp() {

	}

}
