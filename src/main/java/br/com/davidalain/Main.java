package br.com.davidalain;

import java.util.Date;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {

    public static void main(String[] args) {

        System.out.println("Date Ini: " + new Date());
        long timeIni = System.currentTimeMillis();

        final int dimensaoTabuleiro = 6;

        final Tabuleiro tabuleiro = new Tabuleiro(dimensaoTabuleiro);

        IntStream
                .range(0, dimensaoTabuleiro * dimensaoTabuleiro)
                //.range(0, 1)
                //.sorted()
                .mapToObj(i -> tabuleiro.getPosicaoOrdenada(i).posicaoEspelhoOriginal(dimensaoTabuleiro)) //itera nas posições espelho originais
                .collect(Collectors.toSet())
                //.parallel()
                .forEach(tabuleiro::encontrarPasseioDoCavalo7);

        IntStream
                .range(0, dimensaoTabuleiro * dimensaoTabuleiro)
                .sorted()
                .parallel()
                .mapToObj(tabuleiro::getPosicaoOrdenada) //itera todas as posições
                .forEach(tabuleiro::encontrarPasseioDoCavalo7);

        /*for (Map.Entry<Posicao, TreeNode<Posicao>> entry : mapaTreeCaminhos.entrySet()) {
            System.out.println("Key: " + entry.getKey() + "\t->\tValue: " + entry.getValue());
        }*/

        System.out.println("Date End: " + new Date());
        System.out.println();

        long timeEnd = System.currentTimeMillis();

        int somaTotal = tabuleiro.getMapaPosicaoInicialQuantidadeSolucoes()
                .entrySet()
                .stream()
                .sorted(Map.Entry.comparingByKey())
                .peek(e -> System.out.println("Posição " + e.getKey() + " - Quantidade soluções: " + e.getValue()))
                .map(Map.Entry::getValue)
                .reduce(0, Integer::sum);
        System.out.println();

        System.out.println("Total de soluções de um tabuleiro " + dimensaoTabuleiro + "x" + dimensaoTabuleiro + ": " + somaTotal);
        System.out.println();

        System.out.println("Tempo total: " + (timeEnd - timeIni) + " ms");
    }

}