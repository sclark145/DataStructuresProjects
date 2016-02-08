package CSE214_Projects.Solitaire;

/**
 * Sean Clark
 * 109653264
 * Homework #3
 * Tuesday: R03
 * Recitation TA: Sun Lin
 * Grading TA: Ke Ma
 * @author sclark
 */

import java.util.Collections;
import java.util.Scanner;

/**
 * Class: Stackotaire
 * 
 * Contains main method that performs
 * creates a playable game of solitaire.
 * 
 */
public class Stackotaire
{
	private static boolean quit = false;

	public static final char STOCK       = 's';
	public static final char WASTE       = 'w';
	public static final char FOUNDATIONS = 'f';
	public static final char TABLEAU     = 't';

	private CardStack main;
	private Card[] checkDeck;
	private CardStack[] tableau;
	private CardStack[] foundation;
	private CardStack waste;
	private CardStack stock;

	private Scanner stdin = null;

	private String input = "";
	private String in1 = "";
	private String in2 = "";
	private int in3;

	public static void main(String[] args)
	{
		new Stackotaire(args);
	}

	/**
	 * Method that shuffles and resets the game board.
	 */
	private void initializeGame()
	{
		// ---- put all cards back into main, if they aren't there already.
		while(waste.isEmpty() == false)
			main.push(waste.pop());
		while(stock.isEmpty() == false)
			main.push(stock.pop());
		for(int i = 0; i < 7; i++)
			while(tableau[i].isEmpty() == false)
				main.push(tableau[i].pop());
		for(int i = 0; i < 4; i++)
			while(foundation[i].isEmpty() == false)
				main.push(foundation[i].pop());

		Collections.shuffle(main); // ---- shuffle cards within main

		for(int i = 0; i < main.size(); i++) // ---- set all cards facedown
			main.get(i).setFaceUp(false);

		for(int i = 0; i < 7; i++) // ---- fill the tableaus appropriately
			for(int k = 0; k <= i; k++)
				tableau[i].push( main.pop() );

		while(main.isEmpty() == false) // --- put the remaining cards in the stock
			stock.push(main.pop());
	}

	/**
	 * Updates the status of the cards and displays the game board in its current state.
	 */
	private void updateAndDisplayBoard()
	{
		for(int i = 0; i < 4; i++) // ---- printing and updating foundations
			if(foundation[i].isEmpty())
				System.out.print("[F" + (i+1) + "]");
			else
				foundation[i].printStack(FOUNDATIONS);
		System.out.print("     W1 ");
		
		waste.printStack(WASTE); // ---- printing and updating the waste
		System.out.print("    ");
		
		stock.printStack(STOCK); // ---- printing and updating the stock
		System.out.print(" " + stock.size() + "\n\n");
		
		for(int i = 7; i > 0; i--) // ---- printing and updating tableaus
		{
			System.out.print("T" + i + " ");
			tableau[i-1].printStack(TABLEAU);
			System.out.println();
		}
		System.out.println();
	}

	/**
	 * Method that checks if the user has won.
	 * 
	 * @return true if the user has won.
	 * 		   false if the user has not yet won.
	 */
	private boolean checkWin()
	{
		// ---- checking to see if all cards are face up. if this is true, the user has won.
		for(int i = 0; i < checkDeck.length; i++)
			if(checkDeck[i].isFaceUp() == false)
				return false;
		return true;
	}

	/**
	 * Method that draws a card from the stock to the waste.
	 */
	public void draw()
	{
		// ---- if the stock is empty, we must refill it using the cards from the waste.
		if(stock.size() == 0)
		{
			while( waste.isEmpty() == false )
				stock.push(waste.pop());
			
			// ---- we must make sure they are back to being face down in the stock
			for(int i = 0; i < stock.size(); i++)
				stock.get(i).setFaceUp(false);
		}
		else
		{
			waste.push(stock.pop());
		}
	}

	/**
	 * Method that restarts the game.
	 */
	public void restart()
	{
		if(getRestartSelection() == 'Y')
		{
			initializeGame();
		}
		else
			System.out.println("Continuing game..." + "\n");
	}

	/**
	 * Method that ensures the user's selection for restarting is correct.
	 * 
	 * @return the char 'Y' or 'N' the user selects.
	 */
	private char getRestartSelection()
	{
		char selection;
		System.out.print("Do you want to start a new game? (Y/N): ");
		selection = stdin.next().toUpperCase().charAt(0);
		System.out.println();
		if(selection == 'Y' || selection == 'N')
			return selection;
		System.out.println(selection + " is not a valid selection. Please try again.");
		System.out.println();
		return getRestartSelection();	
	}

