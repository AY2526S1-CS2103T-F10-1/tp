package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_REMARK_AMY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_REMARK;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.RemarkCommand;
import seedu.address.model.person.Remark;

public class RemarkCommandParserTest {

    private final RemarkCommandParser parser = new RemarkCommandParser();

    @Test
    public void parse_indexSpecified_success() {
        // have remark
        Index targetIndex = INDEX_FIRST_PERSON;
        String userInputWithRemark = targetIndex.getOneBased() + " " + PREFIX_REMARK + VALID_REMARK_AMY;
        RemarkCommand expectedCommandWithRemark = new RemarkCommand(INDEX_FIRST_PERSON,
                new Remark(VALID_REMARK_AMY));
        assertParseSuccess(parser, userInputWithRemark, expectedCommandWithRemark);

        // no remark
        String userInputWithoutRemark = INDEX_FIRST_PERSON.getOneBased() + " " + PREFIX_REMARK;
        RemarkCommand expectedCommandWithoutRemark = new RemarkCommand(INDEX_FIRST_PERSON, new Remark(""));
        assertParseSuccess(parser, userInputWithoutRemark, expectedCommandWithoutRemark);
    }

    @Test
    public void parse_missingCompulsoryField_failure() {
        String expectedMesssage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, RemarkCommand.MESSAGE_USAGE);

        // no parameter
        assertParseFailure(parser, RemarkCommand.COMMAND_WORD, expectedMesssage);

        // no index
        assertParseFailure(parser, RemarkCommand.COMMAND_WORD + " " + VALID_REMARK_AMY,
                expectedMesssage);
    }
}
