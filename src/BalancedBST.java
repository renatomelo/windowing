
import java.util.LinkedList;
import java.util.Queue;

/*
 * Representa uma arvore binária balanceada (Randomized BST)
 * Considera que todos os pontos possuem coordenadas y diferentes
 */
public class BalancedBST {
	Node raiz;
	
	private class Node{
		int chave;
		Ponto ponto;
		Node esq, dir;
		int n;
		
		public Node (int chave, Ponto ponto){
			this.chave = chave;
			this.ponto = ponto;
			this.n = 1;
		}
	}
	
	boolean contem(int chave){
		return (getPonto(chave) != null);
	}
	
	Ponto getPonto(int chave){
		return getPonto(raiz, chave);
	}
	
	Ponto getPonto(Node r, int ch) {
		if(r == null) 
			return null;
		if(r.chave == ch) 
			return r.ponto;
		else if(ch < r.chave)
			return getPonto(r.esq, ch);
		else 
			return getPonto(r.dir, ch);
	}

	void insere(int chave, Ponto ponto) {
		raiz = inserir(raiz, chave, ponto);
	}

	Node inserir(Node r, int ch, Ponto p) {
		if(r == null ) 
			return new Node(ch, p);
		if (r.chave == ch) {
			r.ponto = p;
			return r;
		}
		if(Math.random()*(r.n + 1) < 1)
			return inserirRaiz(r, ch, p);
		if(ch < r.chave)
			r.esq = inserir(r.esq, ch, p);
		else
			r.dir = inserir(r.dir, ch, p);
		fix(r);
		return r;
	}
	
	Node inserirRaiz(Node r, int ch, Ponto ponto) {
		if(r == null)
			return new Node(ch, ponto);
		if(ch == r.chave){
			r.ponto = ponto;
			return r;
		} else if(ch < r.chave ){
			r.esq = inserirRaiz(r.esq, ch, ponto);
			r = rotDireira(r);
		} else {
			r.dir = inserirRaiz(r.dir, ch, ponto);
			r = rotEsquerda(r);
		}
		return r;
	}

	Node rotDireira(Node r) {
		Node x = r.esq;
		r.esq = x.dir;
		x.dir = r;
		fix(r);
		fix(x);
		return x;
	}
	
	Node rotEsquerda(Node r) {
		Node x = r.dir;
		r.dir = x.esq;
		x.esq = r;
		fix(r);
		fix(x);
		return x;
	}
	
	void fix(Node x) {
		if(x == null)
			return;
		x.n = 1 + tamanho(x.esq) + tamanho(x.dir);
	}

	int tamanho(Node r) {
		if(r == null) return 0;
		else return r.n;
	}

	int altura(Node r) {
		if(r == null) return 0;
		
		return 1 + Math.max(altura(r.esq), altura(r.dir));
	}
	
	void exibe(){
		System.out.print("bst: ");
		exibir(raiz);
	}

	void exibir(Node r) {
		if(r == null) return;
		exibir(r.esq);
		System.out.print("("+r.chave+", "+r.ponto+") ");
		exibir(r.dir);
	}

	Iterable<Integer> range(Intervalo intervalo) {
		Queue<Integer> fila = new LinkedList<>();
		range(raiz, intervalo, fila);
		return fila;
	}

	void range(Node r, Intervalo intervalo, Queue<Integer> fila) {
		if(r == null) 
			return;
		if(r.chave >= intervalo.min)
			range(r.esq, intervalo, fila);
		if(intervalo.contem(r.chave))
			fila.add(r.chave);
		if(intervalo.max >= r.chave)
			range(r.dir, intervalo, fila);
	}
	
	boolean verifica() {
		return verificar() && ehBST();
	}
	
	boolean ehBST() {
		return ehBST(raiz, min(), max());
	}

	boolean ehBST(Node r, int min, int max) {
		if(r == null) return true;
		if((r.chave < min) || (max < r.chave))
			return false;
		return ehBST(r.esq, min, r.chave) && ehBST(r.dir, r.chave, max);
	}

	boolean verificar() {
		return verificar(raiz);
	}

	boolean verificar(Node r) {
		if(r == null) return true;
		
		return verificar(r.esq) && verificar(r.dir) && 
			   (r.n == 1 + tamanho(r.esq) + tamanho(r.dir));
	}
	
	int min(){
		int ch = 0;
		for(Node aux = raiz; aux != null; aux = aux.esq)
			ch = aux.chave;
		return ch;
	}
	int max(){
		int ch = 0;
		for(Node aux = raiz; aux != null; aux = aux.dir)
			ch = aux.chave;
		return ch;
	}

}
