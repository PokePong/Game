package poke.engine.kernel;

import org.lwjgl.Version;
import org.lwjgl.opengl.GL11;

import poke.engine.time.Timer;

public class Engine {

	private Timer timer;
	private EngineSystem engineSystem;
	private EngineConfig config;

	private boolean running;

	public Engine() {
		this.timer = new Timer();
		this.config = new EngineConfig("config.properties");
		this.engineSystem = new EngineSystem(this);
	}

	public void start() {
		if (running)
			return;
		running = true;

		try {
			init();
			loop();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			cleanUp();
		}
	}

	public void stop() {
		if (!running)
			return;
		running = false;
	}

	private void init() {
		timer.mark();
		engineSystem.init();
	}

	private void loop() {
		double delta;
		double interval = 1d / config.getUps_cap();
		double accumulator = 0d;
		double frameCounter = 0d;

		int frames = 0;
		int ticks = 0;

		while (!engineSystem.getWindow().shouldClose()) {
			delta = timer.getDelta();
			accumulator += delta;
			frameCounter += delta;

			while (accumulator >= interval) {
				update(interval);
				accumulator -= interval;
				ticks++;

				if (frameCounter >= 1) {
					System.out.println("FPS: " + frames + " | Ticks: " + ticks);
					frames = 0;
					frameCounter = 0;
					ticks = 0;
				}
			}
			render();
			frames++;
			sync();
		}
		stop();

	}

	private void sync() {
		double slot = 1d / config.getFps_cap();
		double endTime = timer.getLastLoopTime() + slot;
		while (timer.getTime() < endTime) {
			try {
				Thread.sleep(1);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	private void render() {
		engineSystem.getRenderingEngine().render();
		engineSystem.getWindow().draw();
	}

	private void update(double delta) {
		engineSystem.getRenderingEngine().update(delta);
		engineSystem.getInput().update();
		engineSystem.getGame().update(delta);
	}

	private void cleanUp() {
		engineSystem.getGame().cleanUp();
		engineSystem.getRenderingEngine().cleanUp();
		engineSystem.getInput().cleanUp();
		engineSystem.getWindow().destroy();
	}

	public EngineConfig getConfig() {
		return config;
	}

}
