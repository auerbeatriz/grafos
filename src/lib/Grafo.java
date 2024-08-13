package lib;

import lib.exception.VerticeDuplicadoException;
import lib.exception.VerticeNaoEncontradoException;

import java.util.*;
import java.util.stream.Collectors;

public class Grafo<T> implements GrafoInterface<T>{
    List<Vertice<T>> vertices;
    String label;

    public Grafo() {
        this.vertices = new ArrayList<>();
    }

    public Grafo(String label) {
        this.label = label;
        this.vertices = new ArrayList<>();
    }

    @Override
    public void adicionarVertice(T elemento) throws VerticeDuplicadoException {
        if(this.contem(elemento))
            throw new VerticeDuplicadoException();

        Vertice<T> vertice = new Vertice<>(elemento);
        this.vertices.add(vertice);
    }

    @Override
    public void adicionarAresta(T valorOrigem, T valorDestino, double peso) throws VerticeNaoEncontradoException {
        Vertice<T> origem = this.getVertice(valorOrigem);
        Vertice<T> destino = this.getVertice(valorDestino);

        if(origem == null) {
            throw new VerticeNaoEncontradoException((String) valorOrigem);
        }

        if(destino == null) {
            throw new VerticeNaoEncontradoException((String) valorDestino);
        }

        Aresta<T> aresta = new Aresta<>(origem, destino, peso);
        origem.addAresta(aresta);
    }

    /**
     * Implementação do algoritmo de PRIM
     *
     * Cria o grafo MST com um vértice aleatório (nesse caso, o 1.º do grafo original)
     * Usa uma FILA DE PRIORIDADE com as arestas que devem ser processadas
     *      As arestas a serem processadas são aquelas que partem dos vértices da MST
     *      A FILA DE PRIORIDADE garante que a primeira na fila sempre seja a de menor peso
     *      Portanto, a primeira da fila é a que deve ser adicionada ao MST
     *      Mas ela só será adicionada na MST se ainda não tiver sido incluída
     *
     * O algoritmo executa para o primeiro vértice da MST (que adicionamos manualmente),
     * e continua a executar até que todos os vértices do grafo original estejam na MST.
     *
     * Arestas e vértices não são duplicados (se um vértice já estiver na MST, ele não é adicionado de novo, e aquela aresta é descartada)
     * Por fim, o algoritmo adiciona na FILA DE PRIORIDADE as arestas do novo vértice adicionado
     * */
    @Override
    public Grafo<T> arvoreGeradoraMinima() throws VerticeDuplicadoException {
        int verticesCount = this.vertices.size();

        // Se o grafo estiver vazio, retorne
        if(verticesCount == 0) {
            return null;
        }

        Map<T, List<Aresta<T>>> arestasByVertice = this.getArestasByVertice();
        T verticeInicial = this.vertices.get(0).getValor();

        Grafo<T> arvoreGeradoraMinima = new Grafo<>();
        arvoreGeradoraMinima.adicionarVertice(verticeInicial);

        PriorityQueue<Aresta<T>> minHeap = new PriorityQueue<>();
        minHeap.addAll(arestasByVertice.get(verticeInicial));

        while(arvoreGeradoraMinima.vertices.size() < verticesCount) {
            Aresta<T> arestaMenorPeso = minHeap.poll();
            if(arestaMenorPeso != null) {
                // adicionar a menor aresta e o vértice na MST
                T destino = arestaMenorPeso.getDestino().getValor();
                if(!arvoreGeradoraMinima.contem(destino)) {
                    arvoreGeradoraMinima.adicionarVertice(destino);

                    Vertice<T> verticeDestino = arvoreGeradoraMinima.getVertice(destino);
                    Vertice<T> verticeOrigem = arvoreGeradoraMinima.getVertice(arestaMenorPeso.getOrigem().getValor());

                    Aresta<T> novaAresta = new Aresta<>(verticeOrigem, verticeDestino, arestaMenorPeso.getPeso());
                    verticeOrigem.addAresta(novaAresta);

                    minHeap.addAll(arestasByVertice.get(destino));
                }
            }
        }

        return arvoreGeradoraMinima;
    }

    /**
     * Método criado para facilitar a inclusão de arestas na MST sempre que um novo vértice é adicionado,
     * uma vez que novos ponteiros Vertice são criados para a MST, perdendo-se os dados das arestas que
     * devem ser processadas (para evitar inclusão de duplicatas, ou arestas erradas)
     *
     * @return MAP(Vertice: Aresta[])
     * */
    private Map<T, List<Aresta<T>>> getArestasByVertice() {
        Map<T, List<Aresta<T>>> arestasByVertice = new HashMap<>();

        for(Vertice<T> vertice : this.vertices) {
            arestasByVertice.put(vertice.getValor(), vertice.getArestas());
        }

        return arestasByVertice;
    }

