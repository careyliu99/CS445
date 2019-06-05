import java.util.*;
import java.io.*;

public class Potus
{
	public static void main( String[] args )  throws Exception
	{
		BufferedReader state2PresidentsFile = new BufferedReader( new FileReader("state2presidents.txt") );
		BufferedReader allPresidentsFile = new BufferedReader( new FileReader("allPresidents.txt") );
		BufferedReader allStatesFile = new BufferedReader( new FileReader("allStates.txt") );
		
		System.out.println( "\nSTATE TO PRESIDENTS BORN IN THAT STATE\n");


		// ------------------ CODE I ADDED FOR YOU TO USE AND IMITATE -------------------------		
		TreeMap<String,TreeSet<String>> state2Presidents = new TreeMap<String,TreeSet<String>>();
		TreeSet<String> allStates = new TreeSet<String>();
		TreeSet<String> allPresidents = new TreeSet<String>();
		TreeMap<String, String> president2State = new TreeMap<String, String>();
		
		// LOOK AT HOW MUCH WORK WE SAVE BY SPLITTING LINE INTO ARRAY INTO ARRAYLIST
		// WE DONT HAVE TO WRITE LOOP TO ADD PRESIDENTS INTO THE TREESET 
		// 
		while (state2PresidentsFile.ready())
		{	// WE TRIM THE LINE REMOVING LEAD & TRAIL WHITESPACE (WHICH WOULD MESSE UP THE SPLIT)
			ArrayList<String> presidents = new ArrayList<String>( Arrays.asList( state2PresidentsFile.readLine().trim().split(" ")));
			String state = presidents.get(0); 
			presidents.remove(0); // the first element was state - not president
			state2Presidents.put( state, new TreeSet<String>(presidents) ); // YOU CAN FEED AN ARRAYLIST TO A TRESSET CONSTRUCTOR

			//president2State.put( presidents, new TreeSet<String> (state) );

		}

		// MAP IS BUILT. DUMP IT 
		System.out.println( "\nSTATE TO PRESIDENTS BORN IN THAT STATE\n");
		for ( String state : state2Presidents.keySet() )
		{
			System.out.print( state + " ");
			for ( String pres : state2Presidents.get( state ) )
				System.out.print( pres + " ");
			System.out.println();
		}


		System.out.println( "\nPRESIDENT TO STATE BORN IN\n"); // wrong not printing anything
		for (String president: president2State.keySet())
		{
			String state = president2State.get(president);
			System.out.println(president + " " + state);
			//System.out.println("test");
			System.out.print(president + " ");
			/*for (String state: president2State.get(state))
			{
				System.out.print(state + " ");
				System.out.println();
			}*/
		}
		//System.out.println();

		System.out.println( "\nPRESIDENTS BORN BEFORE STATES FORMED\n"); // wrong
		while(allPresidentsFile.ready())
		{
			String key = allPresidentsFile.readLine();
			allPresidents.add(key);
		}
		for (String key: president2State.keySet())
		{
			if(allPresidents.contains(key))
			{
				allPresidents.remove(key);
			}
		}
		for (String president: allPresidents)
		{
			System.out.println(president);
		}

		System.out.println( "\nSTATES HAVING NO PRESIDENT BORN IN THEM\n");

		while(allStatesFile.ready())
		{
			String key = allStatesFile.readLine();
			allStates.add(key); // all possible states added to corres. TreeSet
		}
		for (String key: state2Presidents.keySet())
		{
			if (allStates.contains(key))
			{
				allStates.remove(key);
			}
		}
		for (String state: allStates)
		{
			System.out.println(state);
		}

	} // END MAIN

}	// END POTUS CLASS