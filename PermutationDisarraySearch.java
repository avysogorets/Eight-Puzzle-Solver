package search;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.PriorityQueue;

import puzzle.EightPuzzle;
import structures.DisarrayMeasureComparator;
import structures.YAstate;

public class PermutationDisarraySearch 
{
	protected EightPuzzle puzzle;
	protected DisarrayMeasureComparator comparator;
	protected PriorityQueue<YAstate> heap;
	protected List<List<Integer>> visited;
	protected List<List<Integer>> successors;
	protected List<List<Integer>> solution;
	protected List<List<Integer>> states;
	protected List<List<Integer>> predecessors;
	protected YAstate currSuccessor;
	protected YAstate state;
	protected String solutionStats;
	protected List<Integer> solutionStatsAsList;
	protected YAstate predecessorAsState;
	public PermutationDisarraySearch (EightPuzzle puzzle)
	{
		this.puzzle = puzzle;
		comparator = new DisarrayMeasureComparator();
		visited = new ArrayList<List<Integer>>();
		solution = new ArrayList<List<Integer>>();
		successors = new ArrayList<List<Integer>>();
		states = new ArrayList<List<Integer>>();
		predecessors = new ArrayList<List<Integer>>();
		heap = new PriorityQueue<YAstate>(11, comparator);
		solutionStats = "";
		solutionStatsAsList = new ArrayList<Integer>();
	}
	public List<List<Integer>> findSolution()
	{
		heap.offer(new YAstate(puzzle, puzzle.getInitialState()));
		visited.add(puzzle.getInitialState());
		predecessors.add(puzzle.getInitialState());
		states.add(puzzle.getInitialState());
		while(!heap.isEmpty() && !puzzle.isGoal(heap.peek().getState()))
		{
			predecessorAsState = heap.peek();
			List<Integer> predecessor = predecessorAsState.getState();
        	if(!puzzle.getSuccessors(heap.peek().getState(), predecessor).isEmpty())
        	{
        		successors = puzzle.getSuccessors(heap.peek().getState(), predecessor);
        		heap.remove(heap.peek());
        		if(successors != null)
        		{
        			for(int i = 0; i < successors.size(); i++)
        			{
        				currSuccessor = new YAstate(puzzle, successors.get(i));
        				if(!visited.contains(successors.get(i)) && successors.get(i) != null)
        				{
        					currSuccessor.setDistance(predecessorAsState.getDistance() +1);
        					heap.offer(currSuccessor);
        					visited.add(successors.get(i));
        					states.add(successors.get(i));
        					predecessors.add(states.indexOf(successors.get(i)), predecessor);
        				}
        			}
        		}
        		else
        			heap.poll();
        	}
		}
		List<Integer> state;
		if(heap.size() == 0)
		{
			System.out.print("The heap is empty");
		}
		state = heap.peek().getState();
		solutionStatsAsList.add(puzzle.getDisarray(heap.peek().getState()));
        solution.add(heap.peek().getState());
        if(!heap.isEmpty())
        {
        	List<Integer> predecessor;
        	while(!state.equals(puzzle.getInitialState()))
        	{
        		predecessor = predecessors.get(states.indexOf(state));
        		solution.add(predecessor);
        		solutionStatsAsList.add(puzzle.getDisarray(predecessor));
        		state = predecessor;
        	}
        	Collections.reverse(solution);
        	Collections.reverse(solutionStatsAsList);
        	for(int i = 0; i < solutionStatsAsList.size() - 1; i++)
        	{
        		solutionStats += solutionStatsAsList.get(i) + "-";
        	}
        	solutionStats += solutionStatsAsList.get(solutionStatsAsList.size() - 1);
		}
        return solution;
	}
	public String getSolutionStats()
	{
		return solutionStats;
	}
}
