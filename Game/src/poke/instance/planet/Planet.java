package poke.instance.planet;


import poke.core.engine.renderer.Renderer;
import poke.core.engine.scene.GameObject;
import poke.core.engine.utils.Constants;
import poke.core.gl.config.NoCullFace;
import poke.instance.planet.patch.PatchShader;
import poke.instance.planet.patch.PatchVBO;
import poke.instance.planet.triangle.Triangulator;

public class Planet extends GameObject {

	public float radius;
	private Triangulator triangulator;
	private PatchVBO patch;

	public Planet(float radius) {
		super();
		this.radius = 500f;
		this.triangulator = new Triangulator(this, 7);
		this.patch = new PatchVBO();
	}

	@Override
	public void _init_() {

		triangulator.init();
		patch.uploadPatch(4);
		patch.uploadData(triangulator.getPatchInstancesArray());

		setVbo(patch);

		Renderer renderer = new Renderer(PatchShader.getInstance(), new NoCullFace());
		renderer.setParent(this);
		addComponent(Constants.RENDERER_COMPONENT, renderer);
	}

	@Override
	public void _update_(double delta) {
		triangulator.updateGeometry();
		patch.uploadData(triangulator.getPatchInstancesArray());
	}
	
	public float getRadius() {
		return radius;
	}
	
	public Triangulator getTriangulator() {
		return triangulator;
	}

}
