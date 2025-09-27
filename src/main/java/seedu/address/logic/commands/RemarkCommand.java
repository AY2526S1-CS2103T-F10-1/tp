package seedu.address.logic.commands;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Objects;

import seedu.address.commons.core.index.Index;
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
    public static final String MESSAGE_ARGUMENTS = "Index: %1$d, Remark: %2$s";

    private final Index index;
    private final String remark;

    /**
     * Constructs a {@code RemarkCommand}.
     *
     * @param index Index of the person in the filtered person list to edit the remark of.
     * @param remark The remark to update to.
     */
    public RemarkCommand(Index index, String remark) {
        requireAllNonNull(index, remark);

        this.index = index;
        this.remark = remark;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        throw new CommandException(String.format(MESSAGE_ARGUMENTS, index.getOneBased(), remark));
    }

    @Override
    public final boolean equals(Object o) {
        if (!(o instanceof RemarkCommand that)) {
            return false;
        }

        return index.equals(that.index) && remark.equals(that.remark);
    }

    @Override
    public int hashCode() {
        return Objects.hash(index, remark);
    }
}
