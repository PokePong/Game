package poke.instance;

import java.util.List;

import org.joml.Vector3f;

import poke.core.engine.scene.GameObject;

public abstract class CelestialBody extends GameObject {

	private float mass;
	private float radius;
	
	private Vector3f incline;
	
	private Vector3f rotationVelocity;
	private Vector3f deltaRot;
	
	private Vector3f initialVelocity;
	private Vector3f velocity;
	private Vector3f deltaPos;
	
	
	public CelestialBody(float radius, float mass, Vector3f incline, Vector3f rotationVelocity, Vector3f initialVelocity) {
		this.mass = mass;
		this.radius = radius;
		this.incline = incline;
		this.rotationVelocity = rotationVelocity;
		this.initialVelocity = initialVelocity;
		Universe.bodies.add(this);
	}

	@Override
	public void init() {
		super.init();
		this.velocity = initialVelocity;
		this.deltaPos = new Vector3f();
		this.deltaRot = new Vector3f(rotationVelocity).mul(Universe.PHYSICS_TIME_STEP).mul(3f);
	}

	@Override
	public void update(double delta) {
		super.update(delta);
		updateVelocity(Universe.bodies, Universe.PHYSICS_TIME_STEP);
		updatePosition(Universe.PHYSICS_TIME_STEP);
		updateRotation(Universe.PHYSICS_TIME_STEP);
	}

	public void updateVelocity(List<CelestialBody> bodies, float delta) {
		for (CelestialBody body : bodies) {
			if (body != this) {
				Vector3f pos = new Vector3f(getWorldTransform().getTranslation());
				Vector3f ortherPos = new Vector3f(body.getWorldTransform().getTranslation());

				float sqrDist = ortherPos.distance(pos) * ortherPos.distance(pos);
				Vector3f forceDir = ortherPos.add(pos.mul(-1));
				forceDir.normalize();
				Vector3f force = forceDir.mul(Universe.GRAVITATIONAL_CONSTANT).mul(mass).mul(body.getMass())
						.div(sqrDist);
				Vector3f acceleration = force.div(mass);
				velocity.add(new Vector3f(acceleration).mul(delta));
			}
		}
	}

	public void updatePosition(float delta) {
		deltaPos = new Vector3f(velocity).mul(delta).mul(3f);
		getWorldTransform().translate(deltaPos);
	}
	
	public void updateRotation(float delta) {
		getWorldTransform().rotate(deltaRot);
	}

	public float getMass() {
		return mass;
	}

	public void setMass(float mass) {
		this.mass = mass;
	}

	public float getRadius() {
		return radius;
	}

	public void setRadius(float radius) {
		this.radius = radius;
	}

	public float getDiameter() {
		return radius * 2f;
	}

	public Vector3f getInitialVelocity() {
		return initialVelocity;
	}

	public void setInitialVelocity(Vector3f initialVelocity) {
		this.initialVelocity = initialVelocity;
	}

	public Vector3f getVelocity() {
		return velocity;
	}

	public void setVelocity(Vector3f velocity) {
		this.velocity = velocity;
	}
	
	public Vector3f getDeltaPos() {
		return deltaPos;
	}

}
