package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Person;
import seedu.address.model.person.Student;
import seedu.address.model.person.Volunteer;

/**
 * Adds a person to the address book.
 */
public class AddCommand extends Command {

    public static final String COMMAND_WORD_STUDENT = "addstu";
    public static final String COMMAND_WORD_VOLUNTEER = "addvol";

    public static final String MESSAGE_USAGE_STUDENT = COMMAND_WORD_STUDENT
            + ": Adds a student to the address book (Name must be provided). "
            + "Parameters: "
            + PREFIX_NAME + "NAME "
            + "[" + PREFIX_PHONE + "PHONE] "
            + "[" + PREFIX_EMAIL + "EMAIL] "
            + "[" + PREFIX_ADDRESS + "ADDRESS] "
            + "[" + PREFIX_TAG + "TAG]...\n"
            + "Example: " + COMMAND_WORD_STUDENT + " "
            + "n/Alex Yeoh "
            + "p/87438807 "
            + "e/alexyeoh@example.com "
            + "a/Blk 30 Geylang Street 29, #06-40 "
            + "t/math";

    public static final String MESSAGE_USAGE_VOLUNTEER = COMMAND_WORD_VOLUNTEER
            + ": Adds a volunteer to the address book (Name must be provided). "
            + "Parameters: "
            + PREFIX_NAME + "NAME "
            + "[" + PREFIX_PHONE + "PHONE] "
            + "[" + PREFIX_EMAIL + "EMAIL] "
            + "[" + PREFIX_ADDRESS + "ADDRESS] "
            + "[" + PREFIX_TAG + "TAG]...\n"
            + "Example: " + COMMAND_WORD_VOLUNTEER + " "
            + "n/Bernice Yu "
            + "p/99272758 "
            + "e/berniceyu@example.com "
            + "a/Blk 30 Lorong 3 Serangoon Gardens, #07-18 "
            + "t/physics";

    public static final String MESSAGE_DUPLICATE_PERSON = "This person already exists in the address book";

    private final Person toAdd;

    /**
     * Creates an AddCommand to add the specified {@code Person}
     */
    public AddCommand(Person person) {
        requireNonNull(person);
        if (!(person instanceof Student) && !(person instanceof Volunteer)) {
            throw new IllegalArgumentException(
                    "AddCommand only accepts Student or Volunteer instances");
        }
        toAdd = person;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (model.hasPerson(toAdd)) {
            throw new CommandException(MESSAGE_DUPLICATE_PERSON);
        }

        model.addPerson(toAdd);
        return new CommandResult(String.format("New %s added: %s", noun(toAdd), Messages.format(toAdd)));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof AddCommand)) {
            return false;
        }

        AddCommand otherAddCommand = (AddCommand) other;
        return toAdd.equals(otherAddCommand.toAdd);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("toAdd", toAdd)
                .toString();
    }

    private static String noun(Person p) {
        return (p instanceof Student) ? "student" : "volunteer";
    }

}
