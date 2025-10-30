package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.BOB;

import org.junit.jupiter.api.Test;


public class PersonTest {

    @Test
    public void isSamePerson() {

        // same object -> returns true
        assertTrue(ALICE.isSamePerson(ALICE));

        // same name and same phone -> returns true
        Person editedAlice = new Student.StudentBuilder(ALICE)
                .email(VALID_EMAIL_BOB) // different email, same name + phone
                .build();
        assertTrue(ALICE.isSamePerson(editedAlice));

        // same name and same email -> returns true
        editedAlice = new Student.StudentBuilder(ALICE)
                .phone(VALID_PHONE_BOB) // different phone, same name + email
                .build();
        assertTrue(ALICE.isSamePerson(editedAlice));

        // same name, different phone AND different email -> returns false
        editedAlice = new Student.StudentBuilder(ALICE)
                .phone(VALID_PHONE_BOB)
                .email(VALID_EMAIL_BOB)
                .build();
        assertFalse(ALICE.isSamePerson(editedAlice));

        // different name, all other attributes same -> returns false
        editedAlice = new Student.StudentBuilder(ALICE).name(VALID_NAME_BOB).build();
        assertFalse(ALICE.isSamePerson(editedAlice));

        // same name but different case -> returns true (case-insensitive)
        Person editedBob = new Student.StudentBuilder(BOB).name(VALID_NAME_BOB.toLowerCase()).build();
        assertTrue(BOB.isSamePerson(editedBob));

        // same name but with trailing spaces -> returns true (trimmed)
        String nameWithTrailingSpaces = VALID_NAME_BOB + " ";
        editedBob = new Student.StudentBuilder(BOB).name(nameWithTrailingSpaces).build();
        assertTrue(BOB.isSamePerson(editedBob));
    }

    @Test
    public void equals() {
        // same values -> returns true
        Person aliceCopy = new Student.StudentBuilder(ALICE).build();

        // same object -> returns true
        assertTrue(ALICE.equals(ALICE));

        // null -> returns false
        assertFalse(ALICE.equals(null));

        // different type -> returns false
        assertFalse(ALICE.equals(5));

        // different person -> returns false
        assertFalse(ALICE.equals(BOB));

        // different name -> returns false
        Person editedAlice = new Student.StudentBuilder(ALICE).name(VALID_NAME_BOB).build();
        assertFalse(ALICE.equals(editedAlice));

        // different phone -> returns false
        editedAlice = new Student.StudentBuilder(ALICE).phone(VALID_PHONE_BOB).build();
        assertFalse(ALICE.equals(editedAlice));

        // different email -> returns false
        editedAlice = new Student.StudentBuilder(ALICE).email(VALID_EMAIL_BOB).build();
        assertFalse(ALICE.equals(editedAlice));

        // different address -> returns false
        editedAlice = new Student.StudentBuilder(ALICE).address(VALID_ADDRESS_BOB).build();
        assertFalse(ALICE.equals(editedAlice));

        // different tags -> returns false
        editedAlice = new Student.StudentBuilder(ALICE).tags(VALID_TAG_HUSBAND).build();
        assertFalse(ALICE.equals(editedAlice));
    }

    @Test
    public void toStringMethod() {
        String expected = "[Student] seedu.address.model.person.Student{name=Alice Pauline, phone=94351253,"
                + " email=alice@example.com, address=123, Jurong West Ave 6, #08-111, tags=[[friends]]" + "}";
        assertEquals(expected, ALICE.toString());
    }
}
