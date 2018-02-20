package search;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import puzzle.EightPuzzle;
import structures.QueueforBFS;

/**
 * This class is used to build the table for stage 1 of
 * the algorithm. Construction of the coset table requires
 * about 40% of the full Eigth-Puzzle graph traversal, which
 * can be accomplished in several minutes with this implementation.
 * 
 * Several optimizations may reduce the table construction time.
 * One could achieve better performance using a tree instead
 * of a list as a structure that stores visited nodes
 * (vertices). Search with heuristics might improve traversal as well.
 * However, the table is built only once, and so there is no
 * need to optimize the following version whatsoever.
 * 
 * NOTE:
 * Progress reports indicate the completeness of graph traversal.
 * The table is finished at roughly 40% mark.
 * 
 * @author artemvysogorets
 *
 */
public class QueueBasedBreadthFirstExplorer<T> extends Searcher<T> 
{
	QueueforBFS<T> queue;
	List<T> visited;
	int progress = 0;
	List<T>[] table;
	List<T> predecessors;
	List<T> states;
	List<T> path;
	int tableEntries = 0;
	public QueueBasedBreadthFirstExplorer (SearchProblem<T> searchProblem, List<T>[] table) 
	{
		super(searchProblem);
		this.table = table;
		queue = new QueueforBFS<T>();
		visited = new ArrayList<T>();
		predecessors = new ArrayList<T>();
		states = new ArrayList<T>();
		path = new ArrayList<T>();
	}
	public void explore() 
	{
		List<T> successors = new ArrayList<T>();
		queue.enqueue(searchProblem.getInitialState());
		visited.add(searchProblem.getInitialState());
		predecessors.add(searchProblem.getInitialState());
		states.add(searchProblem.getInitialState());
		T predecessor;
		T pathWalker;
		int tableValsoFar = 0;
        while(!queue.isEmpty())
        {
        	if(!searchProblem.getSuccessors(queue.peek(), searchProblem.getInitialState()).isEmpty())
        	{
        		predecessor = queue.peek();
        		successors.clear();
        		successors = searchProblem.getSuccessors(queue.peek(), predecessor);
        		if(successors != null)
        		{
        			for(int i = 0; i < successors.size(); i++)
        			{
        				if(!visited.contains(successors.get(i)) && successors.get(i) != null)
        				{
        					queue.enqueue(successors.get(i));
        					visited.add(successors.get(i));
        					states.add(successors.get(i));
        					predecessors.add(predecessor);
        					if(table[searchProblem.getHashIndex(successors.get(i))].isEmpty() && searchProblem.isValidForTable(successors.get(i)))
        					{
        						pathWalker = successors.get(i);
        						while(!pathWalker.equals(searchProblem.getInitialState()))
        						{
        							path.add(pathWalker);
        							pathWalker = predecessors.get(states.indexOf(pathWalker));
        						}
        						path.add(searchProblem.getInitialState());
        						for(int j = 0; j < path.size(); j++)
        						{
        							table[searchProblem.getHashIndex(successors.get(i))].add(path.get(j));
        						}
        						path.clear();
        						tableEntries++;
        					}
        					
        					//Below is used for run-time statistics only//
        					if(progress < (100*tableEntries/searchProblem.getNumberOfCosets()))
        					{
        						System.out.print("Progress: " + (100*tableEntries/searchProblem.getNumberOfCosets()) + " % " + "\n");
        						progress = (100*tableEntries/searchProblem.getNumberOfCosets());
        					}
        					for(int k = 0; k < 1000; k++)
        					{
        						if(table[k].size() != 0)
        						{
        							tableValsoFar++;
        						}
        					}
        					if(tableValsoFar == searchProblem.getNumberOfCosets())
        					{
        						return;
        					}
        					tableValsoFar = 0;
        					//Above is used for run-time statistics only//
        				}
        			}
        		}   
        	}
        	queue.dequeue();
        }
    }
	public List<T>[] getTable()
	{
		return table;
	}
	public List<T> findSolution() 
	{
		return null;
	}
}
