package lib;

public class Aresta {
    Vertice origem;
    Vertice destino;
    float peso;

    public Aresta(Vertice origem, Vertice destino, float peso) {
        this.origem = origem;
        this.destino = destino;
        this.peso = peso;
    }

    public Vertice getOrigem() {
        return origem;
    }

    public Vertice getDestino() {
        return destino;
    }

    public float getPeso() {
        return peso;
    }

    @Override
    public String toString() {
        return origem.getValor() +
                " - " + peso + " -> "
                + destino.getValor();
    }
}
