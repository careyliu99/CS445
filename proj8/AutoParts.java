import java.util.*;
import java.io.*;

public class AutoParts
{
	public static void main( String[] args ) throws Exception
	{
		BufferedReader num2nameFile = new BufferedReader( new FileReader( "num2name.txt" ) );
		BufferedReader num2quantFile = new BufferedReader( new FileReader( "num2quant.txt" ) );

		HashMap<Integer,String> numPart = new HashMap<Integer,String>();
		HashMap<Integer,String> quantPart = new HashMap<Integer,String>();
		
		System.out.println("PART NUMBER TO PART NAME\n");
		while(num2nameFile.ready())
		{
			String[] tokens = num2nameFile.readLine().split("\\s+");
			int partNum = Integer.parseInt(tokens[0]);

			String partName = tokens[1];
			numPart.put(partNum, partName);
		}
		for (int curr: numPart.keySet())
		{
			System.out.print(curr + "\t" + numPart.get(curr));
			System.out.println();
		}
		while(num2quantFile.ready())
		{
			String[] tokens = num2quantFile.readLine().split("\\s");
			int partNum = Integer.parseInt(tokens[0]);
			String quant = tokens[1];
			quantPart.put(partNum, quant);
		}
		
		System.out.println("\nJOIN OF PART NUMBER TO NAME TO QUANTITY\n");

		for (int curr: numPart.keySet())
		{
			System.out.println(curr + "\t" + numPart.get(curr) + "\t" + quantPart.get(curr));
		}
	}
}