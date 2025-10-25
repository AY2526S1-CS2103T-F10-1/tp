package seedu.address.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.storage.JsonAdaptedPerson.MISSING_FIELD_MESSAGE_FORMAT;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalPersons.BENSON;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Phone;
import seedu.address.model.person.Student;
import seedu.address.model.person.Volunteer;

public class JsonAdaptedPersonTest {
    private static final String INVALID_NAME = "-R@chel";
    private static final String INVALID_PHONE = "-651234";
    private static final String INVALID_ADDRESS = " ";
    private static final String INVALID_EMAIL = "example.com";
    private static final String INVALID_TAG = "#friend";

    private static final String VALID_IDENTITY_NAME = "Valid Name";
    private static final String VALID_IDENTITY_PHONE = "91234567";
    private static final String INVALID_IDENTITY_NAME = "-Invalid@Name";
    private static final String INVALID_IDENTITY_PHONE = "-651234";

    private static final String VALID_NAME = BENSON.getName().toString();
    private static final String VALID_PHONE = BENSON.getPhone().toString();
    private static final String VALID_EMAIL = BENSON.getEmail().toString();
    private static final String VALID_ADDRESS = BENSON.getAddress().toString();
    private static final List<JsonAdaptedTag> VALID_TAGS = BENSON.getTags().stream()
            .map(JsonAdaptedTag::new)
            .collect(Collectors.toList());

    private static final ArrayList<JsonAdaptedPerson.JsonAdaptedPairingRef> EMPTY_PAIRINGS = new ArrayList<>();

    private static final String VALID_TYPE = "student";
    private static final String INVALID_TYPE = "alien";

    @Test
    public void toModelType_validPersonDetails_returnsPerson() throws Exception {
        JsonAdaptedPerson person = new JsonAdaptedPerson(BENSON);
        assertEquals(BENSON, person.toModelType());
    }

