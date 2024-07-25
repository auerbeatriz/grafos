package lib;

import java.util.ArrayList;
import java.util.List;

public class Vertice<T> {
    private T valor;
    private List<Aresta> arestas;

    public Vertice(T valor) {
        this.valor = valor;
        this.arestas = new ArrayList<Aresta>();
    }

    public T getValor() {
        return valor;
    }

    public List<Aresta> getArestas() {
        return this.arestas;
    }

    public void setArestas(List<Aresta> arestas) {
        this.arestas = arestas;
    }

    public void addAresta(Aresta aresta) {
        this.arestas.add(aresta);
    }

    @Override
    public String toString() {
        String output = "VERTICE: " + valor;
        for(Aresta aresta : arestas) {
            output += "\n" + aresta.toString();
        }
        return output;
    }
}
