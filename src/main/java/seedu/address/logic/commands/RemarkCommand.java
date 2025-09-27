package seedu.address.logic.commands;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;

/**
 * Adds a remark to a {@code Person} or edits it if required.
 */
public class RemarkCommand extends Command {

    public static final String COMMAND_WORD = "remark";
    public static final String MESSAGE_NOT_IMPLEMENTED_YET =
            "The remark command has not been implemented yet.";
    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Edits the remark of the person identified by the index numbered used in the "
            + "last person listing. Existing remark will be overwritten by the input.\n"
            + "Parameters: INDEX (must be a positive integer) r/ [REMARK]\n"
            + "Example: " + COMMAND_WORD + " 1 r/ My best friend!";

    @Override
    public CommandResult execute(Model model) throws CommandException {
        throw new CommandException(MESSAGE_NOT_IMPLEMENTED_YET);
    }
}
