package org.app;

import lib.Grafo;

// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class Main {
    public static void main(String[] args) {
        Grafo<Integer> grafo = new Grafo();
        grafo.adicionarVertice(1);
        grafo.adicionarAresta(1, 2, 0);

        grafo.adicionarVertice(1);
        grafo.adicionarAresta(1, 2, 0);

        grafo.adicionarVertice(2);
        grafo.adicionarAresta(1, 2, 0);

        // Optei por não restringir a inserção de arestas exatamente iguais
        grafo.adicionarAresta(1, 2, 0);

        grafo.print();
    }
}