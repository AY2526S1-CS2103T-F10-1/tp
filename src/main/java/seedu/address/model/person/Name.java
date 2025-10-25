package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

import java.text.Normalizer;

/**
 * Represents a Person's name in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidName(String)}
 */
public class Name {

    public static final String MESSAGE_CONSTRAINTS =
            "Names must not be blank and may include letters (any language), numbers, spaces, and common punctuation "
                    + "like apostrophes (’ or '), hyphens (-), periods (.), slashes (/), commas (,), and parentheses.";

    /*
     * Explanation:
     * ^                                     start
     * [\\p{L}\\p{M}\\p{N}]                 first char: a letter/mark/number (prevents leading whitespace)
     * [\\p{L}\\p{M}\\p{N} .,'’\\-/()]*     subsequent chars: letters/marks/numbers/space/.-,'’/() and hyphen
     * $                                     end
     *
     * \p{L} = any kind of letter from any language
     * \p{M} = combining marks (accents that follow letters)
     * \p{N} = numbers (for rare real names like “X Æ A-12”)
     */
    public static final String VALIDATION_REGEX = "^[\\p{L}\\p{M}\\p{N}][\\p{L}\\p{M}\\p{N} .,'’\\-/()]*$";

    public final String fullName;

    /**
     * Constructs a {@code Name}.
     *
     * @param name A valid name.
     */
    public Name(String name) {
        requireNonNull(name);
        checkArgument(isValidName(name), MESSAGE_CONSTRAINTS);
        fullName = name;
    }

    /**
     * Returns true if a given string is a valid name.
     */
    public static boolean isValidName(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    /**
     * Produces a tolerant, canonical form of a name for identity comparison without
     * changing the user-facing value.
     * <p>
     * Normalization steps:
     * 1) Unicode NFKC, lowercase (Locale.ROOT)
     * 2) Standardize punctuation: fancy apostrophes → ' ; en/em dashes → - ; slash variants → /
     * 3) Collapse runs of whitespace to a single space and trim
     * 4) Canonicalize relationship tokens “s/o”, “d/o”, “w/o” regardless of spacing or slash variant
     *    (e.g., "s / o", "S⁄O") → "s/o", with spaces around when adjacent to letters
     * 5) Collapse whitespace again
     * <p>
     * Examples:
     *  "Abc s/o Bcd"      -> "abc s/o bcd"
     *  "Abc s / o  Bcd"   -> "abc s/o bcd"
     *  "O’Connor"         -> "o'connor"
     *  "Jean–Paul Sartre" -> "jean-paul sartre"
     *  "Ron Aldo"         -> "ron aldo"   // distinct from "ronaldo"
     * <p>
     * Intended for use in isSamePerson or search predicates.
     * Do not use for equals/hashCode or UI rendering.
     *
     * @param s raw name string (non-null)
     * @return canonical representation suitable for tolerant identity checks
     */

    public static String normalizeForIdentity(String s) {

        if (s == null) {
            throw new NullPointerException(MESSAGE_CONSTRAINTS);
        }

        // 1) Unicode normalize and case-fold
        String n = Normalizer.normalize(s, Normalizer.Form.NFKC).toLowerCase();
        n = n.replaceAll("[\\p{Cf}\\u200B\\u200C\\u200D\\uFEFF]+", "");

        // 2) Unify common punctuation variants (keep them, just standardize)
        n = n
                .replace('’', '\'')
                .replace('‘', '\'')
                .replace('‛', '\'')
                .replace('‐', '-')
                .replace('–', '-')
                .replace('—', '-')
                .replace('⁄', '/')
                .replace('∕', '/');

        // 3) Collapse all runs of whitespace to a single space (but keep spaces!)
        n = n.trim().replaceAll("\\s+", " ");

        // 4) Canonicalize s/o, d/o, w/o tokens regardless of spacing or slash variant
        //    e.g. "s / o", "S⁄O", "d   /  o" -> "s/o"
        n = n.replaceAll("\\b([sdw])\\s*[/]\\s*o\\b", "$1/o");

        // 5) Ensure the token is spaced consistently: "... Xs/oY ..." -> "... X s/o Y ..."
        //    (add space before/after s/o when joined to letters)
        n = n.replaceAll("(?<=\\p{L})s/o\\b", " s/o");
        n = n.replaceAll("\\bs/o(?=\\p{L})", "s/o ");

        // 6) Collapse whitespace again in case step 5 introduced double spaces
        n = n.trim().replaceAll("\\s+", " ");

        return n;
    }


    @Override
    public String toString() {
        return fullName;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof Name)) {
            return false;
        }

        Name otherName = (Name) other;
        return fullName.equals(otherName.fullName);
    }

    @Override
    public int hashCode() {
        return fullName.hashCode();
    }

}
