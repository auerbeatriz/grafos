package org.app;

import lib.Grafo;

// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class Main {
    public static void main(String[] args) {
//        Grafo<Integer> grafo = new Grafo<Integer>();
//        grafo.adicionarVertice(1);
//        grafo.adicionarAresta(1, 2, 0);
//
//        grafo.adicionarVertice(1);
//        grafo.adicionarAresta(1, 2, 0);
//
//        grafo.adicionarVertice(2);
//        grafo.adicionarAresta(1, 2, 0);
//
//        // Optei por não restringir a inserção de arestas exatamente iguais
//        grafo.adicionarAresta(1, 2, 0);
//
//        grafo.print();

        // ----------------------------------------------------------------------------------------- //

        Grafo<String> grafo = new Grafo<>();
        grafo.adicionarVertice("a");

        grafo.adicionarVertice("b");
        grafo.adicionarAresta("a", "b", 10);

        grafo.adicionarVertice("c");
        grafo.adicionarAresta("a", "c", 7);
        grafo.adicionarAresta("b", "c", 15);

        grafo.adicionarVertice("d");
        grafo.adicionarAresta("b", "d", 12);
        grafo.adicionarAresta("c", "d", 8);

        grafo.adicionarVertice("e");
        grafo.adicionarAresta("d", "e", 3);

        grafo.adicionarVertice("f");
        grafo.adicionarAresta("e", "f", 6);

        grafo.adicionarVertice("g");
        grafo.adicionarAresta("f", "g", 6);

        grafo.adicionarVertice("h");
        grafo.adicionarAresta("c", "h", 9);
        grafo.adicionarAresta("f", "h", 10);
        grafo.adicionarAresta("g", "h", 6);

        grafo.print();

        Grafo<String> mst = grafo.arvoreGeradoraMinima();
        mst.print();
    }
}