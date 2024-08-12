package app.service;

import app.dao.GrafoDAO;
import lib.exception.VerticeDuplicadoException;
import lib.Grafo;

public class GrafoService {

    private GrafoDAO grafoDAO;

    public GrafoService(Grafo<String> grafo) {
        this.grafoDAO = new GrafoDAO(grafo);
    }

    public void cadastrarCidade(String nomeCidade) throws VerticeDuplicadoException {
        this.grafoDAO.cadastrarCidade(nomeCidade);
    }

    public void cadastrarRota(String origem, String destino, float distancia) {
        System.out.println("NÃO IMPLEMENTADO.");
    }


    public void exibirGrafo() {
        this.grafoDAO.exibirGrafo();
    }
}
