package poke;

public class Engine {

	private int fps_cap = 60;
	private int ups_cap = 120;
	private Timer timer;
	private Window window;

	public Engine() {
		this.timer = new Timer();
		this.window = Window.getInstance();
	}

	public void start() {
		try {
			init();
			loop();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			destroy();
		}
	}

	private void init() {
		timer.mark();
		window.create();
	}

	private void loop() {
		double delta;
		double interval = 1d / ups_cap;
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
		double slot = 1d / fps_cap;
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

	}

	private void destroy() {
		window.destroy();
	}

}
