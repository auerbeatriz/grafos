package app.dao;

import lib.Vertice;
import lib.exception.VerticeDuplicadoException;
import lib.Grafo;
import lib.exception.VerticeNaoEncontradoException;

public class GrafoDAO {
    private Grafo<String> grafo;

    public GrafoDAO(Grafo<String> grafo) {
        this.grafo = grafo;
    }

    public void cadastrarCidade(String nomeCidade) throws VerticeDuplicadoException {
        this.grafo.adicionarVertice(nomeCidade);
    }

    public void cadastrarRota(String origem, String destino, double distancia) throws VerticeNaoEncontradoException {
        this.grafo.adicionarAresta(origem, destino, distancia);
    }

    public Grafo<String> caminhoMinimo(String origem, String destino) throws VerticeDuplicadoException, VerticeNaoEncontradoException {
        return this.grafo.caminhoMinimo(origem, destino);
    }

    public Grafo<String> calcularAGM() throws VerticeDuplicadoException {
        return this.grafo.arvoreGeradoraMinima();
    }

    public void exibirGrafo() {
        System.out.println(this.grafo);
    }

    public Vertice<String> getVertice(String valor) {
        return this.grafo.getVertice(valor);
    }
}
