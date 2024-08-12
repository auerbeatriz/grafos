package app.service;

import app.dao.GrafoDAO;
import lib.Aresta;
import lib.exception.VerticeDuplicadoException;
import lib.Grafo;

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
