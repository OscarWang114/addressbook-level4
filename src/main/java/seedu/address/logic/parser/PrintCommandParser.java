package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.PrintCommand;
import seedu.address.logic.parser.exceptions.ParseException;


/**
 * Parses input arguments and identifies the desired filename to return a new PrintCommand
 */
public class PrintCommandParser implements Parser<PrintCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the PrintCommand
     * and returns a PrintCommand Object with the specified file name
     * @throws ParseException if the user input does not conform the expected format
     * which requires at a valid string
     */
    public PrintCommand parse(String args) throws ParseException {
        try {
            String filename = ParserUtil.parseFilePath(args);
            return new PrintCommand(filename);
        } catch (IllegalValueException ive) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, PrintCommand.MESSAGE_USAGE));
        }
    }

}
