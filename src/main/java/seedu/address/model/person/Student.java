package seedu.address.model.person;

/**
 * Represents a Student in the tutoring volunteer system.
 * Same fields as Person; used to distinguish between Students and Volunteers.
 */
public class Student extends RoleBasedPerson {

    /**
     * The Builder for the Student class.
     */
    public static class StudentBuilder extends PersonBuilder<StudentBuilder, Student> {
        public StudentBuilder() {
            super();
        }

        public StudentBuilder(Person person) {
            super(person);
        }

        public StudentBuilder(PersonBuilder<?, ?> personBuilder) {
            super(personBuilder);
        }

        /**
         * Returns a Student object with the parameter values of the Builder.
         */
        @Override
        public Student build() {
            return new Student(this);
        }

        @Override
        public StudentBuilder self() {
            return this;
        }
    }

    private Student(StudentBuilder builder) {
        super(builder);
    }

    @Override
    public StudentBuilder toBuilder() {
        return new StudentBuilder(this);
    }

    /**
     * Converts the PersonBuilder object to a StudentBuilder object.
     */
    public static StudentBuilder toBuilder(PersonBuilder<?, ?> personBuilder) {
        return copyFields(personBuilder.build(), new StudentBuilder());
    }

    /**
     * Converts the Person object to a StudentBuilder object.
     */
    public static StudentBuilder toBuilder(Person person) {
        return copyFields(person, new StudentBuilder());
    }

    @Override
    public String getType() {
        return "Student";
    }

    @Override
    public boolean equals(Object other) {
        return other instanceof Student && super.equals(other);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }
}
