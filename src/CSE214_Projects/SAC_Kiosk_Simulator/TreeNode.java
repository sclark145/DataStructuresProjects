package CSE214_Projects.SAC_Kiosk_Simulator;


/**
 * Sean Clark
 * 109653264
 * Homework #5
 * Tuesday: R03
 * Recitation TA: Sun Lin
 * Grading TA: Ke Ma
 * @author sclark
 */


public class TreeNode
{
	private String name; // "1-1-1" etc.
	private String selection; // "fresh burger"
	private String message; // "What would you like on that?"
	private TreeNode left;
	private TreeNode middle;
	private TreeNode right;
	
	/**
	 * Constructor for a new TreeNode
	 * @param name the name of this node
	 * @param selection the selection of this node
	 * @param message the message of this node
	 */
	public TreeNode(String name, String selection, String message)
	{
		this.name = name;
		this.selection = selection;
		this.message = message;
		
		left = null;
		middle = null;
		right = null;
	}

	/**
	 * Check to see if this node is a leaf
	 * @return true if the node is a leaf. false if otherwise
	 */
	public boolean isLeaf()
	{
		return ( left==null && middle==null && right==null );
	}

	/**
	 * @return the name
	 */
	public String getName()
	{
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name)
	{
		this.name = name;
	}

	/**
	 * @return the selection
	 */
	public String getSelection()
	{
		return selection;
	}

	/**
	 * @param selection the selection to set
	 */
	public void setSelection(String selection)
	{
		this.selection = selection;
	}

	/**
	 * @return the message
	 */
	public String getMessage()
	{
		return message;
	}

	/**
	 * @param message the message to set
	 */
	public void setMessage(String message)
	{
		this.message = message;
	}

	/**
	 * @return the left
	 */
	public TreeNode getLeft()
	{
		return left;
	}

	/**
	 * @param left the left to set
	 */
	public void setLeft(TreeNode left)
	{
		this.left = left;
	}

	/**
	 * @return the middle
	 */
	public TreeNode getMiddle()
	{
		return middle;
	}

	/**
	 * @param middle the middle to set
	 */
	public void setMiddle(TreeNode middle)
	{
		this.middle = middle;
	}

	/**
	 * @return the right
	 */
	public TreeNode getRight()
	{
		return right;
	}

	/**
	 * @param right the right to set
	 */
	public void setRight(TreeNode right)
	{
		this.right = right;
	}
}
