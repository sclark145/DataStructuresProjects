package CSE214_Projects.PerformanceScheduler;

/**
 * Sean Clark
 * 109653264
 * Homework #2
 * Tuesday: R03
 * Recitation TA: Sun Lin
 * Grading TA: Ke Ma
 * @author sclark
 */

/**
 * Class: PerformanceList
 * 
 * Doubly linked list containing PerformanceNodes.
 * Uses a cursor to keep track of current node.
 * 
 */
public class PerformanceList
{
	private int performanceCount;
	
	private PerformanceNode head;
	private PerformanceNode tail;
	private PerformanceNode cursor;

	/**
	 * Null constructor for a new PerformanceList
	 */
	public PerformanceList()
	{
		head   = null;
		tail   = null;
		cursor = null;
		
		performanceCount = 0;
	}

	/**
	 * Gets the number of the performances in this list.
	 * 
	 * @return
	 * 	Returns number of performances in this list.
	 */
	public int getPerformanceCount()
	{
		return performanceCount;
	}
	
	/**
	 * Gets the PerformanceNode that is currently the cursor.
	 * 
	 * @return
	 * 	Returns PerformanceNode that the cursor is currently on.
	 */
	public PerformanceNode getCursor()
	{
		return cursor;
	}
	
	/**
	 * Adds a new PerformanceNode after the current position of the cursor.
	 * Also adjusts the starting times of the performances when a new one is
	 * added to the middle of the list.
	 * 
	 * @param newPerformance
	 * 	The PerformanceNode that will be added after the current position of the cursor.
	 * @throws IllegalArgumentException
	 * 	Throws exception if either the duration or participants is invalid for the 
	 * 	new performance that we are trying to be added to the list.
	 */
	public void addAfterCurrent(PerformanceNode newPerformance) throws IllegalArgumentException
	{
		if(newPerformance.getDuration() <= 0) // ---- throw an exception if the duration of the performance is less than 0.
			throw new IllegalArgumentException("Illegal argument, duration of the performance must be greater than 0.");
		if(newPerformance.getParticipants() <= 0) // ---- throw an exception if the number of participants is less than 0.
			throw new IllegalArgumentException("Illegal argument, number of participants must be greater than 0.");
		
		if (cursor == null) // ---- if null assume the list is empty
		{
			head = newPerformance; 
			tail = newPerformance; 
			cursor = newPerformance;
			performanceCount++;
		}
		else // ---- the list is not empty
		{
			newPerformance.setPrev(cursor); // ---- set prev to point back to current cursor pos
			
			if (cursor.getNext() == null) // ---- if null we are adding to the end, just append.
			{
				cursor.setNext(newPerformance);
				tail = newPerformance;
				cursor = tail;
				performanceCount++;
			}
			else // ---- we need to insert this node between 2 nodes. Just need to adjust a few references
			{
				newPerformance.setNext(cursor.getNext()); // ---- link the new node to the correct next ( was the next of cursor before this insert )
				cursor.getNext().setPrev(newPerformance); // ---- need to adjust the prev node of the node that is being shifted over
				cursor.setNext(newPerformance);           // ---- now set the next of the cursor to the new node.
				performanceCount++;
			
				adjustStartTimes(); // ---- we added a node we need to adjust the start times.
					
				cursor = cursor.getNext();
			}
		}
	}

	/**
	 * Adds a new PerformanceNode to the end of the list.
	 * 
	 * @param newPerformance
	 * 	The PerformanceNode that will be added to the end of the list.
	 * @throws IllegalArgumentException
	 * 	Throws exception if either the duration or participants is invalid for the 
	 * 	new performance that we are trying to be added to the list.
	 */
	public void addToEnd(PerformanceNode newPerformance) throws IllegalArgumentException
	{
		if(newPerformance.getDuration() <= 0) // ---- throw an exception if the duration of the performance is less than 0.
			throw new IllegalArgumentException("Illegal argument, duration of the performance must be greater than 0.");
		if(newPerformance.getParticipants() <= 0) // ---- throw an exception if the number of participants is less than 0.
			throw new IllegalArgumentException("Illegal argument, number of participants must be greater than 0.");
		
		if (cursor == null) // ---- check if list is empty
		{
			head = newPerformance; tail = newPerformance; cursor = newPerformance;
		}
		else
		{
			tail.setNext(newPerformance);
			newPerformance.setPrev(tail);
			tail = newPerformance;
			cursor = tail;
		}
		performanceCount++;
	}
	
