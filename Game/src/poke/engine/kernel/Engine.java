package poke.engine.kernel;

import org.lwjgl.Version;
import org.lwjgl.opengl.GL11;

import poke.engine.config.Config;

public class Engine {

	private Timer timer;
	private Config config;
	private Window window;
	private Input input;
	private Game game;

	public Engine() {
		this.timer = new Timer();
		this.config = new Config("config.properties");
		this.input = new Input();
		this.window = new Window(config.getWindow_width(), config.getWindow_height(), config.getWindow_title(),
				config.getVersion());
	}

	public void start() {
		try {
			init();
			loop();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			cleanUp();
		}
	}

	private void init() {
		timer.mark();
		window.create();
		try {
			this.game = Engine.class.getClassLoader().loadClass(config.getMain()).asSubclass(Game.class).newInstance();
		} catch (InstantiationException | IllegalAccessException | ClassNotFoundException e) {
			e.printStackTrace();
		}
		input.init(window.getWindow());
		displayGameSettings();
		game.init();
	}

	private void loop() {
		double delta;
		double interval = 1d / config.getUps_cap();
		double accumulator = 0d;
		double frameCounter = 0d;

		int frames = 0;
		int ticks = 0;

		while (!window.shouldClose()) {
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
		window.draw();
	}

	private void update(double delta) {
		game.update(delta);
	}

	private void cleanUp() {
		game.cleanUp();
		input.cleanUp();
		window.destroy();
	}

	private void displayGameSettings() {
		System.out.println("======================================================");
		System.out.println("Name: " + config.getWindow_title());
		System.out.println("Version: " + config.getVersion());
		System.out.println(
				"Window dimension: " + config.getWindow_width() + " x " + config.getWindow_height() + " pixels");
		System.out.println("Fps_cap: " + config.getFps_cap());
		System.out.println("Ups_cap: " + config.getUps_cap());
		System.out.println("Powered by LWJGL: " + Version.getVersion());
		System.out.println("Running with OpenGL: " + GL11.glGetString(GL11.GL_VERSION));
		System.out.println("======================================================");
	}

}
