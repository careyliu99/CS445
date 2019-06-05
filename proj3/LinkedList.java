import java.io.*;
import java.util.*;

public class LinkedList<T>
{
	private Node<T> head;  // pointer to the front (first) element of the list

	public LinkedList()
	{
		head = null; // compiler does this anyway. just for emphasis
	}

	// LOAD LINKED LIST FORM INCOMING FILE
	public LinkedList( String fileName, boolean orderedFlag )
	{
		head=null;
		try
		{
			BufferedReader infile = new BufferedReader( new FileReader( fileName ) );
			while ( infile.ready() )
			{
				if (orderedFlag)
					insertInOrder( (T)infile.readLine() );  // WILL INSERT EACH ELEM INTO IT'S SORTED POSITION
				else
					insertAtTail( (T)infile.readLine() );  // TACK EVERY NEWELEM ONTO END OF LIST. ORIGINAL ORDER PRESERVED
			}
			infile.close();
		}
		catch( Exception e )
		{
			System.out.println( "FATAL ERROR CAUGHT IN C'TOR: " + e );
			System.exit(0);
		}
	}

	//-------------------------------------------------------------

	// inserts new elem at front of list - pushing old elements back one place

	public void insertAtFront(T data)
	{
		head = new Node<T>(data,head);
	}

	// we use toString as our print

	public String toString()
	{
		String toString = "";

		for (Node curr = head; curr != null; curr = curr.getNext())
		{
			toString += curr.getData();		// WE ASSUME OUR T TYPE HAS toString() DEFINED
			if (curr.getNext() != null)
				toString += " ";
		}

		return toString;
	}

	// ########################## Y O U   W R I T E    T H E S E    M E T H O D S ########################


	public int size() // OF COURSE MORE EFFICIENT TO MAINTAIN COUNTER. BUT YOU WRITE LOOP!
	{
		int count = 0;
		Node<T> curr = head;
		while(curr!=null)
		{
			++count;
			curr = curr.getNext();
		}
		return count; // instead of printing, you return 
	}
	public boolean empty()
	{
		return size() == 0; 
	}
	public boolean contains( T key )
	{
		return search(key)!= null; // if it's not null, then it contains
	}
	public Node<T> search( T key )
	{
		Node<T> curr = head;
		while(curr!=null)
		{
			if(curr.getData().equals(key))
			{
				return curr;
			}
			curr = curr.getNext();
		}
		return null;
	}
	public void insertAtTail(T data) // TACK A NEW NODE (CABOOSE) ONTO THE END OF THE LIST
	{
		if (head==null) // base case
		{
			insertAtFront(data);
			return;
		}
		// traversal
		Node<T> curr = head;
		while (curr.getNext() != null) // curr.getNext is used as a tester here
		{
			curr = curr.getNext();
		}
		curr.setNext(new Node(data, null)); // creating new node and setting its reference into the last node's next
	}
	public void insertInOrder(T  data) // PLACE NODE IN LIST AT ITS SORTED ORDER POSTIOPN
	{
		Comparable dataC = (Comparable) data; // dataC reference to comparable 
		/*if (contains(data))
		{
			return;
		}*/

		if (head==null || dataC.compareTo(head.getData()) <= 0) 
		{
			insertAtFront(data); // must put head instead of null
			return;
		} // end of base case
		Node <T> curr = head;
		Node <T> next = head.getNext();
		while(next!=null)
		{
			if (dataC.compareTo(curr.getData()) >= 0 && dataC.compareTo(next.getData()) < 0) // insert in middle
			{
				Node<T> newNode = new Node<T>(data, null);
				curr.setNext(newNode);
				newNode.setNext(next);
				return;
			}
			else 
			{
				curr = curr.getNext();
				next = next.getNext(); // moving pointers
			}
		}
		insertAtTail(data); // third case
	}
	public boolean remove(T key) // FIND/REMOVE 1st OCCURANCE OF A NODE CONTAINING KEY
	{
		if (head == null)
		{
			return false;
		}

		if (head!=null && head.getData().equals(key)) 
		{
			removeAtFront();
			return true;
		}
		Node<T> curr = head.getNext();
		Node<T> prev = head;
		while(curr != null)
		{
			if(curr.getData().equals(key))
			{
				// doing removal here 
				prev.setNext(curr.getNext()); // node behind is set to node in front of it
				return true;
			}
			prev = curr; // sets current node to previous node
			curr = curr.getNext();
		}
		return false;
	}
	public boolean removeAtTail()	// RETURNS TRUE IF THERE WAS NODE TO REMOVE
	{
		Node<T> curr = head;
		Node<T> prev = null;
		if (head == null)
		{
			return false;
		}
		while (curr.getNext() != null)
		{
			prev = curr;
			curr = curr.getNext();
		}
			
		if (prev == null)
		{
			head = null;
			return true; // removed head
		}
		else
		{
			prev.setNext(null);
			return true;
		}
	}
	public boolean removeAtFront() // RETURNS TRUE IF THERE WAS NODE TO REMOVE
	{
		Node<T> curr = head;
		if (head == null)
		{
			return false;
		}
		else
		{
			head = head.getNext(); // 'A' removed, now point to 'B'
			return true;
		}
	}
	public LinkedList<T> union( LinkedList<T> other ) // everything in 'this' and 'other', no dupes, in-order
	{
		LinkedList<T>  testList = new LinkedList<T>();
		Node<T> thisNode = this.head;
		Node<T> otherNode = other.head;

		while (thisNode != null)
		{
			if (!testList.contains(thisNode.getData()))
			{
				testList.insertInOrder(thisNode.getData());
			}
			thisNode = thisNode.getNext(); // advance pointer
		}

		while (otherNode != null)
		{
			if (!testList.contains(otherNode.getData()))
			{
				testList.insertInOrder(otherNode.getData());
			}
			otherNode = otherNode.getNext();
		}
		return  testList; // CHANGE TO YOUR CODE
	}
	public LinkedList<T> inter( LinkedList<T> other ) // everything only in both
	{
		LinkedList<T> interList = new LinkedList<T>();
		Node<T> thisNode = this.head;
		Node<T> otherNode = other.head;
		
		while(otherNode !=null) 
		{
			if (this.contains(otherNode.getData()) && !interList.contains(otherNode.getData())) 
			{
				interList.insertInOrder(otherNode.getData());
			}
			otherNode = otherNode.getNext();
		}
		return  interList; // CHANGE TO YOUR CODE
	}
	public LinkedList<T> diff( LinkedList<T> other ) // this - other 
	{
		LinkedList<T> diffList = new LinkedList<T>();
		Node<T> thisNode = this.head;
		Node<T> otherNode = other.head;
		while(thisNode != null)
		{
			if(!other.contains(thisNode.getData()) && !diffList.contains(thisNode.getData())) // second part checks for dupes
			{
				diffList.insertInOrder(thisNode.getData());
			}
			thisNode = thisNode.getNext(); // must advance this since that's what you're using to compare
		}
		return  diffList; // CHANGE TO YOUR CODE
	}
	public LinkedList<T> xor( LinkedList<T> other ) // in this or other but not both
	{
		return this.union(other).diff(this.inter(other));
	}

} //END LINKEDLIST CLASS

