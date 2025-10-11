package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_ENGLISH;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_FRIEND;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;

import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.model.tag.Tag;
import seedu.address.testutil.TestPersonBuilder;

/**
 * Contains integration test (integration with Tag class) for NameContainsTagPredicate.
 */
public class NameContainsTagPredicateTest {

    @Test
    public void equals() {
        Tag firstTag = new Tag(VALID_TAG_FRIEND);
        Tag secondTag = new Tag(VALID_TAG_HUSBAND);

        NameContainsTagPredicate firstPredicate = new NameContainsTagPredicate(firstTag);
        NameContainsTagPredicate secondPredicate = new NameContainsTagPredicate(secondTag);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        NameContainsTagPredicate firstPredicateCopy = new NameContainsTagPredicate(firstTag);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));
        NameContainsKeywordsPredicate diffPredictType =
                new NameContainsKeywordsPredicate(List.of(VALID_TAG_FRIEND));
        assertFalse(firstPredicate.equals(diffPredictType));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different predicate (of same type) -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_nameContainsTag_returnsTrue() {
        Tag tag = new Tag(VALID_TAG_FRIEND);
        NameContainsTagPredicate predicate = new NameContainsTagPredicate(tag);

        // Person has only one tag
        Person personWithOneTag = new TestPersonBuilder().withTags(tag.tagName).build();
        assertTrue(predicate.test(personWithOneTag));

        // Person has multiple tags
        Person personWithTwoTags = new TestPersonBuilder().withTags(tag.tagName, VALID_TAG_HUSBAND).build();
        assertTrue(predicate.test(personWithTwoTags));
        Person otherPersonWithTwoTags = new TestPersonBuilder().withTags(VALID_TAG_HUSBAND, tag.tagName).build();
        assertTrue(predicate.test(otherPersonWithTwoTags));
        Person personWithThreeTags = new TestPersonBuilder()
                .withTags(VALID_TAG_HUSBAND, tag.tagName, VALID_TAG_ENGLISH).build();
        assertTrue(predicate.test(personWithThreeTags));

        // Mixed-case tags
        Tag tagUppercase = new Tag(VALID_TAG_FRIEND.toUpperCase());
        NameContainsTagPredicate predicateWithUpperCaseTag = new NameContainsTagPredicate(tagUppercase);
        assertTrue(predicateWithUpperCaseTag.test(personWithOneTag));
        Person personWithLowercaseTag = new TestPersonBuilder().withTags(VALID_TAG_FRIEND.toLowerCase()).build();
        assertTrue(predicateWithUpperCaseTag.test(personWithLowercaseTag));

        Tag tagLowercase = new Tag(VALID_TAG_FRIEND.toLowerCase());
        NameContainsTagPredicate predicateWithLowerCaseTag = new NameContainsTagPredicate(tagLowercase);
        Person personWithUppercaseTag = new TestPersonBuilder().withTags(VALID_TAG_FRIEND.toLowerCase()).build();
        assertTrue(predicateWithLowerCaseTag.test(personWithUppercaseTag));
    }

    public void test_nameDoesNotContainTag_returnsFalse() {
        Tag tag = new Tag(VALID_TAG_FRIEND);
        NameContainsTagPredicate predicate = new NameContainsTagPredicate(tag);

        // Zero tags
        assertFalse(predicate.test(new TestPersonBuilder().withTags().build()));
        // While the default Person built with new PersonBuilder().build() already returns a person
        // without any tags, the additional method call to withTags() (with no parameters) is to make it
        // explicit that the Person to be built has no tags. Additionally, it means that this test method
        // implementation does not need to be updated even if PersonBuilder::new()'s implementation is
        // updated to have a different default set of tags.

        // Non-matching tag
        assertFalse(predicate.test(new TestPersonBuilder().withTags(VALID_TAG_HUSBAND).build()));

        // Tag matches name, email, and address, but does not match tags
        assertFalse(predicate.test(new TestPersonBuilder().withName(VALID_TAG_FRIEND)
                .withEmail(VALID_TAG_FRIEND + "@email.com").withAddress(VALID_TAG_FRIEND + "Street")
                .withTags(VALID_TAG_HUSBAND).build()));
    }

    @Test
    public void toStringMethod() {
        Tag tag = new Tag(VALID_TAG_FRIEND);
        NameContainsTagPredicate predicate = new NameContainsTagPredicate(tag);

        String expected = NameContainsTagPredicate.class.getCanonicalName() + "{tag=" + tag + "}";
        assertEquals(expected, predicate.toString());
    }
}
