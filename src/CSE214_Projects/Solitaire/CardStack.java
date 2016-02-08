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

import java.util.Stack;

/**
 * Class: CardStack
 * 
 * Stack containing Cards to be used in Stackotaire.
 * Extends the Stack class.
 * 
 */
@SuppressWarnings("serial")
public class CardStack extends Stack<Card>
{	
	/**
	 * Method that prints and updates the cards of the stack
	 * according to its type.
	 * 
	 * @param stackType the type of stack we are trying to print.
	 */
	public void printStack(char stackType)
	{
		switch(stackType)
		{
		case 's': // -- stock
			if(isEmpty())
				System.out.print("[  ]");
			else
			{
				for(int i = 0; i < size(); i++)
					get(i).setFaceUp(false);
				System.out.print(peek().toString());
			}
			break;
			
		case 'w': // -- waste
			if(isEmpty())
				System.out.print("[  ]");
			else
			{
				for(int i = 0; i < size(); i++)
					get(i).setFaceUp(false);
				peek().setFaceUp(true);
				System.out.print(peek().toString());
			}
			break;
			
		case 'f': // -- foundation
			if(isEmpty())
				System.out.print("[  ]");
			else
			{
				peek().setFaceUp(true);
				System.out.print(peek().toString());
			}
			break;
			
		case 't': // -- tableau
			if(isEmpty())
				System.out.print("[  ]");
			else
			{
				peek().setFaceUp(true);
				for(int i = 0; i < size(); i++)
					System.out.print(get(i).toString());
			}
			break;
			
		default:
			System.out.print("STACK TYPE INPUT ERROR.");
			break;
		}
	}
}
