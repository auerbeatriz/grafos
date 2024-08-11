package main.view;

import lib.Grafo;
import main.service.GrafoService;
import main.util.EntradaSaida;

public class Menu {
    private EntradaSaida io;
    private GrafoService grafoService;

    public Menu() {
        this.io = new EntradaSaida();

        Grafo grafo = io.lerArquivo();
        this.grafoService = new GrafoService(grafo);
    }
}
