
/*
 * Representa um ponto no plano 2D
 */
public class Ponto {
	private int x;
	private int y;

	Segmento segmento;
	
	public Ponto(int x, int y) {
		this.x = x;
		this.y = y;
	}

	/**
	 * Guarda um segmento de reta associado a este ponto
	 */
	public void setSegmento(Segmento segment) {
		this.segmento = segment;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public Segmento getSegmento() {
		return segmento;
	}
	
	public String toString(){
		int x = (int) this.getX();
		int y = (int) this.getY();
//		return ("("+ x + ", "+y+")");
		return ( x+" "+y);
	}
}
