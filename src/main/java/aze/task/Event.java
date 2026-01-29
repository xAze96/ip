package aze.task;

/**
 * Represents an event task.
 * Contains a start and end time.
 */
public class Event extends Task {

    protected String from;
    protected String to;

    /**
     * Constructs a new Event.
     *
     * @param description The description of the event.
     * @param from The start time.
     * @param to The end time.
     */
    public Event(String description, String from, String to) {
        super(description);
        this.from = from;
        this.to = to;
    }

    @Override
    public String toString() {
        return "[E]" + super.toString() + " (from: " + from + " to: " + to + ")";
    }

    @Override
    public String toFileString() {
        return "E | " + super.toFileString() + " | " + from + " | " + to;
    }
}
