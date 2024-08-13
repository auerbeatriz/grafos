package app.view;

import lib.exception.VerticeDuplicadoException;
import lib.exception.VerticeNaoEncontradoException;

public class Main {

    public static void main(String[] args) throws VerticeDuplicadoException, VerticeNaoEncontradoException {
        Menu menu = new Menu();
        menu.exibirMenu();
    }
}
