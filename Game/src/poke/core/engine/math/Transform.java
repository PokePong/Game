package poke.core.engine.math;

import org.joml.Matrix4f;
import org.joml.Vector3f;

public class Transform {

	private Vector3f translation;
	private Vector3f rotation;
	private Vector3f scaling;

	public Transform() {
		this.translation = new Vector3f(0, 0, 0);
		this.rotation = new Vector3f(0, 0, 0);
		this.scaling = new Vector3f(1, 1, 1);
	}

	public Transform translate(float dx, float dy, float dz) {
		return translate(new Vector3f(dx, dy, dz));
	}

	public Transform translate(Vector3f translation) {
		setTranslation(getTranslation().add(translation));
		return this;
	}

	public Transform translateTo(float x, float y, float z) {
		return translateTo(new Vector3f(x, y, z));
	}

	public Transform translateTo(Vector3f translation) {
		setTranslation(new Vector3f(translation));
		return this;
	}

	public Transform rotate(float rx, float ry, float rz) {
		return rotate(new Vector3f(rx, ry, rz));
	}

	public Transform rotate(Vector3f rotation) {
		setRotation(getRotation().add(rotation));
		return this;
	}

	public Transform rotateTo(float x, float y, float z) {
		return rotateTo(new Vector3f(x, y, z));
	}

	public Transform rotateTo(Vector3f rotation) {
		setRotation(new Vector3f(rotation));
		return this;
	}

	public Transform scale(float s) {
		return scale(s, s, s);
	}

	public Transform scale(float sx, float sy, float sz) {
		return scale(new Vector3f(sx, sy, sz));
	}

	public Transform scale(Vector3f scaling) {
		setScaling(getScaling().add(scaling));
		return this;
	}

	public Transform scaleTo(float s) {
		return scaleTo(s, s, s);
	}

	public Transform scaleTo(float x, float y, float z) {
		return scaleTo(new Vector3f(x, y, z));
	}

	public Transform scaleTo(Vector3f scaling) {
		setScaling(new Vector3f(scaling));
		return this;
	}

	public Matrix4f getTranslationMatrix() {
		Matrix4f ret = new Matrix4f().identity();
		ret.translate(translation);
		return ret;
	}

	public Matrix4f getRotationMatrix() {
		Matrix4f ret = new Matrix4f().identity();
		ret.rotateXYZ(rotation);
		return ret;
	}

	public Matrix4f getScalingMatrix() {
		Matrix4f ret = new Matrix4f().identity();
		ret.scale(scaling);
		return ret;
	}

	public Matrix4f getWorldMatrix() {
		return getTranslationMatrix().mul(getRotationMatrix().mul(getScalingMatrix()));
	}

	public Vector3f getTranslation() {
		return translation;
	}

	public void setTranslation(Vector3f translation) {
		this.translation = translation;
	}

	public Vector3f getRotation() {
		return rotation;
	}

	public void setRotation(Vector3f rotation) {
		this.rotation = rotation;
	}

	public Vector3f getScaling() {
		return scaling;
	}

	public void setScaling(Vector3f scaling) {
		this.scaling = scaling;
	}

}
