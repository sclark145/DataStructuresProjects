package CSE214_Projects.PerformanceScheduler;

/**
 * Sean Clark 109653264 Homework #2 Tuesday: R03 Recitation TA: Sun Lin Grading
 * TA: Ke Ma
 *
 * @author sclark
 */
import CSE214_Projects.PerformanceScheduler.PerformanceList.EmptyListException;
import java.util.Scanner;

/**
 * Class: PerformanceScheduler
 *
 * Contains main method that performs operations on a PerformanceList.
 *
 */
public class PerformanceScheduler {

    public static final String ADD_PERFORMANCE = "A";
    public static final String MOVE_CURSOR_BACKWARDS = "B";
    public static final String DISPLAY_CURRENT_PERFORMANCE = "C";
    public static final String DISPLAY_ALL = "D";
    public static final String MOVE_CURSOR_FORWARD = "F";
    public static final String INSERT_AFTER_CURRENT = "I";
    public static final String JUMP_TO_POSITION = "J";
    public static final String REMOVE_CURRENT_PERFORMANCE = "R";
    public static final String EXIT = "Q";

    private String input = "";
    private String performanceIn = "";
    private String performerIn = "";
    private int posIn;
    private int participantsIn;
    private int durationIn;
    private int runningTime = 0;

    private Scanner stdin = null;
    private PerformanceList list = null;

    public static void main(String[] args) {
        new PerformanceScheduler(args);
    }

    public PerformanceScheduler(String[] args) {
        stdin = new Scanner(System.in);
        list = new PerformanceList();

        System.out.println(" ----- Performance Scheduler ----- ");
        System.out.println("This project utilizes the linked list data structure"
                + " to simulate a dynamically changing list of performances that\n"
                + "go on one after another, adjusting the start and end times of"
                + " performances as they are added and removed from the list.\n");
        displayMenu();

        while (!(input.toUpperCase().equals("Q"))) {
            System.out.print("Choose an operation: ");
            input = stdin.nextLine();

            System.out.println();
            switch (input.toUpperCase()) {
                case ADD_PERFORMANCE:
                    addPerformanceToEnd();
                    break;

                case INSERT_AFTER_CURRENT:
                    insertAfterCurrentCursorPos();
                    break;

                case DISPLAY_ALL:
                    System.out.println(list.toString());
                    break;

                case MOVE_CURSOR_FORWARD:
                    moveCursorForward();
                    break;

                case MOVE_CURSOR_BACKWARDS:
                    moveCursorBackwards();
                    break;

                case REMOVE_CURRENT_PERFORMANCE:
                    removeCurrentPerformance();
                    break;

                case DISPLAY_CURRENT_PERFORMANCE:
                    displayCurrentPerformance();
                    break;

                case JUMP_TO_POSITION:
                    jumpToPosition();
                    break;

                default:
                    if (!(input.toUpperCase().equals("Q"))) {
                        displayMenu(); // ---- show the user the menu again if they enter an invalid input.
                        System.out.println("Invalid input, refer to the main menu above. Try again.\n");
                    }
                    break;
            }
        }
        System.out.print("Quitting...\nProgram terminated.");
    }

    /**
     *
     * ---- Displays the Main Menu
     *
     *
     */
    private void displayMenu() {
        System.out.println("-------------MAIN MENU-------------");
        System.out.println("A) Add performance to end" + "\n"
                + "B) Move cursor backward" + "\n"
                + "C) Display current performance" + "\n"
                + "D) Display all" + "\n"
                + "F) Move cursor forward" + "\n"
                + "I) Insert after current performance" + "\n"
                + "J) Jump to position" + "\n"
                + "R) Remove current performance" + "\n"
                + "Q) Exit" + "\n"
                + "-----------------------------------" + "\n");
    }

    /**
     *
     * ---- adds a performance to the end of the list
     *
     *
     */
    private void addPerformanceToEnd() {
        System.out.print("Enter name of performance: ");
        performanceIn = stdin.nextLine();

        System.out.print("Enter name of lead performer: ");
        performerIn = stdin.nextLine();

        System.out.print("Enter the total participants: ");
        participantsIn = stdin.nextInt();

        System.out.print("Enter the duration of the performance: ");
        durationIn = stdin.nextInt();

        try {
            list.addToEnd(new PerformanceNode(performanceIn,
                    performerIn,
                    participantsIn,
                    durationIn,
                    runningTime));
            runningTime += durationIn;

            System.out.println();
            stdin.nextLine();
            System.out.println("New performance " + performanceIn + " is added to the end of the list.\n");
        } catch (Exception e) {
            System.out.println("\n" + e.getMessage() + "\n");
        }
    }

    /**
     *
     * ---- inserts a performance after current cursor position
     *
     *
     *
     */
    private void insertAfterCurrentCursorPos() {
        System.out.print("Enter name of performance: ");
        performanceIn = stdin.nextLine();

        System.out.print("Enter name of lead performer: ");
        performerIn = stdin.nextLine();

        System.out.print("Enter the total participants: ");
        participantsIn = stdin.nextInt();

        System.out.print("Enter the duration of the performance: ");
        durationIn = stdin.nextInt();

        try {
            list.addAfterCurrent(new PerformanceNode(performanceIn,
                    performerIn,
                    participantsIn,
                    durationIn,
                    runningTime));
            runningTime += durationIn;
            System.out.println();
            stdin.nextLine();

            System.out.println("New performance " + performanceIn + " is added after current performance.\n");
        } catch (Exception e) {
            System.out.println("\n" + e.getMessage() + "\n");
        }
    }

    /**
     *
     * ---- move the current cursor position forward by one
     *
     *
     */
    private void moveCursorForward() {
        if (!list.moveCursorForward()) {
            System.out.println("Cursor already at end of list, try again.\n");
        } else {
            System.out.println("Cursor has been move forward.\n");
        }
    }

    /**
     *
     * ---- move the current cursor position back by one
     *
     *
     */
    private void moveCursorBackwards() {
        if (!list.moveCursorBackward()) {
            System.out.println("Cursor already at beginning of list, try again.\n");
        } else {
            System.out.println("Cursor has been move backward.\n");
        }
    }

    /**
     *
     * ---- remove the node at the current cursor position.
     *
     *
     */
    public void removeCurrentPerformance() {
        PerformanceNode tempPtr = list.getCursor();

        if (!list.removeCurrentNode()) {
            System.out.println("List is empty, you cannot remove. Try another operation.\n");
        } else {
            System.out.println("Performance " + tempPtr.getName() + " has been removed.\n");
            runningTime -= tempPtr.getDuration();
        }
    }

    /**
     *
     * ----- display the node at the current cursor position
     *
     *
     */
    public void displayCurrentPerformance() {
        try {
            list.displayCurrentPerformance();
        } catch (EmptyListException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     *
     * ---- move the cursor to the requested position
     *
     *
     */
    public void jumpToPosition() {
        System.out.print("Enter the position: ");
        posIn = stdin.nextInt();

        if (!list.jumpToPosition(posIn)) {
            System.out.println("\nInput not in range, try again. Performance Count = " + list.getPerformanceCount());
        } else {
            System.out.println("\nCursor has been moved to position " + posIn + " of the list.");
        }

        System.out.println();
        stdin.nextLine();
    }
}
