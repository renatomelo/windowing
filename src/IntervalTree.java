
import java.util.ArrayList;
import java.util.List;

/*
 * Uma árvore de intervalos para guardar os segmentos que são 
 * paralelos aos eixos do plano
 */
public class IntervalTree {
	IntervalTreeNode raiz;
	List<Intervalo> intervalos;
	boolean inSync;
	int size;

	IntervalTree() {
		this.raiz = new IntervalTreeNode();
		this.intervalos = new ArrayList<>();
		this.inSync = true;
		this.size = 0;
	}

	IntervalTree(List<Intervalo> intervalos) {
		this.raiz = new IntervalTreeNode(intervalos);
		this.intervalos = new ArrayList<Intervalo>();
		this.intervalos.addAll(intervalos);
		this.inSync = true;
		this.size = intervalos.size();
	}

	/**
	 * Encontra todos os segmentos horizontais que cruzam um 
	 * retângulo dado
	 * 
	 * @param box retângulo 2D de consulta
	 * @return Lista de segmentos que cruzam box
	 */
	List<Segmento> hQuery(Intervalo2D box) {
		build();
		return hQuery(raiz, box.xInt.min, box.yInt);
	}

	/**
	 * Encontra todos os segmentos horizontais que cruzam um 
	 * segmento dado que é ortogonal aos segmentos armazenados na árvore
	 * @param raiz raiz da árvore
	 * @param min 
	 * @param orto intervalo 1D ortogonal aos segmentos da arvore
	 * @return Lista de segmentos que cruzam box no ponto min
	 */
	List<Segmento> hQuery(IntervalTreeNode raiz, int min, Intervalo orto) {
		List<Segmento> result = new ArrayList<>();

		// Constroi uma janela do tipo [-infinito, x] x [y, y']
		Intervalo xMin = new Intervalo(Integer.MIN_VALUE, min);
		Intervalo2D janelaEsquerda = new Intervalo2D(xMin, orto);

		List<Segmento> left = raiz.leftRT.buscar(janelaEsquerda);
		if (left != null) {
			for (Segmento s : left) {

				// caso o intervalo do segmento contenha
				// o ponto caixa.xint.min salva em result
				if (s.intervalo.contem(min) && !s.encontrado) {
					s.encontrado = true;
					result.add(s);
				}
			}
		}

		// Constroi uma janela do tipo [x, +infinito] x [y, y']
		Intervalo xMax = new Intervalo(min, Integer.MAX_VALUE);
		Intervalo2D janelaDireita = new Intervalo2D(xMax, orto);

		List<Segmento> right = raiz.rightRT.buscar(janelaDireita);
		if (right != null) {
			for (Segmento s : right) {
				// caso o intervalo do segmento contenha
				// o ponto caixa.xint.min salva em result
				if (s.intervalo.contem(min) && !s.encontrado) {
					s.encontrado = true;
					result.add(s);
				}
			}
		}

		if (min < raiz.xmid && raiz.esq != null) {
			List<Segmento> resp = hQuery(raiz.esq, min, orto);
			for (Segmento s : resp) {
				result.add(s);
			}
		} else if (min > raiz.xmid && raiz.dir != null) {
			List<Segmento> buscadir = hQuery(raiz.dir, min, orto);
			for (Segmento s : buscadir) {
				result.add(s);
			}
		}
		return result;
	}

	/**
	 * Encontra todos os segmentos verticais que cruzam um 
	 * retângulo dado
	 * 
	 * @param box retângulo 2D de consulta
	 * @return Lista de segmentos que cruzam box
	 */
	List<Segmento> vQuery(Intervalo2D box) {
		build();
		return vQuery(raiz, box.yInt.min, box.xInt);
	}

	/**
	 * Encontra todos os segmentos verticais que cruzam um 
	 * segmento dado que é ortogonal aos segmentos armazenados na árvore
	 * @param raiz raiz da árvore
	 * @param min 
	 * @param orto intervalo 1D ortogonal aos segmentos da arvore
	 * @return Lista de segmentos que cruzam box no ponto min
	 */
	List<Segmento> vQuery(IntervalTreeNode raiz, int min, Intervalo orto) {
		List<Segmento> result = new ArrayList<>();

		Intervalo yMin = new Intervalo(Integer.MIN_VALUE, min);
		Intervalo2D janelaSup = new Intervalo2D(orto, yMin);

		List<Segmento> left = raiz.leftRT.buscar(janelaSup);
		if (left != null) {
			for (Segmento s : left) {
				// caso o intervalo do segmento contenha
				// o ponto caixa.xint.min salva em result
				if (s.intervalo.contem(min) && !s.encontrado) {
					s.encontrado = true;
					result.add(s);
				}
			}
		}

		Intervalo yMax = new Intervalo(min, Integer.MAX_VALUE);
		Intervalo2D janelaInf = new Intervalo2D(orto, yMax);

		List<Segmento> right = raiz.rightRT.buscar(janelaInf);
		if (right != null) {
			for (Segmento s : right) {
				// caso o intervalo do segmento contenha
				// o ponto caixa.xint.min salva em result
				if (s.intervalo.contem(min) && !s.encontrado) {
					s.encontrado = true;
					result.add(s);
				}
			}
		}

		if (min < raiz.xmid && raiz.esq != null) {
			List<Segmento> resp = vQuery(raiz.esq, min, orto);
			for (Segmento s : resp) {
				result.add(s);
			}
		} else if (min > raiz.xmid && raiz.dir != null) {
			List<Segmento> buscadir = vQuery(raiz.dir, min, orto);
			for (Segmento s : buscadir) {
				result.add(s);
			}
		}

		return result;
	}

