package main.service;

import lib.Grafo;
import main.dao.GrafoDAO;

public class GrafoService {

    private GrafoDAO grafoDAO;

    public GrafoService(Grafo grafo) {
        this.grafoDAO = new GrafoDAO(grafo);
    }
}
