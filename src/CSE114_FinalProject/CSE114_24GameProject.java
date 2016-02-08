// Sean Clark (109653264)
package CSE114_FinalProject;

import java.util.ArrayList;
import java.util.Scanner;

public class CSE114_24GameProject {

    public static final int ADDITION = 0;
    public static final int SUBTRACTION = 1;
    public static final int MULTIPLICATION = 2;
    public static final int DIVISION = 3;

    //counter keeping track of the number of solutions found.
    private static int count = 0;

    public static void main(String[] args) {
        

        System.out.println(" ----- Welcome To The 24 Game ----- \n");
        System.out.println("Simply input four integers, and the program will "
                + "see if there is a way to manipulate them to equal 24!\n");

        
        // GET INPUT FROM THE USER
        ArrayList<Double> numbers = getInputFromUser();
        
        // COMPUTE THE SOLUTIONS
        computeSolutions(numbers);
        
        System.out.println("Program terminated.");
    }
    
    public static ArrayList<Double> getInputFromUser()
    {
        @SuppressWarnings("resource")
        Scanner stdin = new Scanner(System.in);
        
        ArrayList<Double> nums = new ArrayList<Double>();

        while (true) {
            System.out.print("Enter 4 Numbers: ");

            try {
                for (int i = 0; i < 4; i++) {
                    nums.add(stdin.nextDouble());
                }
            } catch (Exception e) {
                System.out.println("Invalid Program Input Arguments!!");
                stdin = new Scanner(System.in);
                nums.clear();
                continue;
            }

            break;
        }
        
        return nums;
    }
    
    public static void computeSolutions(ArrayList<Double> numbers)
    {
        System.out.println("\nChecking for possible solutions...");
        ArrayList<ArrayList<Double>> numCombos = permutations(numbers);

        // GROUPING 1: (a opr b) opr c opr d
        testGrouping1(numCombos);

        // GROUPING 2: a opr (b opr c) opr d
        testGrouping2(numCombos);

        // GROUPING 3: a opr b opr (c opr d)
        testGrouping3(numCombos);

        // GROUPING 4: ((a opr b) opr c) opr d
        testGrouping4(numCombos);

        // GROUPING 5: (a opr (b opr c)) opr d
        testGrouping5(numCombos);

        // GROUPING: a opr ((b opr c) opr d)
        testGrouping6(numCombos);

        // GROUPING: a opr (b opr (c opr d))
        testGrouping7(numCombos);

        if (count == 0) {
            System.out.println("There appear to be no solutions!");
        }
    }

    //returns an ArrayList containing all permutations of the given inputs.
    public static ArrayList<ArrayList<Double>> permutations(ArrayList<Double> a) {
        ArrayList<ArrayList<Double>> perms = new ArrayList<ArrayList<Double>>();

        if (a.isEmpty()) {
            ArrayList<Double> onePerm = new ArrayList<Double>();
            perms.add(onePerm);
            return perms;
        }

        for (Double oneElem : a) {
            @SuppressWarnings("unchecked")
            ArrayList<Double> b = (ArrayList<Double>) (a.clone());
            b.remove(oneElem);

            ArrayList<ArrayList<Double>> perms2 = permutations(b);

            for (ArrayList<Double> onePerm : perms2) {
                onePerm.add(oneElem);
                perms.add(onePerm);
            }
        }

        return perms;
    }

    //converts my operators into strings for easier printing
    public static String toOp(int n) {
        switch (n) {
            case 0:
                return "+";
            case 1:
                return "-";
            case 2:
                return "*";
            case 3:
                return "/";
            default:
                return "ERROR";
        }
    }