	void addInterval(Intervalo interval) {
		intervalos.add(interval);
		inSync = false;
	}

	void addSegmento(Segmento s) {
		intervalos.add(new Intervalo(s));
		inSync = false;
	}

	/**
	 * Constroi a arvore de intervalos a partir da lista de segmentos
	 * horizontais/verticais
	 */
	void build() {
		if (!inSync) {
			raiz = new IntervalTreeNode(intervalos);
			inSync = true;
			size = intervalos.size();
		}
	}

	/*
	 * Objetivo: ler um conjunto de segmentos, armazernar em uma arvore de
	 * intervalos e depois pesquisar quais segmentos horizontais/verticais
	 * interceptam um determinado intervalo que é ortogonal aos segmentos.
	 * 
	 * ## mas a arvore armazena intervalos não segmentos - dado que os segmentos
	 * são apenas horizontais (ou verticais) o que dá pra fazer? ## Inserir
	 * todos os segmentos horizontais em uma arvore de intervalos - Encontrar a
	 * ponta do meio para ser a raiz de cada subarvore - guardar as pontas dos
	 * intervalos do meio em uma range tree - uma range tree para cada lado
	 */
	public static void main(String[] args) {
		// Arvore para salvar os segmentos paralelos ao eixo x
		IntervalTree horizontais = new IntervalTree();
		IntervalTree verticais = new IntervalTree(); // Segmentos paralelos ao
														// eixo y

		horizontais.addSegmento(new Segmento(10, 0, 20, 0));
		horizontais.addSegmento(new Segmento(12, 4, 16, 4));
		horizontais.addSegmento(new Segmento(4, 2, 6, 2));
		horizontais.addSegmento(new Segmento(8, 4, 11, 4));
		horizontais.addSegmento(new Segmento(7, 10, 13, 10));
		horizontais.addSegmento(new Segmento(7, 11, 13, 11));
		horizontais.addSegmento(new Segmento(13, 9, 18, 9));
		horizontais.addSegmento(new Segmento(2, 9, 6, 9));
		horizontais.addSegmento(new Segmento(-1, 3, 6, 3));
		horizontais.addSegmento(new Segmento(9, 3, 17, 3));

		Intervalo intX = new Intervalo(0, 5);
		Intervalo intY = new Intervalo(0, 5);
		Intervalo2D caixa = new Intervalo2D(intX, intY);
		System.out.println(caixa);
		List<Segmento> r1 = horizontais.hQuery(caixa);

		Intervalo intX2 = new Intervalo(8, 12);
		Intervalo intY2 = new Intervalo(8, 12);
		Intervalo2D caixa2 = new Intervalo2D(intX2, intY2);
		System.out.println();
		System.out.println(caixa2);
		List<Segmento> r2 = horizontais.hQuery(caixa2);

		Intervalo intX3 = new Intervalo(10, 16);
		Intervalo intY3 = new Intervalo(2, 6);
		Intervalo2D caixa3 = new Intervalo2D(intX3, intY3);
		System.out.println();
		System.out.println(caixa3);
		List<Segmento> r3 = horizontais.hQuery(caixa3);

		Intervalo intX4 = new Intervalo(16, 18);
		Intervalo intY4 = new Intervalo(8, 10);
		Intervalo2D caixa4 = new Intervalo2D(intX4, intY4);
		System.out.println();
		System.out.println(caixa4);
		List<Segmento> r4 = horizontais.hQuery(caixa4);

		System.out.print("\nResultados da busca");
		System.out.print("\nQuem intersecta " + caixa + ": ");
		for (Segmento r : r1)
			System.out.print(r + " ");

		System.out.print("\nQuem intersecta " + caixa2 + ": ");
		for (Segmento r : r2)
			System.out.print(r + " ");

		System.out.print("\nQuem intersecta " + caixa3 + ": ");
		for (Segmento r : r3)
			System.out.print(r + " ");

		System.out.print("\nQuem intersecta " + caixa4 + ": ");
		for (Segmento r : r4)
			System.out.print(r + " ");

		System.out.println();

		verticais.addSegmento(new Segmento(0, 1, 0, 10));
		verticais.addSegmento(new Segmento(11, 7, 11, 11));
		verticais.addSegmento(new Segmento(15, 5, 15, 8));
		verticais.addSegmento(new Segmento(7, 7, 7, 13));
		verticais.addSegmento(new Segmento(11, 12, 11, 14));
		verticais.addSegmento(new Segmento(1, -1, 1, 6));
		verticais.addSegmento(new Segmento(11, 1, 11, 7));

		List<Segmento> r5 = verticais.vQuery(caixa);
		System.out.print("\nQuem intersecta " + caixa + ": ");
		for (Segmento r : r5)
			System.out.print(r + " ");

		List<Segmento> r6 = verticais.vQuery(caixa2);
		System.out.print("\nQuem intersecta " + caixa2 + ": ");
		for (Segmento r : r6)
			System.out.print(r + " ");

		List<Segmento> r7 = verticais.vQuery(caixa3);
		System.out.print("\nQuem intersecta " + caixa3 + ": ");
		for (Segmento r : r7)
			System.out.print(r + " ");
	}
}
