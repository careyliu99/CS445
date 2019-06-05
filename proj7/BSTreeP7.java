import java.io.*;
import java.util.*;

///////////////////////////////////////////////////////////////////////////////
class BSTNode<T>
{	T key;
	BSTNode<T> left,right;
	BSTNode( T key, BSTNode<T> left, BSTNode<T> right )
	{	this.key = key;
		this.left = left;
		this.right = right;
	}
}
///////////////////////////////////////////////////////////////////////////////////////
class Queue<T>
{	LinkedList<BSTNode<T>> queue;
	Queue() { queue =  new LinkedList<BSTNode<T>>(); }
	boolean empty() { return queue.size() == 0; }
	void enqueue( BSTNode<T>  node ) { queue.addLast( node ); }
	BSTNode<T>  dequeue() { return queue.removeFirst(); }
	// THROWS NO SUCH ELEMENT EXCEPTION IF Q EMPTY
}
////////////////////////////////////////////////////////////////////////////////
class BSTreeP7<T>
{
	private BSTNode<T> root;
	private boolean addAttemptWasDupe=false;
	@SuppressWarnings("unchecked")
	public BSTreeP7( String infileName ) throws Exception
	{
		//nodeCount=0;
		root=null;
		Scanner infile = new Scanner( new File( infileName ) );
		while ( infile.hasNext() )
			add( (T) infile.next() ); // THIS CAST RPODUCES THE WARNING
		infile.close();
	}

	// DUPES BOUNCE OFF & RETURN FALSE ELSE INCR COUNT & RETURN TRUE
	@SuppressWarnings("unchecked")
	public boolean add( T key )
	{	addAttemptWasDupe=false;
		root = addHelper( this.root, key );
		return !addAttemptWasDupe;
	}
	@SuppressWarnings("unchecked")
	private BSTNode<T> addHelper( BSTNode<T> root, T key )
	{
		if (root == null) return new BSTNode<T>(key,null,null);
		int comp = ((Comparable)key).compareTo( root.key );
		if ( comp == 0 )
			{ addAttemptWasDupe=true; return root; }
		else if (comp < 0)
			root.left = addHelper( root.left, key );
		else
			root.right = addHelper( root.right, key );

		return root;
    } // END addHelper
    public int countNodes() // DYNAMIC COUNT ON THE FLY TRAVERSES TREE
	{
		return countNodes( this.root );
	}
	private int countNodes( BSTNode<T> root )
	{	
		if (root == null)
		{
			return 0;
		}
		return 1 +countNodes(root.left) + countNodes(root.right);
		//return nodeCount;
	}

	// INORDER TRAVERSAL REQUIRES RECURSION
	public void printInOrder()
	{	printInOrder( this.root );
		System.out.println();
	}
	private void printInOrder( BSTNode<T> root )
	{	
		if (root == null)
		{
			return;
		}
		printInOrder(root.left);
		System.out.print(root.key + " ");
		printInOrder(root.right);
	}
	public void printLevelOrder() 
	{	
		if (root == null)
		{
			return;
		}
		Queue<T> queue = new Queue<T>();
		queue.enqueue(this.root);
		
		while(!queue.empty())
		{
			BSTNode<T> newRoot;
			newRoot = queue.dequeue();
			System.out.print(newRoot.key + " ");

			if(newRoot.left!=null)
			{
				queue.enqueue(newRoot.left);

			}
			if (newRoot.right!=null)
			{
				queue.enqueue(newRoot.right);
			}
		}
	}

	public int countLevels()
  	{
		return countLevels( root ); 
  	}
  
  	private int countLevels( BSTNode root)
  	{
  	  if (root==null) return 0;
   	 return 1 + Math.max( countLevels(root.left), countLevels(root.right) );
  	}
  
 	public int[] calcLevelCounts()
  	{
    	int levelCounts[] = new int[countLevels()];
    	calcLevelCounts( root, levelCounts, 0 );
    	return levelCounts;
  	}
 	private void calcLevelCounts( BSTNode root, int levelCounts[], int level )
 	{
    	if (root==null)return;
    	++levelCounts[level];
    	calcLevelCounts( root.left, levelCounts, level+1 );
   		calcLevelCounts( root.right, levelCounts, level+1 );
  	}


