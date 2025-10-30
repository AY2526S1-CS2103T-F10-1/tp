package seedu.address.testutil;

import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_FRIEND;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Supplier;

import seedu.address.model.AddressBook;
import seedu.address.model.person.Person;
import seedu.address.model.person.Student;
import seedu.address.model.person.Volunteer;

/**
 * A utility class containing a list of {@code Person} objects to be used in tests.
 */
public class TypicalPersons {

    public static final Supplier<Student> ALICE_SUPPLIER = (() ->
            new Student.StudentBuilder()
                    .name("Alice Pauline")
                    .address("123, Jurong West Ave 6, #08-111")
                    .email("alice@example.com")
                    .phone("94351253")
                    .tags("friends")
                    .build()
    );
    public static final Supplier<Student> BENSON_SUPPLIER = (() ->
            new Student.StudentBuilder()
                    .name("Benson Meier")
                    .address("311, Clementi Ave 2, #02-25")
                    .email("johnd@example.com")
                    .phone("98765432")
                    .tags("owesMoney", "friends")
                    .build()
    );
    public static final Supplier<Student> CARL_SUPPLIER = (() ->
            new Student.StudentBuilder()
                    .name("Carl Kurz")
                    .phone("95352563")
                    .email("heinz@example.com")
                    .address("wall street")
                    .build()
    );
    public static final Supplier<Student> DANIEL_SUPPLIER = (() ->
            new Student.StudentBuilder()
                    .name("Daniel Meier")
                    .phone("87652533")
                    .email("cornelia@example.com")
                    .address("10th street")
                    .tags("friends")
                    .build()
    );
    public static final Supplier<Volunteer> ELLE_SUPPLIER = (() ->
            new Volunteer.VolunteerBuilder()
                    .name("Elle Meyer")
                    .phone("9482224")
                    .email("werner@example.com")
                    .address("michegan ave")
                    .build()
    );
    public static final Supplier<Volunteer> FIONA_SUPPLIER = (() ->
            new Volunteer.VolunteerBuilder()
                    .name("Fiona Kunz")
                    .phone("9482427")
                    .email("lydia@example.com")
                    .address("little tokyo")
                    .build()
    );
    public static final Supplier<Volunteer> GEORGE_SUPPLIER = (() ->
            new Volunteer.VolunteerBuilder()
                    .name("George Best")
                    .phone("9482442")
                    .email("anna@example.com")
                    .address("4th street")
                    .build()
    );

    // Manually added
    public static final Supplier<Volunteer> HOON_SUPPLIER = (() ->
            new Volunteer.VolunteerBuilder()
                    .name("Hoon Meier")
                    .phone("8482424")
                    .email("stefan@example.com")
                    .address("little india")
                    .build()
    );
    public static final Supplier<Volunteer> IDA_SUPPLIER = (() ->
            new Volunteer.VolunteerBuilder()
                    .name("Ida Mueller")
                    .phone("8482131")
                    .email("hans@example.com")
                    .address("chicago ave")
                    .build()
    );

    // Manually added - Person's details found in {@code CommandTestUtil}
    public static final Supplier<Volunteer> AMY_SUPPLIER = (() ->
            new Volunteer.VolunteerBuilder()
                    .name(VALID_NAME_AMY)
                    .phone(VALID_PHONE_AMY)
                    .email(VALID_EMAIL_AMY)
                    .address(VALID_ADDRESS_AMY)
                    .tags(VALID_TAG_FRIEND)
                    .build()
    );
    public static final Supplier<Volunteer> BOB_SUPPLIER = (() ->
            new Volunteer.VolunteerBuilder()
                    .name(VALID_NAME_BOB)
                    .phone(VALID_PHONE_BOB)
                    .email(VALID_EMAIL_BOB)
                    .address(VALID_ADDRESS_BOB)
                    .tags(VALID_TAG_HUSBAND, VALID_TAG_FRIEND)
                    .build()
    );

    public static final Student ALICE = ALICE_SUPPLIER.get();
    public static final Student BENSON = BENSON_SUPPLIER.get();
    public static final Student CARL = CARL_SUPPLIER.get();
    public static final Student DANIEL = DANIEL_SUPPLIER.get();
    public static final Volunteer ELLE = ELLE_SUPPLIER.get();
    public static final Volunteer FIONA = FIONA_SUPPLIER.get();
    public static final Volunteer GEORGE = GEORGE_SUPPLIER.get();
    public static final Volunteer HOON = HOON_SUPPLIER.get();
    public static final Volunteer IDA = IDA_SUPPLIER.get();
    public static final Volunteer AMY = AMY_SUPPLIER.get();
    public static final Volunteer BOB = BOB_SUPPLIER.get();

    public static final String KEYWORD_MATCHING_MEIER = "Meier"; // A keyword that matches MEIER

    private TypicalPersons() {} // prevents instantiation

    /**
     * Returns an {@code AddressBook} with all the typical persons.
     */
    public static AddressBook getTypicalAddressBook() {
        AddressBook ab = new AddressBook();
        for (Person person : getTypicalPersons()) {
            ab.addPerson(person);
        }
        return ab;
    }


    public static List<Person> getTypicalPersons() {
        return new ArrayList<>(Arrays.asList((Person) ALICE_SUPPLIER.get(), BENSON_SUPPLIER.get(), CARL_SUPPLIER.get(),
                DANIEL_SUPPLIER.get(), ELLE_SUPPLIER.get(), FIONA_SUPPLIER.get(), GEORGE_SUPPLIER.get()));
    }
}
