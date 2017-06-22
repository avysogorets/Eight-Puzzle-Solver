package puzzle;
import java.util.ArrayList;
import java.util.List;
import search.QueueBasedBreadthFirstSearcher;

/**
 * Reduced Eight-Puzzle is the 2x3 puzzle, which needs
 * to be solved at the last stage of the algorithm.
 * The permutation representation of the reduced puzzle is
 * always [1, 2, 3, x, x, x, x, x, x]. That is, the top layer
 * ([1, 2, 3]) always remains solved, while the bottom tokens
 * might be in disarray. The goal of this last stage is to 
 * bring the reduced puzzle to [1, 2, 3, 4, 5, 6, 7, 8, 0]
 * with BFS. Reduced puzzle has only 360 different positions,
 * which makes graph traversal with BFS a reasonable approach.
 * 
 * @author artemvysogorets
 *
 */

public class ReducedEightPuzzle extends EightPuzzle 
{
	public ReducedEightPuzzle (List<Integer> startingValues) 
	{
		super(startingValues);
		if(!(startingValues.get(0) == 1 && startingValues.get(1) == 2 && startingValues.get(2) == 3))
		{
			throw new IllegalStateException("The Puzzle Is Not Reduced");
		}
	}
	public List<List<Integer>> getSuccessors(List<Integer> currentState, List<Integer> previousMove)
	{
		int stopIndex = previousMove.indexOf(0);
		List<List<Integer>> log = new ArrayList<List<Integer>>();
		List<Integer> successor;
		int indexofZero = currentState.indexOf(0);
		int tryIndex = indexofZero + 1;
		if(tryIndex >= 3 && tryIndex <= 8 && tryIndex != stopIndex)
		{
			if(tryIndex != 3 && tryIndex != 6)
			{
				successor = new ArrayList<Integer>();
				for(int i = 0; i < 9; i++)
				{
					successor.add(i, currentState.get(i));
				}
				successor.set(tryIndex, 0);
				successor.set(indexofZero, currentState.get(tryIndex));
				log.add(successor);
			}
		}
		tryIndex = indexofZero - 1;
		if(tryIndex >= 3 && tryIndex <= 8 && tryIndex != stopIndex)
		{
			if(tryIndex != 5 && tryIndex != 8)
			{
				successor = new ArrayList<Integer>();
				for(int i = 0; i < 9; i++)
				{
					successor.add(i, currentState.get(i));
				}
				successor.set(tryIndex, 0);
				successor.set(indexofZero, currentState.get(tryIndex));
				log.add(successor);
			}
		}
		tryIndex = indexofZero - 3;
		if(tryIndex >= 3 && tryIndex <= 8 && tryIndex != stopIndex)
		{
			successor = new ArrayList<Integer>();
			for(int i = 0; i < 9; i++)
			{
				successor.add(i, currentState.get(i));
			}
			successor.set(tryIndex, 0);
			successor.set(indexofZero, currentState.get(tryIndex));
			log.add(successor);	
		}
		tryIndex = indexofZero + 3;
		if(tryIndex >= 3 && tryIndex <= 8 && tryIndex != stopIndex)
		{
			successor = new ArrayList<Integer>();
			for(int i = 0; i < 9; i++)
			{
				successor.add(i, currentState.get(i));
			}
			successor.set(tryIndex, 0);
			successor.set(indexofZero, currentState.get(tryIndex));
			log.add(successor);	
		}
		return log;
	}
	public int getNumberOfStates()
	{
		return 6*5*4*3;
	}
	public String solve()
	{
		QueueBasedBreadthFirstSearcher search = new QueueBasedBreadthFirstSearcher(this);
		List<List<Integer>> solution = search.findSolution();
		String res = "";
		for(int i = 0; i < solution.size() - 1; i++)
		{
			res += currentState.get(Integer.parseInt(findDifference(solution.get(i), solution.get(i+1))));
			super.playMoves(findDifference(solution.get(i), solution.get(i+1)));
		}
		return res;
	}
}
