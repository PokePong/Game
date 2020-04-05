package poke.core.module.gui;

import org.joml.Matrix4f;
import org.lwjgl.opengl.GL13;

import poke.core.engine.gl.VBO;
import poke.core.engine.math.Transform;
import poke.core.engine.model.Texture2D;
import poke.core.module.color.Color4f;

public abstract class GuiElement {

	private GuiScreen parent;
	private Transform worldTransform;
	private GuiConstraint constraint;
	private Texture2D texture;
	private Color4f color;
	private VBO vbo;
	private boolean enable;

	public GuiElement() {
		this.worldTransform = new Transform();
		this.color = Color4f.TRANSPARENT;
		this.vbo = new GuiVBO();
		this.enable = true;
	}

	public void init() {
		_init_();
		updateConstraints();
	}

	public void update(double delta) {
		_update_(delta);
		updateConstraints();
	}

	public void render() {
		if (texture != null) {
			GL13.glActiveTexture(GL13.GL_TEXTURE0);
			texture.bind();
		}
		GuiShader.getInstance().updateUniforms(this);
		vbo.render();
	}

	public void cleanUp() {
		vbo.cleanUp();
	}

	public void updateConstraints() {
		constraint.applyConstraints();
		Matrix4f ret = new Matrix4f();
		ret.translate(constraint.getX(), constraint.getY(), 0);
		ret.scale(constraint.getWidth(), constraint.getHeight(), 0);
		this.worldTransform.setWorldMatrix(ret);
	}

	public abstract void _init_();

	public abstract void _update_(double delta);

	public GuiScreen getParent() {
		return parent;
	}

	public void setParent(GuiScreen parent) {
		this.parent = parent;
	}

	public Transform getWorldTransform() {
		return worldTransform;
	}

	public void setWorldTransform(Transform worldTransform) {
		this.worldTransform = worldTransform;
	}

	public Texture2D getTexture() {
		return texture;
	}

	public void setTexture(Texture2D texture) {
		if (color == Color4f.TRANSPARENT)
			color = Color4f.WHITE;
		this.texture = texture;
	}

	public Color4f getColor() {
		return color;
	}

	public void setColor(Color4f color) {
		this.color = color;
	}

	public boolean isEnable() {
		return enable;
	}

	public void setEnable(boolean enable) {
		this.enable = enable;
	}

	public GuiConstraint getConstraint() {
		return constraint;
	}

	public void setConstraint(GuiConstraint constraint) {
		this.constraint = constraint;
	}

}
