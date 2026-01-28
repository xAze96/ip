package aze.task;

import java.util.List;
import java.util.ArrayList;

/**
 * Represents a list of tasks.
 * Contains operations to add, remove, and retrieve tasks.
 */
public class Tasklist {
    private List<Task> tasks;

    /**
     * Constructs a Tasklist with the provided list of tasks.
     *
     * @param tasks The list of tasks.
     */
    public Tasklist(List<Task> tasks) {
        this.tasks = tasks;
    }

    /**
     * Constructs an empty Tasklist.
     */
    public Tasklist() {
        this.tasks = new ArrayList<>();
    }

    /**
     * Adds a task to the list.
     *
     * @param task The task to add.
     */
    public void add(Task task) {
        tasks.add(task);
    }

    /**
     * Removes a task from the list at the specified index.
     *
     * @param index The index of the task to remove.
     * @return The removed task.
     */
    public Task remove(int index) {
        return tasks.remove(index);
    }

    /**
     * Retrieves a task from the list at the specified index.
     *
     * @param index The index of the task to retrieve.
     * @return The task at the specified index.
     */
    public Task get(int index) {
        return tasks.get(index);
    }
    
    /**
     * Returns all tasks in the list.
     *
     * @return The list of tasks.
     */
    public List<Task> getAllTasks() {
        return tasks;
    }

    /**
     * Returns the number of tasks in the list.
     *
     * @return The size of the list.
     */
    public int size() {
        return tasks.size();
    }
}