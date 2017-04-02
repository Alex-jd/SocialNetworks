/**
 * 
 */
package graph;

import java.util.Comparator;
import java.util.Set;

/**
 * @author Алла
 *
 */
public class SortedBySize implements Comparator<Set<Integer>> {

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.util.Comparator#compare(java.lang.Object, java.lang.Object)
	 */
	@Override
	public int compare(Set<Integer> set1, Set<Integer> set2) {
		// TODO Auto-generated method stub
		int size1 = set1.size();
		int size2 = set2.size();
		if (size1 > size2)
			return -1;
		if (size1 < size2)
			return 1;
		return 0;
	}

}
