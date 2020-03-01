package poke.engine.kernel;

public abstract class Game {

	public Game() {
		
	}
	
	public abstract void init();
	
	public abstract void update(double delta);
	
	public abstract void cleanUp();
	
}
