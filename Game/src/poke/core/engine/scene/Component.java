package poke.core.engine.scene;

import poke.core.engine.math.Transform;

public abstract class Component {

	private GameObject parent;

	public void init(){
		
	}

	public void update(double delta){
		
	}

	public void render(){
		
	}

	public void cleanUp(){
		
	}

	public GameObject getParent() {
		return parent;
	}

	public void setParent(GameObject parent) {
		this.parent = parent;
	}

	public Transform getLocalTransform() {
		return parent.getLocalTransform();
	}

	public Transform getWorldTransform() {
		return parent.getWorldTransform();
	}

}
