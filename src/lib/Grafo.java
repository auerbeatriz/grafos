package lib;

import java.util.*;

public class Grafo<T> implements GrafoInterface<T>{
    List<Vertice<T>> vertices;
    String label;

    public Grafo() {
        this.vertices = new ArrayList<>();
    }

    public Grafo(String label) {
        this.label = label;
        this.vertices = new ArrayList<>();
    }

    @Override
    public void adicionarVertice(T elemento) {
        if(!this.contem(elemento)) {
            Vertice<T> vertice = new Vertice<>(elemento);
            this.vertices.add(vertice);
        }
    }

    @Override
    public void adicionarAresta(T valorOrigem, T valorDestino, float peso) {
        Vertice<T> origem = this.getVertice(valorOrigem);
        Vertice<T> destino = this.getVertice(valorDestino);

        if(origem != null && destino != null) {
            Aresta<T> aresta = new Aresta<>(origem, destino, peso);
            origem.addAresta(aresta);
        }
    }

    @Override
    public Grafo<T> arvoreGeradoraMinima() {
        int verticesCount = this.vertices.size();
        Map<T, List<Aresta<T>>> arestasByVertice = this.getArestasByVertice();

        T verticeInicial = this.vertices.get(0).getValor();

        Grafo<T> arvoreGeradoraMinima = new Grafo<>();
        arvoreGeradoraMinima.adicionarVertice(verticeInicial);

        PriorityQueue<Aresta<T>> minHeap = new PriorityQueue<>();
        minHeap.addAll(arestasByVertice.get(verticeInicial));

        while(arvoreGeradoraMinima.vertices.size() < verticesCount) {
            // Obter as arestas de todos os nós do MST que ainda não foram adicionadas no grafo
            Aresta<T> arestaMenorPeso = minHeap.poll();

            if(arestaMenorPeso != null) {
                // adicionar a menor aresta e o vértice na MST
                T destino = arestaMenorPeso.getDestino().getValor();
                if(!arvoreGeradoraMinima.contem(destino)) {
                    arvoreGeradoraMinima.adicionarVertice(destino);

                    Vertice<T> verticeDestino = arvoreGeradoraMinima.getVertice(destino);
                    Vertice<T> verticeOrigem = arvoreGeradoraMinima.getVertice(arestaMenorPeso.getOrigem().getValor());

                    Aresta<T> novaAresta = new Aresta<>(verticeOrigem, verticeDestino, arestaMenorPeso.getPeso());
                    verticeOrigem.addAresta(novaAresta);

                    minHeap.addAll(arestasByVertice.get(verticeDestino.getValor()));
                }
            }
        }

        return arvoreGeradoraMinima;
    }

    public Map<T, List<Aresta<T>>> getArestasByVertice() {
        Map<T, List<Aresta<T>>> arestasByVertice = new HashMap<>();

        for(Vertice<T> vertice : this.vertices) {
            arestasByVertice.put(vertice.getValor(), vertice.getArestas());
        }

        return arestasByVertice;
    }

    @Override
    public Grafo<T> caminhoMinimo(Object origem, Object destino) {
        return null;
    }

    public boolean contem(T valor) {
        for(Vertice<T> vertice : this.vertices) {
            if(vertice.getValor() == valor) return true;
        }

        return false;
    }

    public void print() {
        for(Vertice<T> vertice : this.vertices) {
            System.out.println(vertice);
            System.out.println("----------------------------------");
        }
    }

    @Override
    public String toString() {
        String output = "";
        for(Vertice<T> vertice : this.vertices) {
            output += vertice.toString();
            output += "\n----------------------------------\n";
        }

        return output;
    }

    public List<Vertice<T>> getVertices() {
        return vertices;
    }

    public Vertice<T> getVertice(T valor) {
        for(Vertice<T> vertice : this.vertices) {
            if(vertice.getValor() == valor) return vertice;
        }

        return null;
    }

    public String getLabel() {
        return label;
    }
}
