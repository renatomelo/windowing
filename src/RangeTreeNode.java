
public class RangeTreeNode {
	int x, y;
	RangeTreeNode esq, dir;
	BalancedBST associada; // arvore binaria associada
	Ponto ponto;

	/**
	 * Guarda um ponto 2D na range tree e cria uma árvore balanceada associada a
	 * este nó da árvore
	 * 
	 * @param p ponto 2D
	 */
	RangeTreeNode(Ponto p) {
		this.ponto = p;
		this.x = p.getX();
		this.y = p.getY();
		this.associada = new BalancedBST();
		this.associada.insere(y, ponto); // ordena pela coordenada y
	}
}
