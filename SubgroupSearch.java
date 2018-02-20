package search;

import java.util.ArrayList;
import java.util.List;

/**
 * This class is responsible for initiating graph exploration by the QueueBasedBreadthFirstExplorer, 
 * table construction and preparation of the collected data for printing. The actual printing is done by
 * the driver class.
 * 
 *  The underlying idea of the table is the following:
 *  
 *  Suppose we know a solution sequence S for the following state:
 *  
 * 				  | 1 | 		Then, we know that S is a permutation that swaps indices
 * 			   ---+---+---	    in the following way:   1st --> 0th; 3rd --> 1st; 8th --> 2nd,
 * 			    2 |   | 		because tiles 1, 2, 3 move to their corresponding slots in the
 *			   ---+---+---		first row under action of S. With this information, we can conclude
 *			    0 |   | 3 		that the same permutation S will bring any state of the following form
 *
 * to a state with a completed first row:
 * 
 * 				x | 1 | x					1 | 2 | 3						1 | 2 | 3
 * 			   ---+---+---		  S		   ---+---+---		BFS on R-EP	   ---+---+---
 * 				2 | x | x 		---->         |   |			---------->	    4 | 5 | 6
 * 			   ---+---+---				   ---+---+---					   ---+---+---
 * 			    x | 0 | 3					  |   |							7 | 8 | 0
 * 
 * Thus, we create a table of length 1000 that will store such sequences for states with all possible 
 * combinations of tiles 1, 2, 3 on the board. We will order them according to indices of 1, 2, and 3. That is, 
 * a sequence that solves the above state will be located on the 100*(indexOf(1) + 1) + 10*(indexOf(2) + 1) + indexOf(3) + 1
 * slot (or line) of the table. Thus, in order to access a sequence that would bring any other custom state with
 * the same positions of 1, 2 and 3 tiles to a subgroup with the top row solved, we apply the same hashing
 * function to our custom state and take the sequence from the corresponding line in the table. Thus, this allows us to
 * bring any given state of the puzzle to the described subgroup in constant time (simply by accessing the table). From now
 * on, the solution is close: there are 6! = |Perm6| possible states of the puzzle in this subgroup. Simply apply
 * breadth-first-search on this reduced Eight-Puzzle with only 360 vertices in its graph to obtain the solution. 
 *
 * Still, there is one complication. If the index of zero of the 'table' state is not the same as the one in
 * our custom state, then the first entry in S may fail to be applicable to the custom state. In other words, if
 * S starts as a swap of 2 and 0 (which is a completely legal move for the 'table' state), we cannot perform this
 * permutation on our custom state where 0 at the 7th index. In order to solve this problem, we store sequences only
 * for those states, which have 0 in the center (4th index). In addition, we require that our custom state has 0 in
 * the same spot. We achieve this by moving 0 tile to the center before running any algorithm or accessing the table.
 * This additional steps will take at most 2 extra moves, since any index can be accessed from 4 in at most two Manhattan
 * moves. 
 * 
 * The choice of this subgroup (subgroup of states that have their top row solved) is not as random as it 
 * may seem. Our goal was to split the problem into two roughly equal parts. In fact, there are exactly 336 cosets
 * of this subgroup, which has 360 vertices.
 * 
 * This algorithm produces suboptimal solutions. Since we explore using BFS, we are guaranteed to output sequences
 * that solve states, which are closest to 1 and satisfy the zero condition. In other words, our table stores sequences
 * that will bring any custom state to the subgroup in at most (fewest + 2) moves (2 comes from the center requirement).
 * Later, by using BFS on R-EP we ensure that we choose the shortest path from the current state in the subgroup to solution.
 * Please note several optimizations that we apply to our algorithm as well statistics of the table construction. Also, note the
 * role of insertion sorter in determining whether an input state is solvable.
 * 
 * 
 * FURTHER CONSIDERATIONS
 * 
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
					String move = puzzle.findDifference(table[i].get(previous), table[i].get(current)); // outputs the index
					curRes += move; // curRes store the sequence of indices
				}
				result += puzzle.optimize(curRes);
				result += "\n";
				curRes = "";
				// Line 95: Optimization.
				// BreadthFirstExplorer finds complete sequences that solve states - coset representatives that are closest to 1
				// and have 0 at index 4. However, for our purposes, we are not interested in full sequences; we only want the
				// first portion of the sequences which places tiles 1, 2 and 3 in their spots. When this goal is reached by the
				// sequence, it will still work on placing other tiles of the representative in their correct spots. However, this
				// is completely irrelevant for our custom state that will follow this sequence. Therefore, this method traverses
				// the sequence from right to left (form end to beginning) and cuts off entries that are > 2, since those entries
				// are not moving tiles 1, 2 and 3. It is important that we traverse the sequence in the reverse direction to ensure
				// that tiles 1, 2 and 3 are already in the right spot and that moves of indices > 2 only deal with the reduced Eight-
				//-Puzzle.
			}
		}
		return result;
	}	
}
