public class Intervalo implements Comparable<Intervalo> {
	int min, max;
	Segmento seg; //

	public Intervalo(int xmin, int xmax) {
		if (xmax < xmin)
			throw new RuntimeException("Argumento inválido");
		min = xmin;
		max = xmax;
		seg = null;
	}

	/**
	 * Cria um intervalo a partir de um segmento paralelo a um dos eixos do
	 * plano
	 * 
	 * @param s segmento horizontal ou vertical
	 */
	public Intervalo(Segmento s) {
		if (s.ehHorizontal()) {
			min = s.esquerdo.getX();
			max = s.direito.getX();
		} else if (s.ehVertical()) {
			min = s.esquerdo.getY();
			max = s.direito.getY();
		} else
			throw new RuntimeException("Inserir apenas segmentos horizontais/verticais");
		s.setIntervalo(this);
		seg = s;
	}

	/**
	 * Verifica se este intervalo contém um valor x
	 * 
	 * @param x
	 * @return verdadeiro ou falso
	 */
	boolean contem(int x) {
		return (min <= x) && (x <= max);
	}

	boolean intersecta(Intervalo i) {
		if (i.max < min)
			return false;
		if (max < i.min)
			return false;
		return true;
	}

	/**
	 * Compara este intervalo com um intervalo dado.
	 * 
	 * @param Intervalo
	 * @return -1 se i é menor, 0 se for igual e 1 caso contrario
	 */
	public int compareTo(Intervalo i) {
		if (min < i.min)
			return -1;
		else if (min > i.min)
			return +1;
		else if (max < i.max)
			return -1;
		else if (max > i.max)
			return +1;
		else
			return 0;
	}

	public String toString() {
		return "[" + min + ", " + max + "]";
	}

	public static void main(String[] args) {
		Intervalo a = new Intervalo(10, 12);
		System.out.println(a);
	}
}
