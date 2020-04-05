package poke.core.module.gui.constraint;

public class AspectConstraint implements Constraint {

	private float perc;

	public AspectConstraint(float perc) {
		this.perc = perc;
	}

	@Override
	public int value() {
		return (int) (perc * 100);
	}

	@Override
	public ConstraintType getType() {
		return ConstraintType.ASPECT;
	}

}
