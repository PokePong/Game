package poke.core.module.gui.element;

import poke.core.engine.model.Texture2D;
import poke.core.module.gui.GuiConstraint;
import poke.core.module.gui.GuiElement;
import poke.core.module.gui.constraint.AspectConstraint;
import poke.core.module.gui.constraint.CenterConstraint;
import poke.core.module.gui.constraint.PixelConstraint;
import poke.core.module.gui.constraint.RelativeConstraint;

public class GuiButton extends GuiElement {

	@Override
	public void _init_() {
		setTexture(Texture2D.loadTexture("", "tex3.png"));
		GuiConstraint c = new GuiConstraint();
		c.setX(new CenterConstraint());
		c.setY(new PixelConstraint(5));
		c.setWidth(new AspectConstraint(1));
		c.setHeight(new RelativeConstraint(0.2f));
		setConstraint(c);
	}

	@Override
	public void _update_(double delta) {
		
	}

}
