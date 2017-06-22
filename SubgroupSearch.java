package search;

import java.util.ArrayList;
import java.util.List;

/**
 * This class controls table construction and prepares data for printing.
 * 
 * @author artemvysogorets
 */

public class SubgroupSearch<T>
{
	private List<T>[] table;
	SearchProblem<T> puzzle;
	private QueueBasedBreadthFirstExplorer<T> explorer;
	public SubgroupSearch(SearchProblem<T> puzzle, List<T>[] table)
	{
		this.table = table;
		this.puzzle = puzzle;
		explorer = new QueueBasedBreadthFirstExplorer(puzzle, table);
	}
	public void constructTable()
	{
		explorer.explore();
		table = explorer.getTable();
	}
	public List<T>[] getTable()
	{
		return table;
	}
	public String printTable()
	{
		int previous;
		int current;
		String result = "";
		String curRes = "";
		for(int i = 1; i < 1000; i++)
		{
			if(table[i].size() == 0)
			{
				result += "-1\n";
			}
			else
			{	
				for(previous = 0; previous < table[i].size() - 1; previous++)
				{
					current = previous + 1;
					String move = puzzle.findDifference(table[i].get(previous), table[i].get(current));
					curRes += move;
				}
				result += puzzle.optimize(curRes);
				result += "\n";
				curRes = "";
			}
		}
		return result;
	}	
}
