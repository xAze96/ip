import java.util.Scanner;
import java.util.List;
import java.util.ArrayList;
import java.util.stream.IntStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;


public class Aze {

    private static final String FILE_PATH = "./data/aze.txt";

    public static void main(String[] args) {
        
        File file = new File(FILE_PATH);
        List<Task> tasks = loadFile(file);

        display("Hello! I'm Aze\n     What can I do for you?");
        
        Scanner scanner = new Scanner(System.in);
        boolean exit = false;
        while (!exit) {
            try {
                String input = scanner.nextLine();
                String[] inputs = input.split(" ", 2);
                Command command;
                try {
                    command = Command.valueOf(inputs[0]);
                } catch (IllegalArgumentException e) {
                    throw new AzeException("Unknown Command.");
                }
                switch (command) {
                    case bye:
                        exit = true;
                        break;
                        
                    case list:
                        String taskString = String.join("\n     ", IntStream.range(0, tasks.size())
                                .mapToObj(i -> (i + 1) + "." + tasks.get(i))
                                .toList());
                        display("Here are the tasks in your list:\n     " + taskString);
                        break;

                    case mark:
                        try {
                            int taskNum = Integer.parseInt(inputs[1]) - 1;
                            tasks.get(taskNum).markAsDone();
                            display("Nice! I've marked this task as done:\n       " + tasks.get(taskNum));
                        } catch (IndexOutOfBoundsException | NumberFormatException e) {
                            throw new AzeException("Please provide a valid task number to mark.");
                        }
                            break;

                    case unmark:
                        try {
                            int taskNum = Integer.parseInt(inputs[1]) - 1;
                            tasks.get(taskNum).markAsNotDone();
                            display("OK, I've marked this task as not done yet:\n       " + tasks.get(taskNum));
                        } catch (IndexOutOfBoundsException | NumberFormatException e) {
                            throw new AzeException("Please provide a valid task number to unmark.");
                        }
                        break;
                    
                    case todo:
                        if (inputs.length < 2 || inputs[1].isBlank()) {
                            throw new AzeException("Please provide a description for the todo.");
                        }
                        addTask(tasks, new Todo(inputs[1]));
                        break;
                        
                    case deadline:
                        if (inputs.length < 2 || inputs[1].isBlank()) {
                            throw new AzeException("Please provide a description for the deadline.");
                        }
                        String[] deadlineInputs = inputs[1].split(" /by ");
                        if (deadlineInputs.length < 2 || deadlineInputs[0].isBlank()) {
                            throw new AzeException("Please specify a time/date using ' /by '.");
                        }
                        try {
                            addTask(tasks, new Deadline(deadlineInputs[0], deadlineInputs[1]));
                        } catch (Exception e) {
                            throw new AzeException("Please provide the date in the format YYYY-MM-DD.");
                        }
                        break;

                    case event:
                        if (inputs.length < 2 || inputs[1].isBlank()) {
                            throw new AzeException("Please provide a description for the event.");
                        }
                        String[] eventInputs = inputs[1].split(" /from ");
                        if (eventInputs.length < 2 || eventInputs[0].isBlank()) {
                            throw new AzeException("Please specify a start time/date using ' /from ' and ' /to '.");
                        }
                        String[] fromTo = eventInputs[1].split(" /to ");
                        if (fromTo.length < 2 || fromTo[0].isBlank()) {
                            throw new AzeException("Please specify a start time/date using ' /from ' and ' /to '.");
                        }
                        addTask(tasks, new Event(eventInputs[0], fromTo[0], fromTo[1]));
                        break;

                    case delete:
                        try {
                            int taskNum = Integer.parseInt(inputs[1]) - 1;
                            display("Noted. I've removed this task:\n       " + tasks.remove(taskNum) + "\n     Now you have " + tasks.size() + " tasks in the list.");
                        } catch (IndexOutOfBoundsException | NumberFormatException e) {
                            throw new AzeException("Please provide a valid task number to delete.");
                        }
                        break;
                }
                saveFile(file, tasks);
            } catch (AzeException e) {
                display("Error: " + e.getMessage());
            }
        }

        scanner.close();
        display("Bye. Hope to see you again soon!");
    }

    private static void display(String text) {
        System.out.println("    ____________________________________________________________");
        System.out.println("     " + text);
        System.out.println("    ____________________________________________________________\n");
    }

    private static void addTask(List<Task> tasks, Task task) {
        tasks.add(task);
        display("Got it. I've added this task:\n       " + task + "\n     Now you have " + tasks.size() + " tasks in the list.");
    }

    private static List<Task> loadFile(File file) {
        List<Task> tasks = new ArrayList<>();
        try {
            Scanner scanner = new Scanner(file);

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
            return new ArrayList<>();
        }
        return tasks;
    }

    private static void saveFile(File file, List<Task> tasks) throws AzeException {
        try {
            if (!file.getParentFile().exists()) {
                file.getParentFile().mkdirs();
            }
            FileWriter writer = new FileWriter(file);
            for (Task task : tasks) {
                writer.write(task.toFileString() + "\n");
            }
            writer.close();
        } catch (IOException e) {
            throw new AzeException("An error occurred while writing to the file.");
        }
    }
}
