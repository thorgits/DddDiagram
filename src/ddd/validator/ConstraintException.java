package ddd.validator;

/**
 * @author liwenjun
 * @ClassName ConstraintException
 * @Date 2020-01-05 18:15
 */
public class ConstraintException extends  Exception {
    private   Constraint constraint;

    public Constraint getConstraint() {
        return constraint;
    }

    public void setConstraint(Constraint constraint) {
        this.constraint = constraint;
    }

    public ConstraintException(String message, Constraint constraint) {
        super(message);
        this.constraint = constraint;
    }
}
