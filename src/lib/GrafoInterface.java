package lib;

import lib.exception.VerticeDuplicadoException;
import lib.exception.VerticeNaoEncontradoException;

public interface GrafoInterface<T> {

    /**
    * Adicionar um vértice com o valor do objeto T,
    * caso o mesmo ainda não exista no grafo
    * */
    public void adicionarVertice(T elemento) throws VerticeDuplicadoException;

    /**
    * Cria uma aresta cuja origem e destino são os valores passados,
     * caso existam, com o peso associado
    * */
    public void adicionarAresta(T origem, T destino, double peso) throws VerticeNaoEncontradoException;

    /**
     * Computa a árvore geradora mínima,
     * Imprime todas as arestas da AGM (origem, destino, peso)
     * Imprime a soma dos pesos das arestas da AGM
     * @return Grafo
     */
    public Grafo<T> arvoreGeradoraMinima() throws IllegalArgumentException, VerticeDuplicadoException;

    /**
     * Dados dois vértices de origem e destino
     * Calcula a menor distância entre eles
     * Imprime o caminho percorrido
     * Imprime a distância total percorrida
     * @return Grafo
     * */
    public Grafo<T> caminhoMinimo(T origem, T destino) throws VerticeDuplicadoException, VerticeNaoEncontradoException;
}
