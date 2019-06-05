/* This class was borrowed and modified as needed with permission from it's original author
   Mark Stelhik ( http:///www.cs.cmu.edu/~mjs ).  You can find Mark's original presentation of
   this material in the links to his S-01 15111,  and F-01 15113 courses on his home page.
*/

import java.io.*;
import java.util.*;


public class Graph 
{
	public class Edge
	{
		int dest, weight;
		Edge next;
		// a Constructor that takes dest,weight, next
		Edge (int dest, int weight, Edge next) // Constructor
		{
			this.dest = dest;
			this.weight = weight;
			this.next = next; 
		}
		public String toString()
		{
			return "[" + dest + "," + weight + "]";
		}
	}



	private final int NO_EDGE = -1; 
	private Edge G[];              

	private int numEdges;
	public Graph( String graphFileName ) throws Exception  
	{
		loadGraphFile( graphFileName );
	}

	///////////////////////////////////// LOAD GRAPH FILE //////////////////////////////////////////
	//
	// FIRST NUMBER IN GRAPH FILE IS THE SQUARE DIMENSION OF OUR 2D ARRAY
	// THE REST OF THE LINES EACH CONTAIN A TRIPLET <ROW,COL,WEIGHT> REPRESENTING AN EDGE IN THE GRAPH

	private void loadGraphFile( String graphFileName ) throws Exception
	{
		Scanner graphFile = new Scanner( new File( graphFileName ) );

		int dimension = graphFile.nextInt();   	// THE OF OUR N x N GRAPH
		G = new Edge[dimension]; 		// N x N ARRAY OF ZEROS
		numEdges=0;

		while ( graphFile.hasNextInt() )
		{
			// read in the row,col,weight // that eat us this line
			// call add edge
			addEdge(graphFile.nextInt(), graphFile.nextInt(), graphFile.nextInt()); // row, col, weight
		}

	} // END

	private void addEdge( int i, int d, int w )
	{
		insertAtFront(i,d,w);
		++numEdges; 
	}
	
  private boolean hasEdge(int fromNode, int toNode)
  {
   for (int i = 0; i<G.length; i++)
   {
   	for (Edge curr = G[i]; curr!=null; curr = curr.next)
   	{
   		if (fromNode == i && toNode == curr.dest)
   		{
   			return true;
   		}
   	}
   } // end for
   return false;
   
  }

	// IN DEGREE IS NUMBER OF ROADS INTO THIS CITY
	// NODE IS THE ROW COL#. IN DEGREE IS HOW MANY POSITIVE NUMBERS IN THAT COL
	private int inDegree(int node)
	{
		int count = 0;
		for (int j =0; j < G.length; j++)
		{
			/*if (G[j][node] > 0) // exists
			{
				count++; 
			}*/
			for (Edge curr = G[j]; curr != null; curr = curr.next)
			{
				if (curr.dest == node)
				{
					// update the count
					count++;
				}
			}
		}
		return count;
	}

	// OUT DEGREE IS NUMBER OF ROADS OUT OF THIS CITY
	// NODE IS THE ROW #. IN DEGREE IS HOW MANY POSITIVE NUMBERS IN THAT ROW
	private int outDegree(int node)
	{
		int count = 0;
		for (Edge curr = G[node]; curr != null; curr = curr.next)
		{
			count++;
		}
		return count;	
	}

	// DEGREE IS TOTAL NUMBER OF ROAD BOTH IN AND OUT OFR THE CITY 
	private int degree(int node)
	{
		return inDegree(node) + outDegree(node);
	}

	// PUBLIC METHODS 
	
	public int maxOutDegree() // ?
	{
		int max = outDegree(0);

		for (int i = 1; i < G.length; i++)
		{
			if (outDegree(i) > max)
			{
				max = outDegree(i);
			}
		}
		return max;
	}

