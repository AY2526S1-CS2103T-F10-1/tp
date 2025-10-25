package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Person's phone number in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidPhone(String)}
 */
public class Phone {


    /**
     * Phone numbers may:
     *   Optionally start with a '+' (for country code),
     *   Contain digits, spaces, and dashes,
     *   Contain at least 3 digits in total.
     */
    public static final String MESSAGE_CONSTRAINTS =
            "Phone numbers must have at least 3 digits, may start with '+', and may include spaces or dashes.";
    public static final String VALIDATION_REGEX = "^\\+?\\d(?:[ -]?\\d){2,}$";
    public final String value;

    /**
     * Constructs a {@code Phone}.
     *
     * @param phone A valid phone number.
     */
    public Phone(String phone) {
        requireNonNull(phone);
        String s = phone.trim();
        checkArgument(isValidPhone(s), MESSAGE_CONSTRAINTS);
        this.value = canonicalize(s);
    }

    /**
     * Returns true if a given string is a valid phone number.
     */
    public static boolean isValidPhone(String test) {
        requireNonNull(test);
        return test.trim().matches(VALIDATION_REGEX);
    }

    /** Canonicalize: remove spaces/dashes; preserve a single leading '+', if present. */
    private static String canonicalize(String s) {
        boolean hasPlus = s.startsWith("+");
        String digitsOnly = s.replaceAll("[\\s-]", "");
        return hasPlus ? "+" + digitsOnly.substring(1) : digitsOnly;
    }

    /** Normalized representation used for equality: strips spaces and dashes only. */
    public static String normalizeForIdentity(String s) {
        requireNonNull(s);
        return canonicalize(s.trim());
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof Phone)) {
            return false;
        }

        Phone otherPhone = (Phone) other;
        // Compare using normalized forms so "+65 123-456" == "+65123456" and "123 456" == "123-456"
        return normalizeForIdentity(this.value).equals(normalizeForIdentity(otherPhone.value));
    }

    @Override
    public int hashCode() {
        return normalizeForIdentity(this.value).hashCode();
    }

}
