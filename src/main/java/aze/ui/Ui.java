package aze.ui;

import java.util.Scanner;

/**
 * Handles the user interface interactions.
 * Reads user input and displays messages.
 */
public class Ui {
    private final Scanner scanner;
    
    /**
     * Constructs a new Ui instance.
     * Initializes the scanner for user input.
     */
    public Ui() {
        this.scanner = new Scanner(System.in);
    }

    /**
     * Reads a command from the user.
     *
     * @return The command string entered by the user.
     */
    public String readCommand() {
        return scanner.nextLine();
    }

    /**
     * Displays a loading error message.
     */
    public void showLoadingError() {
        display("Save file not found.");
    }

    /**
     * Displays a message to the user formatted with lines.
     *
     * @param text The message to display.
     */
    public void display(String text) {
        System.out.println("    ____________________________________________________________");
        System.out.println("     " + text);
        System.out.println("    ____________________________________________________________\n");
    }

    /**
     * Closes the scanner.
     */
    public void close() {
        scanner.close();
    }
}
