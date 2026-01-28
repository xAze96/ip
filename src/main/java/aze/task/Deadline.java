package aze.task;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * Represents a deadline task.
 * Contains a due date.
 */
public class Deadline extends Task {

    protected LocalDate by;

    /**
     * Constructs a new Deadline.
     *
     * @param description The description of the deadline.
     * @param by The due date of the deadline.
     */
    public Deadline(String description, String by) {
        super(description);
        this.by = LocalDate.parse(by);
    }

    @Override
    public String toString() {
        return "[D]" + super.toString() + " (by: " + by.format(DateTimeFormatter.ofPattern("MMM d yyyy")) + ")";
    }

    @Override
    public String toFileString() {
        return "D | " + super.toFileString() + " | " + by;
    }
}
