import java.util.Scanner;

public class Aze {
    public static void main(String[] args) {
        System.out.println("    ____________________________________________________________");
        System.out.println("     Hello! I'm Aze");
        System.out.println("     What can I do for you?");
        System.out.println("    ____________________________________________________________\n");
        
        Scanner scanner = new Scanner(System.in);
        while (true) {
            String input = scanner.nextLine();
            if (input.equals("bye")) {
                break;
            }
            System.out.println("    ____________________________________________________________");
            System.out.println("     " + input);
            System.out.println("    ____________________________________________________________\n");
        }
        scanner.close();

        System.out.println("    ____________________________________________________________");
        System.out.println("     Bye. Hope to see you again soon!");
        System.out.println("    ____________________________________________________________");
    }
}