	/**
	 * Method that moves a card from the waste to a given tableau.
	 * 
	 * @param x the tableau the card will be moved to.
	 */
	public void moveWT(int x)
	{
		x--;

		if(x > 6 || x < 0)
			throw new IllegalArgumentException();
		else if(tableau[x].isEmpty() && waste.peek().getCardValue() == 13)
		{
			tableau[x].push(waste.pop());
		}
		else if(tableau[x].peek().isRed() != waste.peek().isRed() 
				&& (tableau[x].peek().getCardValue() == waste.peek().getCardValue() + 1))
		{
			tableau[x].push(waste.pop());
		}
		else
			throw new IllegalArgumentException();
	}

	/**
	 * Method that moves a card from the waste to a given foundation.
	 * 
	 * @param x the foundation the card will be moved to.
	 */
	public void moveWF(int x)
	{
		x--;

		if(x > 3 || x < 0)
			throw new IllegalArgumentException();
		else if(foundation[x].isEmpty() && waste.peek().getCardValue() == 1)
		{
			foundation[x].push(waste.pop());
		}
		else if(foundation[x].peek().getCardSuit() == waste.peek().getCardSuit() 
				&& (foundation[x].peek().getCardValue() == waste.peek().getCardValue() - 1))
		{
			foundation[x].push(waste.pop());
		}
		else
			throw new IllegalArgumentException();
	}

	/**
	 * Method that moves a single card from the a given tableau to another given tableau.
	 * 
	 * @param x the tableau the card will be moved from.
	 * @param y the tableau the card will be moved to.
	 */
	public void moveTT(int x, int y)
	{
		x--; y--;
		if(x > 6 || x < 0 || y > 6 || y < 0)
			throw new IllegalArgumentException();
		else if(tableau[y].isEmpty() && tableau[x].peek().getCardValue() == 13)
		{
			tableau[y].push(tableau[x].pop());
		}
		else if(tableau[y].peek().isRed() != tableau[x].peek().isRed() 
				&& (tableau[y].peek().getCardValue() == tableau[x].peek().getCardValue() + 1))
		{
			tableau[y].push(tableau[x].pop());
		}
		else
			throw new IllegalArgumentException();
	}

	/**
	 * Method that moves a card from the a given foundation to a given tableau.
	 * 
	 * @param x the foundation the card will be moved from.
	 * @param y the tableau the card will be moved to.
	 */
	public void moveFT(int x, int y)
	{
		x--; y--;

		if(y > 6 || y < 0 || x > 3 || x < 0 )
			throw new IllegalArgumentException();
		else if(tableau[y].isEmpty() && foundation[x].peek().getCardValue() == 13)
		{
			tableau[y].push(foundation[x].pop());
		}
		else if(tableau[y].peek().isRed() != foundation[x].peek().isRed() 
				&& (tableau[y].peek().getCardValue() == foundation[x].peek().getCardValue() + 1))
		{
			tableau[y].push(foundation[x].pop());
		}
		else
			throw new IllegalArgumentException();
	}

	/**
	 * Method that moves a card from the a given tableau to a given foundation.
	 * 
	 * @param x the tableau the card will be moved from.
	 * @param y the foundation the card will be moved to.
	 */
	public void moveTF(int x, int y)
	{
		x--; y--;

		if(x > 6 || x < 0 || y > 3 || y < 0 )
			throw new IllegalArgumentException();
		else if(foundation[y].isEmpty() && tableau[x].peek().getCardValue() == 1)
		{
			foundation[y].push(tableau[x].pop());
		}
		else if(foundation[y].peek().getCardSuit() == tableau[x].peek().getCardSuit() 
				&& (foundation[y].peek().getCardValue() == tableau[x].peek().getCardValue() - 1))
		{
			foundation[y].push(tableau[x].pop());
		}
		else
			throw new IllegalArgumentException();
	}

	/**
	 * Method that moves a number of cards from the a given tableau to another given tableau.
	 * 
	 * @param x the tableau the cards will be moved from.
	 * @param y the tableau the cards will be moved to.
	 * @param n the number of cards that will be moved.
	 */
	public void moveN(int x, int y, int n)
	{
		x--; y--;

		if(x > 6 || x < 0 || y > 6 || y < 0 || n > tableau[x].size() || n < 0)
			throw new IllegalArgumentException();
		else if(tableau[y].isEmpty() && tableau[x].get(tableau[x].size() - n).getCardValue() == 13)
		{
			int movePos = tableau[x].size() - n; // ---- beginning position of the cards we are moving
			for(int i = 0; i < n; i++)
				tableau[y].push(tableau[x].remove(movePos));
		}
		else if(tableau[x].get(tableau[x].size() - n).isRed() != tableau[y].peek().isRed()
				&& (tableau[x].get(tableau[x].size() - n).getCardValue() ==
				tableau[y].peek().getCardValue() - 1))
		{
			int movePos = tableau[x].size() - n;
			for(int i = 0; i < n; i++)
				tableau[y].push(tableau[x].remove(movePos));
		}
		else
			throw new IllegalArgumentException();
	}

