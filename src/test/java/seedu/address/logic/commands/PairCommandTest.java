package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.util.ArrayList;
import java.util.Collections;
import java.util.OptionalInt;
import java.util.stream.IntStream;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javafx.collections.ObservableList;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Person;
import seedu.address.model.person.Student;
import seedu.address.model.person.Volunteer;


public class PairCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private ObservableList<Person> personList = model.getFilteredPersonList();

    @BeforeEach
    void setUp() {
        // Fresh model EVERY test run
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    }

    @Test
    public void execute_validIndexes_success() throws Exception {
        OptionalInt volunteerIndexInt = IntStream.range(0, personList.size())
                .filter(index -> personList.get(index).getType().equals("Volunteer")).findFirst();
        OptionalInt studentIndexInt = IntStream.range(0, personList.size())
                .filter(index -> personList.get(index).getType().equals("Student")).findFirst();
        assert volunteerIndexInt.isPresent(); // there should be at least 1 volunteer in getTypicalAddressBook()
        assert studentIndexInt.isPresent(); // there should be at least 1 student in getTypicalAddressBook()
        if (volunteerIndexInt.isPresent() && studentIndexInt.isPresent()) {
            Index volunteerIndex = Index.fromZeroBased(volunteerIndexInt.getAsInt());
            Index studentIndex = Index.fromZeroBased(studentIndexInt.getAsInt());
            PairCommand pairCommand = new PairCommand(volunteerIndex, Collections.singletonList(studentIndex));

            Person tutor = (new Volunteer.VolunteerBuilder(model.getFilteredPersonList()
                    .get(volunteerIndex.getZeroBased()))).build();
            Person tutee = (new Student.StudentBuilder(model.getFilteredPersonList()
                    .get(studentIndex.getZeroBased()))).build();
            assert !model.isPaired(tutor, tutee);
            if (!model.isPaired(tutee, tutor)) {
                String expectedMessage = String.format(PairCommand.MESSAGE_EDIT_PERSON_SUCCESS,
                        tutor.getName().toString(), "{" + tutee.getName().toString() + "}");

                CommandResult commandResult = pairCommand.execute(model);
                assertEquals(expectedMessage, commandResult.getFeedbackToUser());
            }
        }

    }
    @Test
    public void execute_invalidIndex_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        ArrayList<Index> indexes = new ArrayList<>();
        indexes.add(INDEX_FIRST_PERSON);
        PairCommand pairCommand = new PairCommand(outOfBoundIndex, indexes);

        assertThrows(CommandException.class, Messages.MESSAGE_INVALID_FIRST_PERSON_DISPLAYED_INDEX, () ->
                pairCommand.execute(model));
    }

    @Test
    public void execute_invalidPartnerIndex_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        ArrayList<Index> indexes = new ArrayList<>();
        indexes.add(outOfBoundIndex);
        PairCommand pairCommand = new PairCommand(INDEX_FIRST_PERSON, indexes);

        assertThrows(CommandException.class, () -> pairCommand.execute(model));
    }

    @Test
    public void equals() {
        PairCommand pairFirstCommand = new PairCommand(INDEX_FIRST_PERSON,
                Collections.singletonList(INDEX_SECOND_PERSON));
        PairCommand pairSecondCommand = new PairCommand(INDEX_SECOND_PERSON,
                Collections.singletonList(INDEX_FIRST_PERSON));

        // same object -> returns true
        assertTrue(pairFirstCommand.equals(pairFirstCommand));

        // same values -> returns true
        PairCommand pairFirstCommandCopy = new PairCommand(INDEX_FIRST_PERSON,
                Collections.singletonList(INDEX_SECOND_PERSON));
        assertTrue(pairFirstCommand.equals(pairFirstCommandCopy));

        // different types -> returns false
        assertFalse(pairFirstCommand.equals(1));

        // null -> returns false
        assertFalse(pairFirstCommand.equals(null));

        // different person -> returns false
        assertFalse(pairFirstCommand.equals(pairSecondCommand));
    }
}