	//////////////////////////////////////////////////////////////////////////////////////
	// # # # #   WRITE THE REMOVE METHOD AND ALL HELPERS / SUPPORTING METHODS   # # # # # 

	// return true only if it finds/removes the node
	public boolean remove( T key2remove )
	{
		/*System.out.println(removeHelper(this.root, key2remove));
		return false;*/
		//return(removeHelper(this.root, key2remove));
		boolean removal = false;
		int nodeTrack = this.countNodes();
		root = removeHelper(root, key2remove);
		int nodeTrackAfterHelper = this.countNodes();

		if ((nodeTrack != nodeTrackAfterHelper))
		{
			removal = true;
		}
		return removal;
	}
	private BSTNode<T> removeHelper(BSTNode<T> root, T key2remove) // T deadkey orig. (boolean type)
	{
		/*BSTNode<T> deadsParent = findDeadsParent( root, deadKey );
		if (deadsParent == null)
		{
			return false;
		}
		BSTNode<T> deadNode = findDeadsParent(root, deadKey);

		if (deadsParent.left != null && deadsParent.left.key.equals(deadKey)) // left child node to remove
		{
			deadNode = deadsParent.left;
		}
		if (deadsParent.right != null && deadsParent.right.key.equals(deadKey)) // right child node to remove
		{
			deadNode = deadsParent.right;
		}

		if (deadNode.left == null && deadNode.right == null) // 0 kids
		{
			removeHelper(root,deadKey); // do the remove
		}
		else if (deadNode.left !=null || deadNode.right != null) // 1 children
		{
			if (deadNode.left == null)
			{
				if(deadsParent.left == deadNode)
				{
					deadsParent.left = deadNode.right;
				}
				else 
				{
					deadsParent.right = deadNode.left;
				}
			}
			else if (deadNode.right == null)
			{
				if (deadsParent.right == deadNode)
				{
					deadsParent.right = deadNode.left;
				}
				else 
				{
					deadsParent.left = deadNode.right;
				}
			}
		}

		// 2 children method
		if (deadNode.left != null && deadNode.right != null)
		{
			T key = getPredsKey(deadNode.left);
			removeHelper(this.root, key);
			deadNode.key = key;
		}
		return true;*/
		if (root == null) // base base (empty tree)
		{
			return root;
		}
		int comp = ((Comparable)key2remove).compareTo(root.key);
		if (comp < 0)
			root.left = removeHelper(root.left, key2remove);
		else if (comp>0)
			root.right = removeHelper(root.right, key2remove);
		else
		{
			if (root.left == null)
			{
				return root.right;
			}
			else if (root.right == null)
			{
				return root.left;
			}
			root.key = keyHelper(root.left);
			root.left = removeHelper(root.left, root.key);
		}
		return root;

	}
	public T keyHelper(BSTNode<T> root)
	{
		T max = root.key;
	
		if (root.right!=null)
		{
			max = root.right.key;
			root = root.right;
			//return keyHelper(root.right);
		}
		else
		{
			return root.key;
		}
		return max;
	}
	/*BSTNode<T> findDeadsParent(BSTNode<T> root, T deadKey)
	{

		if (root == null || (root.left == null && root.right == null))
		{
			return null; // base case
		}

		if (root.left != null && root.left.key.equals(deadKey))
		{
			return root;
		}
		if (root.right != null && root.right.key.equals(deadKey))
		{
			return root;
		}

		int comp = ((Comparable)deadKey).compareTo( root.key );
		if (comp < 0)

			return findDeadsParent(root.left, deadKey);
		else if (comp > 0)
			return findDeadsParent(root.right, deadKey);
		else
		{
			return null;
		}
	}
	T getPredsKey(BSTNode<T> deadNode)
	{
		if (deadNode.right != null)
		{
			return getPredsKey(deadNode.right); // keeps it going right
		}
		else
		{
			return deadNode.key;
		}
	}*/
  
} // END BSTREEP7 CLASS
