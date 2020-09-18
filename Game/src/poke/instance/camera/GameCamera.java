package poke.instance.camera;

import org.joml.Vector3f;

import poke.core.engine.scene.Camera;

public class GameCamera extends Camera {

	private CameraControl control;
	private static GameCamera instance;

	public static GameCamera getInstance() {
		if (instance == null)
			instance = new GameCamera();
		return instance;
	}
	
	public GameCamera() {
		super();
		this.setControl(new CameraFree());
		instance = this;
	}

	public GameCamera(Vector3f pos) {
		super();
		this.setControl(new CameraFree());
		move(pos);
		instance = this;
	}

	@Override
	public void _init_() {
		control.init(this);
	}

	@Override
	public void _update_(double delta) {
		control.update(this, delta);
	}

	public CameraControl getControl() {
		return control;
	}

	public void setControl(CameraControl control) {
		this.control = control;
	}

}
