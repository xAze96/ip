package aze.parser;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import aze.command.Command;
import aze.exception.AzeException;

public class ParserTest {

    @Test
    public void parseCommand_validCommands_success() throws AzeException {
        assertEquals(Command.list, Parser.parseCommand("list"));

        assertEquals(Command.mark, Parser.parseCommand("mark 1"));

        assertEquals(Command.todo, Parser.parseCommand("todo buy groceries"));

        assertEquals(Command.deadline, Parser.parseCommand("deadline assignment /by 2024-12-25"));

        assertEquals(Command.event, Parser.parseCommand("event party /from 2pm /to 4pm"));

        assertEquals(Command.delete, Parser.parseCommand("delete 5"));

        assertEquals(Command.unmark, Parser.parseCommand("unmark 3"));

        assertEquals(Command.bye, Parser.parseCommand("bye"));
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
