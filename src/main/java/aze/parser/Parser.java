package aze.parser;

import aze.command.Command;
import aze.exception.AzeException;

/**
 * Parses user input into commands.
 */
public class Parser {
    /**
     * Parses the full user input into a Command.
     *
     * @param input The user input string.
     * @return The corresponding Command.
     * @throws AzeException If the command is unknown.
     */
    public static Command parseCommand(String input) throws AzeException {
        String[] inputs = input.split(" ", 2);
        try {
            return Command.valueOf(inputs[0].toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new AzeException("Unknown command.");
        }
    }
}
