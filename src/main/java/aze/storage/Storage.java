package aze.storage;

import aze.task.Task;
import aze.task.Todo;
import aze.task.Deadline;
import aze.task.Event;
import aze.exception.AzeException;

import java.util.Scanner;
import java.util.List;
import java.util.ArrayList;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Handles loading and saving tasks to a file.
 */
public class Storage {
    private File file;

    /**
     * Constructs a new Storage instance.
     *
     * @param filePath The path to the file where tasks are stored.
     */
    public Storage(String filePath) {
        file = new File(filePath);
    }

    /**
     * Loads tasks from the file.
     *
     * @return The list of tasks loaded from the file.
     * @throws AzeException If the file is not found.
     */
    public List<Task> load() throws AzeException {
        List<Task> tasks = new ArrayList<>();
        try {
            Scanner scanner = new Scanner(this.file);

            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] parts = line.split(" \\| ");
                String taskType = parts[0];
                boolean isDone = parts[1].equals("1");
                String description = parts[2];

                Task task;
                switch (taskType) {
                    case "T":
                        task = new Todo(description);
                        break;
                    case "D":
                        String by = parts[3];
                        task = new Deadline(description, by);
                        break;
                    case "E":
                        String from = parts[3];
                        String to = parts[4];
                        task = new Event(description, from, to);
                        break;
                    default:
                        continue;
                }
                if (isDone) {
                    task.markAsDone();
                }
                tasks.add(task);
            }
            scanner.close();
        } catch (FileNotFoundException e) {
            throw new AzeException("Save file not found.");
        }
        return tasks;
    }

    /**
     * Saves the list of tasks to the file.
     *
     * @param tasks The list of tasks to save.
     * @throws AzeException If there is an error writing to the file.
     */
    public void save(List<Task> tasks) throws AzeException {
        try {
            if (!this.file.getParentFile().exists()) {
                this.file.getParentFile().mkdirs();
            }
            FileWriter writer = new FileWriter(this.file);
            for (Task task : tasks) {
                writer.write(task.toFileString() + "\n");
            }
            writer.close();
        } catch (IOException e) {
            throw new AzeException("Unable to write to file.");
        }
    }
}