    public static void testGrouping1(ArrayList<ArrayList<Double>> numCombos) {
        int numOfNumCombos = numCombos.size();

        //temporary variables for holding the result of calculations
        double r = 0, r2 = 0;

        // using nested for loops, every combination of numbers is run through each
        // combination of operands. order of operations is kept in check with the if statements.
        for (int i = 0; i < numOfNumCombos; i++) {
            for (int x = 0; x < 4; x++) {
                for (int y = 0; y < 4; y++) {
                    for (int z = 0; z < 4; z++) {
                        switch (x) {
                            case ADDITION:
                                r = (numCombos.get(i).get(0)) + (numCombos.get(i).get(1));
                                break;
                            case SUBTRACTION:
                                r = (numCombos.get(i).get(0)) - (numCombos.get(i).get(1));
                                break;
                            case MULTIPLICATION:
                                r = (numCombos.get(i).get(0)) * (numCombos.get(i).get(1));
                                break;
                            case DIVISION:
                                r = (numCombos.get(i).get(0)) / (numCombos.get(i).get(1));
                                break;
                        }
                        // if y takes precedent
                        if ((z == ADDITION || z == SUBTRACTION)
                                && (y == MULTIPLICATION || y == DIVISION)) {
                            if (y == MULTIPLICATION) {
                                r = r * (double) (numCombos.get(i).get(2));
                            } else if (y == DIVISION) {
                                r = r / (double) (numCombos.get(i).get(2));
                            }
                            if (z == ADDITION) {
                                r = r + (double) (numCombos.get(i).get(3));
                            } else if (z == SUBTRACTION) {
                                r = r - (double) (numCombos.get(i).get(3));
                            }
                            // if z takes precedent
                        } else if ((y == ADDITION || y == SUBTRACTION)
                                && (z == MULTIPLICATION || z == DIVISION)) {
                            if (z == MULTIPLICATION) {
                                r2 = (double) (numCombos.get(i).get(2)) * (double) (numCombos.get(i).get(3));
                            } else if (z == DIVISION) {
                                r2 = (double) (numCombos.get(i).get(2)) / (double) (numCombos.get(i).get(3));
                            }
                            if (y == ADDITION) {
                                r = r + r2;
                            } else if (y == SUBTRACTION) {
                                r = r - r2;
                            }
                        } else // Doesn't matter what order
                        {
                            if (y == ADDITION) {
                                r = r + (numCombos.get(i).get(2));
                            } else if (y == SUBTRACTION) {
                                r = r - (numCombos.get(i).get(2));
                            } else if (y == MULTIPLICATION) {
                                r = r * (numCombos.get(i).get(2));
                            } else if (y == DIVISION) {
                                r = r / (numCombos.get(i).get(2));
                            }
                            if (z == ADDITION) {
                                r = r + (numCombos.get(i).get(3));
                            } else if (z == SUBTRACTION) {
                                r = r - (numCombos.get(i).get(3));
                            } else if (z == MULTIPLICATION) {
                                r = r * (numCombos.get(i).get(3));
                            } else if (z == DIVISION) {
                                r = r / (numCombos.get(i).get(3));
                            }
                        }

                        if ((23.9 < r && r < 24.1) && count < 1) {
                            System.out.println("("
                                    + (numCombos.get(i).get(0))
                                    + toOp(x)
                                    + (numCombos.get(i).get(1)) + ")"
                                    + toOp(y)
                                    + (numCombos.get(i).get(2))
                                    + toOp(z)
                                    + (numCombos.get(i).get(3))
                                    + " = 24.0");
                            count++;
                        }
                    }
                }
            }
        }
    }

