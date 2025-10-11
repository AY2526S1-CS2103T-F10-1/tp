package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.BOB;
import static seedu.address.testutil.TypicalPersons.getFreshTypicalAddressBook;

import java.util.Collections;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Person;
import seedu.address.testutil.TestPersonBuilder;


class UnpairCommandTest {
    private Model model;

    @BeforeEach
    void setUp() {
        // Fresh model EVERY test run
        model = new ModelManager(getFreshTypicalAddressBook(), new UserPrefs());
    }

    @Test
    void execute_validIndexes_success() throws Exception {
        Index tutorIndex = INDEX_FIRST_PERSON;
        Index tuteeIndex = INDEX_SECOND_PERSON;

        // Grab fresh copies (don’t mutate static fixtures directly)
        Person tutor = (new TestPersonBuilder(model.getFilteredPersonList().get(tutorIndex.getZeroBased()))).build();
        Person tutee = (new TestPersonBuilder(model.getFilteredPersonList().get(tuteeIndex.getZeroBased()))).build();

        // Ensure model reflects the pairing before unpairing
        tutor.addPerson(tutee);
        model.setPerson(model.getFilteredPersonList().get(tutorIndex.getZeroBased()), tutor);

        UnpairCommand unpair = new UnpairCommand(tutorIndex, Collections.singletonList(tuteeIndex));
        CommandResult result = unpair.execute(model);

        // Prefer using the same formatter the command uses (if any)
        String formatted = "{" + tutee.getName().toString() + "}";
        String expectedMessage = String.format(UnpairCommand.MESSAGE_EDIT_PERSON_SUCCESS,
                tutor.getName().toString(), formatted);

        assertEquals(expectedMessage, result.getFeedbackToUser());
        // also assert state actually changed
        Person updatedTutor = model.getFilteredPersonList().get(tutorIndex.getZeroBased());
        assertFalse(updatedTutor.getPersonList().contains(tutee));
    }

    @Test
    void getPersonList_modifyList_throwsUnsupportedOperationException() {
        Person alice = new TestPersonBuilder(ALICE).build(); // fresh copy
        assertThrows(UnsupportedOperationException.class, () -> alice.getPersonList().clear());
    }

    @Test
    void getPersonList_noPairings_returnsEmptyList() {
        Person alice = new TestPersonBuilder(ALICE).build(); // fresh copy
        assertTrue(alice.getPersonList().isEmpty());
    }

    @Test
    void getPersonList_withPairings_returnsCorrectList() {
        Person alice = new TestPersonBuilder(ALICE).build(); // fresh copy
        Person bob = new TestPersonBuilder(BOB).build(); // fresh copy
        assertDoesNotThrow(() -> alice.addPerson(bob));
        assertEquals(1, alice.getPersonList().size());
        assertTrue(alice.getPersonList().contains(bob));
    }
}
