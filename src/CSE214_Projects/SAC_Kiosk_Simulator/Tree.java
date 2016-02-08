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

public class Tree
{
	private TreeNode root;

	public Tree()
	{
		root = null;
	}

	/**
	 * Method that tries to find a node with the given name.
	 * @param name the name we are looking for
	 * @return the node if it is found, null otherwise
	 */
	public TreeNode findNode(String name) // – returns a reference to node if it exists in the tree, null otherwise
	{	
		return search(name, root);
	}

	/**
	 * Method that prints the menu of the tree (recursive)
	 * @param parentInfo information of the parent(s)
	 * @param node the next node to be traversed
	 * @param i counter used for formatting
	 */
	public void printMenu(String parentInfo, TreeNode node, int i)
	{
		if(node != null)
		{
			if(node.isLeaf())
			{
				System.out.printf("%-100s%s\n", parentInfo + node.getSelection(), node.getMessage() );
				return;
			}
			if(i < 2) // root, dont wanna print that info in the table
			{
				printMenu("", node.getLeft(), i+1);
				printMenu("", node.getMiddle(), i+1);
				printMenu("", node.getRight(), i+1);
			}
			if(i == 2) // dining selection, must be formatted a bit differently to fit the table
			{
				printMenu(String.format("%-25s", node.getSelection()), node.getLeft(), i+1);
				printMenu(String.format("%-25s", node.getSelection()), node.getMiddle(), i+1);
				printMenu(String.format("%-25s", node.getSelection()), node.getRight(), i+1);
			}
			if(i > 2) // the selection, append to the string normally.
			{
				printMenu(parentInfo + node.getSelection() + ", ", node.getLeft(), i+1);
				printMenu(parentInfo + node.getSelection() + ", ", node.getMiddle(), i+1);
				printMenu(parentInfo + node.getSelection() + ", ", node.getRight(), i+1);
			}
		}
	}

	/**
	 * Method that prints the menu contained in the tree.
	 */
	public void printMenu()
	{
		if(root == null)
		{
			System.out.println("Tree not initalized!\n");
			return;
		}
		
		System.out.println("Menu:");
		System.out.printf("%-25s%-75s%s\n", "Dining", "Selection", "Price");
		System.out.println("-----------------------------------------------------"+
				"------------------------------------------------------");
		printMenu("", root, 1);
		System.out.println();
	}
	
	/**
	 * Method that searches for a node with a certain node
	 * @param name the name we are looking for
	 * @param node the current node we are checking
	 * @return the TreeNode with the name we are looking for, null if it cant be found
	 */
	private TreeNode search(String name, TreeNode node){
		if(node != null)
		{
			if(node.getName().equals(name))
			{
				return node;
			}
			else 
			{
				TreeNode foundNode = search(name, node.getLeft());
				if(foundNode == null)
					foundNode = search(name, node.getMiddle());
				if(foundNode == null)
					foundNode = search(name, node.getRight());

				return foundNode;
			}
		}
		else
		{
			return null;
		}
	}

	/**
	 * Method that adds a node to the tree below a given parent name
	 * @param name name of the node that will be added
	 * @param selection selection of the node that will be added
	 * @param message message of the node that will be added
	 * @param parentName the name of the parent that the node will be linked to
	 * @return true if the node was successfully added, false otherwise.
	 */
	public boolean addNode(String name, String selection, String message, String parentName)
	{
		if(root == null)
		{
			root = new TreeNode(name, selection, message);
			return true;
		}

		TreeNode parent = findNode(parentName);
		if(parent != null)
		{
			if(parent.getLeft() == null)
			{
				parent.setLeft(new TreeNode(name, selection, message));
				return true;
			}
			if(parent.getMiddle() == null)
			{
				parent.setMiddle(new TreeNode(name, selection, message));
				return true;
			}
			if(parent.getRight() == null)
			{
				parent.setRight(new TreeNode(name, selection, message));
				return true;
			}
		}
		return false;
	}

	/**
	 * @return the root
	 */
	public TreeNode getRoot()
	{
		return root;
	}

	/**
	 * @param root the root to set
	 */
	public void setRoot(TreeNode root)
	{
		this.root = root;
	}

}
