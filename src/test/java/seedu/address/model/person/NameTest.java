package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class NameTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Name(null));
    }

    @Test
    public void constructor_invalidName_throwsIllegalArgumentException() {
        String invalidName = "";
        assertThrows(IllegalArgumentException.class, () -> new Name(invalidName));
    }

    @Test
    public void isValidName() {
        assertThrows(NullPointerException.class, () -> Name.isValidName(null));

        // invalid
        assertFalse(Name.isValidName(""));
        assertFalse(Name.isValidName(" "));
        assertFalse(Name.isValidName("^"));
        assertFalse(Name.isValidName("-Alice"));
        assertFalse(Name.isValidName(".Bob"));
        assertFalse(Name.isValidName("/Charlie"));
        assertFalse(Name.isValidName("\u0007Beep"));
        assertFalse(Name.isValidName("Mary*Jane"));

        // valid
        assertTrue(Name.isValidName("peter jack"));
        assertTrue(Name.isValidName("12345"));
        assertTrue(Name.isValidName("peter the 2nd"));
        assertTrue(Name.isValidName("Capital Tan"));
        assertTrue(Name.isValidName("David Roger Jackson Ray Jr 2nd"));
        assertTrue(Name.isValidName("O'Connor"));
        assertTrue(Name.isValidName("D’Amico"));
        assertTrue(Name.isValidName("Jean-Luc"));
        assertTrue(Name.isValidName("A. R. Rahman"));
        assertTrue(Name.isValidName("abc s/o def"));
        assertTrue(Name.isValidName("abc s / o def"));
        assertTrue(Name.isValidName("Ren\u00E9e")); // NFC
        assertTrue(Name.isValidName("Rene\u0301e")); // NFD
        assertTrue(Name.isValidName("李 小龍"));
    }

    @Test
    public void equals_strictBehavior() {
        Name name = new Name("Valid Name");

        assertTrue(name.equals(new Name("Valid Name")));
        assertTrue(name.equals(name));
        assertFalse(name.equals(null));
        assertFalse(name.equals(5.0f));
        assertFalse(name.equals(new Name("Other Valid Name")));
        assertFalse(new Name("abc s/o def").equals(new Name("abc s / o def")));
        assertFalse(new Name("D'Amico").equals(new Name("D’Amico")));
        assertFalse(new Name("Rene\u0301e").equals(new Name("Ren\u00E9e")));
    }

    // -------- normalizeForIdentity tests (static method) --------

    @Test
    public void normalizeForIdentity_collapsesWhitespaceAndMarkers() {
        String a = "abc s/o   def";
        String b = "  abc   s / o   def ";
        assertEquals(
                Name.normalizeForIdentity(a),
                Name.normalizeForIdentity(b),
                "s/o variants and extra spaces should normalize to the same identity form");

        // sanity: a valid canonical form is constructible
        new Name("abc s/o def");
    }

    @Test
    public void normalizeForIdentity_unicodeComposesEqually() {
        Name nfc = new Name("Ren\u00E9e");
        Name nfd = new Name("Rene\u0301e");
        assertEquals(
                Name.normalizeForIdentity(nfc.toString()),
                Name.normalizeForIdentity(nfd.toString()),
                "Unicode composed/decomposed forms should normalize equally");
    }

    @Test
    public void normalizeForIdentity_stripsZeroWidth() {
        String withZwsp = "Li\u200B Wei"; // contains ZWSP
        String clean = "Li Wei";
        assertEquals(
                Name.normalizeForIdentity(clean),
                Name.normalizeForIdentity(withZwsp),
                "Zero-width characters should be removed by normalization");

        // sanity: only the clean version is accepted by the constructor
        new Name(clean);
        assertFalse(Name.isValidName(withZwsp));
    }


    @Test
    public void normalizeForIdentity_trimsAndCollapsesSpaces() {
        String messy = "  Jean   Luc  ";
        String neat = "Jean Luc";
        assertEquals(
                Name.normalizeForIdentity(neat),
                Name.normalizeForIdentity(messy),
                "Leading/trailing and repeated spaces should normalize to single spaces");

        // sanity: only the neat version is constructible
        new Name(neat);
        assertFalse(Name.isValidName(messy));
    }


    @Test
    public void normalizeForIdentity_doesNotOverMergeDistinctNames() {
        Name ronAldo = new Name("Ron Aldo");
        Name ronaldo = new Name("Ronaldo");
        assertFalse(
                Name.normalizeForIdentity(ronaldo.toString())
                        .equals(Name.normalizeForIdentity(ronAldo.toString())),
                "Normalization must not collapse distinct names like 'Ron Aldo' and 'Ronaldo'");
    }
}
