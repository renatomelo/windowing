
/*
 * Estrututa que representa um retângulo 2D paralelo aos eixos do plano
 */
public class Intervalo2D {
	final Intervalo xInt;
	final Intervalo yInt;

	public Intervalo2D(int x1, int x2, int y1, int y2) {
		this.xInt = new Intervalo(x1, x2);
		this.yInt = new Intervalo(y1, y2);
	}

	public Intervalo2D(Intervalo x, Intervalo y) {
		this.xInt = x;
		this.yInt = y;
	}

	boolean contem(int x, int y) {
		return xInt.contem(x) && yInt.contem(y);
	}

	public String toString() {
		return xInt + " x " + yInt;
	}

}
