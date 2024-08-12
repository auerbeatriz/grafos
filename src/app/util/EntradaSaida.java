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
            String[] line = buffer.readLine().split(",");

            for(int j = 0; j < cidades.size(); j++) {
                int n = Integer.parseInt(line[j]);
                if(n > 0) {
                    Vertice<String> origem = cidades.get(i);
                    Vertice<String> destino = cidades.get(j);

                    Aresta<String> aresta = new Aresta<>(origem, destino, n);
                    origem.addAresta(aresta);
                }
            }
        }
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
}
