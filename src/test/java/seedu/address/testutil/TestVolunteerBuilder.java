package seedu.address.testutil;

import java.util.HashSet;
import java.util.Set;

import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Phone;
import seedu.address.model.person.Volunteer;
import seedu.address.model.tag.Tag;
import seedu.address.model.util.SampleDataUtil;

/**
 * A utility class to help with building Volunteer objects for testing.
 */
public class TestVolunteerBuilder {

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
     * Constructs a {@code TestVolunteerBuilder} with default values.
     */
    public TestVolunteerBuilder() {
        name = new Name(DEFAULT_NAME);
        phone = new Phone(DEFAULT_PHONE);
        email = new Email(DEFAULT_EMAIL);
        address = new Address(DEFAULT_ADDRESS);
        tags = new HashSet<>();
    }

    /**
     * Initializes the TestVolunteerBuilder with the data of {@code personToCopy}.
     * This constructor is useful when creating a Volunteer with pre-existing person data.
     *
     * @param personToCopy The person whose data will be used to initialize the builder.
     */
    public TestVolunteerBuilder(seedu.address.model.person.Person personToCopy) {
        name = personToCopy.getName();
        phone = personToCopy.getPhone();
        email = personToCopy.getEmail();
        address = personToCopy.getAddress();
        tags = new HashSet<>(personToCopy.getTags());
    }

    /**
     * Sets the {@code name} of the {@code Volunteer} that we are building.
     *
     * @param name The name to be set for the Volunteer.
     * @return The updated TestVolunteerBuilder object.
     */
    public TestVolunteerBuilder withName(String name) {
        this.name = new Name(name);
        return this;
    }

    /**
     * Sets the {@code phone} of the {@code Volunteer} that we are building.
     *
     * @param phone The phone number to be set for the Volunteer.
     * @return The updated TestVolunteerBuilder object.
     */
    public TestVolunteerBuilder withPhone(String phone) {
        this.phone = new Phone(phone);
        return this;
    }

    /**
     * Sets the {@code email} of the {@code Volunteer} that we are building.
     *
     * @param email The email to be set for the Volunteer.
     * @return The updated TestVolunteerBuilder object.
     */
    public TestVolunteerBuilder withEmail(String email) {
        this.email = new Email(email);
        return this;
    }

    /**
     * Sets the {@code address} of the {@code Volunteer} that we are building.
     *
     * @param address The address to be set for the Volunteer.
     * @return The updated TestVolunteerBuilder object.
     */
    public TestVolunteerBuilder withAddress(String address) {
        this.address = new Address(address);
        return this;
    }

    /**
     * Sets the {@code tags} of the {@code Volunteer} that we are building.
     * The tags are provided as a varargs array of strings, which are then converted into a set of tags.
     *
     * @param tags The tags to be set for the Volunteer.
     * @return The updated TestVolunteerBuilder object.
     */
    public TestVolunteerBuilder withTags(String... tags) {
        this.tags = SampleDataUtil.getTagSet(tags);
        return this;
    }

    /**
     * Builds and returns a {@code Volunteer} object based on the data set in this builder.
     *
     * @return A new Volunteer object with the current data set in the builder.
     */
    public Volunteer build() {
        return new Volunteer(name, phone, email, address, tags);
    }
}
