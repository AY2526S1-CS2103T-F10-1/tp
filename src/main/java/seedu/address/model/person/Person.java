package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.commons.util.CollectionUtil;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.tag.Tag;

/**
 * Represents a Person in the address book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Person {
    public static final String SELF_PAIRING = "Person cannot be paired with themselves.";
    public static final String REPEAT_PAIRING = "{} and {} already paired.";

    public static final Phone DEFAULT_PHONE = new Phone("000");
    public static final Email DEFAULT_EMAIL = new Email("default@email");
    public static final Address DEFAULT_ADDRESS = new Address("Default Address");

    // Identity fields
    private final Name name;
    private final Phone phone;
    private final Email email;

    // Data fields
    private final Address address;
    private final Set<Tag> tags;
    private final ObservableList<Person> pairedPersons;
    private final ObservableList<Person> unmodifiablePairedPersons;
    private final List<Person> unmodifiablePairingsView;

    private final PersonBuilder personBuilder;

    /**
     * The Builder for the Person class.
     */
    public static class PersonBuilder extends Builder<PersonBuilder> {
        // Required parameters
        private Name name;

        // Optional parameters - initialized to default values
        private Phone phone;
        private Email email;
        private Address address;
        private Set<Tag> tags;
        private ObservableList<Person> pairedPersons;

        /**
         * Constructor for PersonBuilder.
         */
        public PersonBuilder() {

        }

        /**
         * Constructor for PersonBuilder with Person.
         */
        public PersonBuilder(Person p) {
            this.name = p.getName();
            this.phone = p.getPhone();
            this.email = p.getEmail();
            this.address = p.getAddress();
            this.tags = p.getTags();
            this.pairedPersons = FXCollections.observableArrayList();
            this.pairedPersons.setAll(p.getPairings());
        }

        /**
         * Copy constructor.
         */
        public PersonBuilder(PersonBuilder toCopy) {
            this.name = toCopy.name;
            this.phone = toCopy.phone;
            this.email = toCopy.email;
            this.address = toCopy.address;
            this.tags = toCopy.tags;
            this.pairedPersons = toCopy.pairedPersons;
        }

        /**
         * Setter for the name parameter.
         */
        public PersonBuilder name(Name name) {
            if (name != null) {
                this.name = name;
            }
            return this;
        }

        /**
         * Setter for the name parameter but with String argument.
         */
        public PersonBuilder name(String name) {
            return this.name(new Name(name));
        }

        /**
         * Setter for the name parameter but only if this.name does not already exist.
         */
        public PersonBuilder nameIfNotPresent(Name name) {
            if (this.name == null && name != null) {
                this.name = name;
            }
            return this;
        }

        /**
         * Setter for the phone parameter.
         */
        public PersonBuilder phone(Phone phone) {
            if (phone != null) {
                this.phone = phone;
            }
            return this;
        }

        /**
         * Setter for the phone parameter but with String argument.
         */
        public PersonBuilder phone(String phone) {
            return this.phone(new Phone(phone));
        }

        /**
         * Setter for the phone parameter but only if this.phone does not already exist.
         */
        public PersonBuilder phoneIfNotPresent(Phone phone) {
            if (this.phone == null && phone != null) {
                this.phone = phone;
            }
            return this;
        }

        /**
         * Setter for the email parameter.
         */
        public PersonBuilder email(Email email) {
            if (email != null) {
                this.email = email;
            }
            return this;
        }

        /**
         * Setter for the email parameter but with String argument.
         */
        public PersonBuilder email(String email) {
            return this.email(new Email(email));
        }

        /**
         * Setter for the email parameter but only if this.email does not already exist.
         */
        public PersonBuilder emailIfNotPresent(Email email) {
            if (this.email == null && email != null) {
                this.email = email;
            }
            return this;
        }

        /**
         * Setter for the address parameter.
         */
        public PersonBuilder address(Address address) {
            if (address != null) {
                this.address = address;
            }
            return this;
        }

        /**
         * Setter for the address parameter but with String argument.
         */
        public PersonBuilder address(String address) {
            return this.address(new Address(address));
        }

        /**
         * Setter for the address parameter but only if this.address does not already exist.
         */
        public PersonBuilder addressIfNotPresent(Address address) {
            if (this.address == null && address != null) {
                this.address = address;
            }
            return this;
        }

        /**
         * Setter for the tags parameter.
         */
        public PersonBuilder tags(Set<Tag> tags) {
            if (tags != null) {
                this.tags = new HashSet<>();
                this.tags.addAll(tags);
            }
            return this;
        }

        /**
         * Setter for the tags parameter but with tags in String format.
         */
        public PersonBuilder tags(String... tags) {
            if (tags != null) {
                this.tags = new HashSet<>();
                for (int i = 0; i < tags.length; i++) {
                    this.tags.add(new Tag(tags[i]));
                }
            }
            return this;
        }

        /**
         * Setter for the tags parameter but only if this.tags does not already exist.
         */
        public PersonBuilder tagsIfNotPresent(Set<Tag> tags) {
            if (this.tags == null && tags != null) {
                this.tags = tags;
            }
            return this;
        }

        /**
         * Setter for the pairedPersons parameter.
         */
        public PersonBuilder pairedPersons(List<Person> pairedPersons) {
            if (pairedPersons != null) {
                this.pairedPersons = FXCollections.observableArrayList();
                this.pairedPersons.setAll(pairedPersons);
                FXCollections.sort(this.pairedPersons, Comparator.comparing(p -> p.getName().toString()));
            }
            return this;
        }

        /**
         * Setter for the pairedPersons parameter but only if this.pairedPersons does not already exist.
         */
        public PersonBuilder pairedPersonsIfNotPresent(List<Person> pairedPersons) {
            if (this.pairedPersons == null && pairedPersons != null) {
                this.pairedPersons = FXCollections.observableArrayList();
                this.pairedPersons.setAll(pairedPersons);
                FXCollections.sort(this.pairedPersons, Comparator.comparing(p -> p.getName().toString()));
            }
            return this;
        }

        /**
         * Returns a Person object with the parameter values of the Builder.
         */
        public Person build() {
            requireNonNull(name);
            return new Person(this);
        }

        /**
         * Returns true if at least one field is edited.
         */
        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyNonNull(name, phone, email, address, tags, pairedPersons);
        }

        public Name getName() {
            return this.name;
        }

        public Phone getPhone() {
            return this.phone;
        }

        public Email getEmail() {
            return this.email;
        }

        public Address getAddress() {
            return this.address;
        }

        public Set<Tag> getTags() {
            return this.tags;
        }

        public ObservableList<Person> getPairedPersons() {
            return this.pairedPersons;
        }

        @Override
        public boolean equals(Object other) {
            if (other == this) {
                return true;
            }

            // instanceof handles nulls
            if (!(other instanceof PersonBuilder)) {
                return false;
            }

            PersonBuilder otherPersonBuilder = (PersonBuilder) other;
            return Objects.equals(name, otherPersonBuilder.name)
                    && Objects.equals(phone, otherPersonBuilder.phone)
                    && Objects.equals(email, otherPersonBuilder.email)
                    && Objects.equals(address, otherPersonBuilder.address)
                    && Objects.equals(tags, otherPersonBuilder.tags)
                    && Objects.equals(pairedPersons, otherPersonBuilder.pairedPersons);
        }

        @Override
        public String toString() {
            return new ToStringBuilder(this)
                    .add("name", name)
                    .add("phone", phone)
                    .add("email", email)
                    .add("address", address)
                    .add("tags", tags)
                    .add("paired persons", pairedPersons)
                    .toString();
        }
    }

    /**
     * Constructor for a Person object using the builder. This is the intended method of constructing
     * the person object via the Builder pattern.
     */
    public Person(PersonBuilder builder) {
        requireAllNonNull(builder.name);
        this.name = builder.name;
        this.phone = builder.phone != null ? builder.phone : DEFAULT_PHONE;
        this.email = builder.email != null ? builder.email : DEFAULT_EMAIL;
        this.address = builder.address != null ? builder.address : DEFAULT_ADDRESS;
        this.tags = builder.tags != null ? builder.tags : new HashSet<>();
        this.pairedPersons = builder.pairedPersons != null
                ? builder.pairedPersons : FXCollections.observableArrayList();
        this.unmodifiablePairedPersons = FXCollections.unmodifiableObservableList(pairedPersons);
        this.unmodifiablePairingsView = Collections.unmodifiableList(pairedPersons);
        this.personBuilder = builder;
    }

    /**
     * Converts the Person back to Builder form so that it can be easily modified.
     */
    public PersonBuilder toBuilder() {
        return new PersonBuilder(this.personBuilder);
    }

    /**
     * Returns the name.
     */
    public Name getName() {
        return name;
    }

    /**
     * Returns the phone number.
     */
    public Phone getPhone() {
        return phone;
    }

    /**
     * Returns the email.
     */
    public Email getEmail() {
        return email;
    }

    /**
     * Returns the address.
     */
    public Address getAddress() {
        return address;
    }

    /**
     * Trims and converts the given email to lowercase for consistent comparison.
     */
    private String normalizeEmail(String email) {
        return email.trim().toLowerCase();
    }

    /**
     * Returns an immutable tag set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public Set<Tag> getTags() {
        return Collections.unmodifiableSet(tags);
    }

    /**
     * Returns an immutable tag set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public List<Person> getPairedPersons() {
        return unmodifiablePairingsView;
    }

    /**
     * Adds a Person pair. Only call this on one of the two partners in each pair.
     * For example, after running {@code person1.addPerson(person2)},
     * you should not then run {@code `person2.addPerson(person1)}.
     */
    public void addPerson(Person otherPerson) throws IllegalValueException {
        if (otherPerson == this) {
            throw new IllegalValueException(SELF_PAIRING);
        }
        if (this.pairedPersons.contains(otherPerson)) {
            throw new IllegalValueException(String.format(REPEAT_PAIRING,
                    getName().toString(), otherPerson.getName().toString()));
        }

        this.pairedPersons.add(otherPerson);
        otherPerson.pairedPersons.add(this);
        FXCollections.sort(this.pairedPersons, Comparator.comparing(s -> s.getName().toString()));
        FXCollections.sort(otherPerson.pairedPersons, Comparator.comparing(s -> s.getName().toString()));
    }

    /**
     * Adds a Person pair. Only call this on one of the two partners in each pair.
     * For example, after running {@code person1.addPerson(person2)},
     * you should not then run {@code `person2.addPerson(person1)}.
     */
    public void removePerson(Person otherPerson) throws IllegalValueException {
        if (otherPerson == this) {
            throw new IllegalValueException(SELF_PAIRING);
        }
        if (!pairedPersons.contains(otherPerson)) {
            throw new IllegalValueException(String.format(REPEAT_PAIRING,
                    getName().toString(), otherPerson.getName().toString()));
        }

        pairedPersons.remove(otherPerson);
        otherPerson.pairedPersons.remove(this);

        FXCollections.sort(pairedPersons, Comparator.comparing(s -> s.getName().toString()));
        FXCollections.sort(otherPerson.pairedPersons, Comparator.comparing(s -> s.getName().toString()));
    }

    /**
     * Returns an immutable pairings list, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public ObservableList<Person> getPairings() {
        return unmodifiablePairedPersons;
    }

    /**
     * Returns true if this person and the given person are considered the same.
     * Two persons are the same if they have the same name (case-insensitive),
     * and either share the same real phone or email, or both have default contact details.
     *
     * @param otherPerson the other person to compare with
     * @return true if both represent the same person, false otherwise
     */
    public boolean isSamePerson(Person otherPerson) {
        requireNonNull(otherPerson);
        if (otherPerson == this) {
            return true;
        }

        // normalize names (case-insensitive, trimmed)
        String thisName = Name.normalizeForIdentity(this.getName().fullName);
        String otherName = Name.normalizeForIdentity(otherPerson.getName().fullName);
        if (!thisName.equals(otherName)) {
            return false;
        }

        // normalize contact values
        String thisPhone = Phone.normalizeForIdentity(getPhone().value);
        String otherPhone = Phone.normalizeForIdentity(otherPerson.getPhone().value);
        String thisEmail = normalizeEmail(getEmail().value);
        String otherEmail = normalizeEmail(otherPerson.getEmail().value);


        // identify real contacts
        boolean thisHasRealPhone = !thisPhone.equals(DEFAULT_PHONE.value);
        boolean otherHasRealPhone = !otherPhone.equals(DEFAULT_PHONE.value);
        boolean thisHasRealEmail = !thisEmail.equals(DEFAULT_EMAIL.value);
        boolean otherHasRealEmail = !otherEmail.equals(DEFAULT_EMAIL.value);

        // compare only when both sides have real data
        boolean isSamePhone = thisHasRealPhone && otherHasRealPhone && thisPhone.equals(otherPhone);
        boolean isSameEmail = thisHasRealEmail && otherHasRealEmail && thisEmail.equals(otherEmail);

        // if both phones default & same; if both emails default → same
        boolean bothPhonesDefault = !thisHasRealPhone && !otherHasRealPhone;
        boolean bothEmailsDefault = !thisHasRealEmail && !otherHasRealEmail;
        boolean bothPhoneAndEmailDefault = bothPhonesDefault && bothEmailsDefault;
        // same name AND (
        //    same real phone OR same real email OR both phones default OR both emails default
        // )
        return isSamePhone || isSameEmail || bothPhoneAndEmailDefault;
    }

    /**
     * Returns true if both persons have the same identity and data fields.
     * This defines a stronger notion of equality between two persons.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof Person)) {
            return false;
        }
        Person otherPerson = (Person) other;
        // Intentionally EXCLUDE personList to avoid deep / cyclic recursion when pairings are mutual.
        return name.equals(otherPerson.name)
                && phone.equals(otherPerson.phone)
                && email.equals(otherPerson.email)
                && address.equals(otherPerson.address)
                && tags.equals(otherPerson.tags);
    }

    @Override
    public int hashCode() {
        // Exclude personList for consistency with equals (prevents cyclic recursion / stack overflow)
        return Objects.hash(name, phone, email, address, tags);
    }

    /**
     * Returns a string representation of the Person and not its subclasses for testing.
     */
    public String originalToString() {
        // Avoid printing entire personList graph (can be cyclic). Show only paired names for debugging.
        List<String> pairedNames = pairedPersons.stream()
                .map(p -> p.getName().toString())
                .collect(Collectors.toList());
        return new ToStringBuilder(this)
                .add("name", name)
                .add("phone", phone)
                .add("email", email)
                .add("address", address)
                .add("tags", tags)
                .add("pairings", pairedNames)
                .toString();
    }

    @Override
    public String toString() {
        return originalToString();
    }

}
