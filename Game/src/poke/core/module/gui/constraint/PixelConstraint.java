package poke.core.module.gui.constraint;

public class PixelConstraint implements Constraint {

	private int pixel;

	public PixelConstraint(int pixel) {
		this.pixel = pixel;
	}

	@Override
	public int value() {
		return pixel;
	}

	@Override
	public ConstraintType getType() {
		return ConstraintType.PIXEL;
	}

}
