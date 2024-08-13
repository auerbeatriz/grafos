package app.dao;

import lib.Aresta;
import lib.Vertice;
import lib.exception.VerticeDuplicadoException;
import lib.Grafo;
import lib.exception.VerticeNaoEncontradoException;

import java.util.List;

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

    public Grafo<String> calcularAGM() throws VerticeDuplicadoException, IllegalArgumentException {
        return this.grafo.arvoreGeradoraMinima();
    }

    public void exibirGrafo() {
        System.out.println(this.grafo);
    }

    public Vertice<String> getVertice(String valor) {
        return this.grafo.getVertice(valor);
    }

    public List<Vertice<String>> getVertices() {
        return this.grafo.getVertices();
    }

    public List<Aresta<String>> getArestas() {
        return this.grafo.getArestas();
    }

    public void adicionarVertice(String origem) throws VerticeDuplicadoException {
        this.grafo.adicionarVertice(origem);
    }

    public Grafo<String> getGrafo() {
        return this.grafo;
    }

    public Grafo<String> getGrafoAtual() {
        return this.grafo;
    }
}
