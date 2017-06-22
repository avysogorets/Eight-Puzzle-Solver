import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import puzzle.EightPuzzle;
import puzzle.ReducedEightPuzzle;
import search.QueueBasedBreadthFirstSearcher;
import search.SearchProblem;
import search.SubgroupSearch;

/**
 * The following is the driver class of the program.
 * @author artemvysogorets
 */
public class EightPuzzleDriver 
{
	boolean isValid = false;
	static long startTime;
	public static List<List<Integer>>[] table;
	
	public static void drawPuzzle(Character[] a)
	{
		System.out.print("\n          "+ a[0] + " | "+ a[1] +" | " + a[2]);
		System.out.print("\n         ---+---+---");
		System.out.print("\n          " + a[3] + " | " + a[4] + " | " + a[5]);
		System.out.print("\n         ---+---+---");
		System.out.print("\n          " + a[6] + " | " + a[7] + " | " + a[8]);
		System.out.print("\n\n");
	}
	public static void buildTable() throws FileNotFoundException
	{
		table = new List[1000];
		Integer[] a = {1,2,3,4,5,6,7,8,0};
		PrintStream out = new PrintStream(new FileOutputStream("table.txt"));
		for(int i = 0; i < 1000; i++)
		{
			table[i] = new ArrayList<List<Integer>>();
		}
		EightPuzzle puzzle = new EightPuzzle(Arrays.asList(a));
		SubgroupSearch ss = new SubgroupSearch(puzzle,table);
		ss.constructTable();
		String results = ss.printTable();
		out.print(results);
		out.close();		
	}
	public static void main(String[] args) throws IOException
	{		
		//buildTable();
		String[] hashTable = new String[1000];
		BufferedReader br = new BufferedReader(new FileReader("table.txt"));
		String line;
		for(int i = 1; i < 1000; i++)
		{
			line = br.readLine();
			hashTable[i] = line;
		}
		boolean isValid = false;
		Character[] array = new Character[9];
		Integer a[] = new Integer[9];
		InsertionSorter sorter;
		Scanner scan = new Scanner(System.in);
		String greeting = "This is an Eight-Puzzle Solver! \nType in a valid position to solve. \n";
		System.out.print(greeting);
		ArrayList<Integer> remaining = new ArrayList<Integer>();
		while(!isValid)
		{
			isValid = true;
			for(int i = 0; i < 9; i++)
			{
				array[i] = 'X';
				remaining.add(i);
			}
			drawPuzzle(array);
			int index = 0;
			Character newChar;
			while(index < 9) 
			{
				System.out.print("Type in a number from " + remaining + "\n");
				newChar = (scan.next()).charAt(0);
				if(!remaining.contains(Integer.parseInt(Character.toString(newChar)))) 
				{
					System.out.println("You have aready entered this one. Try again:");
				} 
				else 
				{
					array[index] = newChar;
					remaining.remove(remaining.indexOf(Integer.parseInt(array[index].toString())));
					index++;
				}
			}
			drawPuzzle(array);
			boolean found = false;
			for(int i = 0; i < 9; i++)
			{
				a[i] = Integer.parseInt((array[i].toString()));
			}
			sorter = new InsertionSorter(a);
			sorter.sort();
			if(((sorter.getCount())%2 == 1))
			{
				System.out.print("You entered an unslovable position :(" + "\n" + "Please try again.\n");
				isValid = false;
			}
		}
		long startTime = System.currentTimeMillis();
		EightPuzzle puzzle = new EightPuzzle(Arrays.asList(a));
		int hash = puzzle.getHashIndex(puzzle.canonize());
		String result = hashTable[hash];
		String toSubgoal = puzzle.playMoves(result);
		System.out.print("The Solution is: " + puzzle.getPreMoves() + " + " + toSubgoal);
		ReducedEightPuzzle rep = new ReducedEightPuzzle(puzzle.currentState);
		String additionalMoves = rep.solve();
		System.out.print(" + " + additionalMoves + "\n");
		int length = puzzle.getPreMoves().length() + toSubgoal.length() + additionalMoves.length();
		System.out.print("Solution Length is: " + length + "\n");
		long endTime = System.currentTimeMillis();
		System.out.print("Execution took " + (endTime - startTime) + " Milliseconds");
	}
}