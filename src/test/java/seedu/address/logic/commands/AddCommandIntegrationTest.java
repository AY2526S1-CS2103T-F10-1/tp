package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.logic.Messages;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Person;
import seedu.address.model.person.Student;

/**
 * Contains integration tests (interaction with the Model) for {@code AddCommand}.
 */
public class AddCommandIntegrationTest {

    private Model model;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    }

    @Test
    public void execute_newPerson_success() {
        // Build a Student (or Volunteer) for the new “typed-only” add flow
        Person validEntry = asStudent(new Student.StudentBuilder().name("new").build());

        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.addPerson(validEntry);

        String expectedMessage = String.format("New %s added: %s",
                noun(validEntry), Messages.format(validEntry));

        assertCommandSuccess(new AddCommand(validEntry), model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_duplicatePerson_throwsCommandException() {
        // Assumes TypicalPersons now returns only Student/Volunteer instances
        Person entryInList = model.getAddressBook().getPersonList().get(0);
        assertCommandFailure(new AddCommand(entryInList), model, AddCommand.MESSAGE_DUPLICATE_PERSON);
    }

    // ---- helpers ----

    /** Convert a Person fixture to a Student with identical core fields (adjust ctor if yours differs). */
    private static Student asStudent(Person p) {
        return Student.toBuilder(p).build();
    }

    /** Returns "student" if Student, otherwise "volunteer". */
    private static String noun(Person p) {
        return (p instanceof Student) ? "student" : "volunteer";
    }
}
