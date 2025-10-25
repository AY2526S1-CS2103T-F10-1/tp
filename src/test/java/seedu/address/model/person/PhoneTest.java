package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class PhoneTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Phone(null));
    }

    @Test
    public void constructor_invalidPhone_throwsIllegalArgumentException() {
        String invalidPhone = "";
        assertThrows(IllegalArgumentException.class, () -> new Phone(invalidPhone));
    }

    @Test
    public void isValidPhone() {
        // null phone number
        assertThrows(NullPointerException.class, () -> Phone.isValidPhone(null));

        // invalid phone numbers
        assertFalse(Phone.isValidPhone("")); // empty
        assertFalse(Phone.isValidPhone(" ")); // spaces only
        assertFalse(Phone.isValidPhone("91")); // fewer than 3 digits
        assertFalse(Phone.isValidPhone("phone")); // letters only
        assertFalse(Phone.isValidPhone("9011p041")); // letters mixed in
        assertFalse(Phone.isValidPhone("+")); // plus with no digits
        assertFalse(Phone.isValidPhone("12+34")); // plus not at start
        assertFalse(Phone.isValidPhone("++1234")); // multiple plus
        assertFalse(Phone.isValidPhone("-1234")); // starts with dash
        assertFalse(Phone.isValidPhone("1234-")); // ends with dash (reject if your validator does)

        // valid phone numbers
        assertTrue(Phone.isValidPhone("911")); // exactly 3 digits
        assertTrue(Phone.isValidPhone("93121534")); // digits only
        assertTrue(Phone.isValidPhone("124293842033123")); // long numbers
        assertTrue(Phone.isValidPhone("9312 1534")); // spaces allowed
        assertTrue(Phone.isValidPhone("9312-1534")); // dashes allowed
        assertTrue(Phone.isValidPhone("+65 9312 1534")); // leading plus + spaces
        assertTrue(Phone.isValidPhone("+1-202-555-0173")); // leading plus + dashes
        assertTrue(Phone.isValidPhone("00123")); // leading zeros ok
    }

    @Test
    public void equals_normalization() {
        // same digits; spaces/dashes ignored
        assertEquals(new Phone("123-456"), new Phone("123 456"));
        assertEquals(new Phone("  123  "), new Phone("123"));
        assertEquals(new Phone("+65 9123-4567"), new Phone("+65 91234567"));

        // leading '+' distinguishes canonical form (E.164 vs local)
        assertFalse(new Phone("+1 234").equals(new Phone("1234")));

        // different numbers
        assertFalse(new Phone("995").equals(new Phone("999")));

        // reflexive & null/type checks
        Phone p = new Phone("999");
        assertTrue(p.equals(p)); // same object
        assertFalse(p.equals(null)); // null
        assertFalse(p.equals(5.0f)); // different type
    }

    @Test
    public void hashCode_consistentWithEquals() {
        // equal numbers -> same hash
        assertEquals(new Phone("123-456").hashCode(), new Phone("123 456").hashCode());
        assertEquals(new Phone("+65 9123 4567").hashCode(), new Phone("+65-91234567").hashCode());
    }
}
