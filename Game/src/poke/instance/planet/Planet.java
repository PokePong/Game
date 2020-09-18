package poke.instance.planet;

import org.joml.Vector3f;

import poke.core.engine.model.Texture2D;
import poke.core.engine.renderer.Renderer;
import poke.core.engine.utils.Constants;
import poke.core.gl.config.NoCullFace;
import poke.instance.CelestialBody;
import poke.instance.planet.noise.NoiseFilter;
import poke.instance.planet.noise.NoiseSettings;
import poke.instance.planet.patch.PatchShader;
import poke.instance.planet.patch.PatchVBO;
import poke.instance.planet.triangle.Triangulator;

public class Planet extends CelestialBody {

	private Triangulator triangulator;
	private PatchVBO patch;
	private NoiseFilter noiseFilter;
	private Texture2D texture;

	public Planet(float radius, float mass, Vector3f incline, Vector3f rotationVelocity, Vector3f initialVelocity) {
		super(radius, mass, incline, rotationVelocity, initialVelocity);
		this.triangulator = new Triangulator(this, 5);
		this.patch = new PatchVBO();
		this.noiseFilter = new NoiseFilter(new NoiseSettings());

		this.texture = Texture2D.loadTexture("", "moon.png");
		texture.bind();
		texture.linearFilter();
		texture.unbind();
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

	public Triangulator getTriangulator() {
		return triangulator;
	}

	public NoiseFilter getNoiseFilter() {
		return noiseFilter;
	}

	public Texture2D getTexture() {
		return texture;
	}

}
