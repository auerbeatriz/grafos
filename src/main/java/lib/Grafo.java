package lib;

import exception.DuplicatedException;
import exception.NotFoundException;

import java.util.ArrayList;
import java.util.List;

public class Grafo<T> implements GrafoInterface<T>{
    List<Vertice> vertices;
    String label;

    public Grafo() {
        this.vertices = new ArrayList<Vertice>();
    }

    public Grafo(String label) {
        this.label = label;
        this.vertices = new ArrayList<Vertice>();
    }

    @Override
    public void adicionarVertice(T elemento) {
        Vertice vertice = this.estaNoGrafo(elemento);

        if(vertice == null) {
            vertice = new Vertice(elemento);
            this.vertices.add(vertice);
        }
    }

    @Override
    public void adicionarAresta(T valorOrigem, T valorDestino, float peso) {
        Vertice origem = this.estaNoGrafo(valorOrigem);
        Vertice destino = this.estaNoGrafo(valorDestino);

        if(origem != null && destino != null) {
            Aresta aresta = new Aresta(origem, destino, peso);
            origem.addAresta(aresta);
        }
    }

    @Override
    public Grafo arvoreGeradoraMinima() {
        return null;
    }

    @Override
    public Grafo caminhoMinimo(Object origem, Object destino) {
        return null;
    }

    private Vertice estaNoGrafo(T valor) {
        for(Vertice vertice : this.vertices) {
            if(vertice.getValor() == valor) return vertice;
        }

        return null;
    }

    public void print() {
        for(Vertice vertice : this.vertices) {
            System.out.println(vertice);
            System.out.println("----------------------------------");
        }
    }

    @Override
    public String toString() {
        String output = "";
        for(Vertice vertice : this.vertices) {
            output += vertice.toString();
            output += "\n----------------------------------\n";
        }

        return output;
    }
}
