

/**
 * InsertionSorter is one of the standard sorting algorithms.
 * With help of the InsertionSorter our program checks whether
 * the entered position is solvable (valid).
 * 
 * How many permutations are sovable?
 * Exactly half of all possible elements of Perm9 represent
 * solvable puzzle positions. Since no move could possibly 
 * change the evenness of the number of derangements in the
 * permutation, only even permutations are valid positions. By 
 * sorting the given array of numbers we count the derangments
 * in the permutation they give. Therefore, we are able to tell
 * whether given permutation is even and so can be solved.
 *   
 * @author artemvysogorets
 */
	public class InsertionSorter
	{
		Integer[] array = new Integer[8];
		int buffer;
		int count = 0;
		int zeroIndex = 0;
		boolean found = false;
		public InsertionSorter(Integer[] arr) 
		{
			for(int i = 0; i < 9; i++)
			{
				if(arr[i] == 0)
				{
					zeroIndex = (i+3)/3;
					found = true;
				}
				else if(found)
				{
					this.array[i-1] = arr[i];
				}
				if(!found)
				{
					this.array[i] = arr[i];
				}
			}
		}
		public void swap(int i1, int i2)
		{
			buffer = array[i1];
			array[i1] = array[i2];
			array[i2] = buffer;
			count++;
		}

		public void sort() 
		{
			int j = 0;
			int curr = 0;
	        for (int i = 1; i < array.length; i++)
	        {
	        	curr = i;
	        	j = curr - 1;
	        	while(j >= 0 && array[curr] - array[j] < 0)
	        	{
	        		swap(curr, j);
	        		j--;
	        		curr--;
	        	}
	        }
		}
		public int getCount()
		{
			return count;
		}
		public int getZero()
		{
			return zeroIndex;
		}
	}
