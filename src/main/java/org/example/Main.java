package org.example;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {

    public static void main(String[] args) {

        System.out.println("Date Init: " + new Date());
        long timeIni = System.currentTimeMillis();

        final int DIMENSAO_TABULEIRO = 6;

        final int dim = DIMENSAO_TABULEIRO;
        final Tabuleiro tabuleiro = new Tabuleiro(dim);

        //final Map<Posicao, TreeNode<Posicao>> mapaTreeCaminhos = new HashMap<>();

        IntStream
                .range(0, dim * dim)
                //.range(0, 1)
                .sorted()
                //.parallel()
                .forEach(index ->
                        {
                            final Posicao posicaoInicial = tabuleiro.getPosicaoOrdenada(index);
                            //System.out.println("posicaoInicial: " + posicaoInicial);

                            final TreeNode<Posicao> treeCaminhos = tabuleiro.encontrarPasseioDoCavalo5(posicaoInicial);
                            //mapaTreeCaminhos.put(posicaoInicial, treeCaminhos);
                        }
                );

        /*for (Map.Entry<Posicao, TreeNode<Posicao>> entry : mapaTreeCaminhos.entrySet()) {
            System.out.println("Key: " + entry.getKey() + "\t->\tValue: " + entry.getValue());
        }*/

        System.out.println("Date End: " + new Date());
        long timeEnd = System.currentTimeMillis();

        int somaTotal = tabuleiro.getMapaPosicaoInicialQuantidadeSolucoes()
                .entrySet()
                .stream()
                .sorted((e1,e2) -> e1.getKey().compareTo(e2.getKey()))
                .peek(e -> System.out.println("Posição " + e.getKey() + " - Quantidade soluções: " + e.getValue()) )
                .map(Map.Entry::getValue)
                .reduce(0, Integer::sum);
        System.out.println("Total de soluções de um tabuleiro "+dim+"x"+dim+": " + somaTotal);
        System.out.println();

        System.out.println("Tempo total: " + (timeEnd - timeIni) + " ms");
    }

}