package app.service;

import app.dao.GrafoDAO;
import lib.Aresta;
import lib.Vertice;
import lib.exception.VerticeDuplicadoException;
import lib.Grafo;
import lib.exception.VerticeNaoEncontradoException;

import java.util.List;

public class GrafoService {

    private GrafoDAO grafoDAO;

    public GrafoService(Grafo<String> grafo) {
        this.grafoDAO = new GrafoDAO(grafo);
    }


    public void cadastrarCidade(String nomeCidade) throws VerticeDuplicadoException {
        this.grafoDAO.cadastrarCidade(nomeCidade);
    }

    public void cadastrarRota(String origem, String destino, float distancia) throws VerticeDuplicadoException, VerticeNaoEncontradoException {
        if (this.grafoDAO.getVertice(origem) == null) {
            this.grafoDAO.adicionarVertice(origem);
        }

        if (this.grafoDAO.getVertice(destino) == null) {
            this.grafoDAO.adicionarVertice(destino);
        }

        this.grafoDAO.cadastrarRota(origem, destino, distancia);
    }

    public Grafo<String> caminhoMinimo(String origem, String destino) {
        try {
            Grafo<String> caminho = this.grafoDAO.caminhoMinimo(origem, destino);
            exibirCaminhoMinimo(caminho, origem, destino);
            return caminho;
        } catch (VerticeNaoEncontradoException e) {
            System.out.println("Erro ao calcular o caminho mínimo. " + e.getMessage());
            return new Grafo<>();
        } catch (VerticeDuplicadoException e) {
            System.out.println("Erro ao calcular o caminho mínimo. " + e.getMessage());
            return new Grafo<>();
        }
    }

    public Grafo<String> caminhoMinimoAGM(String origem, String destino) throws IllegalArgumentException {
        try {
            Grafo<String> agm = this.calcularAGM();
            // Verificando se o grafo AGM é nulo
            if (agm == null) {
                System.out.println("Erro: A AGM calculada é nula.");
                return new Grafo<>();
            }

            Grafo<String> caminhoMinimoAGM = agm.caminhoMinimo(origem, destino);

            // Verificando se o caminho mínimo é nulo
            if (caminhoMinimoAGM == null) {
                System.out.println("Não foi possível encontrar um caminho mínimo entre " + origem + " e " + destino + ".");
                return new Grafo<>();
            }

            // Exibindo o caminho mínimo encontrado na AGM
            exibirCaminhoMinimo(caminhoMinimoAGM, origem, destino);

            return caminhoMinimoAGM;
        } catch (VerticeNaoEncontradoException e) {
            System.out.println("Erro ao calcular o caminho mínimo na AGM: " + e.getMessage());
            return new Grafo<>();
        } catch (VerticeDuplicadoException e) {
            System.out.println("Erro ao calcular a AGM: " + e.getMessage());
            return new Grafo<>();
        }
    }

    private void exibirCaminhoMinimo(Grafo<String> caminho, String origem, String destino) {
        if (caminho == null) {
            System.out.println("Não é possível seguir esse caminho.");
            return;
        }

        List<Aresta<String>> arestas = caminho.getArestas();
        if (arestas.isEmpty()) {
            System.out.println("Não há arestas no caminho mínimo.");
            return;
        }

        System.out.println("Caminho mínimo de " + origem + " até " + destino + ":");
        double distanciaTotal = 0.0;

        for (Aresta<String> aresta : arestas) {
            System.out.println(aresta.getOrigem() + " -> " + aresta.getDestino() + " : " + aresta.getPeso() + " km");
            distanciaTotal += aresta.getPeso();
        }

        System.out.println("Distância total: " + distanciaTotal + " km");
    }



    public Grafo<String> calcularAGM() throws VerticeDuplicadoException, IllegalArgumentException {
        return this.grafoDAO.calcularAGM();
    }

    public double calcularPesoTotal(Grafo<String> grafo) {
        List<Aresta<String>> arestas = grafo.getArestas();
        return arestas.stream().map(Aresta::getPeso).reduce(0.0, Double::sum)/2;
    }

    public void exibirGrafo() {
        this.grafoDAO.exibirGrafo();
    }

    public Grafo<String> getGrafoAtual() {
        return this.grafoDAO.getGrafoAtual();
    }
}