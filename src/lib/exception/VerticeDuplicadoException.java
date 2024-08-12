package lib.exception;

public class VerticeDuplicadoException extends Exception {
    public VerticeDuplicadoException() {
        super("Essa cidade já foi adicionada ao grafo.");
    }
}
