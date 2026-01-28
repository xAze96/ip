package aze.storage;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import aze.exception.AzeException;
import aze.task.Deadline;
import aze.task.Event;
import aze.task.Task;
import aze.task.Todo;

public class Storage {
    private File file;

    public Storage(String filePath) {
        file = new File(filePath);
    }

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
