package org.example;

import java.util.*;
import java.util.stream.Collectors;

public class Tabuleiro {
    private final int dimensao;
    private final Posicao[][] tabuleiro;
    private final Map<Posicao, List<Posicao>> grafoMovimentos;

    private final Map<Posicao, Integer> mapaPosicaoInicialQuantidadeSolucoes;
    
    // Construtor
    public Tabuleiro(int dimensao) {
        if (dimensao <= 0) {
            throw new IllegalArgumentException("Dimensão deve ser maior que zero");
        }
        this.dimensao = dimensao;
        this.tabuleiro = new Posicao[dimensao][dimensao];
        inicializarTabuleiro();

        this.grafoMovimentos = criarGrafoMovimentosCavalo();

        this.mapaPosicaoInicialQuantidadeSolucoes = new HashMap<>();
    }
    
    // Inicializa o tabuleiro criando posições
    private void inicializarTabuleiro() {
        for (int i = 0; i < dimensao; i++) {
            for (int j = 0; j < dimensao; j++) {
                tabuleiro[i][j] = new Posicao(i, j);
            }
        }
    }
    
    // Verifica se uma posição está dentro dos limites do tabuleiro
    /*public boolean posicaoValida(int linha, int coluna) {
        return linha >= 0 && linha < dimensao && coluna >= 0 && coluna < dimensao;
    }*/
    
    // Retorna a posição em determinada coordenada
    public Posicao getPosicao(int linha, int coluna) {
        if (!Posicao.posicaoValida(linha, coluna, this.dimensao)) {
            throw new IllegalArgumentException("Posição inválida");
        }
        return tabuleiro[linha][coluna];
    }

    // Retorna a posição ordenada
    public Posicao getPosicaoOrdenada(int numOrdem) {
        if(numOrdem < 0 || numOrdem >= (this.dimensao * this.dimensao))
            throw new IllegalArgumentException("Posição inválida");

        int linha = numOrdem / this.dimensao;
        int coluna = numOrdem % this.dimensao;

        if (!Posicao.posicaoValida(linha, coluna, this.dimensao)) {
            throw new IllegalArgumentException("Posição inválida");
        }
        return tabuleiro[linha][coluna];
    }
    
    // Retorna a dimensão do tabuleiro
    public int getDimensao() {
        return dimensao;
    }
    
    // Metodo para imprimir o tabuleiro (útil para debug)
    public void imprimirTabuleiro() {
        for (int i = 0; i < dimensao; i++) {
            for (int j = 0; j < dimensao; j++) {
                System.out.print("(" + tabuleiro[i][j].getLinha() +
                               "," + tabuleiro[i][j].getColuna() + ") ");
            }
            System.out.println();
        }
    }

    // Metodo para gerar e armazenar todos os movimentos possíveis do cavalo
    private Map<Posicao, List<Posicao>> criarGrafoMovimentosCavalo() {
        Map<Posicao, List<Posicao>> todosMovimentosCavalo = new HashMap<>();

        // Percorre todas as posições do tabuleiro
        for (int i = 0; i < dimensao; i++) {
            for (int j = 0; j < dimensao; j++) {
                Posicao posicaoAtual = tabuleiro[i][j];
                // Calcula os movimentos possíveis para a posição atual
                List<Posicao> movimentos = posicaoAtual.movimentosPossiveisCavalo(this.dimensao);
                // Armazena no HashMap
                todosMovimentosCavalo.put(posicaoAtual, movimentos);
            }
        }

        return todosMovimentosCavalo;
    }