	/**
	 * Removes the node that the cursor is currently on, given that the cursor
	 * is currently on a node.
	 * Also fixes the starting times of the performances when a node is removed
	 * from the beginning or middle of the list.
	 * 
	 * @return
	 * 	True if the node was successfully removed.
	 * 	False if the node could not be removed (i.e. if the list is empty).
	 */
	public boolean removeCurrentNode()
	{
		if (cursor == null) // ---- list must be empty
		{
			return false;
		}
		else if(cursor == head && performanceCount == 1 ) // ---- we are removing the only node in the list.
		{
			head = null; 
			cursor = null; 
			tail = null;
			performanceCount = 0;
			return true;
		}
		else if(cursor == head) // ---- we are removing the first node in the list
		{
			head = cursor.getNext(); // ---- this is now the NEW head
			cursor = head;
			performanceCount--;
			head.setStartTime(0);
			
			adjustStartTimes(); // ---- we always have to re-adjust the starting times after we remove a node.
			
			cursor = head;
			
			return true;
		}
		else if(cursor == tail) // ---- we are removing the last node in the list
		{
			tail = cursor.getPrev();
			cursor = tail;
			performanceCount--;
			return true;
		}
		else // ----- when removing a node in between 2 nodes, we just need to adjust a few references
		{
			cursor.getPrev().setNext(cursor.getNext());
			cursor.getNext().setPrev(cursor.getPrev());
			moveCursorForward();
			performanceCount--;
			
			adjustStartTimes(); // ---- we always have to re-adjust the starting times after we remove a node.
			
			return true;
		}
	}

	/**
	 * Moves the cursor forward one position in the list, if possible.
	 * 
	 * @return
	 * 	True if the cursor was successfully move forward.
	 * 	False if the cursor could not be moved forward
	 * 		  (i.e. if it is already at the end of the list).
	 */
	public boolean moveCursorForward()
	{
		if (cursor != tail) // ---- check if we are already at the end
		{
			cursor = cursor.getNext();
			return true;
		} 
		else
			return false;
	}
	
	/**
	 * Moves the cursor backward one position in the list, if possible.
	 * 
	 * @return
	 * 	True if the cursor was successfully move backward.
	 * 	False if the cursor could not be moved backward
	 * 		  (i.e. if it is already at the beginning of the list).
	 */
	public boolean moveCursorBackward()
	{
		if (cursor != head) // ----  check if we are already at the beginning
		{
			cursor = cursor.getPrev();
			return true;
		} 
		else
			return false;
	}
	

	/**
	 * Displays the information of the current PerformanceNode.
	 * 
	 * @throws EmptyListException
	 * 	Exception is thrown if the list is currently empty and there is nothing to display.
	 */
	public void displayCurrentPerformance() throws EmptyListException
	{
		if(cursor == null) // ---- make sure there is a node to display
			throw new EmptyListException();
		System.out.println(cursor.toString() + "\n");
	}
	
	/**
	 * Moves the cursor to a specified location in the list.
	 * 
	 * @param position
	 * 	The position that the cursor will be moved to.
	 * @return
	 * 	True if the position inputed is valid, and the cursor is successfully moved.
	 *  False if the position inputed is out of range.
	 */
	public boolean jumpToPosition(int position)
	{
		if(position < 1)
		{
	        return false;
		}
		
		if ( head == null ) // ---- make sure we have something in the list first
		{
	        return false; 
		}
		
		int count = 1;
		PerformanceNode cursorHolder = cursor;
		cursor = head;
		while(count < position)
		{
			if(cursor.getNext() == null)
			{
				cursor = cursorHolder;
				return false;
			}
			moveCursorForward();
			count++;
		}
		return true;
	}
	
	/**
	 * Returns a string that formats every element of the PerformanceList into a 
	 * neat table. The asterisk represent the node that the cursor is currently on.
	 */
	public String toString()
	{
		if(performanceCount == 0)
			return "The list is empty, no performances to show.\n";
		else
		{
		PerformanceNode cursorHolder = cursor;
		cursor = head;
		String s =  
			"Current No. Performance Name          Lead Performer Name       Participants Duration Start Time\n" +
			"------------------------------------------------------------------------------------------------\n";
		for(int i = 1; i <= performanceCount; i++)
		{
			if(cursor == cursorHolder)
				s = s + "      *   " + i + " " + cursor.toStringTableFormat() + "\n";
			else
				s = s + "          " + i + " " + cursor.toStringTableFormat() + "\n";
			moveCursorForward();
		}
	    
		cursor = cursorHolder;
		return s;
		}
	}
	
	private void adjustStartTimes()
	{
	    int runningTime = 0;
	
	    PerformanceNode currPos = head.getNext();
	
	    while( currPos != null ) // ---- iter thru this list.
	    {
		    runningTime += currPos.getPrev().getDuration();
		    currPos.setStartTime(runningTime);
		
		    currPos = currPos.getNext(); // ---- jump to the next node
	    }
	}
	
	/**
	 * Class: EmptyListException
	 * 
	 * Exception used to handle when the list is empty
	 * and an operation cannot be performed.
	 * 
	 * @author sclark
	 *
	 */
	@SuppressWarnings("serial")
	static class EmptyListException extends Exception
	{
		public EmptyListException()
		{
			super("The list is currently empty, perform another operation.\n");
		}
	}
}
