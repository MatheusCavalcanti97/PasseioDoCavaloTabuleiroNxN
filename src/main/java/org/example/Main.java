package org.example;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.IntStream;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {

    public static void main(String[] args) {

        final int dimensaoTabuleiro = 6;
        final Tabuleiro tabuleiro = new Tabuleiro(dimensaoTabuleiro);

        final Map<Posicao, TreeNode<Posicao>> mapaPasseio = new HashMap<>();

        IntStream
                .range(0, dimensaoTabuleiro * dimensaoTabuleiro)
                .sorted()
                .parallel()
                .forEach(index ->
                        {
                            final Posicao posicaoInicial = tabuleiro.getPosicaoOrdenada(index);
                            final TreeNode<Posicao> treeCaminhos = tabuleiro.encontrarPasseioDoCavalo3(posicaoInicial);

                            mapaPasseio.put(posicaoInicial, treeCaminhos);
                        }
                );

        for (Map.Entry<Posicao, TreeNode<Posicao>> entry : mapaPasseio.entrySet()) {
            System.out.println("Key: " + entry.getKey() + "\t->\tValue: " + entry.getValue());
        }

        /*
        mapaPasseio.forEach((posicaoInicial,passeio) -> {
            if (passeio != null) {
                System.out.println();
                System.out.println("Solução encontrada para posição inicial: " + posicaoInicial);
                for (int i = 0; i < passeio.size(); i++) {
                    System.out.printf("Movimento %d: %s%n", i + 1, passeio.get(i));
                }
                System.out.println();
            } else {
                System.out.println("Não foi possível encontrar uma solução.");
            }
        }
        );
        */

    }

}