    /*
    public List<Posicao> encontrarPasseioDoCavalo(Posicao inicio) {
        List<Posicao> caminho = new ArrayList<>();
        Set<Posicao> visitados = new HashSet<>();

        // PriorityQueue ordenada pelo número de movimentos disponíveis (heurística de Warnsdorff)
        PriorityQueue<Posicao> fila = new PriorityQueue<>((p1, p2) -> {
            List<Posicao> movs1 = this.grafoMovimentos.get(p1);
            List<Posicao> movs2 = this.grafoMovimentos.get(p2);
            return Integer.compare(
                    (int) movs1.stream().filter(p -> !visitados.contains(p)).count(),
                    (int) movs2.stream().filter(p -> !visitados.contains(p)).count()
            );
        });

        // Começar pela posição inicial
        fila.offer(inicio);
        caminho.add(inicio);
        visitados.add(inicio);

        // Total de posições a visitar
        int totalPosicoes = dimensao * dimensao;

        while (!fila.isEmpty() && visitados.size() < totalPosicoes) {
            Posicao atual = fila.poll();
            List<Posicao> movimentosPossiveis = grafoMovimentos.get(atual);

            // Ordena movimentos pela heurística de Warnsdorff (menos movimentos disponíveis primeiro)
            List<Posicao> movimentosOrdenados = movimentosPossiveis.stream()
                    .filter(p -> !visitados.contains(p))
                    .sorted((p1, p2) -> {
                        long movs1 = grafoMovimentos.get(p1).stream()
                                .filter(p -> !visitados.contains(p)).count();
                        long movs2 = grafoMovimentos.get(p2).stream()
                                .filter(p -> !visitados.contains(p)).count();
                        return Long.compare(movs1, movs2);
                    })
                    .collect(Collectors.toList());

            // Tenta cada movimento possível
            for (Posicao proximaPosicao : movimentosOrdenados) {
                if (!visitados.contains(proximaPosicao)) {
                    visitados.add(proximaPosicao);
                    caminho.add(proximaPosicao);
                    fila.offer(proximaPosicao);
                    break; // Encontrou um movimento válido, continua com a próxima posição
                }
            }
        }

        // Verifica se encontrou um caminho completo
        if (caminho.size() != totalPosicoes) {
            return null; // Não foi possível encontrar um caminho completo
        }

        return caminho;
    }

    //===================================================================================
    //===================================================================================

    public List<Posicao> encontrarPasseioDoCavalo2(Posicao inicio) {
        List<Posicao> caminho = new ArrayList<>();
        boolean[][] visitado = new boolean[dimensao][dimensao];

        // Inicializa com a posição inicial
        caminho.add(inicio);
        visitado[inicio.getLinha()][inicio.getColuna()] = true;

        if (encontrarPasseioRecursivo(inicio, caminho, visitado, 1)) {
            return caminho;
        }
        return null;
    }


    private boolean encontrarPasseioRecursivo(Posicao atual, List<Posicao> caminho,
                                              boolean[][] visitado, int movimentos) {
        // Se já visitamos todas as casas, encontramos uma solução
        if (movimentos == dimensao * dimensao) {
            return true;
        }

        List<Posicao> proximosMovimentos = grafoMovimentos.get(atual);

        // Ordena os movimentos usando a heurística de Warnsdorff
        proximosMovimentos.sort((p1, p2) -> {
            long movs1 = contarMovimentosDisponiveis(p1, visitado);
            long movs2 = contarMovimentosDisponiveis(p2, visitado);
            return Long.compare(movs1, movs2);
        });

        // Tenta cada movimento possível
        for (Posicao proxima : proximosMovimentos) {
            if (!visitado[proxima.getLinha()][proxima.getColuna()]) {
                // Tenta este movimento
                visitado[proxima.getLinha()][proxima.getColuna()] = true;
                caminho.add(proxima);

                if (encontrarPasseioRecursivo(proxima, caminho, visitado, movimentos + 1)) {
                    return true;
                }

                // Se não funcionou, desfaz o movimento (backtracking)
                visitado[proxima.getLinha()][proxima.getColuna()] = false;
                caminho.remove(caminho.size() - 1);
            }
        }

        return false;
    }

    private int contarMovimentosDisponiveis(Posicao posicao, boolean[][] visitado) {
        return (int) grafoMovimentos.get(posicao).stream()
                .filter(p -> !visitado[p.getLinha()][p.getColuna()])
                .count();
    }

     */

    //===================================================================================
    //===================================================================================

    public TreeNode<Posicao> encontrarPasseioDoCavalo3(Posicao inicio) {
        int movimentos = 1;
        final TreeNode<Posicao> initialNode = new TreeNode<Posicao>(inicio, movimentos, null);
        boolean[][] visitados = new boolean[dimensao][dimensao];

        // Inicializa com a posição inicial
        visitados[inicio.linha][inicio.coluna] = true;

        if(encontrarPasseioRecursivo3(initialNode, visitados, movimentos)) {
            return initialNode;
        }
        return null;
    }

    private boolean encontrarPasseioRecursivo3(TreeNode<Posicao> currentNode, boolean[][] visitados, int movimentos) {
        // Se já visitamos todas as casas, encontramos uma solução
        if (movimentos == dimensao * dimensao) {
            System.out.println("==========================");

            final List<Posicao> caminho = new ArrayList<>();
            TreeNode<Posicao> currToPrint = currentNode;

            while(currToPrint != null) {
                caminho.add(currToPrint.value);
                currToPrint = currToPrint.parentNode;
            }

            Collections.reverse(caminho);

            Posicao primeiraPosicao = caminho.getFirst();
            int quantidadeSolucoes = 1 + this.mapaPosicaoInicialQuantidadeSolucoes.getOrDefault(primeiraPosicao, 0);
            this.mapaPosicaoInicialQuantidadeSolucoes.put(primeiraPosicao, quantidadeSolucoes);

            System.out.println("Posição Inicial: " + primeiraPosicao + " - Quantidade de Soluções Encontradas (até o momento): " + quantidadeSolucoes);
            //System.out.println("Solução mais recente: " + caminho.stream().map(Object::toString).collect(Collectors.joining(" -> ")));

            return true;
        }

        final List<Posicao> proximosMovimentos = grafoMovimentos.get(currentNode.value);

        //((movimentos % 2 == 0) ?
                (proximosMovimentos.parallelStream())
        //        :
        //        (proximosMovimentos.stream())
        //)
        .forEach(proximaPosicao -> {
            if (!visitados[proximaPosicao.linha][proximaPosicao.coluna]) {

                final TreeNode<Posicao> childNode = currentNode.addChild(proximaPosicao);

                boolean[][] visitadosNovo = new boolean[dimensao][dimensao];
                for (int i = 0; i < dimensao; i++) {
                    visitadosNovo[i] = Arrays.copyOf(visitados[i], dimensao);
                }
                visitadosNovo[proximaPosicao.linha][proximaPosicao.coluna] = true;

                encontrarPasseioRecursivo3(childNode, visitadosNovo, movimentos + 1);
            }
        });


        //System.out.println("#########################");
        if(currentNode.depth < (dimensao * dimensao - 2) &&
                currentNode.childNodes.isEmpty() &&
                currentNode.parentNode != null)
        {
            //System.out.println("currentNode.parentNode.deleteChild(" + currentNode + ")");
            currentNode.parentNode.deleteChild(currentNode);
        }

        return false;
    }

    public Map<Posicao, Integer> getMapaPosicaoInicialQuantidadeSolucoes() {
        return mapaPosicaoInicialQuantidadeSolucoes;
    }
}