    public static void testGrouping2(ArrayList<ArrayList<Double>> numCombos) {
        int numOfNumCombos = numCombos.size();

        //temporary variables for holding the result of calculations
        double r = 0, r2 = 0;

        // using nested for loops, every combination of numbers is run through each
        // combination of operands. order of operations is kept in check with the if statements.
        for (int i = 0; i < numOfNumCombos; i++) {
            for (int x = 0; x < 4; x++) {
                for (int y = 0; y < 4; y++) {
                    for (int z = 0; z < 4; z++) {
                        switch (y) {
                            case ADDITION:
                                r = (double) (numCombos.get(i).get(1)) + (double) (numCombos.get(i).get(2));
                                break;

                            case SUBTRACTION:
                                r = (double) (numCombos.get(i).get(1)) - (double) (numCombos.get(i).get(2));
                                break;

                            case MULTIPLICATION:
                                r = (double) (numCombos.get(i).get(1)) * (double) (numCombos.get(i).get(2));
                                break;

                            case DIVISION:
                                r = (double) (numCombos.get(i).get(1)) / (double) (numCombos.get(i).get(2));
                                break;
                        }

                        // if z takes precedent
                        if ((x == ADDITION || x == SUBTRACTION)
                                && (z == MULTIPLICATION || z == DIVISION)) {
                            if (z == MULTIPLICATION) {
                                r = r * (double) (numCombos.get(i).get(3));
                            } else if (z == DIVISION) {
                                r = r / (double) (numCombos.get(i).get(3));
                            }
                            if (x == ADDITION) {
                                r = r + (double) (numCombos.get(i).get(0));
                            } else if (x == SUBTRACTION) {
                                r = (double) (numCombos.get(i).get(0)) - r;
                            }

                            // if x takes precedent
                        } else if ((x == MULTIPLICATION || x == DIVISION)
                                && (z == ADDITION || z == SUBTRACTION)) {
                            if (x == MULTIPLICATION) {
                                r = r * (double) (numCombos.get(i).get(0));
                            } else if (x == DIVISION) {
                                r = (double) (numCombos.get(i).get(0)) / r;
                            }
                            if (z == ADDITION) {
                                r = r + (double) (numCombos.get(i).get(3));
                            } else if (z == SUBTRACTION) {
                                r = r - (double) (numCombos.get(i).get(3));
                            }
                        } else // Doesn't matter what order
                        {
                            if (x == ADDITION) {
                                r = r + (double) (numCombos.get(i).get(0));
                            } else if (x == SUBTRACTION) {
                                r = (double) (numCombos.get(i).get(0)) - r;
                            } else if (x == MULTIPLICATION) {
                                r = r * (double) (numCombos.get(i).get(0));
                            } else if (x == DIVISION) {
                                r = (double) (numCombos.get(i).get(0)) / r;
                            }
                            if (z == ADDITION) {
                                r = r + (double) (numCombos.get(i).get(3));
                            } else if (z == SUBTRACTION) {
                                r = r - (double) (numCombos.get(i).get(3));
                            } else if (z == MULTIPLICATION) {
                                r = r * (double) (numCombos.get(i).get(3));
                            } else if (z == DIVISION) {
                                r = r / (double) (numCombos.get(i).get(3));
                            }
                        }

                        if ((23.9 < r && r < 24.1) && count < 1) {
                            System.out.println((double) (numCombos.get(i)
                                    .get(0))
                                    + toOp(x)
                                    + "("
                                    + (double) (numCombos.get(i).get(1))
                                    + toOp(y)
                                    + (double) (numCombos.get(i).get(2))
                                    + ")"
                                    + toOp(z)
                                    + (double) (numCombos.get(i).get(3))
                                    + " = 24.0");
                            count++;
                        }
                    }
                }
            }
        }

    }

