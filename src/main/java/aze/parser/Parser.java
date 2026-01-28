package aze.parser;

import aze.command.Command;
import aze.exception.AzeException;

public class Parser {
    public static Command parseCommand(String input) throws AzeException {
        String[] inputs = input.split(" ", 2);
        try {
            return Command.valueOf(inputs[0].toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new AzeException("Unknown command.");
        }
    }
}
