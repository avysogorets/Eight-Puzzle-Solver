package search;

import java.util.List;

/**
 *This class is an interface for a generic search problem, such as Eight-Puzzle.
 *Any search problem has an initial state and a goal state. It should also
 *be able to tell what are the successors of any certain state. 
 * 
 * -----------
 * 
 * Интерфейс для абстрактной поисковой задачи. Поисковая задача обусловлена
 * начальным положением системы, состояниями (или положениями) решения задачи
 * и методом, определяющим все возможные следующие состояния для каждой
 * заданной позиции системы. 
 */

public interface SearchProblem<T> 
{
	T getInitialState();
	List<T> getSuccessors(T currentState, T previous);
	boolean isGoal(T state);
	int getNumberOfStates();
	int getHashIndex(T state);
	int getNumberOfCosets();
	String findDifference(T t, T t2);
	boolean isValidForTable(T t);
	String optimize(String result);
}
