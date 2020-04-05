package poke.core.module.gui;

import poke.core.engine.core.Window;
import poke.core.module.gui.constraint.CenterConstraint;
import poke.core.module.gui.constraint.Constraint;
import poke.core.module.gui.constraint.RelativeConstraint;

public class GuiConstraint {

	private int width;
	private int height;
	private int x;
	private int y;

	private Constraint widthConstraint;
	private Constraint heightConstraint;
	private Constraint xConstraint;
	private Constraint yConstraint;

	public void applyConstraints() {
		switch (widthConstraint.getType()) {
		case PIXEL:
			this.width = widthConstraint.value();
			break;
		case RELATIVE:
			this.width = (int) (Window.width * ((float) widthConstraint.value() / 100));
			break;
		case ASPECT:
			this.width = (int) (height * ((float) widthConstraint.value() / 100));
			break;
		default:
			break;
		}

		switch (heightConstraint.getType()) {
		case PIXEL:
			this.height = heightConstraint.value();
			break;
		case RELATIVE:
			this.height = (int) (Window.height * ((float) heightConstraint.value() / 100));
			break;
		case ASPECT:
			this.height = (int) (width * ((float) heightConstraint.value() / 100));
			break;
		default:
			break;
		}

		switch (xConstraint.getType()) {
		case PIXEL:
			if (xConstraint.value() >= 0) {
				this.x = Window.width / 2 - width / 2 - xConstraint.value();
			} else {
				this.x = -Window.width / 2 + width / 2 - xConstraint.value();
			}
			break;
		case CENTER:
			this.x = xConstraint.value();
			break;
		default:
			break;
		}

		switch (yConstraint.getType()) {
		case PIXEL:
			if (yConstraint.value() >= 0) {
				this.y = Window.height / 2 - height / 2 - yConstraint.value();
			} else {
				this.y = -Window.height / 2 + height / 2 - yConstraint.value();
			}
			break;
		case CENTER:
			this.y = yConstraint.value();
			break;
		default:
			break;
		}
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public void setWidth(Constraint constraint) {
		this.widthConstraint = constraint;
	}

	public void setHeight(Constraint constraint) {
		this.heightConstraint = constraint;
	}

	public void setX(Constraint constraint) {
		this.xConstraint = constraint;
	}

	public void setY(Constraint constraint) {
		this.yConstraint = constraint;
	}

	public static GuiConstraint fullScreenConstraint() {
		GuiConstraint ret = new GuiConstraint();
		ret.setX(new CenterConstraint());
		ret.setY(new CenterConstraint());
		ret.setWidth(new RelativeConstraint(1f));
		ret.setHeight(new RelativeConstraint(1f));
		return ret;
	}

}
