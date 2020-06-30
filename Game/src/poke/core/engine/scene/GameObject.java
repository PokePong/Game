package poke.core.engine.scene;

import java.util.HashMap;

import poke.core.engine.gl.VBO;
import poke.core.engine.renderer.Renderer;
import poke.core.engine.utils.Constants;
import poke.core.gl.config.Default;
import poke.core.gl.rendering.StaticShader;

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
		_init_();
		for (String key : components.keySet()) {
			components.get(key).init();
		}
		
		if (vbo == null)
			throw new IllegalStateException("[GameObject] VBO null, cannot render this object!");
		
		if (!getComponents().containsKey(Constants.RENDERER_COMPONENT)) {
			Renderer renderer = new Renderer(StaticShader.getInstance(), new Default());
			renderer.setParent(this);
			renderer.init();
			getComponents().put(Constants.RENDERER_COMPONENT, renderer);
		}
	}

	public void update(double delta) {
		_update_(delta);
		for (String key : components.keySet()) {
			components.get(key).update(delta);
		}
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
