package seedu.address.logic.commands;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import java.util.List;
import java.util.Objects;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Person;
import seedu.address.model.person.Remark;

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
    public static final String MESSAGE_DELETE_REMARK_SUCCESS = "Removed remark from Person: %1$s";
    public static final String MESSAGE_ADD_REMARK_SUCCESS = "Added remark to Person: %1$s";

    private final Index index;
    private final Remark remark;

    /**
     * Constructs a {@code RemarkCommand}.
     *
     * @param index Index of the person in the filtered person list to edit the remark of.
     * @param remark The remark to update to.
     */
    public RemarkCommand(Index index, Remark remark) {
        requireAllNonNull(index, remark);

        this.index = index;
        this.remark = remark;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        List<Person> lastShownList = model.getFilteredPersonList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        Person personToEdit = lastShownList.get(index.getZeroBased());
        Person editedPerson = new Person(
                personToEdit.getName(), personToEdit.getPhone(), personToEdit.getEmail(),
                personToEdit.getAddress(), remark, personToEdit.getTags());

        model.setPerson(personToEdit, editedPerson);
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);

        return new CommandResult(generateSuccessMessage(editedPerson));
    }

    /**
     * Generates a command execution success method based on whether the remark is added to
     * or removed from {@code personToEdit}.
     */
    private String generateSuccessMessage(Person personToEdit) {
        String message;
        if (remark.value.isEmpty()) {
            message = MESSAGE_DELETE_REMARK_SUCCESS;
        } else {
            message = MESSAGE_ADD_REMARK_SUCCESS;
        }
        return String.format(message, Messages.format(personToEdit));
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
