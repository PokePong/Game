package poke.core.module.gui.constraint;

public class CenterConstraint implements Constraint {

	public CenterConstraint() {
		
	}

	@Override
	public int value() {
		return 0;
	}

	@Override
	public ConstraintType getType() {
		return ConstraintType.CENTER;
	}

}
