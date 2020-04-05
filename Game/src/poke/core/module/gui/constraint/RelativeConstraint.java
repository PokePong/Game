package poke.core.module.gui.constraint;

public class RelativeConstraint implements Constraint {

	private float perc;

	public RelativeConstraint(float perc) {
		this.perc = perc;
	}

	@Override
	public int value() {
		return (int) (perc * 100);
	}

	@Override
	public ConstraintType getType() {
		return ConstraintType.RELATIVE;
	}

}
