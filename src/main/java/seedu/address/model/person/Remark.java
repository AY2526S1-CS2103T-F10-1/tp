package seedu.address.model.person;

import static java.util.Objects.requireNonNull;

/**
 * Represents a remark about a Person.
 * Guarantees: immutable.
 */
public class Remark {

    public final String value;

    public Remark(String remark) {
        requireNonNull(remark);
        value = remark;
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public final boolean equals(Object o) {
        if (!(o instanceof Remark remark)) {
            return false;
        }

        return value.equals(remark.value);
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
