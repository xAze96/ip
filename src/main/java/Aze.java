import java.util.Scanner;
import java.util.List;
import java.util.ArrayList;

public class Aze {
    public static void main(String[] args) {
        System.out.println("    ____________________________________________________________");
        System.out.println("     Hello! I'm Aze");
        System.out.println("     What can I do for you?");
        System.out.println("    ____________________________________________________________\n");
        
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
                    System.out.println("    ____________________________________________________________");
                    for (int i = 0; i < tasks.size(); i++) {
                        System.out.println("     " + (i + 1) + ". " + tasks.get(i));
                    }
                    System.out.println("    ____________________________________________________________\n");
                    break;

                case "mark":
                    taskNum = Integer.parseInt(inputs[1]) - 1;
                    tasks.get(taskNum).markAsDone();
                    System.out.println("    ____________________________________________________________");
                    System.out.println("     Nice! I've marked this task as done:");
                    System.out.println("       " + tasks.get(taskNum));
                    System.out.println("    ____________________________________________________________\n");
                    break;

                case "unmark":
                    taskNum = Integer.parseInt(inputs[1]) - 1;
                    tasks.get(taskNum).markAsNotDone();
                    System.out.println("    ____________________________________________________________");
                    System.out.println("     OK, I've marked this task as not done yet:");
                    System.out.println("       " + tasks.get(taskNum));
                    System.out.println("    ____________________________________________________________\n");
                    break;

                default:
                    tasks.add(new Task(input));
                    System.out.println("    ____________________________________________________________");
                    System.out.println("     added: " + input);
                    System.out.println("    ____________________________________________________________\n");
            }
        }
        scanner.close();

        System.out.println("    ____________________________________________________________");
        System.out.println("     Bye. Hope to see you again soon!");
        System.out.println("    ____________________________________________________________\n");
    }
}
