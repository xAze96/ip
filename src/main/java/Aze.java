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

            switch (inputs[0]) {
                case "list":
                    String taskString = String.join("\n     ", IntStream.range(0, tasks.size())
                            .mapToObj(i -> (i + 1) + "." + tasks.get(i))
                            .toList());
                    display("Here are the tasks in your list:\n     " + taskString);
                    break;

                case "mark":
                    taskNum = Integer.parseInt(inputs[1]) - 1;
                    tasks.get(taskNum).markAsDone();
                    display("Nice! I've marked this task as done:\n       " + tasks.get(taskNum));
                    break;

                case "unmark":
                    taskNum = Integer.parseInt(inputs[1]) - 1;
                    tasks.get(taskNum).markAsNotDone();
                    display("OK, I've marked this task as not done yet:\n       " + tasks.get(taskNum));
                    break;
                
                case "todo":
                    addTask(tasks, new Todo(inputs[1]));
                    break;
                    
                case "deadline":
                    String[] deadlineInputs = inputs[1].split(" /by ");
                    addTask(tasks, new Deadline(deadlineInputs[0], deadlineInputs[1]));
                    break;

                case "event":
                    String[] eventInputs = inputs[1].split(" /from ");
                    String[] fromTo = eventInputs[1].split(" /to ");
                    addTask(tasks, new Event(eventInputs[0], fromTo[0], fromTo[1]));
                    break;
                
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
