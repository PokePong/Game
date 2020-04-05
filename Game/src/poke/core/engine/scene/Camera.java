package poke.core.engine.scene;

import java.nio.FloatBuffer;

import org.joml.Matrix4f;
import org.joml.Vector3f;

import poke.core.engine.core.Window;
import poke.core.engine.core.engine.Engine;
import poke.core.engine.utils.Buffer;
import poke.core.engine.utils.Constants;
import poke.core.gl.buffer.ubo.BlockUBO;

public abstract class Camera {

	private Vector3f position;
	private Vector3f rotation;
	private Vector3f forward;
	private Vector3f up;
	private Vector3f right;

	private float z_near;
	private float z_far;
	private float fov;
	private Matrix4f projectionMatrix;

	private Matrix4f viewMatrix;

	private FloatBuffer buffer;
	private BlockUBO ubo;
	private final int bufferSize = Float.BYTES * (3 + 2 * (4 * 4));

	public Camera() {
		this.position = new Vector3f(0, 0, 0);
		this.rotation = new Vector3f(0, 0, 0);
		setProjection(Engine.getInstance().getConfig().getFov(), Window.getInstance().getAspectRatio(),
				Engine.getInstance().getConfig().getZ_near(), Engine.getInstance().getConfig().getZ_far());
	}

	public void init() {
		this.ubo = new BlockUBO();
		this.ubo.setBindingIndex(Constants.CAMERA_UBO_BINDING_INDEX);
		this.ubo.allocate(bufferSize);
		this.ubo.bindBufferBase();
		this.buffer = Buffer.createFloatBuffer(bufferSize);

		_init_();
		setView();
		setForward();
		setUp();
		setRight();
		updateUBO();
	}

	public void update(double delta) {
		_update_(delta);

		setView();
		setForward();
		setUp();
		setRight();
		updateUBO();
	}

	public void cleanUp() {
		buffer.clear();
		ubo.cleanUp();
	}

	public abstract void _init_();

	public abstract void _update_(double delta);

	public void move(Vector3f direction, float amount) {
		Vector3f ret = position.add(direction.mul(amount));
		this.position = ret;
	}

	public void rotateX(float angle) {
		Vector3f ret = rotation.add(new Vector3f(0, 1, 0).mul(angle));
		this.rotation = ret;
	}

	public void rotateY(float angle) {
		Vector3f ret = rotation.add(new Vector3f(1, 0, 0).mul(angle));
		this.rotation = ret;
	}

	private void updateUBO() {
		buffer.clear();
		buffer.put(Buffer.createFlippedBuffer(getProjectionMatrix()));
		buffer.put(Buffer.createFlippedBuffer(getViewMatrix()));
		ubo.updateData(buffer, bufferSize);
	}

	private void setProjection(int fov, float aspectRatio, float z_near, float z_far) {
		this.fov = (float) fov;
		this.z_near = z_near;
		this.z_far = z_far;
		Matrix4f ret = new Matrix4f().identity();
		ret.perspective(fov, aspectRatio, z_near, z_far);
		this.projectionMatrix = ret;
	}
	
	public void updateProjection(float aspectRatio) {
		Matrix4f ret = new Matrix4f().identity();
		ret.perspective(fov, aspectRatio, z_near, z_far);
		this.projectionMatrix = ret;
	}

	private void setView() {
		Matrix4f ret = new Matrix4f().identity();
		ret.rotate((float) Math.toRadians(rotation.x), new Vector3f(1, 0, 0))
				.rotate((float) Math.toRadians(rotation.y), new Vector3f(0, 1, 0))
				.rotate((float) Math.toRadians(rotation.z), new Vector3f(0, 0, 1));
		ret.translate(-position.x, -position.y, -position.z);
		this.viewMatrix = ret;
	}

	private void setForward() {
		Matrix4f viewMatrix = getViewMatrix();
		Vector3f ret = new Vector3f();
		ret.x = -viewMatrix.m02();
		ret.y = -viewMatrix.m12();
		ret.z = -viewMatrix.m22();
		this.forward = ret;
	}

	private void setUp() {
		Matrix4f viewMatrix = getViewMatrix();
		Vector3f ret = new Vector3f();
		ret.x = viewMatrix.m01();
		ret.y = viewMatrix.m11();
		ret.z = viewMatrix.m21();
		this.up = ret;
	}

	private void setRight() {
		Matrix4f viewMatrix = getViewMatrix();
		Vector3f ret = new Vector3f();
		ret.x = viewMatrix.m00();
		ret.y = viewMatrix.m10();
		ret.z = viewMatrix.m20();
		this.right = ret;
	}

	public Matrix4f getViewProjectionMatrix() {
		return getProjectionMatrix().mul(getViewMatrix());
	}

	public Vector3f getPosition() {
		return position;
	}

	public Vector3f getRotation() {
		return rotation;
	}

	public Vector3f getForward() {
		return forward;
	}

	public Vector3f getUp() {
		return up;
	}

	public Vector3f getRight() {
		return right;
	}

	public Matrix4f getProjectionMatrix() {
		return projectionMatrix;
	}

	public Matrix4f getViewMatrix() {
		return viewMatrix;
	}

	public float getZ_near() {
		return z_near;
	}

	public float getZ_far() {
		return z_far;
	}

	public float getFov() {
		return fov;
	}

	public void setPosition(Vector3f position) {
		this.position = position;
	}

	public void setRotation(Vector3f rotation) {
		this.rotation = rotation;
	}

}
