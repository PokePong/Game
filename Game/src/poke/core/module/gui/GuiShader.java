package poke.core.module.gui;

import poke.core.engine.scene.GameObject;
import poke.core.engine.shader.Shader;

public class GuiShader extends Shader {

	private static GuiShader instance;

	public static GuiShader getInstance() {
		if (instance == null)
			instance = new GuiShader();
		return instance;
	}

	public GuiShader() {
		super();

		addVertexShader("gui/gui_v.glsl");
		addFragmentShader("gui/gui_f.glsl");
		validateShader();

		addUniform("m_Ortho");
		addUniform("color");
		addUniform("textured");
	}

	@Override
	public void updateUniforms(GameObject object) {

	}

	@Override
	public void updateUniforms(GuiElement element) {
		setUniform("m_Ortho", element.getWorldTransform().getOrthoMatrix());
		setUniform("color", element.getColor().toVector4f());
		setUniform("textured", element.getTexture() != null);
	}

}
