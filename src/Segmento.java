//Representa um segmento de reta
public class Segmento implements Comparable<Segmento> {
	
	Ponto esquerdo; // ponta esquerda/inferior
	Ponto direito; // ponta direita/superior
	
	// marca quando uma das pontas já foi encontrada
	// para não devolver duas vezes o mesmo segmento usando a Range Tree
	boolean encontrado = false;
	
	//Mantem o intervalo associado a este segmento (util para IntervalTree)
	Intervalo intervalo;
	int indice; 
	
	public Segmento(Ponto esq, Ponto dir) {
		if (esq.getX() > dir.getX()) {
			Ponto aux = esq;
			esq = dir;
			dir = aux;
		}
		
		esq.setSegmento(this);
		dir.setSegmento(this);
		
		this.esquerdo = esq;
		this.direito = dir;
	}
	
	public Segmento (int x1, int y1, int x2, int y2){
		this(new Ponto(x1, y1), new Ponto(x2, y2));
	}
	
	public boolean ehHorizontal(){
		return (esquerdo.getY() == direito.getY());
	}
	
	boolean ehVertical(){
		return (esquerdo.getX() == direito.getX());
	}
	
	void setIntervalo(Intervalo intervalo) {
		this.intervalo = new Intervalo(intervalo.min, intervalo.max);
		
	}

	void setIndice(int j) {
		this.indice = j;
	}
	
	//Compara pela coordenada esquerda.getY()
	@Override
	public int compareTo(Segmento s) {
		if(esquerdo.getY() < s.esquerdo.getY())
			return -1;
		else if(esquerdo.getY() > s.esquerdo.getY())
			return +1;
		else if(direito.getY() < s.direito.getY())
			return -1;
		else if(direito.getY() > s.direito.getY())
			return +1;
		else if(esquerdo.getX() < s.esquerdo.getX())
			return -1;
		else if(esquerdo.getX() > s.esquerdo.getX())
			return +1;
		else if(direito.getX() < s.direito.getX())
			return -1;
		else if(direito.getX() > s.direito.getX())
			return +1;
		return 0;
	}
	
	public String toString(){
		String s = "";
		if(ehHorizontal())
			s = "h: ";
		else if (ehVertical()) 
			s = "v: ";
		s = s + "("+ esquerdo.getX()+", "+ esquerdo.getY()+") -> ("
					+direito.getX()+", "+ direito.getY()+")";
		return s;
	}
	
	public static void main(String[] args) {
		Segmento a, b, c;
		a = new Segmento(15, 20, 15, 60);
		b = new Segmento(10, 40, 35, 40);
		c = new Segmento(20, 40, 30, 40);

		System.out.println("a = "+a);
		System.out.println("b = "+b);
		System.out.println("c = "+c);
	}
}
