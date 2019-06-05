
import java.io.*;
import java.util.*;

public class Boggle 
{
	static TreeSet<String> dictionary = new TreeSet<String>();
	static TreeSet<String> matches = new TreeSet<String>();
	
	public static void main(String args[]) throws Exception
	{
		String infileName = args[1];
		Scanner infile = new Scanner(new File(infileName));
		//int size = infile.nextInt();
		int count = 0;
		int row = infile.nextInt();
		int col = row;
		
		String[][] board = new String[row][col];

		for(int r=0; r<row; r++)
		{
			for(int c=0; c<col; c++)
			{
				board[r][c] = infile.next();
			}
		}
		infile.close(); // close the file
		//return board;
		
		BufferedReader dictFile = new BufferedReader(new FileReader(args[0])); 
		while (dictFile.ready()) 
		{
			dictionary.add(dictFile.readLine());
			//dictionary.put(dictFile.nextLine())
		}
	
		for (int r = 0; r<board.length; r++)
		{
			for(int c=0;c<board.length;c++)
			{
				dfs(board,r,c,"");
			}
		}
		
		for (String word : matches)
		{
			System.out.println(word);
		}
	}

	static void dfs(String[][] board, int r, int c, String word )
	{
		/*HashMap<String, Integer> dictionary = new HashMap(); // creates a new HashMap 
		File dicFile = new File("dictionary.txt"); */
		
		word+=board[r][c]; 
		word=word.toLowerCase();
		
		if ((word.length() > 2) && (dictionary.contains(word))) 
		{
			matches.add(word);
		}
		else 
		if (dictionary.subSet(word, word+Character.MAX_VALUE).size() == 0)
		{
			return;
			//break;
		}	
		if ((r>0)&&(isLowerCase(board[(r-1)][c]))) //N
		{
			board[r][c] = board[r][c].toUpperCase(); 
			dfs(board, r-1, c, word); 
			board[r][c] = board[r][c].toLowerCase(); 
		}
		if ((r>0) && (c < board[r].length - 1) && (isLowerCase(board[(r - 1)][(c + 1)]))) //NE
		{
			board[r][c] = board[r][c].toUpperCase(); 
			dfs(board, r-1, c+1, word);
			board[r][c] = board[r][c].toLowerCase(); 
		}
		if ((c<board[r].length - 1) && (isLowerCase(board[r][(c+1)]))) //E
		{
			board[r][c] = board[r][c].toUpperCase(); 
			dfs(board, r, c+1, word); 
			board[r][c] = board[r][c].toLowerCase(); 
		}
		if ((r< board.length - 1) && (c< board[r].length-1) && (isLowerCase(board[(r+1)][(c+1)]))) //SE
		{
			board[r][c] = board[r][c].toUpperCase();
			dfs(board, r+1, c+1, word);
			board[r][c] = board[r][c].toLowerCase(); 
		}
		if ((r< board.length - 1) && (isLowerCase(board[(r+1)][c]))) //S
		{
			board[r][c] = board[r][c].toUpperCase(); 
			dfs(board, r+1, c, word); 
			board[r][c] = board[r][c].toLowerCase(); 
		}
		if ((r< board.length-1) && (c>0) && (isLowerCase(board[(r+1)][(c-1)]))) //SW
		{
			board[r][c] = board[r][c].toUpperCase(); 
			dfs(board, r+1, c-1, word); 
			board[r][c] = board[r][c].toLowerCase(); 
		}
		if ((c>0) && (isLowerCase(board[r][(c-1)]))) //W
		{
			board[r][c] = board[r][c].toUpperCase();
			dfs(board, r, c-1, word); 
			board[r][c] = board[r][c].toLowerCase(); 
		}
		if ((r>0) && (c>0) && (isLowerCase(board[(r-1)][(c-1)]))) //NW
		{
			board[r][c] = board[r][c].toUpperCase(); 
			dfs(board, r-1, c-1, word); 
			board[r][c] = board[r][c].toLowerCase(); 
		}
	}
	static boolean isLowerCase(String s)
	{
		for (char c: s.toCharArray())
		{
			if (!Character.isLowerCase(c)) 
			{
				return false;
			}
		}
		return true;
	}
}