package seedu.address.model.person;

abstract class RoleBasedPerson extends Person {

    protected RoleBasedPerson(PersonBuilder<?, ?> builder) {
        super(builder);
    }

    public abstract String getType();

    @Override public String toString() {
        return "[" + getType() + "] " + super.toString();
    }
}
