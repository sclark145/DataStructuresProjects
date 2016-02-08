package CSE214_Projects.HashTableLibrary;

/**
 * Sean Clark
 * 109653264
 * Homework #6
 * Tuesday: R03
 * Recitation TA: Sun Lin
 * Grading TA: Ke Ma
 * @author sclark
 */

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Scanner;

public class DatabaseDriver
{
	HashedLibrary library = null;

	private String input = null;
	private String titleIn = null;
	private String authorIn = null;
	private String publisherIn = null;
	private String isbnIn = null;

	private Scanner stdin = null;
	private boolean quit = false;

	ObjectOutputStream outStream = null;
	ObjectInputStream inStream   = null;
	FileInputStream fileIn       = null;
	FileOutputStream fileOut     = null;

	public static void main(String[] args)
	{
		new DatabaseDriver(args);
	}

	public DatabaseDriver(String[] args)
	{
                System.out.println(" --- Welcome to the Library Database Simulator --- ");
                System.out.println("This project uses the hashtable data structure to "
                        + "simulate a library database.");
                System.out.println("It also saves the changes you make to the library to a library.obj "
                        + "file which can be found in the data folder of this project.");
                System.out.println("I have provided two text files with sample books that can be added.");
                System.out.println("Try pressing 'L' then enter 'data/book_list.txt' or 'data/book_list2.txt' to add some books to the library!");
                System.out.println("Enjoy!\n");
                
                
		stdin = new Scanner(System.in);
		
		getLibrary();

		while(quit == false)
		{
			displayMenu();
			switch ( getMenuSelection() )
			{
				case 'D': library.printCatalog(); break;
				case 'G': getBook(); break;
				case 'L': loadFile(); break;
				case 'R': readInBook(); break;
				case 'Q': quit = true;
			}
		}

		saveChanges();
		System.out.print("Program terminated.");
	}

	/**
	 * prompt the user for properties of a new book and creates it.
	 */
	public void readInBook()
	{
		System.out.print("Enter book title: ");
		titleIn = stdin.nextLine();
		System.out.print("Enter book author: ");
		authorIn = stdin.nextLine();
		System.out.print("Enter book publisher: ");
		publisherIn = stdin.nextLine();
		System.out.print("Enter book ISBN: ");
		isbnIn = stdin.nextLine();

		library.addBook(titleIn, authorIn, publisherIn, isbnIn);

		System.out.println();
	}

	/**
	 * loads books based on a text file
	 */
	public void loadFile()
	{
		System.out.print("Enter the file to load: ");
		input = stdin.nextLine();
		System.out.println();
		try
		{
			library.addAllBookInfo(input);
		}
		catch (Exception e)
		{
			System.out.println("Error loading from file.");
		}

	}

	/**
	 * Prints info of the book we are looking for, if its exists.
	 */
	public void getBook()
	{
		System.out.print("Enter Book ISBN: ");
		input = stdin.nextLine();
		Book b = library.getBookByisbn(input);

		if(b==null)
			System.out.println("Book could not be found.\n");
		else
		{
			System.out.println("\nBook ISBN       Title                              " +
					"Author                          Publisher");
			System.out.println("------------------------------------------------------------"
					+ "--------------------------------------------------------------");
			System.out.println(b.toString()+ "\n");
		}
	}


	/**
	 * Method that gets a selection from the user
	 * @return the char the user selected
	 */
	private char getMenuSelection() {

		char selection;
		System.out.print("Select an operation: ");
		selection = stdin.nextLine().toUpperCase().charAt(0);
		System.out.println();

		if (isValidSelection(selection))
			return selection;

		System.out.println(selection + " is not a valid selection."
				+ " Please try again.");
		System.out.println();
		return getMenuSelection();	
	}

	/**
	 * Method that checks if the input is valid
	 * @param selection the selection the user gave
	 * @return true if the selection is valid, false otherwise
	 */
	private  boolean isValidSelection(char selection) {
		return selection == 'D' || selection == 'G' || selection == 'L'
				|| selection == 'R'	|| selection == 'Q';
	}

	/**
	 * loads in or creates a new library at launch
	 */
	public void getLibrary()
	{
		try
		{
			fileIn = new FileInputStream("./data/library.obj");
			inStream = new ObjectInputStream(fileIn);
			library = (HashedLibrary) inStream.readObject();
			inStream.close();
			System.out.println("Successfully loaded contents of library.obj.\n");
		}
		catch (Exception e)
		{
			System.out.println("The file library.obj was not found. Using a new HashedLibrary.\n");
			library = new HashedLibrary();
		}
	}

	/**
	 * displays the menu choices
	 */
	public void displayMenu()
	{
		System.out.println("(D) Display Books");
		System.out.println("(G) Get Book");
		System.out.println("(L) Load File");
		System.out.println("(R) Record Book");
		System.out.println("(Q) Quit\n");
	}

	/**
	 * before termination, saves changes made to the library
	 */
	public void saveChanges()
	{
		try
		{
			fileOut = new FileOutputStream("./data/library.obj");
			outStream = new ObjectOutputStream(fileOut);
			outStream.writeObject(library);
			outStream.close();
			System.out.println("The HashedLibrary is saved into the file library.obj.");
		}
		catch (Exception e)
		{
			System.out.println("Error. Changes could not be saved.");
		}
	}

}