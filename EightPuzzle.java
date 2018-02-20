package puzzle;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Queue;
import java.util.Stack;

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
	protected int initDisarray;
	protected InsertionSorter sorter;
	protected Queue<List<Integer>> queueMan;
	public EightPuzzle(List<Integer> startingValues) 
	{
		initialState = new ArrayList<Integer>();
		goalState = new ArrayList<Integer>();
		Integer[] init = new Integer[9];
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
		for(int i = 0; i < 9; i++)
		{
			init[i] = initialState.get(i);
		}
		// The below code instantiates a sorter object, which is passed the input initial state of the puzzle (init),
		// and checks if this is a reachable puzzle state using the permutation disarray measure criterion.
		sorter = new InsertionSorter(init);
		sorter.sort();
		initDisarray = sorter.getCount();
		currentState = new ArrayList<Integer>(initialState);
	}
	public List<Integer> getInitialState() 
	{
		return initialState;
	}
	public List<Integer> getCurrentState()
	{
		return currentState;
	}
	//The following method outputs the list of all successor of a given state (currentState) without the previous
	// state (previousMove), if it is passed one. The method identifies the place of a zero tile (indexOfZero),
	// looks around it and checks if above, below, right and/or left slots are within the board. If so,
	// it adds the corresponding successor. 
	public List<List<Integer>> getSuccessors(List<Integer> currentState, List<Integer> previousMove) 
	{
		List<List<Integer>> log = new ArrayList<List<Integer>>();
		List<Integer> successor;
		int indexofZero = currentState.indexOf(0);
		int tryIndex = indexofZero + 1;
		if(tryIndex >= 0 && tryIndex <= 8)
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
		if(tryIndex >= 0 && tryIndex <= 8)
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
		if(tryIndex >= 0 && tryIndex <= 8)
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
		if(tryIndex >= 0 && tryIndex <= 8)
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
		//The following conditional is required to remove the previousMove (previous state, really) from the log, 
		//which is the final list of successors of a current state.
		if(previousMove != null && log.contains(previousMove))
		{
			log.remove(previousMove);
		}
		return log;
	}
	public boolean isGoal(List<Integer> state) 
	{
		return state.equals(goalState);
	}
	// The below method outputs the number of vertices in a graph of states of the puzzle.
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
	// The following method swaps tiles with indices 'first' and 'last' in the passed state.
	public void swap (int first, int last, List<Integer> state)
	{
		int buffer = state.get(first);
		state.set(first, state.get(last));
		state.set(last, buffer);
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
	// The following method takes two (by assumption adjacent) states t1 and t2, and outputs the index of the tile
	// in t1 that needs to be moved in order to obtain state t2.
	public String findDifference(List<Integer> t1, List<Integer> t2) 
	{
		String result = "";
		for(int i = 0; i < t1.size(); i++)
		{
			result = String.valueOf(t2.indexOf(0));
		}
		return result;
	}
	// The only states that are valid for the table are those with 0 in the center (index 4). Only such states are wanted
	// for the table because any given sequence in the table  
	public boolean isValidForTable(List<Integer> state) 
	{
		return (state.get(4) == 0);
	}
	public List<Integer> canonize() 
	{
		preMovesAsString = "";
		if((currentState.indexOf(0))%2 == 1)
		{
			swap(currentState.indexOf(0),4, currentState);
			preMovesAsString += initialState.get(4);
		}
		else if(currentState.indexOf(0) == 0)
		{
			preMovesAsString += currentState.get(1);
			swap(0,1, currentState);
			preMovesAsString += currentState.get(4);
			swap(1,4, currentState);
		}
		else if(currentState.indexOf(0) == 2)
		{
			preMovesAsString += currentState.get(1);
			swap(2,1, currentState);
			preMovesAsString += currentState.get(4);
			swap(1,4, currentState);
		}
		else if(currentState.indexOf(0) == 6)
		{
			preMovesAsString += currentState.get(7);
			swap(6,7, currentState);
			preMovesAsString += currentState.get(4);
			swap(7,4, currentState);
		}
		else if(currentState.indexOf(0) == 8)
		{
			preMovesAsString += currentState.get(7);
			swap(7,8, currentState);
			preMovesAsString += currentState.get(4);
			swap(7,4, currentState);
		}
		return currentState;	
	}
	public String getPreMoves() 
	{
		return preMovesAsString;
	}
	//Below method translates passed moves from index notation to the corresponding tile notation.
	//It also plays the passed moves on the current state.
	public String interpretSubgroupSolution(String result) 
	{
		String res = "";
		for(int i = 0; i < result.length(); i++)
		{
			res += String.valueOf(currentState.get(Character.getNumericValue(result.charAt(i))));
			swap(Character.getNumericValue(result.charAt(i)), currentState.indexOf(0), currentState);
		}
		return res;
	}
	
	//Below method performs passed moves on current state of the puzzle and performs checks on whether the goal
	// state is reached. Output is >= 0 if the goal is reached, -1 otherwise.
	public int playMoves (String moves)
	{
		for(int i = 0; i < moves.length(); i++)
		{
			swap(currentState.indexOf(Character.getNumericValue(moves.charAt(i))), currentState.indexOf(0), currentState);
			if(currentState.equals(goalState))
			{
				return i;
			}
		}
		return -1;
	}
	// The below method is a critical tool used by SubgroupSearcher while filling the sequences in the table.
	// BreadthFirstExplorer finds complete sequences that solve states - coset representatives that are closest to 1
	// and have 0 at index 4. However, for our purposes, we are not interested in full sequences; we only want the
	// first portion of the sequences which places tiles 1, 2 and 3 in their spots. When this goal is reached by the
	// sequence, it will still work on placing other tiles of the representative in their correct spots. However, this
	// is completely irrelevant for our custom state that will follow this sequence. Therefore, this method traverses
	// the sequence from right to left (form end to beginning) and cuts off entries that are > 2, since those entries
	// are not moving tiles 1, 2 and 3. It is important that we traverse the sequence in the reverse direction to ensure
	// that tiles 1, 2 and 3 are already in the right spot and that moves of indices > 2 only deal with the reduced Eight-
	//-Puzzle.
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
	// The below method compiles the resulting solution out of 3 pieces:
	//  1) 0, 1 or 2 moves that bring 0 to index 4 in the initial state (String first)
	//  2) An optimized sequence of moves that brings current state to the subgroup (String second)
	//  3) Sequence of moves obtained by BFS on R-EP that brings the current state to 1 (String third)
	// The output consists of two strings, one of which is a solution, and the other is a disarray map of this solution.
	public String[] processSolution(String first, String second, String third) 
	{
		String fullSolution = first + second + third;
		List<Integer> cur = initialState;
		int zeroIndex;
		List<Integer> prev = new ArrayList<Integer>();
		String result[] = {"",""};
		for(int i = 0; i < fullSolution.length(); i++)
		{
			prev.addAll(cur);
			zeroIndex = cur.indexOf(0);
			cur.set(cur.indexOf(Integer.parseInt(Character.toString(fullSolution.charAt(i)))), 0);
			cur.set(zeroIndex, Integer.parseInt(Character.toString(fullSolution.charAt(i))));
			if(Math.abs(prev.indexOf(0) - cur.indexOf(0)) > 2) //only vertical shifts of 0 tile may change disarray measure.
			{
				if(getRelativeDisarray(prev, cur) > 0)
				{
					result[1] += "+";
				}
				result[1] += getRelativeDisarray(prev, cur) + " ";
			}
			else
			{
				result[1] += "0 ";
			}
			prev.clear();
		}
		//Below is another round of optimization; we are looking for same entries on ends of pieces of solution.
		while(!first.equals("") && !second.equals("") && second.charAt(0) == first.charAt(first.length() - 1))
		{
			second = second.substring(1, second.length());
			first = first.substring(0, first.length() - 1);
		}
		while(!third.equals("") && !second.equals("") && second.charAt(second.length() - 1) == third.charAt(0))
		{
			second = second.substring(0, second.length() - 1);
			third = third.substring(1, third.length());
		}
		if(!second.equals(""))
		{
			third = " + " + third;
		}
		if(!first.equals(""))
		{
			second = " + " + second;
		}
		result[0] = first + second + third;
		return result;
	}
	public int getDisarray(List<Integer> state) 
	{
		Integer[] array = new Integer[state.size()];
		for(int i = 0; i < state.size(); i++)
		{
			array[i] = state.get(i);
		}
		InsertionSorter sort = new InsertionSorter(array);
		sort.sort();
		return sort.getCount();
	}
	public int getRelativeDisarray(List<Integer> list1, List<Integer> list2)
	{
		return this.getDisarray(list1) - this.getDisarray(list2);
	}
	public int getInitDisarray()
	{
		return initDisarray;
	}
	public String solveWithPrePass(int prePassDepth, List<Integer> state)
	{
		String[] score = {"0", "0"};
		int test = 100;
		int cycleCount = 0;
		int resume = -1;
		String path;
		int lastMove = -1;
		String overallPath = "";
		List<List<Integer>> visited = new ArrayList<List<Integer>>();
		visited.add(initialState);
		String testVals = "";
		List<Integer> modelState = new ArrayList<Integer>(initialState);
		List<String[]> prePass = new ArrayList<String[]>();
		while(resume < 0)	
		{
			prePassRec(prePass, prePassDepth, score, lastMove, currentState, visited);
			cycleCount++;
			if(!prePass.isEmpty())
			{
				path = String.copyValueOf(prePass.get(0)[1].toCharArray());
				test = 10;
				for(int i = 0; i < prePass.size(); i++)
				{
					if(test > Integer.parseInt(prePass.get(i)[0]))
					{
						test = Integer.parseInt(prePass.get(i)[0]);
						path = String.copyValueOf(prePass.get(i)[1].toCharArray());
					}
				}
				testVals += String.valueOf(test);
				prePass.clear();
				path = path.substring(1);
				overallPath += String.valueOf(path);
				for(int i = 0; i < String.valueOf(path).length(); i++)
				{
					swap(modelState.indexOf(Integer.parseInt(String.valueOf(String.valueOf(path).charAt(i)))), modelState.indexOf(0), modelState);
					visited.add(new ArrayList<Integer>(modelState));
				}
				resume = playMoves(String.valueOf(path));
				lastMove = Integer.parseInt(String.valueOf(overallPath.charAt(overallPath.length() - 1)));
				if(cycleCount == 5)
				{
					break;
				}
			}
		}
		System.out.print("Disarray map: " + testVals + "\n");
		//overallPath = overallPath.substring(0, overallPath.length() - prePassDepth + resume);
		return overallPath;
	}
	private void prePassRec(List<String[]> toWrite, int prePassDepth, String[] score, int prevMove, List<Integer> curState, List<List<Integer>> visited) 
	{
		String[] bufferToWrite = new String[2];
		if(prePassDepth == 0)
		{
			bufferToWrite[0] = String.copyValueOf(score[0].toCharArray());
			bufferToWrite[1] = String.copyValueOf(score[1].toCharArray());
			toWrite.add(bufferToWrite);
		}
		else	
		{
			List<Integer> previousMove = new ArrayList<Integer>(curState);
			List<List<Integer>> successors;
			String[] newScore = new String[2];
			if(prevMove >= 0)
			{
				swap(curState.indexOf(prevMove), curState.indexOf(0), previousMove);
				successors = getSuccessors(curState, previousMove);
			}
			else
			{
				successors = getSuccessors(curState, null);
			}
			for(List<Integer> successor : successors)
			{
				if(!visited.contains(successor))
				{	
					String fakeScore0 = new String(score[0]);
					String fakeScore1 = new String(score[1]);
					newScore[0] = fakeScore0; 
					newScore[1] = fakeScore1;
					newScore[0] = String.copyValueOf(String.valueOf(Integer.parseInt(newScore[0]) + getRelativeDisarray(curState, successor)).toCharArray());
					newScore[1] += String.valueOf(curState.get(Integer.parseInt(findDifference(curState, successor))));
					prePassRec(toWrite, prePassDepth - 1, newScore, curState.get(successor.indexOf(0)), successor, visited);
				}
			}
		}
	}
	public int getManhattan(List<Integer> curr)
	{
		int result = 0;
		result += (curr.indexOf(1)%3) + (curr.indexOf(4)%3) + (curr.indexOf(7)%3) + (2 - (curr.indexOf(3))%3) + (2 - (curr.indexOf(6))%3) + (2 - (curr.indexOf(0))%3);
		if(curr.indexOf(2)%3 != 1)
		{
			result +=1;
		}
		if(curr.indexOf(5)%3 != 1)
		{
			result+=1;
		}
		if(curr.indexOf(8)%3 != 1)
		{
			result+=1;
		}
		result += (curr.indexOf(1))/3 + (curr.indexOf(2))/3 + (curr.indexOf(3)/3) + (2 - (curr.indexOf(7)/3)) + (2 - (curr.indexOf(8))/3) + (2 - (curr.indexOf(0))/3);
		if((curr.indexOf(4))/3 != 1)
		{
			result+=1;
		}
		if((curr.indexOf(5))/3 != 1)
		{
			result+=1;
		}
		if((curr.indexOf(6))/3 != 1)
		{
			result+=1;
		}
		return result;
	}
	public String interpretAStarSolution(List<List<Integer>> solution)
	{
		String res = "";
		for(int i = 0; i < solution.size() - 1; i++)
		{
			res += currentState.get(Integer.parseInt(findDifference(solution.get(i), solution.get(i+1))));
			interpretSubgroupSolution(findDifference(solution.get(i), solution.get(i+1)));
		}
		return res;
	}
}










