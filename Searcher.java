package search;

import java.util.ArrayList;
import java.util.List;

/**
 * The following is an abstract class, 
 * which represents an idea of search.
 * ---------
 * Абстрактный класс поиска.
 */

public abstract class Searcher<T> {
	protected final SearchProblem<T> searchProblem;
	protected final List<T> visited;
	protected List<T> solution;

	public Searcher(SearchProblem<T> searchProblem) {
		this.searchProblem = searchProblem;
		this.visited = new ArrayList<T>();
	}
	public abstract List<T> findSolution();
	public final boolean isValidSolution(List<T> solution) 
	{
		T successor;
		if(solution == null)
		{
			throw new NullPointerException();
		}
		if(solution.isEmpty())
		{
			return false;
		}
        return true;
	}
}
