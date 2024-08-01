package lib;

import java.util.ArrayList;
import java.util.List;

public class Vertice<T> {
    private T valor;
    private List<Aresta<T>> arestas;

    public Vertice(T valor) {
        this.valor = valor;
        this.arestas = new ArrayList<>();
    }

    public T getValor() {
        return valor;
    }

    public List<Aresta<T>> getArestas() {
        return this.arestas;
    }

    public void setArestas(List<Aresta<T>> arestas) {
        this.arestas = arestas;
    }

    public void addAresta(Aresta<T> aresta) {
        this.arestas.add(aresta);
    }

    @Override
    public String toString() {
        String output = "VERTICE: " + valor;
        for(Aresta<T> aresta : arestas) {
            output += "\n" + aresta.toString();
        }
        return output;
    }
}
