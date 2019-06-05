import java.io.*;
import java.util.*;

public class Project2
{
	public static void main( String args[] )
	{
		Scanner kbd = new Scanner(System.in);
		int number = 0;
		boolean success = false;
		do
		{
			System.out.print("Enter int in range 1..100 inclusive: ");
			try
			{
				number = kbd.nextInt();
				if (number>100 || number <1 ) // if number out of range
 				{
					throw new NumberOutOfRangeException();
				}
			}
			catch (InputMismatchException e)
			{
				System.out.println("Input was not an integer ");
				kbd.nextLine(); // clear keyboard w/ buffer
			}
			catch (NumberOutOfRangeException e)
			{

			}
			catch (Exception e) // catches EVERYTHING else
			{
				System.out.println(e);
				System.exit(0);
			}
		} while(number>100 || number <1);
		
		System.out.format("Thank you. You entered %d\n", number );
		
	} //END main
	static class NumberOutOfRangeException extends Exception // allows you to use w/out an object -- all share 1 NOR excep.
	{
		public NumberOutOfRangeException()
		{
			System.out.println("Number out of range. Must be in 1..100 ");
		}
	}
} //END CLSS