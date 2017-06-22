package puzzle;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import search.QueueBasedBreadthFirstSearcher;
import search.SearchProblem;

/**
 * This is a simulation of an eight-puzzle, which is a smaller version
 * of the well-known 15-puzzle (https://en.wikipedia.org/wiki/15_puzzle)
 * 
 * The puzzle's states are modeled with permutations from Perm9, 
 * which is a set of all permutations of a set {1, 2, 3,..., 9}. 
 * 
 * The labeling of positions in the puzzle is as follows:
 * 
 * 			1 | 2 | 3
 * 		   ---+---+---
 * 			4 | 5 | 6
 * 		   ---+---+---
 * 			7 | 8 | 9
 * 			
 * The entry at the i-th position of the permutation (with the first
 * entry of a permutation being at the first position) is the number
 * token, which is currently at the i-th position on the board.
 * 
 * NOTE: Since one element of the permutation needs to represent an
 * empty space in the puzzle, we choose it to be 9. However, for
 * convenience purposes we set 9 to be 0. (One might find this equivalent
 * to working with Perm9 modulo 9).
 * 
 * Thus, solved puzzle is represented with a permutation:
 * [1, 2, 3, 4, 5, 6, 7, 8, 0]
 * @author artemvysogorets
 */

public class EightPuzzle implements SearchProblem<List<Integer>> 
{
	protected List<Integer> initialState;
	protected List<Integer> goalState;
	public List<Integer> currentState;
	protected String preMovesAsString;
	public EightPuzzle(List<Integer> startingValues) 
	{
		initialState = new ArrayList<Integer>();
		goalState = new ArrayList<Integer>();
		if(startingValues.size() == 9)
		{
			if(startingValues.contains(0) && startingValues.contains(1) && startingValues.contains(2) && startingValues.contains(3) && startingValues.contains(4) && startingValues.contains(5) && startingValues.contains(6) && startingValues.contains(7) && startingValues.contains(8))
			{
				for(int i = 0; i <= 8; i++)
				{
					initialState.add(i, startingValues.get(i));
					goalState.add(i, i);
				}
				goalState.remove(0);
				goalState.add(8, 0);
			}
			else
			{
				throw new IllegalArgumentException();
			}
		}
		else
		{
			throw new IllegalArgumentException();
		}
		currentState = new ArrayList<Integer>(initialState);
	}
	public List<Integer> getInitialState() 
	{
		return initialState;
	}
	public List<List<Integer>> getSuccessors(List<Integer> currentState, List<Integer> previousMove) 
	{
		int stopIndex = previousMove.indexOf(0);
		List<List<Integer>> log = new ArrayList<List<Integer>>();
		List<Integer> successor;
		int indexofZero = currentState.indexOf(0);
		int tryIndex = indexofZero + 1;
		if(tryIndex >= 0 && tryIndex <= 8 && tryIndex != stopIndex)
		{
			if(tryIndex != 0 && tryIndex != 3 && tryIndex != 6)
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
		if(tryIndex >= 0 && tryIndex <= 8 && tryIndex != stopIndex)
		{
			if(tryIndex != 2 && tryIndex != 5 && tryIndex != 8)
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
		if(tryIndex >= 0 && tryIndex <= 8 && tryIndex != stopIndex)
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
		if(tryIndex >= 0 && tryIndex <= 8 && tryIndex != stopIndex)
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
	public boolean isGoal(List<Integer> state) 
	{
		return state.equals(goalState);
	}
	public int getNumberOfStates()
	{
		return 3*4*5*6*7*8*9;
	}
	public int getHashIndex(List<Integer> state) 
	{
		int result = 100*(state.indexOf(1) + 1) + 10*(state.indexOf(2) + 1) + state.indexOf(3) + 1;
		return result;
	}
	public int getNumberOfCosets() 
	{
		return 336;
	}
	public void swap (int first, int last)
	{
		int buffer = currentState.get(first);
		currentState.set(first, currentState.get(last));
		currentState.set(last, buffer);
	}
	public String printCurrentState()
	{
		String result = new String();
		for(int i = 0; i < currentState.size(); i++)
		{
			result += currentState.get(i);
		}
		return result;
	}
	public String findDifference(List<Integer> t1, List<Integer> t2) 
	{
		String result = "";
		for(int i = 0; i < t1.size(); i++)
		{
			result = String.valueOf(t2.indexOf(0));
		}
		return result;
	}
	public boolean isValidForTable(List<Integer> state) 
	{
		return (state.get(4) == 0);
	}
	public List<Integer> canonize() 
	{
		preMovesAsString = "";
		if((currentState.indexOf(0))%2 == 1)
		{
			swap(currentState.indexOf(0),4);
			preMovesAsString += initialState.get(4);
		}
		else if(currentState.indexOf(0) == 0)
		{
			preMovesAsString += currentState.get(1);
			swap(0,1);
			preMovesAsString += currentState.get(4);
			swap(1,4);
		}
		else if(currentState.indexOf(0) == 2)
		{
			preMovesAsString += currentState.get(1);
			swap(2,1);
			preMovesAsString += currentState.get(4);
			swap(1,4);
		}
		else if(currentState.indexOf(0) == 6)
		{
			preMovesAsString += currentState.get(7);
			swap(6,7);
			preMovesAsString += currentState.get(4);
			swap(7,4);
		}
		else if(currentState.indexOf(0) == 8)
		{
			preMovesAsString += currentState.get(7);
			swap(7,8);
			preMovesAsString += currentState.get(4);
			swap(7,4);
		}
		return currentState;	
	}
	public String getPreMoves() 
	{
		return preMovesAsString;
	}
	public String playMoves(String result) 
	{
		String res = "";
		for(int i = 0; i < result.length(); i++)
		{
			res += String.valueOf(currentState.get(Character.getNumericValue(result.charAt(i))));
			swap(Character.getNumericValue(result.charAt(i)), currentState.indexOf(0));
		}
		return res;
	}
	public String optimize(String result) 
	{
		int lastImportantMove = 0;
		for(int i = result.length() - 1; i >= 0; i--)
		{
			if(Character.getNumericValue(result.charAt(i)) <= 2)
			{
				lastImportantMove = i + 1;
				break;
			}
		}
		String optResult = "";
		for(int j = 0; j <= lastImportantMove; j++)
		{
			optResult += result.charAt(j);
		}
		return optResult;
	}
}