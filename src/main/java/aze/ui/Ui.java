package aze.ui;

import java.util.Scanner;

public class Ui {
    private final Scanner scanner;
    
    public Ui() {
        this.scanner = new Scanner(System.in);
    }

    public String readCommand() {
        return scanner.nextLine();
    }

    public void showLoadingError() {
        display("Save file not found.");
    }

    public void display(String text) {
        System.out.println("    ____________________________________________________________");
        System.out.println("     " + text);
        System.out.println("    ____________________________________________________________\n");
    }

    public void close() {
        scanner.close();
    }
}
