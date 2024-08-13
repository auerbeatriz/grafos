package app.view;

import app.service.GrafoVazioException;
import lib.Aresta;
import lib.exception.VerticeDuplicadoException;
import lib.Grafo;
import app.service.GrafoService;
import app.util.EntradaSaida;
import lib.exception.VerticeNaoEncontradoException;

import java.io.IOException;
import java.util.List;

public class Menu {
    private EntradaSaida io;
    private GrafoService grafoService;

    public Menu() {
        this.io = new EntradaSaida();

        Grafo<String> grafo = io.lerArquivo();
        this.grafoService = new GrafoService(grafo);
    }

    public void exibirMenu() {
        int opcao;
        do {
            System.out.println("1. Cadastrar cidade");
            System.out.println("2. Cadastrar rota");
            System.out.println("3. Calcular árvore geradora mínima (AGM)");
            System.out.println("4. Calcular caminho mínimo entre duas cidades");
            System.out.println("5. Calcular caminho mínimo entre duas cidades (AGM)");
            System.out.println("6. Gravar dados");
            System.out.println("7. Exibir grafo");
            System.out.println("0. Sair");
            System.out.print("Escolha uma opção: ");

            opcao = io.lerInteiro();

            switch (opcao) {
                case 1:
                    cadastrarCidade();
                    break;
                case 2:
                    cadastrarRota();
                    break;
                case 3:
                    calcularAGM();
                    break;
                case 4:
                    this.calcularCaminhoMinimo();
                    break;
                case 5:
                    this.calcularCaminhoMinimoAGM();
                    break;
                case 6:
                    this.gravarGrafoArquivo();
                    break;
                case 7:
                    this.exibirGrafo();
                    break;
                case 0:
                    System.out.println("Tchau!");
                    break;
                default:
                    System.out.println("Opção inválida. Tente novamente.");
            }
        } while (opcao != 0);
    }

    private void cadastrarCidade() {
        System.out.println("------------------ CADASTRO CIDADE -----------------");

        System.out.print("Nome: ");
        String nomeCidade = io.lerString();

        try {
            this.grafoService.cadastrarCidade(nomeCidade);
            System.out.println("Cidade cadastrado com sucesso.");
        } catch (VerticeDuplicadoException e) {
            System.out.println(e.getMessage());
        }

        System.out.println("-----------------------------------------------------");
    }

    private void cadastrarRota() {
        System.out.println("------------------ CADASTRO ROTA -------------------");

        System.out.print("Cidade de origem: ");
        String origem = io.lerString();

        System.out.print("Cidade de destino: ");
        String destino = io.lerString();

        System.out.print("Distância: ");
        double distancia = io.lerDouble();

        try {
            this.grafoService.cadastrarRota(origem, destino, distancia);
            System.out.println("Rota cadastrada com sucesso.");
        } catch (VerticeDuplicadoException | VerticeNaoEncontradoException e) {
            System.out.println("Erro interno. Erro: " + e.getMessage());
        }

        System.out.println("-----------------------------------------------------");
    }

    private void calcularAGM() {
        System.out.println("------------------------ AGM ------------------------");
        try {
            Grafo<String> agm = this.grafoService.calcularAGM();

            if(agm != null) {
                double pesoTotal = this.grafoService.calcularPesoTotal(agm);

                this.io.exibirGrafo(agm);
                System.out.println("Peso total da AGM: " + pesoTotal);
            } else {
                System.out.println("ⓘ A AGM é nula.");
            }
        } catch (VerticeDuplicadoException | IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }

        System.out.println("-----------------------------------------------------");
    }

    private void calcularCaminhoMinimo(){
        System.out.println("------------------ CAMINHO MINIMO -------------------");

        System.out.print("Cidade de origem: ");
        String origem = io.lerString();

        System.out.print("Cidade de destino: ");
        String destino = io.lerString();

        try{
            Grafo<String> caminhoMinimo = this.grafoService.caminhoMinimo(origem,destino);
            this.io.exibirCaminhoMinimo(caminhoMinimo, origem, destino);
        } catch (VerticeDuplicadoException | VerticeNaoEncontradoException e) {
            System.out.println(e.getMessage());
        }

        System.out.println("-----------------------------------------------------");
    }

    private void calcularCaminhoMinimoAGM() {
        System.out.println("------------------ CAMINHO MINIMO AGM -------------------");

        System.out.print("Cidade de origem: ");
        String origem = io.lerString();

        System.out.print("Cidade de destino: ");
        String destino = io.lerString();

        try {
            Grafo<String> caminhoMinimo = this.grafoService.caminhoMinimoAGM(origem,destino);
            this.io.exibirCaminhoMinimo(caminhoMinimo, origem, destino);
        } catch (IllegalArgumentException | GrafoVazioException | VerticeDuplicadoException | VerticeNaoEncontradoException  e) {
            System.out.println(e.getMessage());
        }

        System.out.println("-----------------------------------------------------");
    }

    private void gravarGrafoArquivo() {
        System.out.println("ⓘ Gravando...");

        try {
            Grafo<String> grafo = this.grafoService.getGrafoAtual();
            Grafo<String> agm = this.grafoService.calcularAGM();

            this.io.gravarGrafo(grafo, "grafoCompleto.txt");
            this.io.gravarGrafo(agm, "agm.txt");
            System.out.println("ⓘ Grafo gravado com sucesso.");
        } catch (IOException | IllegalArgumentException | VerticeDuplicadoException e) {
            System.out.println("⚠ Não foi possível gravar o grafo no arquivo. \nErro: " + e.getMessage());
        }
    }

    private void exibirGrafo() {
        this.grafoService.exibirGrafo();
    }

}
