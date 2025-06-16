package org.example;

import java.util.HashMap;
import java.util.Map;
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

    }

}