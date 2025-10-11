package seedu.address.testutil;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Phone;
import seedu.address.model.person.Student;
import seedu.address.model.tag.Tag;
import seedu.address.model.util.SampleDataUtil;

/**
 * A utility class to help with building {@code Student} objects for testing.
 */
public class TestStudentBuilder {

    public static final String DEFAULT_NAME = "Alice Pauline";
    public static final String DEFAULT_PHONE = "85355255";
    public static final String DEFAULT_EMAIL = "alice@example.com";
    public static final String DEFAULT_ADDRESS = "123, Jurong West Ave 6, #08-111";

    private Name name;
    private Phone phone;
    private Email email;
    private Address address;
    private Set<Tag> tags;

    /**
     * Creates a {@code TestStudentBuilder} with the default details.
     */
    public TestStudentBuilder() {
        name = new Name(DEFAULT_NAME);
        phone = new Phone(DEFAULT_PHONE);
        email = new Email(DEFAULT_EMAIL);
        address = new Address(DEFAULT_ADDRESS);
        tags = SampleDataUtil.getTagSet();
    }

    /**
     * Initializes the {@code TestStudentBuilder} with the data of {@code studentToCopy}.
     *
     * @param studentToCopy The {@code Student} whose details are to be copied into this builder.
     */
    public TestStudentBuilder(Student studentToCopy) {
        name = studentToCopy.getName();
        phone = studentToCopy.getPhone();
        email = studentToCopy.getEmail();
        address = studentToCopy.getAddress();
        tags = studentToCopy.getTags();
    }

    /**
     * Sets the {@code Name} of the {@code Student} that we are building.
     *
     * @param name The name to set.
     * @return This {@code TestStudentBuilder} instance, to allow method chaining.
     */
    public TestStudentBuilder withName(String name) {
        this.name = new Name(name);
        return this;
    }

    /**
     * Sets the {@code Phone} of the {@code Student} that we are building.
     *
     * @param phone The phone number to set.
     * @return This {@code TestStudentBuilder} instance, to allow method chaining.
     */
    public TestStudentBuilder withPhone(String phone) {
        this.phone = new Phone(phone);
        return this;
    }

    /**
     * Sets the {@code Email} of the {@code Student} that we are building.
     *
     * @param email The email address to set.
     * @return This {@code TestStudentBuilder} instance, to allow method chaining.
     */
    public TestStudentBuilder withEmail(String email) {
        this.email = new Email(email);
        return this;
    }

    /**
     * Sets the {@code Address} of the {@code Student} that we are building.
     *
     * @param address The address to set.
     * @return This {@code StudentBuilder} instance, to allow method chaining.
     */
    public TestStudentBuilder withAddress(String address) {
        this.address = new Address(address);
        return this;
    }

    /**
     * Parses the {@code tags} into a {@code Set<Tag>} and sets it to the {@code Student} that we are building.
     *
     * @param tags The tags to assign to the student.
     * @return This {@code TestStudentBuilder} instance, to allow method chaining.
     */
    public TestStudentBuilder withTags(String... tags) {
        this.tags = Stream.of(tags).map(Tag::new).collect(Collectors.toSet());
        return this;
    }

    /**
     * Builds and returns a {@code Student} object with the current attributes set in this builder.
     *
     * @return A new {@code Student} object based on the builder’s current state.
     */
    public Student build() {
        return new Student(name, phone, email, address, tags);
    }
}
