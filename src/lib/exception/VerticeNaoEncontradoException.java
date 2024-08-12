package lib.exception;

public class VerticeNaoEncontradoException extends Exception {

    public VerticeNaoEncontradoException(String valor) {
        super(String.format("Vértice com valor %s não encontrado no grafo.", valor));
    }
}
