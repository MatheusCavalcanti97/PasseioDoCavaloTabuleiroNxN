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

        final int dimensaoTabuleiro = 6;
        final Tabuleiro tabuleiro = new Tabuleiro(dimensaoTabuleiro);

        final Map<Posicao, TreeNode<Posicao>> mapaTreeCaminhos = new HashMap<>();

        IntStream
                .range(0, dimensaoTabuleiro * dimensaoTabuleiro)
                //.range(0, 1)
                .sorted()
                .parallel()
                .forEach(index ->
                        {
                            final Posicao posicaoInicial = tabuleiro.getPosicaoOrdenada(index);
                            final TreeNode<Posicao> treeCaminhos = tabuleiro.encontrarPasseioDoCavalo3(posicaoInicial);

                            mapaTreeCaminhos.put(posicaoInicial, treeCaminhos);
                        }
                );

        /*for (Map.Entry<Posicao, TreeNode<Posicao>> entry : mapaTreeCaminhos.entrySet()) {
            System.out.println("Key: " + entry.getKey() + "\t->\tValue: " + entry.getValue());
        }*/


        System.out.println("Date End: " + new Date());
        tabuleiro.getMapaPosicaoInicialQuantidadeSolucoes()
                .entrySet()
                .stream()
                .sorted((e1,e2) -> e1.getValue().compareTo(e2.getValue()))
                .forEach(e -> {
                    System.out.println("Posição " + e.getKey() + " - Quantidade soluções: " + e.getValue());
                });

        int somaTotal = tabuleiro.getMapaPosicaoInicialQuantidadeSolucoes()
                .values()
                .stream()
                .reduce(0, Integer::sum);
        System.out.println("Soma Total: " + somaTotal);

    }

}