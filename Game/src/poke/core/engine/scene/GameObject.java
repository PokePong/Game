package poke.core.engine.scene;

import java.util.HashMap;

import poke.core.engine.gl.VBO;
import poke.core.engine.utils.Constants;
import poke.core.gl.renderer.StaticRenderer;

public abstract class GameObject extends Node {

	private VBO vbo;
	private HashMap<String, Component> components;

	public GameObject() {
		this.components = new HashMap<String, Component>();
	}

	public void addComponent(String name, Component component) {
		if (components.containsKey(name))
			throw new IllegalStateException("[Component] Already exists! " + name);
		component.setParent(this);
		components.put(name, component);
	}

	@Override
	public void init() {
		for (String key : components.keySet()) {
			components.get(key).init();
		}
		_init_();

		if (vbo == null)
			throw new IllegalStateException("[GameObject] VBO null, cannot render this object!");

		if (!components.containsKey(Constants.RENDERER_COMPONENT)) {
			StaticRenderer renderer = new StaticRenderer();
			renderer.setParent(this);
			components.put(Constants.RENDERER_COMPONENT, renderer);
		}
	}

	public void update(double delta) {
		for (String key : components.keySet()) {
			components.get(key).update(delta);
		}
		_update_(delta);
	}

	public void render() {
		for (String key : components.keySet()) {
			components.get(key).render();
		}

	}

	public void cleanUp() {
		for (String key : components.keySet()) {
			components.get(key).cleanUp();
		}
	}

	public abstract void _init_();

	public abstract void _update_(double delta);

	public HashMap<String, Component> getComponents() {
		return components;
	}

	public VBO getVbo() {
		return vbo;
	}

	public void setVbo(VBO vbo) {
		this.vbo = vbo;
	}

}
