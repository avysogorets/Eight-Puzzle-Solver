package structures;

import java.util.List;

import puzzle.EightPuzzle;

/*
 * YAstate = Yet Another state class (not to confuse with Java's State class)
 * This class incorporates all necessary information about a puzzle position
 * to be used by ManhattanSearch. In particular, this class defines the order
 * of states that is used by heap.
 * 
*/
public class YAstate implements Comparable<YAstate> 
{
	List<Integer> position;
	protected EightPuzzle puzzle;
	int manhattan;
	int distanceTravelled;
	public YAstate (EightPuzzle puzzle, List<Integer> position)
	{
		this.puzzle = puzzle;
		this.position = position;
		manhattan = puzzle.getManhattan(position);
		distanceTravelled = 0;
	}
	public int getDistance()
	{
		return distanceTravelled;
	}
	public void setDistance(int distance)
	{
		distanceTravelled = distance;
	}
	public List<Integer> getState()
	{
		return position;
	}
	public int compareTo_Man(YAstate o2) 
	{
		return (puzzle.getManhattan(this.position) + distanceTravelled) - (puzzle.getManhattan(o2.position) + o2.distanceTravelled);
	}
	public int compareTo(YAstate o) 
	{
		return puzzle.getRelativeDisarray(this.position, o.position) + (distanceTravelled - o.distanceTravelled);
	}
}
