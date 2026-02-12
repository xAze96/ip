package aze;

import java.util.stream.IntStream;

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

/**
 * Main class for the Aze chatbot application.
 * Initializes the application and handles the main loop.
 */
public class Aze {

    private Storage storage;
    private Tasklist tasks;
    private Ui ui;
    private boolean isExit = false;

    /**
     * Constructs a new Aze instance.
     * Initializes the user interface, storage, and task list.
     *
     * @param filePath The path to the file where tasks are stored.
     */
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

    /**
     * Runs the main loop of the application.
     * Handles user input and executes commands.
     */
    public void run() {
        ui.display("Hello! I'm Aze\n     What can I do for you?");
        while (!isExit) {
            String input = ui.readCommand();
            String response = getResponse(input);
            ui.display(response);
        }
        ui.close();
    }

    /**
     * Gets the response for a given user input.
     * @param input The user input string.
     * @return The response string.
     */
    public String getResponse(String input) {
        try {
            String response;
            String[] inputs = input.split(" ", 2);
            Command command = Parser.parseCommand(input);
            switch (command) {
            case BYE:
                isExit = true;
                response = "Bye. Hope to see you again soon!";
                break;
            case LIST:
                response = handleList();
                break;
            case MARK:
                response = handleMark(inputs);
                break;
            case UNMARK:
                response = handleUnmark(inputs);
                break;
            case TODO:
                response = handleTodo(inputs);
                break;
            case DEADLINE:
                response = handleDeadline(inputs);
                break;
            case EVENT:
                response = handleEvent(inputs);
                break;
            case DELETE:
                response = handleDelete(inputs);
                break;
            case FIND:
                response = handleFind(inputs);
                break;
            default:
                throw new AzeException("Unknown command.");
            }
            storage.save(tasks.getAllTasks());
            return response;
        } catch (AzeException e) {
            return "Error: " + e.getMessage();
        }
    }

    private String handleList() {
        String taskString = String.join("\n     ", IntStream.range(0, tasks.size())
                .mapToObj(i -> (i + 1) + "." + tasks.get(i))
                .toList());
        return "Here are the tasks in your list:\n     " + taskString;
    }

    private String handleMark(String[] inputs) throws AzeException {
        try {
            int taskNum = Integer.parseInt(inputs[1]) - 1;
            tasks.get(taskNum).markAsDone();
            return "Nice! I've marked this task as done:\n       " + tasks.get(taskNum);
        } catch (IndexOutOfBoundsException | NumberFormatException e) {
            throw new AzeException("Please provide a valid task number to mark.");
        }
    }

    private String handleUnmark(String[] inputs) throws AzeException {
        try {
            int taskNum = Integer.parseInt(inputs[1]) - 1;
            tasks.get(taskNum).markAsNotDone();
            return "OK, I've marked this task as not done yet:\n       " + tasks.get(taskNum);
        } catch (IndexOutOfBoundsException | NumberFormatException e) {
            throw new AzeException("Please provide a valid task number to unmark.");
        }
    }

    private String handleTodo(String[] inputs) throws AzeException {
        if (inputs.length < 2 || inputs[1].isBlank()) {
            throw new AzeException("Please provide a description for the todo.");
        }
        return addTask(tasks, new Todo(inputs[1]));
    }

    private String handleDeadline(String[] inputs) throws AzeException {
        if (inputs.length < 2 || inputs[1].isBlank()) {
            throw new AzeException("Please provide a description for the deadline.");
        }
        String[] deadlineInputs = inputs[1].split(" /by ");
        if (deadlineInputs.length < 2 || deadlineInputs[0].isBlank()) {
            throw new AzeException("Please specify a time/date using ' /by '.");
        }
        try {
            return addTask(tasks, new Deadline(deadlineInputs[0], deadlineInputs[1]));
        } catch (Exception e) {
            throw new AzeException("Please provide the date in the format YYYY-MM-DD.");
        }
    }

    private String handleEvent(String[] inputs) throws AzeException {
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
        return addTask(tasks, new Event(eventInputs[0], fromTo[0], fromTo[1]));
    }

    private String handleDelete(String[] inputs) throws AzeException {
        try {
            int taskNum = Integer.parseInt(inputs[1]) - 1;
            return "Noted. I've removed this task:\n       " + tasks.remove(taskNum)
                    + "\n     Now you have " + tasks.size() + " tasks in the list.";
        } catch (IndexOutOfBoundsException | NumberFormatException e) {
            throw new AzeException("Please provide a valid task number to delete.");
        }
    }

    private String handleFind(String[] inputs) throws AzeException {
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
        return "Here are the matching tasks in your list:\n     " + matchingString;
    }
    
    private String addTask(Tasklist tasks, Task task) {
        tasks.add(task);
        return "Got it. I've added this task:\n       " + task
                + "\n     Now you have " + tasks.size() + " tasks in the list.";
    }

    /**
     * The entry point of the application.
     *
     * @param args Command line arguments.
     */
    public static void main(String[] args) {
        new Aze("data/tasks.txt").run();
    }

    /**
     * Checks if the exit command has been issued.
     * @return true if the exit command has been issued, false otherwise.
     */
    public boolean isExit() {
        return isExit;
    }
}
