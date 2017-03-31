package SCPS;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import graph.CapGraph;
import graph.Graph;
import util.GraphLoader;

public class SCPSFilter {
	interface Filter<T> {
		boolean matches(T t);
	}

	private static <T> Set<T> shortestCombination(Filter<Set<T>> filter, List<T> listOfSets) {
		final int size = listOfSets.size();
		System.out.println(size);
		if (size > 20000)
			throw new IllegalArgumentException("Too many combinations");
		// int combinations = 1 << size;
		int combinations = 8000;
		System.out.println(combinations);
		List<Set<T>> possibleSolutions = new ArrayList<Set<T>>();
		for (int l = 0; l < combinations; l++) {
			Set<T> combination = new LinkedHashSet<T>();
			for (int j = 0; j < size; j++) {
				if (((l >> j) & 1) != 0)
					combination.add(listOfSets.get(j));
			}
			possibleSolutions.add(combination);
		}
		// the possible solutions in order of size.
		Collections.sort(possibleSolutions, new Comparator<Set<T>>() {
			public int compare(Set<T> o1, Set<T> o2) {
				return o1.size() - o2.size();
			}
		});
		for (Set<T> possibleSolution : possibleSolutions) {
			if (filter.matches(possibleSolution))
				return possibleSolution;
		}
		return null;
	}

	public static void main(String[] args) {
		Graph g = new CapGraph();
		// GraphLoader.loadGraph(g, "data/twitter_combined.txt");
		GraphLoader.loadGraph(g, "data/facebook_ucsd.txt");
		Map<Integer, HashSet<Integer>> mapTemp = g.exportGraph();
		List<Set<Integer>> listOfSets = new ArrayList<Set<Integer>>();
		for (final Map.Entry<Integer, HashSet<Integer>> entry : mapTemp.entrySet()) {
			listOfSets.add(new LinkedHashSet<Integer>(entry.getValue()));
		}

		Set<Integer> universe = g.getUniverse();
		final Set<Integer> solutionSet = new LinkedHashSet<Integer>(universe);
		Filter<Set<Set<Integer>>> filter = new Filter<Set<Set<Integer>>>() {
			public boolean matches(Set<Set<Integer>> integers) {
				Set<Integer> union = new LinkedHashSet<Integer>();
				for (Set<Integer> ints : integers)
					union.addAll(ints);
				return union.equals(solutionSet);
			}
		};
		Set<Set<Integer>> firstSolution = shortestCombination(filter, listOfSets);
		System.out.println("The shortest combination was " + firstSolution);
	}
}