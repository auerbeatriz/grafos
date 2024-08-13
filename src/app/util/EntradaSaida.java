package app.util;

import lib.Aresta;
import lib.Grafo;
import lib.Vertice;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class EntradaSaida {
    public Scanner input;

    public EntradaSaida() {
        this.input = new Scanner(System.in);
    }

    public Grafo<String> lerArquivo() {
        Grafo<String> grafo = new Grafo<String>();

        try {
            FileReader f = new FileReader("entrada.txt");
            BufferedReader buffer = new BufferedReader(f);

            String firstLine = buffer.readLine();
            if(firstLine != null) {
                int quantidadeCidades = Integer.parseInt(firstLine);

                List<Vertice<String>> cidades = this.lerCidades(quantidadeCidades, buffer);
                this.lerCaminhos(cidades, buffer);
                grafo.setVertices(cidades);
            }
        }
        catch (FileNotFoundException e) {
            System.out.println("⚠ Nenhum arquivo de dados encontrado.");
        }
        catch (IOException | NumberFormatException e) {
            System.out.println("⚠ Não foi possível realizar leitura do arquivo de dados.");
        }

        return grafo;
    }

    private List<Vertice<String>> lerCidades(int n, BufferedReader buffer) throws IOException {
        List<Vertice<String>> cidades = new ArrayList<>();

        for(int i = 0; i < n; i++) {
            String nomeCidade = buffer.readLine().trim();
            cidades.add(new Vertice<>(nomeCidade));
        }

        return cidades;
    }

    private void lerCaminhos(List<Vertice<String>> cidades, BufferedReader buffer) throws IOException {
        for(int i = 0; i < cidades.size(); i++) {
            String[] line = buffer.readLine().trim().split(",");

            for(int j = 0; j < cidades.size(); j++) {
                double n = Double.parseDouble(line[j]);

                if(n > 0) {
                    Vertice<String> origem = cidades.get(i);
                    Vertice<String> destino = cidades.get(j);

                    Aresta<String> aresta = new Aresta<>(origem, destino, n);
                    origem.addAresta(aresta);
                }
            }
        }
    }

    public void gravarGrafo(Grafo grafo, String filename) throws IOException {
        FileWriter f = null;

        f = new FileWriter(filename);
        BufferedWriter buffer = new BufferedWriter(f);

        List<Vertice<String>> vertices = grafo.getVertices();

        buffer.write(String.valueOf(vertices.size()) + "\n");

        for(Vertice<String> vertice : vertices) {
            buffer.write(vertice.getValor() + "\n");
        }

        for(Vertice<String> origem : vertices) {
            for(Vertice<String> destino : vertices) {
                Aresta<String> aresta = origem.getAresta(destino.getValor());

                if(aresta != null)
                    buffer.write(aresta.getPeso() + ", ");
                else
                    buffer.write("0, ");
            }

            buffer.write("\n");
        }

        buffer.close();
    }

    public int lerInteiro() {
        String linha = this.input.nextLine();
        return Integer.parseInt(linha);
    }

    public String lerString() {
        return this.input.nextLine();
    }

    public float lerFloat() {
        String linha = this.input.nextLine();
        return Float.parseFloat(linha);
    }

    public void exibirGrafo(Grafo<String> grafo) {
        System.out.println(grafo);
    }
}
