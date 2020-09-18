package poke.instance;

import org.joml.Vector3f;

import poke.core.engine.core.Game;
import poke.core.module.color.Color4f;
import poke.instance.camera.CameraCelestialBody;
import poke.instance.camera.GameCamera;
import poke.instance.planet.Planet;
import poke.instance.planet.noise.Noise;

public class Main extends Game {

	private Planet planet;
	private Planet moon;
	private GameCamera camera;

	@Override
	public void _init_() {
		this.planet = new Planet(1000, 100, new Vector3f(0f, 0f, 0f), new Vector3f(0f, 0.001f, 0f), new Vector3f(0f, 0f, 0f));
		this.planet.getWorldTransform().translate(0, 0, 0);
		getScenegraph().getRoot().addChildren(planet);
		
		//this.moon = new Planet(20, 10, new Vector3f(0f, 0f, 0f), new Vector3f(0f, 0.002f, 0f), new Vector3f(0, 0, 1f));
		//this.moon.getWorldTransform().translate(400, 0, 0);
		//getScenegraph().getRoot().addChildren(planet, moon);

		
		int num = 30;
		for(int i = 0; i < num; i++) {
			Cube cube = new Cube(Color4f.random());
			getScenegraph().getRoot().addChild(cube);
		}
		
		Lampe lampe = new Lampe();
		getScenegraph().getRoot().addChild(lampe);
		
		
		this.camera = new GameCamera(new Vector3f(0, 0, 1500));
		getScenegraph().setCamera(camera);
	}

	@Override
	public void _update_(double delta) {
		
	}

}
