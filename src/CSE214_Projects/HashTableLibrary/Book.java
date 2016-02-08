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

import java.io.Serializable;

@SuppressWarnings("serial")
public class Book implements Serializable
{
	private String title, author, publisher, isbn;

	/**
	 * Constructor to create a new Book object.
	 * @param title the title of the book
	 * @param author the author of the book
	 * @param publisher the publisher of the book
	 * @param isbn the isbn number of the book
	 */
	public Book(String title, String author, String publisher, String isbn)
	{
		this.title = title;
		this.author = author;
		this.publisher = publisher;
		this.isbn = isbn;
	}
	
	/**
	 * returns a string representation of this book object
	 */
	@Override
	public String toString()
	{
		return String.format("%-16s%-35s%-32s%s", isbn, title, author, publisher);
	}

	/**
	 * @return the title
	 */
	public String getTitle()
	{
		return title;
	}

	/**
	 * @param title the title to set
	 */
	public void setTitle(String title)
	{
		this.title = title;
	}

	/**
	 * @return the author
	 */
	public String getAuthor()
	{
		return author;
	}

	/**
	 * @param author the author to set
	 */
	public void setAuthor(String author)
	{
		this.author = author;
	}

	/**
	 * @return the publisher
	 */
	public String getPublisher()
	{
		return publisher;
	}

	/**
	 * @param publisher the publisher to set
	 */
	public void setPublisher(String publisher)
	{
		this.publisher = publisher;
	}

	/**
	 * @return the isbn
	 */
	public String getIsbn()
	{
		return isbn;
	}

	/**
	 * @param isbn the isbn to set
	 */
	public void setIsbn(String isbn)
	{
		this.isbn = isbn;
	}

}
