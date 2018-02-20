package structures;

import java.util.Comparator;
/*
 * This class is a comparator used by the heap in ManhattanSearch.
 */
public class ManhattanComparator implements Comparator<YAstate> 
{
	public int compare(YAstate o1, YAstate o2) 
	{
		return o1.compareTo_Man(o2);
	}

}