    public static void testGrouping3(ArrayList<ArrayList<Double>> numCombos) {
        int numOfNumCombos = numCombos.size();

        //temporary variables for holding the result of calculations
        double r = 0, r2 = 0;

        // GROUPING: a opr b opr (c opr d)
        // using nested for loops, every combination of numbers is run through each
        // combination of operands. order of operations is kept in check with the if statements.
        for (int i = 0; i < numOfNumCombos; i++) {
            for (int x = 0; x < 4; x++) {
                for (int y = 0; y < 4; y++) {
                    for (int z = 0; z < 4; z++) {
                        switch (z) {
                            case ADDITION:
                                r = (double) (numCombos.get(i).get(2)) + (double) (numCombos.get(i).get(3));
                                break;
                            case SUBTRACTION:
                                r = (double) (numCombos.get(i).get(2)) - (double) (numCombos.get(i).get(3));
                                break;
                            case MULTIPLICATION:
                                r = (double) (numCombos.get(i).get(2)) * (double) (numCombos.get(i).get(3));
                                break;
                            case DIVISION:
                                r = (double) (numCombos.get(i).get(2)) / (double) (numCombos.get(i).get(3));
                                break;
                        }

                        // if y takes precedent
                        if ((x == ADDITION || x == SUBTRACTION)
                                && (y == MULTIPLICATION || y == DIVISION)) {
                            if (y == MULTIPLICATION) {
                                r = (double) (numCombos.get(i).get(1)) * r;
                            } else if (y == DIVISION) {
                                r = (double) (numCombos.get(i).get(1)) / r;
                            }
                            if (x == ADDITION) {
                                r = (double) (numCombos.get(i).get(0)) + r;
                            } else if (x == SUBTRACTION) {
                                r = (double) (numCombos.get(i).get(0)) - r;
                            }

                            // if x takes precedent
                        } else if ((y == ADDITION || y == SUBTRACTION)
                                && (x == MULTIPLICATION || x == DIVISION)) {
                            if (x == MULTIPLICATION) {
                                r2 = (double) (numCombos.get(i).get(0))
                                        * (double) (numCombos.get(i).get(1));
                            } else if (x == DIVISION) {
                                r2 = (double) (numCombos.get(i).get(0))
                                        / (double) (numCombos.get(i).get(1));
                            }
                            if (y == ADDITION) {
                                r = r2 + r;
                            } else if (y == SUBTRACTION) {
                                r = r2 - r;
                            }
                        } else // Doesn't matter what order
                        {

                            if (y == ADDITION) {
                                r = (double) (numCombos.get(i).get(1)) + r;
                            } else if (y == SUBTRACTION) {
                                r = (double) (numCombos.get(i).get(1)) - r;
                            } else if (y == MULTIPLICATION) {
                                r = (double) (numCombos.get(i).get(1)) * r;
                            } else if (y == DIVISION) {
                                r = (double) (numCombos.get(i).get(1)) / r;
                            }
                            if (x == ADDITION) {
                                r = (double) (numCombos.get(i).get(0)) + r;
                            } else if (x == SUBTRACTION) {
                                r = (double) (numCombos.get(i).get(0)) - r;
                            } else if (x == MULTIPLICATION) {
                                r = (double) (numCombos.get(i).get(0)) * r;
                            } else if (x == DIVISION) {
                                r = (double) (numCombos.get(i).get(0)) / r;
                            }

                        }

                        if ((23.9 < r && r < 24.1) && count < 1) {
                            System.out.println((double) (numCombos.get(i)
                                    .get(0))
                                    + toOp(x)
                                    + (double) (numCombos.get(i).get(1))
                                    + toOp(y)
                                    + "("
                                    + (double) (numCombos.get(i).get(2))
                                    + toOp(z)
                                    + (double) (numCombos.get(i).get(3))
                                    + ")"
                                    + " = 24.0");
                            count++;
                        }
                    }
                }
            }
        }

    }

    public static void testGrouping4(ArrayList<ArrayList<Double>> numCombos) {
        int numOfNumCombos = numCombos.size();

        //temporary variables for holding the result of calculations
        double r = 0, r2 = 0;
        // using nested for loops, every combination of numbers is run through each
        // combination of operands. order of operations is kept in check with the if statements.
        for (int i = 0; i < numOfNumCombos; i++) {
            for (int x = 0; x < 4; x++) {
                for (int y = 0; y < 4; y++) {
                    for (int z = 0; z < 4; z++) {
                        if (x == ADDITION) {
                            r = (double) (numCombos.get(i).get(0)) + (double) (numCombos.get(i).get(1));
                        } else if (x == SUBTRACTION) {
                            r = (double) (numCombos.get(i).get(0)) - (double) (numCombos.get(i).get(1));
                        } else if (x == MULTIPLICATION) {
                            r = (double) (numCombos.get(i).get(0)) * (double) (numCombos.get(i).get(1));
                        } else if (x == DIVISION) {
                            r = (double) (numCombos.get(i).get(0)) / (double) (numCombos.get(i).get(1));
                        }

                        if (y == ADDITION) {
                            r = r + (double) (numCombos.get(i).get(2));
                        } else if (y == SUBTRACTION) {
                            r = r - (double) (numCombos.get(i).get(2));
                        } else if (y == MULTIPLICATION) {
                            r = r * (double) (numCombos.get(i).get(2));
                        } else if (y == DIVISION) {
                            r = r / (double) (numCombos.get(i).get(2));
                        }

                        if (z == ADDITION) {
                            r = r + (double) (numCombos.get(i).get(3));
                        } else if (z == SUBTRACTION) {
                            r = r - (double) (numCombos.get(i).get(3));
                        } else if (z == MULTIPLICATION) {
                            r = r * (double) (numCombos.get(i).get(3));
                        } else if (z == DIVISION) {
                            r = r / (double) (numCombos.get(i).get(3));
                        }

                        if ((23.9 < r && r < 24.1) && count < 1) {
                            System.out.println("(("
                                    + (double) (numCombos.get(i).get(0))
                                    + toOp(x)
                                    + (double) (numCombos.get(i).get(1)) + ")"
                                    + toOp(y)
                                    + (double) (numCombos.get(i).get(2)) + ")"
                                    + toOp(z)
                                    + (double) (numCombos.get(i).get(3))
                                    + " = 24.0");
                            count++;
                        }
                    }
                }
            }
        }

    }