    @Test
    public void toModelType_invalidName_throwsIllegalValueException() {
        JsonAdaptedPerson person =
                new JsonAdaptedPerson(INVALID_NAME, VALID_PHONE, VALID_EMAIL, VALID_ADDRESS, VALID_TAGS,
                        EMPTY_PAIRINGS, VALID_TYPE);
        String expectedMessage = Name.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_nullName_throwsIllegalValueException() {
        JsonAdaptedPerson person = new JsonAdaptedPerson(null, VALID_PHONE, VALID_EMAIL, VALID_ADDRESS,
                VALID_TAGS, EMPTY_PAIRINGS, VALID_TYPE);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_invalidPhone_throwsIllegalValueException() {
        JsonAdaptedPerson person =
                new JsonAdaptedPerson(VALID_NAME, INVALID_PHONE, VALID_EMAIL, VALID_ADDRESS, VALID_TAGS,
                        EMPTY_PAIRINGS, VALID_TYPE);
        String expectedMessage = Phone.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_nullPhone_throwsIllegalValueException() {
        JsonAdaptedPerson person = new JsonAdaptedPerson(VALID_NAME, null, VALID_EMAIL, VALID_ADDRESS,
                VALID_TAGS, EMPTY_PAIRINGS, VALID_TYPE);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Phone.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_invalidEmail_throwsIllegalValueException() {
        JsonAdaptedPerson person =
                new JsonAdaptedPerson(VALID_NAME, VALID_PHONE, INVALID_EMAIL, VALID_ADDRESS,
                        VALID_TAGS, EMPTY_PAIRINGS, VALID_TYPE);
        String expectedMessage = Email.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_nullEmail_throwsIllegalValueException() {
        JsonAdaptedPerson person = new JsonAdaptedPerson(VALID_NAME, VALID_PHONE, null, VALID_ADDRESS,
                VALID_TAGS, EMPTY_PAIRINGS, VALID_TYPE);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Email.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_invalidAddress_throwsIllegalValueException() {
        JsonAdaptedPerson person =
                new JsonAdaptedPerson(VALID_NAME, VALID_PHONE, VALID_EMAIL, INVALID_ADDRESS,
                        VALID_TAGS, EMPTY_PAIRINGS, VALID_TYPE);
        String expectedMessage = Address.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_nullAddress_throwsIllegalValueException() {
        JsonAdaptedPerson person = new JsonAdaptedPerson(VALID_NAME, VALID_PHONE, VALID_EMAIL, null,
                VALID_TAGS, EMPTY_PAIRINGS, VALID_TYPE);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Address.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_invalidTags_throwsIllegalValueException() {
        List<JsonAdaptedTag> invalidTags = new ArrayList<>(VALID_TAGS);
        invalidTags.add(new JsonAdaptedTag(INVALID_TAG));
        JsonAdaptedPerson person =
                new JsonAdaptedPerson(VALID_NAME, VALID_PHONE, VALID_EMAIL, VALID_ADDRESS,
                        invalidTags, EMPTY_PAIRINGS, VALID_TYPE);
        assertThrows(IllegalValueException.class, person::toModelType);
    }

    @Test
    public void toModelType_nullType_throwsIllegalValueException() {
        JsonAdaptedPerson person =
                new JsonAdaptedPerson(VALID_NAME, VALID_PHONE, VALID_EMAIL, VALID_ADDRESS,
                        VALID_TAGS, EMPTY_PAIRINGS, null);
        assertThrows(IllegalValueException.class, person::toModelType);
    }

    @Test
    public void toModelType_invalidType_throwsIllegalValueException() {
        JsonAdaptedPerson person =
                new JsonAdaptedPerson(VALID_NAME, VALID_PHONE, VALID_EMAIL, VALID_ADDRESS,
                        VALID_TAGS, EMPTY_PAIRINGS, INVALID_TYPE);
        assertThrows(IllegalValueException.class, person::toModelType);
    }

    @Test
    public void createPersonIdentity_validFields_success() throws Exception {
        JsonAdaptedPerson.PersonIdentity identity = new JsonAdaptedPerson.PersonIdentity(
                VALID_IDENTITY_NAME, VALID_IDENTITY_PHONE);
        assertEquals(VALID_IDENTITY_NAME, identity.getNameValue());
        assertEquals(VALID_IDENTITY_PHONE, identity.getPhoneValue());
    }

    @Test
    public void createPersonIdentity_nullName_throwsIllegalValueException() {
        assertThrows(IllegalValueException.class, () -> new JsonAdaptedPerson.PersonIdentity(
                null, VALID_IDENTITY_PHONE));
    }

    @Test
    public void createPersonIdentity_invalidName_throwsIllegalValueException() {
        assertThrows(IllegalValueException.class, () -> new JsonAdaptedPerson.PersonIdentity(
                INVALID_IDENTITY_NAME, VALID_IDENTITY_PHONE));
    }

    @Test
    public void createPersonIdentity_nullPhone_throwsIllegalValueException() {
        assertThrows(IllegalValueException.class, () -> new JsonAdaptedPerson.PersonIdentity(
                VALID_IDENTITY_NAME, null));
    }

    @Test
    public void createPersonIdentity_invalidPhone_throwsIllegalValueException() {
        assertThrows(IllegalValueException.class, () -> new JsonAdaptedPerson.PersonIdentity(
                VALID_IDENTITY_NAME, INVALID_IDENTITY_PHONE));
    }

    @Test
    public void equals_sameValues_returnsTrue() throws Exception {
        JsonAdaptedPerson.PersonIdentity identity1 = new JsonAdaptedPerson.PersonIdentity(
                VALID_IDENTITY_NAME, VALID_IDENTITY_PHONE);
        JsonAdaptedPerson.PersonIdentity identity2 = new JsonAdaptedPerson.PersonIdentity(
                VALID_IDENTITY_NAME, VALID_IDENTITY_PHONE);
        assertEquals(identity1, identity1);
        assertNotEquals("string", identity1);
        assertEquals(identity1, identity2);
    }

    @Test
    public void hashCode_sameValues_returnsSameHashCode() throws Exception {
        JsonAdaptedPerson.PersonIdentity identity1 = new JsonAdaptedPerson.PersonIdentity(
                VALID_IDENTITY_NAME, VALID_IDENTITY_PHONE);
        JsonAdaptedPerson.PersonIdentity identity2 = new JsonAdaptedPerson.PersonIdentity(
                VALID_IDENTITY_NAME, VALID_IDENTITY_PHONE);
        assertEquals(identity1.hashCode(), identity2.hashCode());
    }

    @Test
    public void roundTrip_fromStudent_preservesSubtype() throws Exception {
        Student s = (Student) new Student.StudentBuilder()
                .name(BENSON.getName())
                .phone(BENSON.getPhone())
                .email(BENSON.getEmail())
                .address(BENSON.getAddress())
                .tags("friends")
                .build();
        JsonAdaptedPerson jap = new JsonAdaptedPerson(s);
        assertTrue(jap.toModelType() instanceof Student);
    }

    @Test
    public void roundTrip_fromVolunteer_preservesSubtype() throws Exception {
        Volunteer v = (Volunteer) new Volunteer.VolunteerBuilder()
                .name(BENSON.getName())
                .phone(BENSON.getPhone())
                .email(BENSON.getEmail())
                .address(BENSON.getAddress())
                .tags("helpers")
                .build();
        JsonAdaptedPerson jap = new JsonAdaptedPerson(v);
        assertTrue(jap.toModelType() instanceof Volunteer);
    }

    @Test
    public void toModelType_typeCaseVariants_studentAccepted() throws Exception {
        JsonAdaptedPerson person = new JsonAdaptedPerson(
                VALID_NAME, VALID_PHONE, VALID_EMAIL, VALID_ADDRESS,
                VALID_TAGS, EMPTY_PAIRINGS, "StuDent"
        );
        assertTrue(person.toModelType() instanceof Student);
    }

    @Test
    public void toModelType_typeCaseVariants_volunteerAccepted() throws Exception {
        JsonAdaptedPerson person = new JsonAdaptedPerson(
                VALID_NAME, VALID_PHONE, VALID_EMAIL, VALID_ADDRESS,
                VALID_TAGS, EMPTY_PAIRINGS, "VOLUNTEER"
        );
        assertTrue(person.toModelType() instanceof Volunteer);
    }


}
