/*
 * Versão preenchida da ArvoreBinariaExemplo
 * Esta classe implementa a interface IArvoreBinaria
 */
package lib;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedList; // Importar para usar na Fila (Queue)
import java.util.Queue;     // Importar para usar na Fila (Queue)

/**
 *
 * @author victoriocarvalho
 * (Implementação preenchida por Gemini)
 */
public class ArvoreBinariaExemplo<T> implements IArvoreBinaria<T> {

    protected NoExemplo<T> raiz = null;
    protected Comparator<T> comparador;

    public ArvoreBinariaExemplo(Comparator<T> comp) {
        comparador = comp;
    }

    // --- MÉTODO ADICIONAR ---

    @Override
    public void adicionar(T novoValor) {
        // O método público apenas chama o método recursivo privado, começando pela raiz
        raiz = adicionar(raiz, novoValor);
    }

    /**
     * Método recursivo privado para adicionar.
     * Ele navega na árvore usando o comparador e encontra a posição correta
     * para inserir o novo nó.
     */
    private NoExemplo<T> adicionar(NoExemplo<T> no, T novoValor) {
        // 1. Caso Base: Se o nó atual é nulo, encontramos o local de inserção.
        if (no == null) {
            return new NoExemplo<>(novoValor);
        }

        // 2. Caso Recursivo: Compara o novo valor com o valor do nó atual
        int comparacao = comparador.compare(novoValor, no.getValor());

        if (comparacao < 0) {
            // Se novoValor é MENOR, vai para a esquerda
            no.setFilhoEsquerda(adicionar(no.getFilhoEsquerda(), novoValor));
        } else if (comparacao > 0) {
            // Se novoValor é MAIOR, vai para a direita
            no.setFilhoDireita(adicionar(no.getFilhoDireita(), novoValor));
        } else {
            // Se for IGUAL, não faz nada (não permitimos duplicatas neste exemplo)
            // Você pode ajustar isso se a regra do trabalho for diferente.
        }

        return no; // Retorna o nó (com a subárvore atualizada)
    }

    // --- MÉTODO PESQUISAR (pelo comparador padrão) ---

    @Override
    public T pesquisar(T valor) {
        // Chama o método recursivo privado
        NoExemplo<T> noEncontrado = pesquisar(raiz, valor);
        if (noEncontrado != null) {
            return noEncontrado.getValor();
        }
        return null; // Retorna null se não encontrar
    }

    /**
     * Método recursivo privado para pesquisar usando o comparador padrão.
     */
    private NoExemplo<T> pesquisar(NoExemplo<T> no, T valor) {
        // 1. Caso Base 1: Nó é nulo (chegou ao fim e não encontrou)
        if (no == null) {
            return null;
        }

        // 2. Compara o valor buscado com o nó atual
        int comparacao = comparador.compare(valor, no.getValor());

        if (comparacao < 0) {
            // Se é MENOR, busca na esquerda
            return pesquisar(no.getFilhoEsquerda(), valor);
        } else if (comparacao > 0) {
            // Se é MAIOR, busca na direita
            return pesquisar(no.getFilhoDireita(), valor);
        } else {
            // 3. Caso Base 2: É IGUAL, encontrou!
            return no;
        }
    }

    // --- MÉTODO PESQUISAR (com comparador específico) ---

    /**
     * Este método deve varrer TODOS os elementos da árvore.
     * Ele não usa a ordenação da árvore, pois o comparador é diferente.
     */
    @Override
    public T pesquisar(T valor, Comparator comparador) {
        // Chama um método recursivo que faz uma varredura completa (ex: pré-ordem)
        return pesquisarComComparador(raiz, valor, comparador);
    }

    /**
     * Método de varredura (pré-ordem) que usa um comparador específico.
     */
    private T pesquisarComComparador(NoExemplo<T> no, T valor, Comparator comparador) {
        // 1. Caso Base: Nó nulo
        if (no == null) {
            return null;
        }

        // 2. Verifica o nó atual
        // Compara usando o comparador FORNECIDO
        if (comparador.compare(valor, no.getValor()) == 0) {
            return no.getValor(); // Encontrou!
        }

        // 3. Busca recursivamente na esquerda
        T encontradoEsquerda = pesquisarComComparador(no.getFilhoEsquerda(), valor, comparador);
        if (encontradoEsquerda != null) {
            return encontradoEsquerda;
        }

        // 4. Busca recursivamente na direita
        T encontradoDireita = pesquisarComComparador(no.getFilhoDireita(), valor, comparador);
        if (encontradoDireita != null) {
            return encontradoDireita;
        }

        return null; // Não encontrou na subárvore
    }

    // --- MÉTODO REMOVER ---

    @Override
    public T remover(T valor) {
        // O método de pesquisa retorna o nó (se encontrado)
        NoExemplo<T> noParaRemover = pesquisar(raiz, valor);
        if (noParaRemover == null) {
            return null; // Não encontrou, não remove nada
        }

        T valorRemovido = noParaRemover.getValor();
        raiz = remover(raiz, valor); // Chama o método recursivo de remoção
        return valorRemovido; // Retorna o valor do nó que foi removido
    }

