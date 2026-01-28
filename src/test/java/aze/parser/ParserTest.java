package aze.parser;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import aze.command.Command;
import aze.exception.AzeException;

public class ParserTest {

    @Test
    public void parseCommand_validCommands_success() throws AzeException {
        assertEquals(Command.LIST, Parser.parseCommand("list"));

        assertEquals(Command.MARK, Parser.parseCommand("mark 1"));

        assertEquals(Command.TODO, Parser.parseCommand("todo buy groceries"));

        assertEquals(Command.DEADLINE, Parser.parseCommand("deadline assignment /by 2024-12-25"));

        assertEquals(Command.EVENT, Parser.parseCommand("event party /from 2pm /to 4pm"));

        assertEquals(Command.DELETE, Parser.parseCommand("delete 5"));

        assertEquals(Command.UNMARK, Parser.parseCommand("unmark 3"));

        assertEquals(Command.FIND, Parser.parseCommand("find homework"));

        assertEquals(Command.BYE, Parser.parseCommand("bye"));
    }

    @Test
    public void parseCommand_invalidCommand_exceptionThrown() {
        try {
            Parser.parseCommand("invalid");
            fail();
        } catch (AzeException e) {
            assertEquals("Unknown command.", e.getMessage());
        }
    }
}