	/**
	 * Method that prompts the user for a command and executes the operation.
	 * 
	 * @throws IllegalArgumentException 
	 *  Throws exception if the input from the user does not match what is expected.
	 */
	public void makeMove() throws IllegalArgumentException
	{
		System.out.print("Enter a command: ");
		input = stdin.next();
		System.out.println();

		switch(input.toUpperCase())
		{
		case "MOVE": // ---- we now expect two more inputs
			in1 = stdin.next();
			in2 = stdin.next();
			
			// ---- checking which type of move the user wants to make, and sending the
			//		number of the stack to the right method.
			if(in1.toUpperCase().charAt(0) == 'W' && in2.toUpperCase().charAt(0) == 'T')
			{
				moveWT( Integer.parseInt( in2.substring(1) ) );
			}
			else if(in1.toUpperCase().charAt(0) == 'W' && in2.toUpperCase().charAt(0) == 'F')
			{
				moveWF( Integer.parseInt( in2.substring(1) ) );
			}
			else if(in1.toUpperCase().charAt(0) == 'F' && in2.toUpperCase().charAt(0) == 'T')
			{
				moveFT(Integer.parseInt( in1.substring(1) ), Integer.parseInt( in2.substring(1) ) );
			}
			else if(in1.toUpperCase().charAt(0) == 'T' && in2.toUpperCase().charAt(0) == 'F')
			{
				moveTF(Integer.parseInt( in1.substring(1) ), Integer.parseInt( in2.substring(1) ) );
			}
			else if(in1.toUpperCase().charAt(0) == 'T' && in2.toUpperCase().charAt(0) == 'T')
			{
				moveTT(Integer.parseInt( in1.substring(1) ), Integer.parseInt( in2.substring(1) ) );
			}
			else
			{
				throw new IllegalArgumentException();
			}

			break;

		case "MOVEN": // ---- we now expect three more inputs, last one is an integer
			in1 = stdin.next();
			in2 = stdin.next();
			in3 = stdin.nextInt();

			// ---- checking to make sure the user selected two tableaus 
			if(in1.toUpperCase().charAt(0) == 'T' && in2.toUpperCase().charAt(0) == 'T')
			{
				moveN( Integer.parseInt( in1.substring(1) ), Integer.parseInt( in2.substring(1) ),
						in3) ;
			}
			else
			{
				throw new IllegalArgumentException();
			}

			break;

		case "DRAW": draw(); break;

		case "RESTART": restart(); break;

		case "QUIT": quit = true; break;

		default:
			throw new IllegalArgumentException();
		}
	}

	/**
	 * Method that creates the game and initializes all stacks.
	 */
	public void createGame()
	{
		stdin = new Scanner(System.in);
		
		// ---- Create all necessary stacks and fill the main
		main = new CardStack();
		for(int i = 1; i <= 13; i++)
			for(int j = 1; j <= 4; j++)
				main.push( new Card(i, j) );
		
		// ---- shallow copy of all cards, makes checking for win easier
		checkDeck = new Card[main.size()];
		for(int i = 0; i < main.size(); i++)
			checkDeck[i] = main.get(i);
		
		// ---- initializing the tableau stacks
		tableau = new CardStack[7];
		for(int i = 0; i < 7; i++)
			tableau[i] = new CardStack();
		
		// ---- initializing foundation stacks
		foundation = new CardStack[4];
		for(int i = 0; i < 4; i++)
			foundation[i] = new CardStack();
		
		// ---- Initializing waste and stock stacks.
		waste = new CardStack();
		stock = new CardStack();
		
		// ---- Creation of stacks and cards done
	}

	public Stackotaire(String[] args)
	{
		System.out.println("Welcome to Stackotaire! This project uses"
                        + " the stack data structure to simulate a game of Solitaire.");
		System.out.println("=======================");
		System.out.println("Key:");
		System.out.println("[] - The card is black.");
		System.out.println("() - The card is red.");
		System.out.println("=======================");
                System.out.println("Example commands: draw\n"
                                 + "                  move t1 t2\n"
                                 + "                  move t1 f1\n"
                                 + "                  etc...");
		System.out.println("Good luck!\n");

		createGame();
		initializeGame();
		updateAndDisplayBoard();

		while( checkWin() == false && quit == false ) // ---- Loop that allows player to play until
		{											  //      they quit or have won the game.
			try
			{
				makeMove();
				if(quit == false)
					updateAndDisplayBoard();
			}
			catch(Exception e)
			{
				System.out.print( "Not a valid move, try again." + "\n\n");
			}
		}

		if( checkWin() )
			System.out.println("Congrats! You've won!");
		if( quit == true )
			System.out.println("Sorry, you lose.");

		System.out.println("Program terminated.");
	}
}