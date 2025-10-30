package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;

public class HelpCommandTest {
    private Model model = new ModelManager();
    private Model expectedModel = new ModelManager();

    @Test
    public void execute_help_success() {
        CommandResult result = new HelpCommand().execute(model);

        assertEquals("Opened help window with command summary.", result.getFeedbackToUser(),
                "CLI should show the short confirmation message.");

        assertTrue(result.isShowHelp(), "Help window flag should be true.");

        assertFalse(result.isExit(), "Exit flag should be false.");

        assertEquals(model, expectedModel, "Model state should remain unchanged after help command.");
    }

    @Test
    public void execute_help_setsContentCorrectly() {
        HelpCommand command = new HelpCommand();
        CommandResult result = command.execute(model);

        assertTrue(result.getHelpContent().contains("addstu"), "Help content should include 'addstu'.");
        assertTrue(result.getHelpContent().contains("UserGuide.html"),
                "Help content should contain the link to the User Guide.");
    }
    @Test
    public void execute_helpWithValidCommand_returnsFilteredHelp() {
        HelpCommand command = new HelpCommand("add");
        CommandResult result = command.execute(model);

        assertTrue(result.getFeedbackToUser().contains("Opened help for command: add"),
                "Feedback should indicate specific help command.");
        assertTrue(result.getHelpContent().contains("addstu"),
                "Filtered help should include matching 'addstu' command.");
        assertFalse(result.getHelpContent().contains("delete"),
                "Filtered help should not contain unrelated commands like 'delete'.");
    }

    @Test
    public void execute_helpWithInvalidCommand_returnsUnknownMessage() {
        HelpCommand command = new HelpCommand("nonexistent");
        CommandResult result = command.execute(model);

        assertTrue(result.getHelpContent().contains("Unknown command"),
                "Should show an 'Unknown command' message for invalid inputs.");
        assertTrue(result.getHelpContent().contains("help"),
                "Should guide user to use 'help' command for full list.");
    }

    @Test
    public void execute_helpWithBlankCommand_behavesLikeNormalHelp() {
        HelpCommand command = new HelpCommand("   ");
        CommandResult result = command.execute(model);

        assertEquals("Opened help window with command summary.", result.getFeedbackToUser());
        assertTrue(result.getHelpContent().contains("Command Summary"),
                "Should display full command summary for blank input.");
    }

    @Test
    public void execute_helpWithMultipleMatches_returnsMultipleSections() {
        HelpCommand command = new HelpCommand("add");
        CommandResult result = command.execute(model);

        assertTrue(result.getHelpContent().contains("addstu"), "Should include 'addstu' section.");
        assertTrue(result.getHelpContent().contains("addvol"), "Should include 'addvol' section.");
        assertTrue(result.getHelpContent().contains("<table>"), "Should still be wrapped in table tags.");
    }

    @Test
    public void execute_helpWithSpecialCharacters_sanitizedProperly() {
        HelpCommand command = new HelpCommand("a$d!d@vol");
        CommandResult result = command.execute(model);

        assertTrue(result.getHelpContent().contains("addvol"),
                "Special characters should be stripped and valid command recognized.");
    }

}
