import java.util.Scanner;

public class Aze {
    public static void main(String[] args) {
        System.out.println("    ____________________________________________________________");
        System.out.println("     Hello! I'm Aze");
        System.out.println("     What can I do for you?");
        System.out.println("    ____________________________________________________________\n");
        
        String[] tasks = new String[100];
        int taskNum = 0;
        Scanner scanner = new Scanner(System.in);
        while (true) {
            String input = scanner.nextLine();
            if (input.equals("bye")) {
                break;
            }
            switch (input) {
                case "list":
                    System.out.println("    ____________________________________________________________");
                    for (int i = 0; i < taskNum; i++) {
                        System.out.println("     " + (i + 1) + ". " + tasks[i]);
                    }
                    System.out.println("    ____________________________________________________________\n");
                    break;
                default:
                    tasks[taskNum++] = input;
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