	public int maxInDegree()
	{
		int max = inDegree(0);

		for (int i = 1; i < G.length; i++)
		{
			if (inDegree(i) > max)
			{
				max = outDegree(i);
			}
		}
		return max;
	}

	public int minOutDegree()
	{
		int min = outDegree(0);
		for (int i = 1; i < G.length; i++)
		{
			if (outDegree(i) < min)
			{
				min = outDegree(i); // new min
			}
		}
		return min;
	}
	public int minInDegree()
	{
		int min = inDegree(0);
		for (int i = 1; i < G.length; i++)
		{
			if (inDegree(i) < min)
			{
				min = inDegree(i); // new min
			}
		}
		return min;
	}	
	
	public int maxDegree()
	{
		int max = degree(0);
		for (int i = 1; i < G.length; i++)
		{
			if (degree(i) > max)
			{
				max = degree(i);
			}
		}
		return max;
	}

	public int minDegree()
	{
		int min = degree(0);
		for (int i = 0; i < G.length; i++)
		{
			if (degree(i) < min)
			{
				min = degree(i);
			}
		}
		return min;
	}
	
	public void removeEdge(int fromNode, int toNode)
	{
	
		try
		{
			if (!hasEdge(fromNode, toNode))
			{
				throw new nonExistentEdgeException();
			}
			else
			{
				//G[fromNode][toNode] = NO_EDGE;
				remove(fromNode, toNode);
			}
		}
		catch(nonExistentEdgeException e)
		{
			System.out.format("removeEdge(%d, %d)", fromNode, toNode);
		}
	}
	
	// TOSTRING
	public String toString()
	{	
		for (int r=0 ; r < G.length ;++r )
		{
			System.out.print(r + ":");
			for (Edge curr = G[r]; curr !=null; curr = curr.next)
			{
				System.out.print(" -> ");
				System.out.print(curr);
			}
			System.out.println();
		}
		return "";
	} // END TOSTRING


	// Edge 
	public int search(int fromNode, int toNode )
	{
		for(int i = 0; i < G.length; i++)
		{
			for(Edge curr = G[i]; curr != null; curr = curr.next)
			{
				if(fromNode == i && toNode == curr.dest)
					return i;
			}
		}
		return -1;
	}
	public void insertAtFront(int index, int dest, int weight)
	{
		G[index] = new Edge(dest, weight, G[index]);
	}

	public boolean removeAtFront(int index) 
	{
		
		if(null == G[index])
		{
			return false;
		}
		G[index] = G[index].next;
		/*else
		{
			return true;
		}
		if (!null == G[index])
		{
			return true;
		}*/
		
		return true; 
		
	}

	public boolean remove(int fromNode, int toNode) 
	{
		int index = search(fromNode, toNode);
		
		
		if(G[index] != null && index == fromNode && G[index].dest == toNode)
		{
			removeAtFront(index);
			return true;
		}
		
		Edge test = null;
		Edge curr = G[index];
		
		while(curr != null)
		{
			
			if(index == fromNode && curr.dest == toNode && curr.next == null) // last in
			{ 
				removeAtTail(index); // remove last item
				return true;
			}
			
			if(index == fromNode && curr.dest == toNode)
			{

				test.next = curr.next;
				return true;
				
			}
			test = curr;
			curr = curr.next; //adv pointers
		}
		return false;
		
	}

	public boolean removeAtTail(int index)	
	{
		if(null == G[index])
		{
			return false;
		}
		
		Edge test = null;
		Edge curr = G[index];
		while(curr.next != null)
		{
			test = curr;
			curr = curr.next;
		}
		if(test == null)
		{
			G[index] = null;
			return true;
		}
		else
		{
			test.next = null;
			return true;
		}
		 
	}
	class nonExistentEdgeException extends Exception
	{
		public nonExistentEdgeException()
		{
			System.out.print("java.lang.Exception: ");
			System.out.print("Non Existent Edge Exception: ");
		}
	}
} // EOF
	