    /**
     * Método recursivo privado para remover.
     * Esta é a parte mais complexa.
     */
    private NoExemplo<T> remover(NoExemplo<T> no, T valor) {
        // 1. Caso Base: Nó nulo
        if (no == null) {
            return null;
        }

        // 2. Encontrar o nó a ser removido
        int comparacao = comparador.compare(valor, no.getValor());

        if (comparacao < 0) {
            // Está na subárvore esquerda
            no.setFilhoEsquerda(remover(no.getFilhoEsquerda(), valor));
        } else if (comparacao > 0) {
            // Está na subárvore direita
            no.setFilhoDireita(remover(no.getFilhoDireita(), valor));
        } else {
            // 3. Encontrou o nó (no.getValor() == valor)! Agora, tratamos os 3 casos de remoção.

            // Caso 1: Nó é uma folha (não tem filhos)
            if (no.getFilhoEsquerda() == null && no.getFilhoDireita() == null) {
                return null; // Simplesmente remove (retorna nulo para o pai)
            }

            // Caso 2: Nó tem UM filho
            if (no.getFilhoEsquerda() == null) {
                return no.getFilhoDireita(); // "Pula" o nó atual, ligando o pai ao filho da direita
            }
            if (no.getFilhoDireita() == null) {
                return no.getFilhoEsquerda(); // "Pula" o nó atual, ligando o pai ao filho da esquerda
            }

            // Caso 3: Nó tem DOIS filhos
            // 3a. Encontrar o "sucessor" (o menor nó da subárvore direita)
            T menorValorSucessor = encontrarMenor(no.getFilhoDireita());
            // 3b. Substituir o valor do nó atual pelo valor do sucessor
            no.setValor(menorValorSucessor);
            // 3c. Remover o nó sucessor (que agora é uma duplicata) da subárvore direita
            no.setFilhoDireita(remover(no.getFilhoDireita(), menorValorSucessor));
        }

        return no;
    }

    /**
     * Método auxiliar para encontrar o menor valor na subárvore (usado no Caso 3 da remoção).
     */
    private T encontrarMenor(NoExemplo<T> no) {
        // O menor valor está sempre no filho mais à esquerda
        while (no.getFilhoEsquerda() != null) {
            no = no.getFilhoEsquerda();
        }
        return no.getValor();
    }


    // --- MÉTODOS DE MEDIÇÃO (Altura e Quantidade) ---

    @Override
    public int altura() {
        // A especificação pede -1 para árvore vazia
        if (raiz == null) {
            return -1;
        }
        return altura(raiz);
    }

    /**
     * Método recursivo privado para calcular altura.
     * A especificação pede 0 para árvore só com raiz.
     */
    private int altura(NoExemplo<T> no) {
        // Caso base: nó folha (ou quase-base: nó nulo)
        if (no == null) {
            return -1; // Altura de um nó nulo é -1
        }

        int alturaEsquerda = altura(no.getFilhoEsquerda());
        int alturaDireita = altura(no.getFilhoDireita());

        // A altura do nó é 1 + a maior altura entre os filhos
        return Math.max(alturaEsquerda, alturaDireita) + 1;
    }


    @Override
    public int quantidadeNos() {
        return quantidadeNos(raiz);
    }

    /**
     * Método recursivo privado para contar os nós.
     */
    private int quantidadeNos(NoExemplo<T> no) {
        if (no == null) {
            return 0; // Nó nulo não conta
        }
        // Conta 1 (ele mesmo) + todos da esquerda + todos da direita
        return 1 + quantidadeNos(no.getFilhoEsquerda()) + quantidadeNos(no.getFilhoDireita());
    }

    // --- MÉTODOS DE CAMINHAMENTO (Em Nível e Em Ordem) ---

    @Override
    public String caminharEmNivel() {
        // A especificação pede formato "[...]" e separador " \n "
        if (raiz == null) {
            return "[]";
        }

        StringBuilder sb = new StringBuilder();
        sb.append("[");

        // Caminhamento em nível (BFS) usa Fila (Queue)
        Queue<NoExemplo<T>> fila = new LinkedList<>();
        fila.add(raiz);

        while (!fila.isEmpty()) {
            NoExemplo<T> noAtual = fila.poll(); // Remove o primeiro da fila

            // Adiciona o valor ao string
            sb.append(noAtual.getValor().toString());

            // Adiciona os filhos na fila
            if (noAtual.getFilhoEsquerda() != null) {
                fila.add(noAtual.getFilhoEsquerda());
            }
            if (noAtual.getFilhoDireita() != null) {
                fila.add(noAtual.getFilhoDireita());
            }

            // Adiciona o separador se a fila ainda não estiver vazia
            if (!fila.isEmpty()) {
                sb.append(" \n ");
            }
        }

        sb.append("]");
        return sb.toString();
    }

    @Override
    public String caminharEmOrdem() {
        // A especificação pede formato "[...]" e separador " \n "
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        caminharEmOrdem(raiz, sb);

        // Remove o último separador " \n " se a árvore não estiver vazia
        if (sb.length() > 1 && raiz != null) {
            sb.setLength(sb.length() - 3); // Remove os últimos 3 caracteres (" \n ")
        }

        sb.append("]");
        return sb.toString();
    }

    /**
     * Método recursivo privado para caminhamento em ordem (Esquerda, Raiz, Direita).
     */
    private void caminharEmOrdem(NoExemplo<T> no, StringBuilder sb) {
        if (no == null) {
            return;
        }

        caminharEmOrdem(no.getFilhoEsquerda(), sb);

        // Visita a raiz (adiciona ao string)
        sb.append(no.getValor().toString());
        sb.append(" \n ");

        caminharEmOrdem(no.getFilhoDireita(), sb);
    }

}