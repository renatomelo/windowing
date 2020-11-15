
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

/**
 * Lê da entrada padrão um conjunto de segmentos e janelas de buscas. Imprime
 * na saida padrão os índices de todos os segmentos que intersectam as
 * janelas de busca. 
 * 
 * Compilação: make
 * Execução: java Windowing < entrada.txt
 * 
 * @param args
 */
public class Windowing {
	// Arvore para salvar os segmentos paralelos ao eixo x
	IntervalTree horizontais = new IntervalTree();
	IntervalTree verticais = new IntervalTree(); // Segmentos paralelos ao
													// eixo y
	RangeTree ranget = new RangeTree();

	/**
	 * Insere os segmentos em três avores. Os segmentos horizontais e verticais
	 * são armazenados em arvores de intervalos, enquanto uma Range Tree salva
	 * todos os segmentos.
	 * 
	 * @param lista
	 *            conjunto de sementos
	 */
	void addSegmentos(List<Segmento> lista) {
		for (Segmento s : lista) {
			if (s.ehHorizontal())
				horizontais.addSegmento(s);
			else if (s.ehVertical())
				verticais.addSegmento(s);

			ranget.insere(s);
		}
	}

	/**
	 * Encontra todos os intervalos que interceptam um determinado retângulo no
	 * plano. Com a restrição de que todos os segmentos que cruzam o retângulo
	 * devem ser horizontais ou verticais.
	 * 
	 * @param caixa
	 *            Retângulo 2D paralelo aos eixos
	 * @return um conjunto de segmentos que intersectam a caixa
	 */
	List<Segmento> query(Intervalo2D caixa) {
		List<Segmento> l = new LinkedList<>();
		// busca na range tree
		for (Segmento s : ranget.buscar(caixa)) {
			if (!s.encontrado) {
				s.encontrado = true;
				l.add(s);
			}
		}
		// busca nas duas interval tree
		l.addAll(horizontais.hQuery(caixa));
		l.addAll(verticais.vQuery(caixa));
		return l;
	}

	public static void main(String[] args) {
		Windowing w = new Windowing();
		List<Segmento> segmentos = new LinkedList<>();
		List<Intervalo2D> caixas = new LinkedList<>();

		Scanner in = new Scanner(System.in);

		int n = in.nextInt();
		int m = in.nextInt();
		in.nextLine();

		// lê os segmentos
		int j = 1;
		while (j <= n) {
			int x1 = in.nextInt();
			int x2 = in.nextInt();
			int y1 = in.nextInt();
			int y2 = in.nextInt();
			Segmento s = new Segmento(x1, y1, x2, y2);
			s.setIndice(j);
			segmentos.add(s);
			in.nextLine();
			j++;
		}
		// lê as janelas de busca
		int i = 0;
		while (i < m) {
			int x1 = in.nextInt();
			int x2 = in.nextInt();
			int y1 = in.nextInt();
			int y2 = in.nextInt();

			caixas.add(new Intervalo2D(x1, x2, y1, y2));

			in.nextLine();
			i++;
		}

		// realiza o pre-processamento
		w.addSegmentos(segmentos);

		for (Intervalo2D cx : caixas) {
			for (Segmento s : w.query(cx)) {
				System.out.print(" " + s.indice);
				s.encontrado = false;
			}
			System.out.println();
		}

	}
}
