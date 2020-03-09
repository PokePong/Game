package poke.core.engine.scene;


import org.joml.Matrix4f;
import org.joml.Vector3f;

import poke.core.engine.core.Window;
import poke.core.engine.core.engine.Engine;

public abstract class Camera {

	private Vector3f position;
	private Vector3f rotation;

	private float z_near;
	private float z_far;
	private int fov;

	private Matrix4f projectionMatrix;

	public Camera() {
		this.position = new Vector3f(0, 0, 0);
		this.rotation = new Vector3f(0, 0, 0);
		this.fov = Engine.getInstance().getConfig().getFov();
		this.z_near = Engine.getInstance().getConfig().getZ_near();
		this.z_far = Engine.getInstance().getConfig().getZ_far();
		this.projectionMatrix = new Matrix4f().identity().perspective((float) Math.toRadians(fov),
				Window.getInstance().getAspectRatio(), z_near, z_far);
	}

	public abstract void init();

	public abstract void update(double delta);

	public Vector3f getPosition() {
		return position;
	}

	public void setPosition(Vector3f position) {
		this.position = position;
	}

	public Vector3f getRotation() {
		return rotation;
	}

	public void setRotation(Vector3f rotation) {
		this.rotation = rotation;
	}

	public Vector3f getForward() {
		Matrix4f viewMatrix = getViewMatrix();
		Vector3f ret = new Vector3f();
		ret.x = -viewMatrix.m02();
		ret.y = -viewMatrix.m12();
		ret.z = -viewMatrix.m22();
		return ret;
	}

	public Vector3f getUp() {
		Matrix4f viewMatrix = getViewMatrix();
		Vector3f ret = new Vector3f();
		ret.x = -viewMatrix.m01();
		ret.y = -viewMatrix.m11();
		ret.z = -viewMatrix.m21();
		return ret;
	}

	public Vector3f getRight() {
		Matrix4f viewMatrix = getViewMatrix();
		Vector3f ret = new Vector3f();
		ret.x = -viewMatrix.m00();
		ret.y = -viewMatrix.m10();
		ret.z = -viewMatrix.m20();
		return ret;
	}

	public Matrix4f getViewMatrix() {
		Matrix4f ret = new Matrix4f().identity();
		ret.rotate((float) Math.toRadians(rotation.x), new Vector3f(1, 0, 0))
				.rotate((float) Math.toRadians(rotation.y), new Vector3f(0, 1, 0))
				.rotate((float) Math.toRadians(rotation.z), new Vector3f(0, 0, 1));
		ret.translate(-position.x, -position.y, -position.z);
		return ret;
	}

	public Matrix4f getProjectionMatrix() {
		return projectionMatrix;
	}

}
