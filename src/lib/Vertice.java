package lib;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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

    public List<Vertice<T>> getDestinos() {
        return this.arestas.stream()
                .map(Aresta::getDestino)
                .distinct()
                .collect(Collectors.toList());
    }

    @Override
    public String toString() {
        String output = "VERTICE: " + valor;
        for(Aresta<T> aresta : arestas) {
            output += "\n" + aresta.toString();
        }
        return output;
    }

    public Aresta<T> getAresta(T destino) {
        for(Aresta<T> aresta : this.arestas) {
            if(aresta.getDestino().getValor().equals(destino)) {
                return aresta;
            }
        }

        return null;
    }
}
