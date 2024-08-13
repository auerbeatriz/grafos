package app.service;

import app.dao.GrafoDAO;
import lib.Aresta;
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

    public void cadastrarRota(String origem, String destino, float distancia) {
        System.out.println("NOT IMPLEMENTED.");
    }

    public Grafo<String> caminhoMinimo(String origem, String destino) throws VerticeDuplicadoException, VerticeNaoEncontradoException {
        Grafo<String> caminho = this.grafoDAO.caminhoMinimo(origem, destino);
        exibirCaminhoMinimo(caminho, origem, destino);
        return caminho;
    }

    public Grafo<String> caminhoMinimoAGM(String origem, String destino) throws VerticeDuplicadoException, VerticeNaoEncontradoException {

        Grafo<String> agm = this.calcularAGM();
        Grafo<String> caminhoMinimoAGM = agm.caminhoMinimo(origem, destino);

        return caminhoMinimoAGM;
    }

    private void exibirCaminhoMinimo(Grafo<String> caminho, String origem, String destino) {
        System.out.println("Caminho mínimo de " + origem + " até " + destino + ":");
        List<Aresta<String>> arestas = caminho.getArestas();
        double distanciaTotal = 0.0;

        for (Aresta<String> aresta : arestas) {
            System.out.println(aresta.getOrigem() + " -> " + aresta.getDestino() + " : " + aresta.getPeso() + " km");
            distanciaTotal += aresta.getPeso();
        }

        System.out.println("Distância total: " + distanciaTotal + " km");
    }

    public Grafo<String> calcularAGM() {
        Grafo<String> agm = null;

        try {
            agm = this.grafoDAO.calcularAGM();
            System.out.println(agm);
            System.out.println("Peso total da AGM: " + this.calcularPesoTotal(agm));
        } catch (VerticeDuplicadoException e) {
            System.out.println("Não foi possível calcular a AGM. \nErro: " + e.getMessage());
        }

        return agm;
    }

    private double calcularPesoTotal(Grafo<String> grafo) {
        List<Aresta<String>> arestas = grafo.getArestas();
        return arestas.stream().map(Aresta::getPeso).reduce(0.0, Double::sum);
    }

    public void exibirGrafo() {
        this.grafoDAO.exibirGrafo();
    }
}
