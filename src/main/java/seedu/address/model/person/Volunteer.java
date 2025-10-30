package seedu.address.model.person;

/**
 * Represents a Volunteer in the tutoring volunteer system.
 * Same fields as Person; used to distinguish between Students and Volunteers.
 */
public class Volunteer extends RoleBasedPerson {

    /**
     * The Builder for the Volunteer class.
     */
    public static class VolunteerBuilder extends PersonBuilder<VolunteerBuilder, Volunteer> {
        public VolunteerBuilder() {
            super();
        }

        private VolunteerBuilder(Volunteer volunteer) {
            super(volunteer);
        }

        /**
         * Returns a Student object with the parameter values of the Builder.
         */
        @Override
        public Volunteer build() {
            return new Volunteer(this);
        }

        @Override
        public VolunteerBuilder self() {
            return this;
        }
    }

    private Volunteer(VolunteerBuilder builder) {
        super(builder);
    }

    @Override
    public VolunteerBuilder toBuilder() {
        return new VolunteerBuilder(this);
    }

    /**
     * Converts the PersonBuilder object to a VolunteerBuilder object.
     */
    public static VolunteerBuilder toBuilder(PersonBuilder<?,?> personBuilder) {
        return copyFields(personBuilder.build(), new VolunteerBuilder());
    }

    /**
     * Converts the Person object to a VolunteerBuilder object.
     */
    public static VolunteerBuilder toBuilder(Person person) {
        return copyFields(person, new VolunteerBuilder());
    }

    @Override
    public String getType() {
        return "Volunteer";
    }

    @Override
    public boolean equals(Object other) {
        return other instanceof Volunteer && super.equals(other);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }
}
