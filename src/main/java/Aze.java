import java.util.Scanner;
import java.util.List;
import java.util.ArrayList;
import java.util.stream.IntStream;

public class Aze {
    public static void main(String[] args) {
        display("Hello! I'm Aze\n     What can I do for you?");
        
        List<Task> tasks = new ArrayList<>();
        int taskNum;
        Scanner scanner = new Scanner(System.in);
        while (true) {
            String input = scanner.nextLine();
            if (input.equals("bye")) {
                break;
            }
            String[] inputs = input.split(" ", 2);
            try {

                switch (inputs[0]) {
                    case "list":
                        String taskString = String.join("\n     ", IntStream.range(0, tasks.size())
                                .mapToObj(i -> (i + 1) + "." + tasks.get(i))
                                .toList());
                        display("Here are the tasks in your list:\n     " + taskString);
                        break;

                    case "mark":
                        try {
                            taskNum = Integer.parseInt(inputs[1]) - 1;
                            tasks.get(taskNum).markAsDone();
                            display("Nice! I've marked this task as done:\n       " + tasks.get(taskNum));
                        } catch (IndexOutOfBoundsException | NumberFormatException e) {
                            throw new AzeException("Please provide a valid task number to mark.");
                        }
                            break;

                    case "unmark":
                        try {
                            taskNum = Integer.parseInt(inputs[1]) - 1;
                            tasks.get(taskNum).markAsNotDone();
                            display("OK, I've marked this task as not done yet:\n       " + tasks.get(taskNum));
                        } catch (IndexOutOfBoundsException | NumberFormatException e) {
                            throw new AzeException("Please provide a valid task number to unmark.");
                        }
                        break;
                    
                    case "todo":
                        if (inputs.length < 2 || inputs[1].isBlank()) {
                            throw new AzeException("Please provide a description for the todo.");
                        }
                        addTask(tasks, new Todo(inputs[1]));
                        break;
                        
                    case "deadline":
                        if (inputs.length < 2 || inputs[1].isBlank()) {
                            throw new AzeException("Please provide a description for the deadline.");
                        }
                        String[] deadlineInputs = inputs[1].split(" /by ");
                        if (deadlineInputs.length < 2 || deadlineInputs[0].isBlank()) {
                            throw new AzeException("Please specify a time/date using ' /by '.");
                        }
                        addTask(tasks, new Deadline(deadlineInputs[0], deadlineInputs[1]));
                        break;

                    case "event":
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

                    case "delete":
                        try {
                            taskNum = Integer.parseInt(inputs[1]) - 1;
                            display("Noted. I've removed this task:\n       " + tasks.remove(taskNum) + "\n     Now you have " + tasks.size() + " tasks in the list.");
                        } catch (IndexOutOfBoundsException | NumberFormatException e) {
                            throw new AzeException("Please provide a valid task number to delete.");
                        }
                        break;
                    default:
                        display("Invalid command.");
                }
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
}
