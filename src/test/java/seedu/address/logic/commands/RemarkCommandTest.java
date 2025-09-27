package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_REMARK_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_REMARK_BOB;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.RemarkCommand.MESSAGE_ARGUMENTS;
import static seedu.address.logic.commands.RemarkCommand.MESSAGE_NOT_IMPLEMENTED_YET;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.jupiter.api.Test;

import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Remark;

public class RemarkCommandTest {

    private final Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute() throws ParseException {
        final Remark remark = new Remark("Hello world");

        assertCommandFailure(new RemarkCommand(INDEX_FIRST_PERSON, remark), model,
                String.format(MESSAGE_ARGUMENTS, INDEX_FIRST_PERSON.getOneBased(), remark));
    }

    @Test
    public void equals() {
        final Remark nonEmptyRemark = new Remark(VALID_REMARK_AMY);

        final RemarkCommand standardCommand = new RemarkCommand(INDEX_FIRST_PERSON, nonEmptyRemark);

        // same values -> returns true
        RemarkCommand commandWithSameValues = new RemarkCommand(INDEX_FIRST_PERSON, nonEmptyRemark);
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearCommand()));

        // different index -> return false
        assertFalse(standardCommand.equals(new RemarkCommand(INDEX_SECOND_PERSON, nonEmptyRemark)));

        // different remark -> return false
        final Remark differentRemark = new Remark(VALID_REMARK_BOB);
        assertFalse(standardCommand.equals(new RemarkCommand(INDEX_FIRST_PERSON, differentRemark)));
    }
}
