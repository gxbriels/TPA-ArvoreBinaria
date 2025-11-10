package lib;

import java.util.Comparator;

public class ArvoreAVLExemplo<T> extends ArvoreBinariaExemplo<T> {

    public ArvoreAVLExemplo(Comparator<T> comparator) {
        super(comparator);
    }

    // --- MÉTODOS AUXILIARES PARA AVL ---

    /**
     * Obtém a altura de um nó (retorna -1 se o nó for nulo,
     * seguindo a convenção da interface IArvoreBinaria).
     */
    private int getHeight(NoExemplo<T> no) {
        if (no == null) {
            return -1;
        }
        return no.getAltura();
    }

    /**
     * Atualiza a altura de um nó com base na altura de seus filhos.
     * A altura é 1 + a maior altura entre os dois filhos.
     */
    private void updateHeight(NoExemplo<T> no) {
        if (no != null) {
            no.setAltura(1 + Math.max(getHeight(no.getFilhoEsquerda()), getHeight(no.getFilhoDireita())));
        }
    }

    /**
     * Calcula o Fator de Balanceamento (FB) de um nó.
     * FB = Altura(Esquerda) - Altura(Direita)
     */
    private int getBalance(NoExemplo<T> no) {
        if (no == null) {
            return 0;
        }
        return getHeight(no.getFilhoEsquerda()) - getHeight(no.getFilhoDireita());
    }

    // --- MÉTODOS DE ROTAÇÃO ---

    /**
     * Rotação Simples à Direita (Caso Esquerda-Esquerda)
     */
    private NoExemplo<T> rotateRight(NoExemplo<T> y) {
        NoExemplo<T> x = y.getFilhoEsquerda();
        NoExemplo<T> T2 = x.getFilhoDireita();

        // Realiza a rotação
        x.setFilhoDireita(y);
        y.setFilhoEsquerda(T2);

        // Atualiza as alturas (ORDEM IMPORTA: primeiro y, depois x)
        updateHeight(y);
        updateHeight(x);

        // Retorna a nova raiz da subárvore
        return x;
    }

    /**
     * Rotação Simples à Esquerda (Caso Direita-Direita)
     */
    private NoExemplo<T> rotateLeft(NoExemplo<T> x) {
        NoExemplo<T> y = x.getFilhoDireita();
        NoExemplo<T> T2 = y.getFilhoEsquerda();

        // Realiza a rotação
        y.setFilhoEsquerda(x);
        x.setFilhoDireita(T2);

        // Atualiza as alturas (ORDEM IMPORTA: primeiro x, depois y)
        updateHeight(x);
        updateHeight(y);

        // Retorna a nova raiz da subárvore
        return y;
    }

    // --- SOBRESCRITA DO MÉTODO ADICIONAR ---

    /**
     * Sobrescreve o método adicionar para incluir o balanceamento AVL.
     *
     */
    @Override
    public void adicionar(T novoValor) {
        // A raiz deve ser atualizada, pois pode mudar com as rotações
        this.raiz = adicionar(this.raiz, novoValor);
    }

    /**
     * Método recursivo PRIVADO para adicionar e balancear.
     * (Este método é NOVO para a AVL, ele não reusa o da classe pai
     * porque precisa fazer o balanceamento no retorno da recursão).
     */
    private NoExemplo<T> adicionar(NoExemplo<T> no, T novoValor) {
        // --- 1. Inserção Padrão (igual à ArvoreBinariaExemplo) ---
        if (no == null) {
            return new NoExemplo<>(novoValor);
        }

        int comparacao = comparador.compare(novoValor, no.getValor());

        if (comparacao < 0) {
            no.setFilhoEsquerda(adicionar(no.getFilhoEsquerda(), novoValor));
        } else if (comparacao > 0) {
            no.setFilhoDireita(adicionar(no.getFilhoDireita(), novoValor));
        } else {
            // Valores duplicados não são inseridos
            return no;
        }

        // --- 2. Atualização da Altura ---
        // Atualiza a altura do nó ancestral atual
        updateHeight(no);

        // --- 3. Cálculo do Balanceamento e Rotações ---
        int balance = getBalance(no);

        // Caso 1: Esquerda-Esquerda (Rotação Simples à Direita)
        if (balance > 1 && comparador.compare(novoValor, no.getFilhoEsquerda().getValor()) < 0) {
            return rotateRight(no);
        }

        // Caso 2: Direita-Direita (Rotação Simples à Esquerda)
        if (balance < -1 && comparador.compare(novoValor, no.getFilhoDireita().getValor()) > 0) {
            return rotateLeft(no);
        }

        // Caso 3: Esquerda-Direita (Rotação Dupla)
        if (balance > 1 && comparador.compare(novoValor, no.getFilhoEsquerda().getValor()) > 0) {
            no.setFilhoEsquerda(rotateLeft(no.getFilhoEsquerda()));
            return rotateRight(no);
        }

        // Caso 4: Direita-Esquerda (Rotação Dupla)
        if (balance < -1 && comparador.compare(novoValor, no.getFilhoDireita().getValor()) < 0) {
            no.setFilhoDireita(rotateRight(no.getFilhoDireita()));
            return rotateLeft(no);
        }

        // Retorna o nó (potencialmente) balanceado
        return no;
    }

    /**
     * A classe ArvoreBinariaExemplo calcula a altura de forma
     * recursiva O(n). A AVL armazena a altura no nó O(1).
     * Podemos sobrescrever o método altura() para ser mais eficiente.
     */
    @Override
    public int altura() {
        return getHeight(this.raiz);
    }
}