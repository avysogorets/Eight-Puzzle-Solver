package search;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Stack;

/**
 * An implementation of a Searcher that performs an iterative search,
 * storing the list of next states in a Stack. This results in a
 * depth-first search.
 * 
 * -----------
 * 
 * Реализация абстрактного классa Searcher с помощью стэка. В стэке
 * располагается динамичный набор из состояний системы, по которым
 * ведется поиск решенного состояния. Такая реализация поиска задает
 * поиск в глубину (depth-first search).
 * 
 */
/*public class StackBasedDepthFirstSearcher<T> extends Searcher<T> 
{
	Stack<T> stack;
	List<T> visited;
	public StackBasedDepthFirstSearcher(SearchProblem<T> searchProblem) 
	{
		super(searchProblem);
		stack = new Stack<T>();
		visited = new ArrayList<T>();
	}

	public List<T> findSolution() 
	{
		int progress = 0;
		List<T> successors = new ArrayList<T>();
		boolean bool;
        stack.push(searchProblem.getInitialState());
        visited.add(searchProblem.getInitialState());
        while(!stack.isEmpty() && !searchProblem.isGoal(stack.peek()))
        {
    	   if(!searchProblem.getSuccessors(stack.peek()).isEmpty())
    	   {
    		   successors = searchProblem.getSuccessors(stack.peek());
    		   bool = false;
    		   if(successors == null)
    		   {
    			   stack.pop();
    		   }
    		   else  
    		   {	
    			   for(int i = 0; i < successors.size(); i++)
    			   {
    				   if(!visited.contains(successors.get(i)) && successors.get(i) != null)
    				   {
    					   stack.push(successors.get(i));
    					   visited.add(successors.get(i));
    					   if(progress < (100*visited.size())/searchProblem.getNumberOfStates())
       					{
       						System.out.print("Progress: " + (100*visited.size())/searchProblem.getNumberOfStates() + " %" + "\n");
       						progress = (100*visited.size())/searchProblem.getNumberOfStates();
       					}
    					   bool = true;
    					   break;
    				   }
    			   }
    			   if(bool == false)
    			   {
    				   stack.pop();
    			   }
    		   }
    	   }
    	   else
    	   {
    			stack.pop();
    	   }
       }
       List<T> result = new ArrayList<T>();
       while(!stack.isEmpty())
       {
    	  result.add(stack.pop());
       }
       Collections.reverse(result);
       if (!isValidSolution(result)) 
       {
			throw new RuntimeException("searcher should never find an invalid solution!");
       }
       return result;
	}
}
*/