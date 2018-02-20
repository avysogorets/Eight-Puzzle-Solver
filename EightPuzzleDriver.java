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
import search.ManhattanSearch;
import search.PermutationDisarraySearch;
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
	// The below method is responsible for finishing the table.txt file. It instantiates its own table and
	// the SubroupSearch object (ss), which is then asked to construct the table and prepare the collected data
	// given the empty table created above. Then, the filled table is copied to the table.txt file.
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
		String scanned;
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
				scanned = scan.next();
				if(scanned.length() == 9)
				{
					for(int i = 0; i < 9; i++)
					{
						array[i] = scanned.charAt(i);
					}
					break;
				}
				else
				{
					newChar = scanned.charAt(0);
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
			}
			drawPuzzle(array);
			boolean found = false;
			for(int i = 0; i < 9; i++)
			{
				a[i] = Integer.parseInt((array[i].toString()));
			}
			sorter = new InsertionSorter(a);
			sorter.sort();
			int initialDisarray = sorter.getCount();
			if(((sorter.getCount())%2 == 1))
			{
				System.out.print("You entered an unslovable position :(" + "\n" + "Please try again.\n");
				isValid = false;
			}
		}
		long startTime = System.currentTimeMillis();
		EightPuzzle puzzle = new EightPuzzle(Arrays.asList(a));
		EightPuzzle puzzle2 = new EightPuzzle(Arrays.asList(a));
		EightPuzzle puzzle3 = new EightPuzzle(Arrays.asList(a));
		//Regular Solution below//
		int hash = puzzle.getHashIndex(puzzle.canonize());
		String result = hashTable[hash];
		String toSubgoal = puzzle.interpretSubgroupSolution(result);
		String preMove = puzzle.getPreMoves();
		ReducedEightPuzzle rep = new ReducedEightPuzzle(puzzle.currentState);
		String additionalMoves = rep.solve();
		String res[] = new String[2];
		res = puzzle.processSolution(preMove, toSubgoal, additionalMoves);
		System.out.print("Subgroup Solution is: " + res[0] + "\n");
		int length = puzzle.getPreMoves().length() + toSubgoal.length() + additionalMoves.length();
		System.out.print("Solution Length is: " + length + "\n");
		
		long endTime = System.currentTimeMillis();
		System.out.print("Execution time: " + (endTime - startTime) + " Milliseconds" + "\n\n");
		long startTime2 = System.currentTimeMillis();
		
		//Solution with prepass below//
		/*String solutionWithPrePass = puzzle.solveWithPrePass(3, puzzle.getInitialState());
		System.out.print("PrePass Solution is: " + solutionWithPrePass + "\n");
		System.out.print("Initial Disarrangement Count: " + puzzle.getInitDisarray() + "\n");
		System.out.print(puzzle.getCurrentState() + "\n");
		//System.out.print("Disarrangement Map: " + res[1] + "\n");*/
		
		//Solution with A* and Manhattan distance heuristic//
		ManhattanSearch manSearch = new ManhattanSearch(puzzle2);
		String manSolution = puzzle2.interpretAStarSolution(manSearch.findSolution());
		System.out.print("Manhattan Solution is: " + manSolution + "\n");
		System.out.print("Initial Manhattan Distance is: " + puzzle2.getManhattan(puzzle2.getInitialState()) + "\n");
		System.out.print("Statistics: " + manSearch.getSolutionStats() +"\n");
		System.out.print("Solution length is: " + manSolution.length() + "\n");
		
		long endTime2 = System.currentTimeMillis();
		System.out.print("Execution time: " + (endTime2 - startTime2) + " Milliseconds" + "\n\n");
		
		//Solution With A* and Permutation Disarray Measure Heuristic
		long startTime3 = System.currentTimeMillis();
		PermutationDisarraySearch permSearch = new PermutationDisarraySearch(puzzle3);
		String permSolution = puzzle3.interpretAStarSolution(permSearch.findSolution());
		System.out.print("Permutation Disarray Solution is: " + permSolution + "\n");
		System.out.print("Initial Permuatation Disarray is: " + puzzle3.getInitDisarray() + "\n");
		System.out.print("Statistics: " + permSearch.getSolutionStats() +"\n");
		System.out.print("Solution length is: " + permSolution.length() + "\n");
		
		long endTime3 = System.currentTimeMillis();
		System.out.print("Execution time: " + (endTime3 - startTime3) + " Milliseconds" + "\n");
		// Next: You can also try to solve use ManDis + prepass technique. 
		
	}
}