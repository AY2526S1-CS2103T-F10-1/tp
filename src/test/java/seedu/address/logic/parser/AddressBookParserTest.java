package seedu.address.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.Messages.MESSAGE_UNKNOWN_COMMAND;
import static seedu.address.logic.commands.CommandTestUtil.ADDRESS_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.ADDRESS_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.EMAIL_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.EMAIL_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.PHONE_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.PHONE_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_FRIEND;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.commands.ClearCommand;
import seedu.address.logic.commands.DeleteCommand;
import seedu.address.logic.commands.EditCommand;
import seedu.address.logic.commands.ExitCommand;
import seedu.address.logic.commands.FindByTagCommand;
import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.commands.HelpCommand;
import seedu.address.logic.commands.ListCommand;
import seedu.address.logic.commands.PairCommand;
import seedu.address.logic.commands.UnpairCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.NameContainsKeywordsPredicate;
import seedu.address.model.person.NameContainsTagPredicate;
import seedu.address.model.person.Person;
import seedu.address.model.person.Student;
import seedu.address.model.person.Volunteer;
import seedu.address.model.tag.Tag;
import seedu.address.testutil.PersonUtil;

public class AddressBookParserTest {

    private final AddressBookParser parser = new AddressBookParser();

    @Test
    public void parseCommand_addStudent() throws Exception {
        Person person = new Student.StudentBuilder().name("new").build();
        AddCommand command = (AddCommand) parser.parseCommand(PersonUtil.getAddCommandStudent(person));
        assertEquals(new AddCommand(person), command);
    }

    @Test
    public void parseCommand_addVolunteer() throws Exception {
        Person person = new Volunteer.VolunteerBuilder().name("new").build();
        AddCommand command = (AddCommand) parser.parseCommand(PersonUtil.getAddCommandVolunteer(person));
        assertEquals(new AddCommand(person), command);
    }


    @Test
    public void parseCommand_clear() throws Exception {
        assertTrue(parser.parseCommand(ClearCommand.COMMAND_WORD) instanceof ClearCommand);
        assertTrue(parser.parseCommand(ClearCommand.COMMAND_WORD + " 3") instanceof ClearCommand);
    }

    @Test
    public void parseCommand_delete() throws Exception {
        DeleteCommand command = (DeleteCommand) parser.parseCommand(
                DeleteCommand.COMMAND_WORD + " " + INDEX_FIRST_PERSON.getOneBased());
        assertEquals(new DeleteCommand(INDEX_FIRST_PERSON), command);
    }

    @Test
    public void parseCommand_edit() throws Exception {
        Person.PersonBuilder descriptor = new Student.StudentBuilder().name("new");
        EditCommand command = (EditCommand) parser.parseCommand(EditCommand.COMMAND_WORD + " "
                + INDEX_FIRST_PERSON.getOneBased() + " " + PersonUtil.getEditPersonDescriptorDetails(descriptor));
        assertEquals(new EditCommand(INDEX_FIRST_PERSON, descriptor), command);
    }

    @Test
    public void parseCommand_exit() throws Exception {
        assertTrue(parser.parseCommand(ExitCommand.COMMAND_WORD) instanceof ExitCommand);
        assertTrue(parser.parseCommand(ExitCommand.COMMAND_WORD + " 3") instanceof ExitCommand);
    }

    @Test
    public void parseCommand_find() throws Exception {
        List<String> keywords = Arrays.asList("foo", "bar", "baz");
        FindCommand command = (FindCommand) parser.parseCommand(
                FindCommand.COMMAND_WORD + " " + keywords.stream().collect(Collectors.joining(" ")));
        assertEquals(new FindCommand(new NameContainsKeywordsPredicate(keywords)), command);
    }

    @Test
    public void parseCommand_findByTag() throws Exception {
        FindByTagCommand command = (FindByTagCommand) parser.parseCommand(
                FindByTagCommand.COMMAND_WORD + " " + VALID_TAG_FRIEND);
        Tag expectedTag = new Tag(VALID_TAG_FRIEND);
        assertEquals(new FindByTagCommand(new NameContainsTagPredicate(Collections.singletonList(expectedTag))),
                command);
    }

    @Test
    public void parseCommand_help() throws Exception {
        assertTrue(parser.parseCommand(HelpCommand.COMMAND_WORD) instanceof HelpCommand);
        assertTrue(parser.parseCommand(HelpCommand.COMMAND_WORD + " 3") instanceof HelpCommand);
    }

    @Test
    public void parseCommand_list() throws Exception {
        assertTrue(parser.parseCommand(ListCommand.COMMAND_WORD) instanceof ListCommand);
        assertTrue(parser.parseCommand(ListCommand.COMMAND_WORD + " 3") instanceof ListCommand);
    }

    @Test
    public void parseCommand_pair() throws Exception {
        assertTrue(parser.parseCommand(PairCommand.COMMAND_WORD + " 1 2") instanceof PairCommand);
    }

    @Test
    public void parseCommand_unpair() throws Exception {
        assertTrue(parser.parseCommand(UnpairCommand.COMMAND_WORD + " 1 2") instanceof UnpairCommand);
    }

    @Test
    public void parseCommand_unrecognisedInput_throwsParseException() {
        assertThrows(ParseException.class, String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE), ()
            -> parser.parseCommand(""));
    }

    @Test
    public void parseCommand_unknownCommand_throwsParseException() {
        assertThrows(ParseException.class, MESSAGE_UNKNOWN_COMMAND, ()
                -> parser.parseCommand("unknownCommand"));
    }

    @Test
    public void parseCommand_addStudent_success() throws Exception {
        Student expected = (Student) new Student.StudentBuilder()
                .name("Amy Bee")
                .phone("11111111")
                .email("amy@example.com")
                .address("Block 312, Amy Street 1")
                .build();

        AddCommand command = (AddCommand) parser.parseCommand(
                AddCommand.COMMAND_WORD_STUDENT + " "
                        + NAME_DESC_AMY + PHONE_DESC_AMY + EMAIL_DESC_AMY + ADDRESS_DESC_AMY
        );

        assertEquals(new AddCommand(expected), command);
    }

    @Test
    public void parseCommand_addVolunteer_success() throws Exception {
        Volunteer expected = (Volunteer) new Volunteer.VolunteerBuilder()
                .name("Bob Choo")
                .phone("22222222")
                .email("bob@example.com")
                .address("Block 123, Bobby Street 3")
                .tags()
                .build();

        AddCommand command = (AddCommand) parser.parseCommand(
                AddCommand.COMMAND_WORD_VOLUNTEER + " "
                        + NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB + ADDRESS_DESC_BOB
        );

        assertEquals(new AddCommand(expected), command);
    }


}