    @Override
    public Grafo<T> caminhoMinimo(Object origem, Object destino) throws VerticeDuplicadoException, VerticeNaoEncontradoException {
        // Pego os vértices de origem e destino a partir dos objetos passados.
        Vertice<T> verticeOrigem = this.getVertice((T) origem);
        Vertice<T> verticeDestino = this.getVertice((T) destino);

        // Se não encontrar no grafo, retorno null.
        if (verticeOrigem == null || verticeDestino == null) {
            return null;
        }

        // Map para armazenar as distâncias mínimas dos vértices.
        Map<Vertice<T>, Float> distancias = new HashMap<>();
        // Map para guardar o caminho (quem é o antecessor de quem).
        Map<Vertice<T>, Vertice<T>> antecessores = new HashMap<>();
        // Fila de prioridade (min-heap) para processar os vértices pela menor distância.
        PriorityQueue<Vertice<T>> minHeap = new PriorityQueue<>(Comparator.comparing(distancias::get));

        // Inicializo as distâncias com infinito(no caso o valor máximo possível) e antecessores com null.
        for (Vertice<T> vertice : this.vertices) {
            distancias.put(vertice, Float.MAX_VALUE);
            antecessores.put(vertice, null);
        }

        // A distância da origem pra ela mesma é, é claro, zero.
        distancias.put(verticeOrigem, 0f);
        // Adiciono a origem na fila de prioridade.
        minHeap.add(verticeOrigem);

        // Enquanto a fila de prioridade não ficar vazia, continuo processando.
        while (!minHeap.isEmpty()) {
            // Pego o vértice com a menor distância.
            Vertice<T> verticeAtual = minHeap.poll();

            // Para cada aresta do vértice atual, calculo as novas distâncias.
            for (Aresta<T> aresta : verticeAtual.getArestas()) {
                Vertice<T> vizinho = aresta.getDestino();
                // Calculo a nova distância desse vizinho.
                float novaDistancia = distancias.get(verticeAtual) + (float)aresta.getPeso();

                // Se essa nova distância for menor, atualizo tudo.
                if (novaDistancia < distancias.get(vizinho)) {
                    distancias.put(vizinho, novaDistancia);
                    antecessores.put(vizinho, verticeAtual);

                    // Atualizo a posição do vizinho na fila de prioridade.
                    if (minHeap.contains(vizinho)) {
                        minHeap.remove(vizinho); // Remove a entrada antiga.
                    }
                    minHeap.add(vizinho); // Adiciona o vizinho com a nova distância.
                }
            }
        }

        // Aqui reconstruo o grafo que representa o caminho mínimo.
        Grafo<T> caminhoMinimoGrafo = new Grafo<>();
        Vertice<T> passo = verticeDestino;

        // Vou percorrendo os antecessores a partir do destino até chegar na origem.
        while (passo != null) {
            // Adiciono o vértice atual ao caminho mínimo.
            caminhoMinimoGrafo.adicionarVertice(passo.getValor());
            Vertice<T> antecessor = antecessores.get(passo);

            if (antecessor != null) {
                // Pego a aresta que conecta o antecessor ao passo atual.
                final Vertice<T> passoFinal = passo;

                Aresta<T> arestaOriginal = antecessor.getArestas().stream()
                        .filter(a -> a.getDestino().equals(passoFinal))
                        .findFirst()
                        .orElse(null);

                // Adiciono essa aresta ao caminho mínimo.
                caminhoMinimoGrafo.adicionarAresta(antecessor.getValor(), passoFinal.getValor(), (float)arestaOriginal.getPeso());
            }

            // passo vira o antecessor, voltando no caminho.
            passo = antecessor;
        }

        return caminhoMinimoGrafo;
    }

    public boolean contem(T valor) {
        for(Vertice<T> vertice : this.vertices) {
            if(vertice.getValor().equals(valor)) return true;
        }

        return false;
    }

    public void print() {
        for(Vertice<T> vertice : this.vertices) {
            System.out.println(vertice);
            System.out.println("----------------------------------");
        }
    }

    @Override
    public String toString() {
        String output = "";
        for(Vertice<T> vertice : this.vertices) {
            output += vertice.toString();
            output += "\n----------------------------------\n";
        }

        return output;
    }

    public List<Vertice<T>> getVertices() {
        return vertices;
    }

    public Vertice<T> getVertice(T valor) {
        for(Vertice<T> vertice : this.vertices) {
            if(vertice.getValor().equals(valor)) return vertice;
        }

        return null;
    }

    public String getLabel() {
        return label;
    }

    public void setVertices(List<Vertice<T>> vertices) {
        this.vertices = vertices;
    }

    public List<Aresta<T>> getArestas() {
        return this.vertices.stream()
                .flatMap(vertice -> vertice.getArestas().stream())
                .collect(Collectors.toList());
    }
}
