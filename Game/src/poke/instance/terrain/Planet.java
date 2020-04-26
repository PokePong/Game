package poke.instance.terrain;

import poke.core.engine.scene.Node;

public class Planet extends Node {

	private QuadtreeFace north, south, west, east, far, near;

	public Planet() {
		this.north = new QuadtreeFace();
		this.south = new QuadtreeFace();
		this.west = new QuadtreeFace();
		this.east = new QuadtreeFace();
		this.far = new QuadtreeFace();
		this.near = new QuadtreeFace();

		this.near.getLocalTransform().translate(0, 0, 1f);

		this.far.getLocalTransform().translate(0, 0, -1f);
		this.far.getLocalTransform().rotate(0, (float) (Math.PI), 0);

		this.north.getLocalTransform().translate(0, 1f, 0);
		this.north.getLocalTransform().rotate((float) (Math.PI / 2f), (float) Math.PI, 0);

		this.south.getLocalTransform().translate(0, -1f, 0);
		this.south.getLocalTransform().rotate((float) (Math.PI / 2f), 0, 0);

		this.east.getLocalTransform().translate(1f, 0, 0);
		this.east.getLocalTransform().rotate(0, (float) (Math.PI / 2f), 0);

		this.west.getLocalTransform().translate(-1f, 0, 0);
		this.west.getLocalTransform().rotate(0, (float) (-Math.PI / 2f), 0);

		addChildren(near, far, east, west, north, south);
	}
	
	@Override
	public void init() {
		super.init();
		getWorldTransform().scaleTo(50f);
	}

	@Override
	public void update(double delta) {
		super.update(delta);
		//getWorldTransform().translate(0.5f, 0, 0);
	}
}
