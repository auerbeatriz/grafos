package app.service;

import app.dao.GrafoDAO;
import lib.Aresta;
import lib.Vertice;
import lib.exception.VerticeDuplicadoException;
import lib.Grafo;
import lib.exception.VerticeNaoEncontradoException;

import java.util.List;

public class GrafoService {

    private GrafoDAO grafoDAO;

    public GrafoService(Grafo<String> grafo) {
        this.grafoDAO = new GrafoDAO(grafo);
    }


    public void cadastrarCidade(String nomeCidade) throws VerticeDuplicadoException {
        this.grafoDAO.cadastrarCidade(nomeCidade);
    }

    public void cadastrarRota(String origem, String destino, double distancia) throws VerticeDuplicadoException, VerticeNaoEncontradoException {
        if (this.grafoDAO.getVertice(origem) == null) {
            this.grafoDAO.adicionarVertice(origem);
        }

        if (this.grafoDAO.getVertice(destino) == null) {
            this.grafoDAO.adicionarVertice(destino);
        }

        this.grafoDAO.cadastrarRota(origem, destino, distancia);
    }

    public Grafo<String> caminhoMinimo(String origem, String destino) throws VerticeDuplicadoException, VerticeNaoEncontradoException {
        return this.grafoDAO.caminhoMinimo(origem, destino);
    }

    public Grafo<String> caminhoMinimoAGM(String origem, String destino) throws IllegalArgumentException, VerticeDuplicadoException, GrafoVazioException, VerticeNaoEncontradoException {
        Grafo<String> agm = this.calcularAGM();
        if (agm == null)
            throw new GrafoVazioException("⚠ Não foi possível calcular o caminho mínimo. A AGM do grafo é nula.");

        Grafo<String> caminhoMinimoAGM = agm.caminhoMinimo(origem, destino);
        if (caminhoMinimoAGM == null)
            throw new GrafoVazioException("⚠ Não foi possível encontrar um caminho mínimo entre " + origem + " e " + destino + ".");

        return caminhoMinimoAGM;
    }

    public Grafo<String> calcularAGM() throws VerticeDuplicadoException, IllegalArgumentException {
        return this.grafoDAO.calcularAGM();
    }

    public double calcularPesoTotal(Grafo<String> grafo) {
        List<Aresta<String>> arestas = grafo.getArestas();
        return arestas.stream().map(Aresta::getPeso).reduce(0.0, Double::sum)/2;
    }

    public void exibirGrafo() {
        this.grafoDAO.exibirGrafo();
    }

    public Grafo<String> getGrafoAtual() {
        return this.grafoDAO.getGrafoAtual();
    }
}