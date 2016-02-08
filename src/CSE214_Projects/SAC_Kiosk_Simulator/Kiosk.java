package CSE214_Projects.SAC_Kiosk_Simulator;

/**
 * Sean Clark 109653264 Homework #5 Tuesday: R03 Recitation TA: Sun Lin Grading
 * TA: Ke Ma
 *
 * @author sclark
 */
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.util.Scanner;

public class Kiosk {

    private String fileName = "";
    private String input = "";
    private int nodeCount;
    private String parentName = "";
    private int choice;
    private String runningOrder = "";

    private Tree t;
    private boolean quit;

    private TreeNode nodePtr = null;

    private BufferedReader reader = null;
    private FileInputStream fis = null;
    private InputStreamReader inStream = null;

    private Scanner stdin = null;

    public static void main(String[] args) {
        new Kiosk(args);
    }

    public Kiosk(String[] args) {
        
        System.out.println(" --- Welcome to the SAC Ordering Kiosk Simulator! ---");
        System.out.println("This project uses the tree data structure to simulate ordering food at the SAC cafeteria.");
        System.out.println("I have provided a sample menu in the 'data' folder.");
        System.out.println("Try typing 'L' and then enter 'data/sampleMenu.txt' to load it.\n");
        
        stdin = new Scanner(System.in);
        t = new Tree();
        quit = false;

        while (quit == false) {
            displayMenu();
            switch (getMenuSelection()) {
                case 'L':
                    try {
                        loadFromFile();
                    } catch (Exception e) {
                        System.out.println("Tree could not be built from that file.\n");
                    }
                    break;
                case 'P':
                    t.printMenu();
                    break;
                case 'S':
                    try {
                        beginSession();
                    } catch (Exception e) {
                        stdin.nextLine();
                        System.out.println("Invalid input! Returning to menu.\n");
                    }
                    break;

                case 'Q':
                    quit = true;
            }
        }

        System.out.print("Kiosk shutting down...");
    }

    /**
     * Method that gets a selection from the user
     *
     * @return the char the user selected
     */
    private char getMenuSelection() {

        char selection;
        System.out.print("Select an operation: ");
        selection = stdin.nextLine().toUpperCase().charAt(0);
        System.out.println();

        if (isValidSelection(selection)) {
            return selection;
        }

        System.out.println(selection + " is not a valid selection."
                + " Please try again.");
        System.out.println();
        return getMenuSelection();
    }

    /**
     * Method that checks if the input is valid
     *
     * @param selection the selection the user gave
     * @return true if the selection is valid, false otherwise
     */
    private boolean isValidSelection(char selection) {
        return selection == 'L' || selection == 'P' || selection == 'S'
                || selection == 'Q';
    }

    /**
     * Method that displays the menu choices
     */
    public void displayMenu() {
  
        System.out.println("L) Load tree");
        System.out.println("P) Print menu");
        System.out.println("S) Start service");
        System.out.println("Q) Quit\n");
    }

    /**
     * Method that begins a session for the user.
     *
     * @throws Exception if the input is invalid
     */
    public void beginSession() throws Exception {
        if (t.getRoot() == null) {
            System.out.println("Tree not initalized!\n");
            return;
        }

        runningOrder = "";
        nodePtr = t.getRoot();

        System.out.println("Help session starting...\n");

        while (!nodePtr.isLeaf()) {
            System.out.println(nodePtr.getMessage());

            if (nodePtr.getLeft() != null) {
                System.out.println("1 " + nodePtr.getLeft().getSelection());
            }
            if (nodePtr.getMiddle() != null) {
                System.out.println("2 " + nodePtr.getMiddle().getSelection());
            }
            if (nodePtr.getRight() != null) {
                System.out.println("3 " + nodePtr.getRight().getSelection());
            }

            System.out.println("0 Exit Session");

            System.out.print("Choice: ");
            choice = stdin.nextInt();

            System.out.println();

            if (choice == 1) {
                nodePtr = nodePtr.getLeft();

                if (nodePtr == null) {
                    throw new Exception();
                }

                if (!runningOrder.equals("")) {
                    runningOrder = runningOrder + nodePtr.getSelection() + ", ";
                } else {
                    runningOrder = nodePtr.getSelection() + ": ";
                }
            }
            if (choice == 2) {
                nodePtr = nodePtr.getMiddle();

                if (nodePtr == null) {
                    throw new Exception();
                }

                if (!runningOrder.equals("")) {
                    runningOrder = runningOrder + nodePtr.getSelection() + ", ";
                } else {
                    runningOrder = nodePtr.getSelection() + ": ";
                }
            }
            if (choice == 3) {
                nodePtr = nodePtr.getRight();

                if (nodePtr == null) {
                    throw new Exception();
                }

                if (!runningOrder.equals("")) {
                    runningOrder = runningOrder + nodePtr.getSelection() + ", ";
                } else {
                    runningOrder = nodePtr.getSelection() + ": ";
                }
            }
            if (choice == 0) {
                System.out.println("Session exited.\n");
                stdin.nextLine();
                return;
            }
            if (choice > 3) {
                throw new Exception();
            }
        }

        System.out.print("The order at " + runningOrder + "has been sent to the kitchen.");
        System.out.println(" The total amount would be " + nodePtr.getMessage() + ".\n");

        stdin.nextLine();
    }

    /**
     * Method that loads the menu based on a file
     */
    public void loadFromFile() throws Exception {
        System.out.print("Enter the location and name of the file: ");
        fileName = stdin.nextLine();

        try {
            fis = new FileInputStream(fileName);
            inStream = new InputStreamReader(fis);
            reader = new BufferedReader(inStream);
        } catch (FileNotFoundException e) {
            System.out.println("\nFile not found. Returning to menu.\n");
            return;
        }

        try {
            buildTree();
        } catch (Exception e) {
            System.out.println("\nFile does not appear to be formatted correctly.\n");
        }
    }

    /**
     * method that builds a tree based on the file given
     *
     * @throws Exception if the file is incorrectly formatted.
     */
    public void buildTree() throws Exception {
        t = new Tree();
        t.setRoot(new TreeNode(reader.readLine(), reader.readLine(), reader.readLine()));

        input = reader.readLine();
        while (input != null) {
            parentName = input.substring(0, input.length() - 2);
            nodeCount = Integer.parseInt(input.substring(input.length() - 1));

            for (int i = 0; i < nodeCount; i++) {
                if (t.addNode(reader.readLine(), reader.readLine(), reader.readLine(), parentName) == false) {
                    throw new Exception();
                }
            }

            input = reader.readLine();
        }

        System.out.println("\nMenu has been updated!\n");
    }
}
