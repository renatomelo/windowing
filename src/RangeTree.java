

import java.util.LinkedList;
import java.util.List;

public class RangeTree {
	RangeTreeNode raiz;

	/**
	 * Guarda as extremidades de um segmento dado na árvore 
	 * @param seg segmento em qualquer orientação
	 */
	void insere(Segmento seg) {
		raiz = inserir(raiz, seg.esquerdo);
		raiz = inserir(raiz, seg.direito);
	}

	void insere(Ponto p) {
		raiz = inserir(raiz, p);
	}

	RangeTreeNode inserir(RangeTreeNode r, Ponto p) {
		if (r == null)
			return new RangeTreeNode(p);

		r.associada.insere(p.getY(), p);

		if (p.getX() < r.x)
			r.esq = inserir(r.esq, p);
		else
			r.dir = inserir(r.dir, p);

		return r;
	}

	void exibe() {
		System.out.println("Conteudo da range tree: ");
		exibir(raiz);
	}

	void exibir(RangeTreeNode r) {
		if (r == null)
			return;
		exibir(r.esq);
		System.out.print("(" + r.x + ", " + r.y + ") -> ");
		r.associada.exibe();
		System.out.println();
		exibir(r.dir);
	}

	List<Segmento> buscar(Intervalo2D caixa) {
		List<Segmento> result = new LinkedList<Segmento>();
		Intervalo intX = caixa.xInt;
		RangeTreeNode r = raiz;
		while (r != null && !intX.contem(r.x)) {
			if (intX.max < r.x)
				r = r.esq;
			else if (r.x < intX.min)
				r = r.dir;
		}

		if (r == null)
			return null;

		if (caixa.contem(r.x, r.y)){
			result.add(r.ponto.segmento);
		}
		List<Segmento> left = buscaEsq(r.esq, caixa);
		if(left != null)
			result.addAll(left);
		
		List<Segmento> right = buscaDir(r.dir, caixa);
		if(right != null)
			result.addAll(right);
		
		return result;
	}

	List<Segmento> buscaEsq(RangeTreeNode r, Intervalo2D caixa) {
		List<Segmento> result = new LinkedList<Segmento>();
		if (r == null)
			return null;
		if (caixa.contem(r.x, r.y)){
			result.add(r.ponto.getSegmento());
		}
			
		if (r.x >= caixa.xInt.min) {
			List<Segmento> enumerados = enumerar(r.dir, caixa);
			if(enumerados != null) 
				result.addAll(enumerados);
			
			List<Segmento> left = buscaEsq(r.esq, caixa);
			if (left != null) 
				result.addAll(left);
		} else {
			List<Segmento> left = buscaEsq(r.dir, caixa);
			if (left != null) 
				result.addAll(left);
		}
		return result;
	}

	List<Segmento> buscaDir(RangeTreeNode r, Intervalo2D caixa) {
		List<Segmento> result = new LinkedList<Segmento>();
		if (r == null)
			return null;
		if (caixa.contem(r.x, r.y)){
			result.add(r.ponto.segmento);
		}
		if (caixa.xInt.max >= r.x) {
			List<Segmento> enumerados = enumerar(r.esq, caixa);
			if(enumerados != null) 
				result.addAll(enumerados);
			
			List<Segmento> right = buscaDir(r.dir, caixa);
			if(right != null) 
				result.addAll(right);
		} else {
			List<Segmento> right = buscaDir(r.esq, caixa);
			if(right != null) 
				result.addAll(right);
		}
		
		return result;
	}

	List<Segmento> enumerar(RangeTreeNode r, Intervalo2D caixa) {
		List<Segmento> result = new LinkedList<Segmento>();
		if (r == null)
			return null;
		Iterable<Integer> lista = r.associada.range(caixa.yInt);
		for (Integer y : lista) {
			Ponto p = r.associada.getPonto(y);
			result.add(p.segmento);
		}
		return result;
	}

	public static void main(String[] args) {
		int m = 5, n = 10;

		RangeTree rt = new RangeTree();

		rt.insere(new Segmento(0, 1, 0, 10));
		rt.insere(new Segmento(10, 0, 20, 0));
		rt.insere(new Segmento(1, 1, 10,10));
		
		rt.insere(new Segmento(12, 4, 16, 4));
		rt.insere(new Segmento(11, 7, 11, 11));
		rt.insere(new Segmento(15, 5, 15, 8));
		rt.insere(new Segmento(4, 2, 6, 2));
		rt.insere(new Segmento(7, 7, 7, 13));
		rt.insere(new Segmento(11, 12, 11, 14));
		rt.insere(new Segmento(8, 4, 11, 4));
		
		Intervalo intX = new Intervalo(0, 5);
		Intervalo intY = new Intervalo(0, 5);
		Intervalo2D caixa = new Intervalo2D(intX, intY);
		
		Intervalo intX2 = new Intervalo(8, 12);
		Intervalo intY2 = new Intervalo(8, 12);
		Intervalo2D caixa2 = new Intervalo2D(intX2, intY2);
		
		Intervalo intX3 = new Intervalo(10, 16);
		Intervalo intY3 = new Intervalo(2, 6);
		Intervalo2D caixa3 = new Intervalo2D(intX3, intY3);
		
		rt.exibe();
		
		System.out.println("\nResultado da busca");
		System.out.println("Caixa 1: ");
		rt.buscar(caixa);
		
		System.out.println("\nCaixa 2: ");
		rt.buscar(caixa2);
		
		System.out.println("\nCaixa 3: ");
		rt.buscar(caixa3);
	}
	
}
