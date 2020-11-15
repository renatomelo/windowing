import java.util.ArrayList;
import java.util.List;
import java.util.SortedMap;
import java.util.SortedSet;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.Map.Entry;
/*
 * Representa um nó da árvore de intervalos
 */
public class IntervalTreeNode {
	// Listas para guardar os segmentos que intersectam xmid 
	RangeTree leftRT, rightRT;
	int xmid;
	IntervalTreeNode esq;
	IntervalTreeNode dir;

	public IntervalTreeNode() {
		leftRT = new RangeTree();
		rightRT = new RangeTree();
		xmid = 0;
		esq = null;
		dir = null;
	}

	/**
	 * Constroi uma arvore a partir de uma lista de intervalos dada
	 * @param intervalList lista de intervalos 
	 */
	public IntervalTreeNode(List<Intervalo> intervalList) {
		leftRT = new RangeTree();
		rightRT = new RangeTree();
		SortedSet<Integer> endpoints = new TreeSet<Integer>();

		for (Intervalo interval : intervalList) {
			endpoints.add(interval.min);
			endpoints.add(interval.max);
		}

		//Escolhe o endpoint do meio para ser o xmid
		int median = getMedian(endpoints);
		xmid = median;
		
		// Listas para guardar os segmentos que não intersectam com xmid 
		List<Intervalo> left = new ArrayList<Intervalo>();
		List<Intervalo> right = new ArrayList<Intervalo>();

		for (Intervalo i : intervalList) {
			if (i.max < median)
				left.add(i);
			else if (i.min > median)
				right.add(i);
			else { // O intervalo i contem xmid
				leftRT.insere(i.seg.esquerdo);
				rightRT.insere(i.seg.direito);
			}
		}
		
		if (left.size() > 0)
			esq = new IntervalTreeNode(left);

		if (right.size() > 0)
			dir = new IntervalTreeNode(right);
	}

	Integer getMedian(SortedSet<Integer> set) {
		int i = 0;
		int middle = set.size() / 2;
		for (Integer point : set) {
			if (i == middle)
				return point;
			i++;
		}
		return null;
	}
}
