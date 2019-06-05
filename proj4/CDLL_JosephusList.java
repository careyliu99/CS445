import java.io.*;
import java.util.*;

public class CDLL_JosephusList<T>
{
	private CDLL_Node<T> head;  // pointer to the front (first) element of the list
	private int count=0;
	// private Scanner kbd = new Scanner(System.in); // FOR DEBUGGING. See executeRitual() method
	public CDLL_JosephusList()
	{
		head = null; // compiler does this anyway. just for emphasis
	}

	// LOAD LINKED LIST FORM INCOMING FILE

	public CDLL_JosephusList( String infileName ) throws Exception
	{
		BufferedReader infile = new BufferedReader( new FileReader( infileName ) );
		while ( infile.ready() )
		{	@SuppressWarnings("unchecked")
			T data = (T) infile.readLine(); // CAST CUASES WARNING (WHICH WE CONVENIENTLY SUPPRESS)
			insertAtTail( data );
		}
		infile.close();
	}



	// ########################## Y O U   W R I T E / F I L L   I N   T H E S E   M E T H O D S ########################

	// TACK ON NEW NODE AT END OF LIST
	@SuppressWarnings("unchecked")
	public void insertAtTail(T data)
	{
		CDLL_Node<T> newNode = new CDLL_Node( data,null,null);
		if (head==null)
		{
			newNode.setNext( newNode );
			newNode.setPrev( newNode );
			head = newNode;
			return;
		}
		// NOT EMPTY. INSERT NEW NODE AFTER THE LAST/TAIL NODE
		if (head!=null)
		{
			newNode.setNext(head); // diff pointers
			newNode.setPrev(head.getPrev());
			head.getPrev().setNext(newNode);
			head.setPrev(newNode);
		}

	}
	public int size()
	{
		int count = 0;
		CDLL_Node<T> curr = head;
		do
		{
			if(head==null)
			{
				return 0;
			}
		++count;
		curr = curr.getNext();
		}while(curr!=head);
		return count;
	}

	// RETURN REF TO THE FIRST NODE CONTAINING  KEY. ELSE RETURN NULL
	public CDLL_Node<T> search( T key )
	{
		CDLL_Node<T> curr = head;
		if (head == null)
		{
			return null;
		}
		do
		{
			if(curr.getData().equals(key))
			{
				return curr;
			}
			curr = curr.getNext();
		}while(curr!=head);
		return null;
	}

	// RETURNS CONATENATION OF CLOCKWISE TRAVERSAL
	@SuppressWarnings("unchecked")
	public String toString()
	{
		String toString = "";
		CDLL_Node<T> curr = head;
		if (head == null)
		{
			return toString;
		}
		do
		{
			if (curr.getNext()!=null && !curr.equals(head))
			{
				toString += "<=>";
			}

			toString +=curr.getData();
		curr = curr.getNext();
		}while(curr!=head);

		return toString;
	}

	void removeNode( CDLL_Node<T> deadNode)
	{
		CDLL_Node<T> after = deadNode.getNext();
		CDLL_Node<T> before = deadNode.getPrev();
		after.setPrev(before);
		before.setNext(after);
	}

	public void executeRitual( T first2Bdeleted, int skipCount )
	{
		if (size() < 1 ) return;
		CDLL_Node<T> curr = search( first2Bdeleted );
		if ( curr==null ) return;

		do
		{
			CDLL_Node<T> deadNode = curr;
			System.out.println("stopping on " + deadNode.getData() + " to delete " + deadNode.getData());
			if (skipCount > 0) 
			{ // clockwise
				curr = deadNode.getNext();
			} else 
			{ // counterclockwise
				curr = deadNode.getPrev();
			}

			// only if head is dead... 
			if (head == deadNode) 
			{ 
				head = curr; 
			}

			removeNode(deadNode);
			System.out.println("deleted. list now:\t" + toString());
			if (size() == 1) 
			{ 
				return; 
			}

			// now, start skipping nodes
			for (int i = 0; i < Math.abs(skipCount); i++) 
			{ // I use absolute value in case the skipCount is negative
				if (skipCount < 0) 
				{ 
					curr = curr.getPrev(); 
				} // counterclockwise
				else if (skipCount > 0) 
				{ 
					curr = curr.getNext(); 
				} // clockwise
			} // end for
		}
		while (true); // NOTE: This is always true until line 141 says there is only one person left

	}

} // END CDLL_LIST CLASS
