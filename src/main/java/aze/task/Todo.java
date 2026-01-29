package aze.task;

/**
 * Represents a todo task.
 */
public class Todo extends Task {

    /**
     * Constructs a new Todo.
     *
     * @param description The description of the todo.
     */
    public Todo(String description) {
        super(description);
    }

    @Override
    public String toString() {
        return "[T]" + super.toString();
    }

    @Override
    public String toFileString() {
        return "T | " + super.toFileString();
    }
}