    public static void testGrouping5(ArrayList<ArrayList<Double>> numCombos) {
        int numOfNumCombos = numCombos.size();

        //temporary variables for holding the result of calculations
        double r = 0, r2 = 0;
        // using nested for loops, every combination of numbers is run through each
        // combination of operands. order of operations is kept in check with the if statements.
        for (int i = 0; i < numOfNumCombos; i++) {
            for (int x = 0; x < 4; x++) {
                for (int y = 0; y < 4; y++) {
                    for (int z = 0; z < 4; z++) {
                        if (y == ADDITION) {
                            r = (double) (numCombos.get(i).get(1)) + (double) (numCombos.get(i).get(2));
                        } else if (y == SUBTRACTION) {
                            r = (double) (numCombos.get(i).get(1)) - (double) (numCombos.get(i).get(2));
                        } else if (y == MULTIPLICATION) {
                            r = (double) (numCombos.get(i).get(1)) * (double) (numCombos.get(i).get(2));
                        } else if (y == DIVISION) {
                            r = (double) (numCombos.get(i).get(1)) / (double) (numCombos.get(i).get(2));
                        }

                        if (x == ADDITION) {
                            r = r + (double) (numCombos.get(i).get(0));
                        } else if (x == SUBTRACTION) {
                            r = (double) (numCombos.get(i).get(0)) - r;
                        } else if (x == MULTIPLICATION) {
                            r = r * (double) (numCombos.get(i).get(0));
                        } else if (x == DIVISION) {
                            r = (double) (numCombos.get(i).get(0)) / r;
                        }

                        if (z == ADDITION) {
                            r = r + (double) (numCombos.get(i).get(3));
                        } else if (z == SUBTRACTION) {
                            r = r - (double) (numCombos.get(i).get(3));
                        } else if (z == MULTIPLICATION) {
                            r = r * (double) (numCombos.get(i).get(3));
                        } else if (z == DIVISION) {
                            r = r / (double) (numCombos.get(i).get(3));
                        }

                        if ((23.9 < r && r < 24.1) && count < 1) {
                            System.out.println("("
                                    + (double) (numCombos.get(i).get(0))
                                    + toOp(x) + "("
                                    + (double) (numCombos.get(i).get(1))
                                    + toOp(y)
                                    + (double) (numCombos.get(i).get(2)) + "))"
                                    + toOp(z)
                                    + (double) (numCombos.get(i).get(3))
                                    + " = 24.0");
                            count++;
                        }
                    }
                }
            }
        }
    }

