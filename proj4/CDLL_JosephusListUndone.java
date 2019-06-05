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
	
	void removeNode( CDLL_Node<T> deadNode, int skipCount )
	{
		CDLL_Node<T> after = deadNode.getNext();
		CDLL_Node<T> before = deadNode.getPrev();

		// removeNode is fine now - this is all it has to do - I put the other code in the executeRitual method for now
	}
	
	public void executeRitual( T first2Bdeleted, int skipCount )
	{
		if (size() < 1 ) return;
		CDLL_Node<T> curr = search( first2Bdeleted );
		if ( curr==null ) return;
		
		// OK THERE ARE AT LEAST 2 NODES AND CURR IS SITING ON first2Bdeleted
		do
		{
			CDLL_Node<T> deadNode = curr;
			System.out.println("stopping on " + deadNode.getData() + " to delete " + deadNode.getData());
			T deadName = deadNode.getData();

			// Hoffman said that the code below is not needed in the removeNode method
			if (deadNode == head) { // SPECIAL CASE - you MUST reassign the head after it is disconnected from the list (deleted)
			if (skipCount > 0) {
				head = after;
			} else {
				head = before;
			}
			}
			after.setPrev(before);
			before.setNext(after);
			

			// remove this node form the list
			removeNode(deadNode, skipCount);
			curr = head;	
		
			System.out.println("deleted. list now:   " + toString());
			// if the list size has reached 1 break out of this loop YOU ARE DONE 
			if (size() == 1)
			{
				return;
			}

			// direction
			String direction = "";
			if (skipCount>0)
			{
				direction = "CLOCKWISE after";
			}
			if (skipCount<0)
			{
				direction = "COUNTER_CLOCKWISE after";
			}
			
			// othewise make curr point to same thing you just made head point to so you can resume the ritual
			
			// ** println("resuming at Mosse, skipping Mosse + 4 nodes CLOCKWISE after");
			System.out.println("resuming at " + curr + "," + " skipping " + curr + " + 2 nodes " + direction);
			
			// write loop that advance curr skipCount times (be sure of CLOCKWISE or COUNTER)
			if (skipCount>0) // clockwise case
			{
				for (int i=0; i<skipCount; i++)
				{
					curr = curr.getNext();
				}
			}
		
			if (skipCount<0) // counterclockwise case 
			{
				for (int i=0; i>skipCount; i--)
				{
					curr = curr.getNext();
				}
			}

			// String junk = kbd.nextLine();  <= MIGHT FIND THis HELPFUL. FOR DEBUGGING. WAITS FOR YOU TO HIT RETUN KEY
			
		}
		while (size() > 1 );

	}
	
} // END CDLL_LIST CLASS