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

/**
 * Class: Card
 * 
 * Card object used in Stackotaire.
 * 
 */
public class Card
{
	String values[] = {" ","A","2","3","4","5","6","7","8","9","10","J","Q","K"};
    char suits[]    = {' ', '\u2666', '\u2663','\u2665', '\u2660'};   // {' ', '♦', '♣', '♥', '♠'}

    private int cardValue;
    private int cardSuit;
    private boolean isFaceUp;
    
    /**
     * Gets the value of the card.
     * 
	 * @return the cardValue
	 */
	public int getCardValue()
	{
		return cardValue;
	}

	/**
	 * Sets the value of the card.
	 * 
	 * @param cardValue the cardValue to set
	 */
	public void setCardValue(int cardValue)
	{
		this.cardValue = cardValue;
	}

	/**
	 * Gets the suit of the card.
	 * 
	 * @return the cardSuit
	 */
	public int getCardSuit()
	{
		return cardSuit;
	}

	/**
	 * Sets the suit of the card.
	 * 
	 * @param cardSuit the cardSuit to set
	 */
	public void setCardSuit(int cardSuit)
	{
		this.cardSuit = cardSuit;
	}

	/**
	 * Returns if the card is face up or not.
	 * 
	 * @return the isFaceUp
	 */
	public boolean isFaceUp()
	{
		return isFaceUp;
	}

	/**
	 * Sets the card face up or face down.
	 * 
	 * @param isFaceUp the isFaceUp to set
	 */
	public void setFaceUp(boolean isFaceUp)
	{
		this.isFaceUp = isFaceUp;
	}

	/**
	 * Constructor that creates a default Card
	 */
	public Card()
    {
    	cardValue = 0;
    	cardSuit = 0;
    	isFaceUp = false;
    }
    
	/**
	 * Constructor that creates a new Card based on the given parameters.
	 * 
	 * @param cardValue the cardValue the new card will have.
	 * @param cardSuit the cardSuit the new card will have.
	 */
    public Card(int cardValue, int cardSuit)
    {
    	this.cardSuit = cardSuit;
    	this.cardValue = cardValue;
    	isFaceUp = false;
    }
    
    /**
     * Method that returns true if the card is red.
     * 
     * @return true if the card is red.
     * 		   false if the card is black.
     */
	public boolean isRed()
	{
		if(cardSuit % 2 == 0)
			return false;
		else
			return true;
	}

	@Override
	/**
	 * Returns a string representation of this Card.
	 */
    public String toString()
    {
		if(!isFaceUp)
			return "[XX]";
		else
		{
			if(isRed())
				return "(" + values[cardValue]+suits[cardSuit] + ")"; // -- parentheses means red
			else
				return "[" + values[cardValue]+suits[cardSuit] + "]"; // -- hard brackets means black
		}
    }
	
}