package poke.instance.camera;

import poke.core.engine.scene.Camera;

public interface CameraControl {

	public void init(Camera camera);
	
	public void update(Camera camera, double delta);

}
