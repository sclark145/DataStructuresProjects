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
 * Class: PerformanceNode
 * 
 * Node used in PerformanceList.
 * 
 */
public class PerformanceNode
{
	private String name;
	private String performer;
	private int participants;
	private int duration;
	private int startTime;
	
	private PerformanceNode prev;
	private PerformanceNode next;
	
	//---Begin getters and setters.
	
	/**
	 * Gets the name of the performance.
	 * 
	 * @return
	 * 	Returns name of performance.
	 */
	public String getName()
	{
		return name;
	}
	
	/**
	 * Sets the name of the performance.
	 * 
	 * @param name
	 * 	Name that will be set to the name of the performance.
	 */
	public void setName(String name)
	{
		this.name = name;
	}
	
	/**
	 * Gets the name of the performer.
	 * 
	 * @return
	 * 	Returns name of performer.
	 */
	public String getPerformer()
	{
		return performer;
	}
	
	/**
	 * Sets the name of the performer.
	 * 
	 * @param performer
	 * 	Name that will be set to the name of the performer.
	 */
	public void setPerformer(String performer)
	{
		this.performer = performer;
	}
	
	/**
	 * Gets the number of the participants.
	 * 
	 * @return
	 * 	Returns number of participants.
	 */
	public int getParticipants()
	{
		return participants;
	}
	
	/**
	 * Sets the number of participants.
	 * 
	 * @param participants
	 * 	Number that will be set to the number of participants.
	 */
	public void setParticipants(int participants)
	{
		this.participants = participants;
	}
	
	/**
	 * Gets the duration of the performance.
	 * 
	 * @return
	 * 	Returns the duration of the performance.
	 */
	public int getDuration()
	{
		return duration;
	}
	
	/**
	 * Sets the duration of the performance.
	 * 
	 * @param duration
	 * 	Length that will be set to the duration of the performance.
	 */
	public void setDuration(int duration)
	{
		this.duration = duration;
	}
	
	/**
	 * Gets the start time of the performance.
	 * 
	 * @return
	 * 	Returns the start time of the performance.
	 */
	public int getStartTime()
	{
		return startTime;
	}
	
	/**
	 * Sets the start time of the performance.
	 * 
	 * @param startTime
	 * 	Time that will be set to the start time of the performance.
	 */
	public void setStartTime(int startTime)
	{
		this.startTime = startTime;
	}
	
	/**
	 * Sets the node that will be linked to the next position.
	 * 
	 * @param next
	 * 	Node that will be linked to the next position.
	 */
	public void setNext(PerformanceNode next)
	{
		this.next = next;
	}
	
	/**
	 * Gets the node that is linked to be the previous position.
	 * 
	 * @return
	 * 	Returns the node in the previous position.
	 */
	public PerformanceNode getPrev()
	{
		return prev;
	}
	
	/**
	 * Sets the node that will be linked to the previous position.
	 * 
	 * @param prev
	 * 	Node that will be linked to the previous position.
	 */
	public void setPrev(PerformanceNode prev)
	{
		this.prev = prev;
	}
	
	/**
	 * Gets the node that is linked to be the next position.
	 * 
	 * @return
	 * 	Returns the node in the next position.
	 */
	public PerformanceNode getNext()
	{
		return next;
	}
	
	//---End getters and setters.
	
	/**
	 * Null constructor that creates an empty PerformanceNode
	 */
	public PerformanceNode()
	{
		name = null;
		performer = null;
		participants = 0;
		duration = 0;
		startTime = 0;
		next = null;
		prev = null;
	}
	
	/**
	 * Creates a new Performance node based on the given parameters.
	 * 
	 * @param name
	 * 	Name that will be set to the name of the performance.
	 * @param performer
	 * 	Name that will be set to the name of the performer.
	 * @param participants
	 * 	Number that will be set to the number of participants.
	 * @param duration
	 * 	Length that will be set to the duration of the performance.
	 * @param startTime
	 *  Time that will be set to the starting time of the performance.
	 */
	public PerformanceNode(String name, String performer, int participants,
			int duration, int startTime)
	{
		this.name = name;
		this.performer = performer;
		this.participants = participants;
		this.duration = duration;
		this.startTime = startTime;
		next = null;
		prev = null;
	}
	
	/**
	 * Returns a string representation of this PerformanceNode.
	 */
	public String toString()
	{
		return String.format("%s, %s. %d participant(s), %d minutes long, starts at %d.",
				name, performer, participants, duration, startTime);
	}
	
	/**
	 * Returns a string representation of this PerformanceNode
	 * that will fit my table properly.
	 */		
	public String toStringTableFormat()
	{
		return String.format("%-26s%-26s%-13d%-9d%d",
				name, performer, participants, duration, startTime);
	}
}
