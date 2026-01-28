package aze.parser;

import aze.exception.AzeException;
import aze.command.Command;

public class Parser {
    public static Command parseCommand(String input) throws AzeException {
        String[] inputs = input.split(" ", 2);
        try {
            return Command.valueOf(inputs[0]);
        } catch (IllegalArgumentException e) {
            throw new AzeException("Unknown command.");
        }
    }
}