    public static void testGrouping6(ArrayList<ArrayList<Double>> numCombos) {
        int numOfNumCombos = numCombos.size();

        //temporary variables for holding the result of calculations
        double r = 0, r2 = 0;
        // using nested for loops, every combination of numbers is run through each
        // combination of operands. order of operations is kept in check with the if statements.
        for (int i = 0; i < numOfNumCombos; i++) {
            for (int x = 0; x < 4; x++) {
                for (int y = 0; y < 4; y++) {
                    for (int z = 0; z < 4; z++) {
                        if (y == ADDITION) {
                            r = (double) (numCombos.get(i).get(1)) + (double) (numCombos.get(i).get(2));
                        } else if (y == SUBTRACTION) {
                            r = (double) (numCombos.get(i).get(1)) - (double) (numCombos.get(i).get(2));
                        } else if (y == MULTIPLICATION) {
                            r = (double) (numCombos.get(i).get(1)) * (double) (numCombos.get(i).get(2));
                        } else if (y == DIVISION) {
                            r = (double) (numCombos.get(i).get(1)) / (double) (numCombos.get(i).get(2));
                        }

                        if (z == ADDITION) {
                            r = r + (double) (numCombos.get(i).get(3));
                        } else if (z == SUBTRACTION) {
                            r = r - (double) (numCombos.get(i).get(3));
                        } else if (z == MULTIPLICATION) {
                            r = r * (double) (numCombos.get(i).get(3));
                        } else if (z == DIVISION) {
                            r = r / (double) (numCombos.get(i).get(3));
                        }

                        if (x == ADDITION) {
                            r = r + (double) (numCombos.get(i).get(0));
                        } else if (x == SUBTRACTION) {
                            r = (double) (numCombos.get(i).get(0)) - r;
                        } else if (x == MULTIPLICATION) {
                            r = r * (double) (numCombos.get(i).get(0));
                        } else if (x == DIVISION) {
                            r = (double) (numCombos.get(i).get(0)) / r;
                        }

                        if ((23.9 < r && r < 24.1) && count < 1) {
                            System.out.println((double) (numCombos.get(i)
                                    .get(0))
                                    + toOp(x)
                                    + "(("
                                    + (double) (numCombos.get(i).get(1))
                                    + toOp(y)
                                    + (double) (numCombos.get(i).get(2))
                                    + ")"
                                    + toOp(z)
                                    + (double) (numCombos.get(i).get(3))
                                    + ")"
                                    + " = 24.0");
                            count++;

                        }
                    }
                }
            }

        }
    }

    public static void testGrouping7(ArrayList<ArrayList<Double>> numCombos) {
        int numOfNumCombos = numCombos.size();

        //temporary variables for holding the result of calculations
        double r = 0, r2 = 0;
        // using nested for loops, every combination of numbers is run through each
        // combination of operands. order of operations is kept in check with the if statements.
        for (int i = 0; i < numOfNumCombos; i++) {
            for (int x = 0; x < 4; x++) {
                for (int y = 0; y < 4; y++) {
                    for (int z = 0; z < 4; z++) {
                        if (z == ADDITION) {
                            r = (double) (numCombos.get(i).get(2)) + (double) (numCombos.get(i).get(3));
                        } else if (z == SUBTRACTION) {
                            r = (double) (numCombos.get(i).get(2)) - (double) (numCombos.get(i).get(3));
                        } else if (z == MULTIPLICATION) {
                            r = (double) (numCombos.get(i).get(2)) * (double) (numCombos.get(i).get(3));
                        } else if (z == DIVISION) {
                            r = (double) (numCombos.get(i).get(2)) / (double) (numCombos.get(i).get(3));
                        }

                        if (y == ADDITION) {
                            r = (double) (numCombos.get(i).get(1)) + r;
                        } else if (y == SUBTRACTION) {
                            r = (double) (numCombos.get(i).get(1)) - r;
                        } else if (y == MULTIPLICATION) {
                            r = (double) (numCombos.get(i).get(1)) * r;
                        } else if (y == DIVISION) {
                            r = (double) (numCombos.get(i).get(1)) / r;
                        }

                        if (x == ADDITION) {
                            r = (double) (numCombos.get(i).get(0)) + r;
                        } else if (x == SUBTRACTION) {
                            r = (double) (numCombos.get(i).get(0)) - r;
                        } else if (x == MULTIPLICATION) {
                            r = r * (double) (numCombos.get(i).get(0));
                        } else if (x == DIVISION) {
                            r = (double) (numCombos.get(i).get(0)) / r;
                        }

                        if ((23.9 < r && r < 24.1) && count < 1) {
                            System.out.println((double) (numCombos.get(i)
                                    .get(0))
                                    + toOp(x)
                                    + "("
                                    + (double) (numCombos.get(i).get(1))
                                    + toOp(y)
                                    + "("
                                    + (double) (numCombos.get(i).get(2))
                                    + toOp(z)
                                    + (double) (numCombos.get(i).get(3))
                                    + "))"
                                    + " = 24.0");
                            count++;
                        }
                    }
                }
            }
        }
    }

} // end of class

