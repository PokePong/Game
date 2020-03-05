package poke.engine.core;

import org.lwjgl.Version;
import org.lwjgl.opengl.GL11;

import poke.engine.input.Input;

public class EngineSystem {

	private Engine engine;
	private Window window;
	private Input input;
	private RenderingEngine renderingEngine;
	private Game game;

	public EngineSystem(Engine engine) {
		this.engine = engine;
		this.input = new Input();
		this.window = new Window(engine);
	}

	public void init() {
		window.create();
		try {
			this.game = Engine.class.getClassLoader().loadClass(engine.getConfig().getMain()).asSubclass(Game.class)
					.newInstance();
		} catch (InstantiationException | IllegalAccessException | ClassNotFoundException e) {
			e.printStackTrace();
		}
		this.renderingEngine = new RenderingEngine(this);
		input.init(window.getWindow());
		displayGameSettings();
		game.init();
		renderingEngine.init();
	}

	public Window getWindow() {
		return window;
	}

	public Input getInput() {
		return input;
	}

	public RenderingEngine getRenderingEngine() {
		return renderingEngine;
	}

	public Game getGame() {
		return game;
	}

	private void displayGameSettings() {
		System.out.println("======================================================");
		System.out.println("Name: " + engine.getConfig().getWindow_title());
		System.out.println("Version: " + engine.getConfig().getVersion());
		System.out.println("Window dimension: " + engine.getConfig().getWindow_width() + " x "
				+ engine.getConfig().getWindow_height() + " pixels");
		System.out.println("Fps_cap: " + engine.getConfig().getFps_cap());
		System.out.println("Ups_cap: " + engine.getConfig().getUps_cap());
		System.out.println("Powered by LWJGL: " + Version.getVersion());
		System.out.println("Running with OpenGL: " + GL11.glGetString(GL11.GL_VERSION));
		System.out.println("======================================================");
	}

}
