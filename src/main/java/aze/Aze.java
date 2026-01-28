package aze;
import aze.command.Command;
import aze.exception.AzeException;
import aze.parser.Parser;
import aze.storage.Storage;
import aze.task.Deadline;
import aze.task.Event;
import aze.task.Task;
import aze.task.Tasklist;
import aze.task.Todo;
import aze.ui.Ui;

import java.util.Scanner;
import java.util.stream.IntStream;

public class Aze {

    private Storage storage;
    private Tasklist tasks;
    private Ui ui;

    public Aze(String filePath) {
        ui = new Ui();
        storage = new Storage(filePath);
        try {
            tasks = new Tasklist(storage.load());
        } catch (AzeException e) {
            ui.showLoadingError();
            tasks = new Tasklist();
        }
    }
    
    public void run() {
        ui.display("Hello! I'm Aze\n     What can I do for you?");
        Scanner scanner = new Scanner(System.in);
        boolean isExit = false;
        while (!isExit) {
            try {
                String input = scanner.nextLine();
                String[] inputs = input.split(" ", 2);
                Command command = Parser.parseCommand(input);
                switch (command) {
                    case bye:
                        isExit = true;
                        ui.display("Bye. Hope to see you again soon!");
                        break;
                        
                    case list:
                        String taskString = String.join("\n     ", IntStream.range(0, tasks.size())
                                .mapToObj(i -> (i + 1) + "." + tasks.get(i))
                                .toList());
                        ui.display("Here are the tasks in your list:\n     " + taskString);
                        break;

                    case mark:
                        try {
                            int taskNum = Integer.parseInt(inputs[1]) - 1;
                            tasks.get(taskNum).markAsDone();
                            ui.display("Nice! I've marked this task as done:\n       " + tasks.get(taskNum));
                        } catch (IndexOutOfBoundsException | NumberFormatException e) {
                            throw new AzeException("Please provide a valid task number to mark.");
                        }
                            break;

                    case unmark:
                        try {
                            int taskNum = Integer.parseInt(inputs[1]) - 1;
                            tasks.get(taskNum).markAsNotDone();
                            ui.display("OK, I've marked this task as not done yet:\n       " + tasks.get(taskNum));
                        } catch (IndexOutOfBoundsException | NumberFormatException e) {
                            throw new AzeException("Please provide a valid task number to unmark.");
                        }
                        break;
                    
                    case todo:
                        if (inputs.length < 2 || inputs[1].isBlank()) {
                            throw new AzeException("Please provide a description for the todo.");
                        }
                        addTask(tasks, new Todo(inputs[1]), ui);
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
                            addTask(tasks, new Deadline(deadlineInputs[0], deadlineInputs[1]), ui);
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
                        addTask(tasks, new Event(eventInputs[0], fromTo[0], fromTo[1]), ui);
                        break;

                    case delete:
                        try {
                            int taskNum = Integer.parseInt(inputs[1]) - 1;
                            ui.display("Noted. I've removed this task:\n       " + tasks.remove(taskNum) + "\n     Now you have " + tasks.size() + " tasks in the list.");
                        } catch (IndexOutOfBoundsException | NumberFormatException e) {
                            throw new AzeException("Please provide a valid task number to delete.");
                        }
                        break;
                    
                    case find:
                        if (inputs.length < 2 || inputs[1].isBlank()) {
                            throw new AzeException("Please provide a keyword to search.");
                        }
                        String keyword = inputs[1];
                        Tasklist matchingTasks = new Tasklist(tasks.getAllTasks().stream()
                                .filter(task -> task.matchesDescription(keyword))
                                .toList());
                        String matchingString = String.join("\n     ", IntStream.range(0, matchingTasks.size())
                                .mapToObj(i -> (i + 1) + "." + matchingTasks.get(i))
                                .toList());
                        ui.display("Here are the matching tasks in your list:\n     " + matchingString);
                        break;
                }
                storage.save(tasks.getAllTasks());
            } catch (AzeException e) {
                ui.display("Error: " + e.getMessage());
            }
        }

        scanner.close();
    }

    public static void main(String[] args) {
        new Aze("data/tasks.txt").run();
    }

    private static void addTask(Tasklist tasks, Task task, Ui ui) {
        tasks.add(task);
        ui.display("Got it. I've added this task:\n       " + task + "\n     Now you have " + tasks.size() + " tasks in the list.");
    }
}