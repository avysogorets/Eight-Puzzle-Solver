package search;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import structures.QueueforBFS;

/**
 * The implementation of a Breadth-First-Search (BFS) using lists
 * to store visited vertices of the graph. The effect of optimizations
 * would be negligible, since the size of the graph we traverse
 * is only 360 vertices.
 * 
 * ---------
 * 
 * Реализация абстрактного классa Searcher с помощью очереди. В очереди
 * располагается динамичный набор из состояний системы, по которым
 * ведется поиск решенного состояния. Такая реализация поиска задает
 * поиск в ширину (breadth-first search).
 * 
 * @author artemvysogorets
 * 
 */
public class QueueBasedBreadthFirstSearcher<T> extends Searcher<T> 
{
	QueueforBFS<T> queue;
	List<T> visited;
	List<T> states;
	List<T> predecessors;
	public QueueBasedBreadthFirstSearcher(SearchProblem<T> searchProblem) 
	{
		super(searchProblem);
		queue = new QueueforBFS<T>();
		visited = new ArrayList<T>();
		states = new ArrayList<T>();
		predecessors = new ArrayList<T>();
	}
	public List<T> findSolution() 
	{
		List<T> successors = new ArrayList<T>();
		int progress = 0;
		queue.enqueue(searchProblem.getInitialState());
		visited.add(searchProblem.getInitialState());
		predecessors.add(searchProblem.getInitialState());
		states.add(searchProblem.getInitialState());
        while(!queue.isEmpty() && !searchProblem.isGoal(queue.peek()))
        {
        	T predecessor = queue.peek();
        	if(!searchProblem.getSuccessors(queue.peek(), predecessor).isEmpty())
        	{
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
        					predecessors.add(states.indexOf(successors.get(i)), predecessor);
        				}
        			}
        		}
        	}
        	queue.dequeue();
        }    
        List<T> result = new ArrayList<T>();
        T state;
        state = queue.peek();
        result.add(queue.peek());
        if(!queue.isEmpty())
        {
        	T predecessor;
        	while(!state.equals(searchProblem.getInitialState()))
        	{
        		predecessor = predecessors.get(states.indexOf(state));
        		result.add(predecessor);
        		state = predecessor;
        	}
        	Collections.reverse(result);
        }
        if (!isValidSolution(result)) 
        {
        	System.out.print(result);
			throw new RuntimeException("searcher should never find an invalid solution!");
        }
        return result;
	}
}
