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

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.util.Enumeration;
import java.util.Hashtable;

import big.data.DataSource;

@SuppressWarnings("serial")
public class HashedLibrary extends Hashtable<String, Book> implements Serializable
{
	/**
	 * Method that adds a book to the library based on inputs
	 * @param title the title of the new book
	 * @param author the author of the new book
	 * @param publisher the publisher of the new book
	 * @param isbn the isbn number of the new book
	 * @throws IllegalArgumentException
	 */
	public void addBook(String title, String author, String publisher, String isbn) throws IllegalArgumentException
	{
		if( get(isbn) == null )
		{
			Book newBook = new Book(title, author, publisher, isbn);
			this.put(isbn, newBook);
		}
		else
			throw new IllegalArgumentException();
	}

	/**
	 * Adds a list of books listed in the given textfile
	 * @param fileName the file with the book names
	 * @throws Exception if there is a problem finding or loading the file
	 */
	@SuppressWarnings("resource")
	public void addAllBookInfo(String fileName) throws Exception
	{
		BufferedReader reader = null;
		String input = null;

		try
		{
			FileInputStream fis = new FileInputStream(fileName); 
			InputStreamReader inStream = new InputStreamReader(fis);
			reader = new BufferedReader(inStream);
		}
		catch (FileNotFoundException e)
		{
			System.out.println("\nFile not found. Returning to menu.");
			return;
		}

		input = reader.readLine();

		while( input != null )
		{
			DataSource ds = DataSource.connect("http://www.cs.stonybrook.edu/~cse214/hw/hw6/" + input + ".xml").load();
			try
			{
				input = reader.readLine();
				
				addBook( ds.fetchString("title"), ds.fetchString("author"),
						ds.fetchString("publisher"), ds.fetchString("isbn") );
				
				System.out.println(ds.fetchString("isbn") + ": " + ds.fetchString("title")
									+ " by " + ds.fetchString("author") + " recorded.");
			}
			catch(IllegalArgumentException e)
			{
				System.out.println(ds.fetchString("isbn") + ": Book already recorded.");
			}
			catch(Exception e)
			{
				System.out.println("Error reading that file.");
			}
		}
		System.out.println();
	}
	
	/**
	 * looks for an returns a book by its isbn value
	 * @param isbn the isbn to look for
	 * @return the book if it exists, else return null
	 */
	public Book getBookByisbn(String isbn)
	{
		return get(isbn);
	}
	
	/**
	 * prints all the items in the library
	 */
	public void printCatalog()
	{
		Enumeration<String> k = keys();
		
		if(k.hasMoreElements())
		{
		System.out.println("Book ISBN       Title                              " +
						   "Author                          Publisher");
		System.out.println("------------------------------------------------------------"
				+ "--------------------------------------------------------------");
		}
		else
		{
			System.out.println("The library is currently empty.\n");
			return;
		}
		
		while( k.hasMoreElements() )
		{
			System.out.println( getBookByisbn(k.nextElement()).toString() ); 
		}
		
		System.out.println();
	}
}
