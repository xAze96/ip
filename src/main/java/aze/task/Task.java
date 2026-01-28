package aze.task;

/**
 * Represents a generic task.
 * Contains a description and a completion status.
 */
public class Task {
    protected String description;
    protected boolean isDone;
    
    /**
     * Constructs a new Task.
     *
     * @param description The description of the task.
     */
    public Task(String description) {
        this.description = description;
        this.isDone = false;
    }

    /**
     * Returns the status icon of the task.
     *
     * @return "X" if done, " " otherwise.
     */
    public String getStatusIcon() {
        return (isDone ? "X" : " ");
    }

    @Override
    public String toString() {
        return "[" + getStatusIcon() + "] " + description;
    }

    /**
     * Marks the task as done.
     */
    public void markAsDone() {
        this.isDone = true;
    }

    /**
     * Marks the task as not done.
     */
    public void markAsNotDone() {
        this.isDone = false;
    }

    /**
     * Returns the string representation of the task for file storage.
     *
     * @return The formatted string for file storage.
     */
    public String toFileString() {
        return (isDone ? "1" : "0") + " | " + description;
    }
}