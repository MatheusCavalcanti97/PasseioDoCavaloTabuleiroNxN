package org.example;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Posicao {
    private int linha;
    private int coluna;

    public Posicao(int linha, int coluna) {
        this.linha = linha;
        this.coluna = coluna;
    }
    
    public int getLinha() {
        return linha;
    }
    
    public void setLinha(int linha) {
        this.linha = linha;
    }
    
    public int getColuna() {
        return coluna;
    }
    
    public void setColuna(int coluna) {
        this.coluna = coluna;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Posicao posicao = (Posicao) o;
        return linha == posicao.linha && coluna == posicao.coluna;
    }

    @Override
    public int hashCode() {
        return Objects.hash(linha, coluna);
    }

    // Metodo que retorna todas as possíveis posições do movimento do cavalo
    public List<Posicao> movimentosPossiveisCavalo(int dimensao) {
        List<Posicao> movimentos = new ArrayList<>();

        // Os 8 possíveis movimentos do cavalo
        int[][] deslocamentos = {
                {-2, -1}, {-2, 1},  // Dois movimentos para cima
                {2, -1},  {2, 1},   // Dois movimentos para baixo
                {-1, -2}, {1, -2},  // Dois movimentos para esquerda
                {-1, 2},  {1, 2}    // Dois movimentos para direita
        };

        // Verifica cada possível movimento
        for (int[] deslocamento : deslocamentos) {
            int novaLinha = this.linha + deslocamento[0];
            int novaColuna = this.coluna + deslocamento[1];

            // Se a nova posição é válida, adiciona à lista de movimentos possíveis
            if (this.posicaoValida(novaLinha, novaColuna, dimensao)) {
                movimentos.add(new Posicao(novaLinha, novaColuna));
            }
        }

        return movimentos;
    }

    private boolean posicaoValida(int novaLinha, int novaColuna, int dimensao) {
        if((novaLinha < 0) || (novaLinha >= dimensao))
            return false;
        if((novaColuna < 0) || (novaColuna >= dimensao))
            return false;

        return true;
    }

    @Override
    public String toString() {
        return "Posicao{" +
                "linha=" + linha +
                ", coluna=" + coluna +
                '}';
    }
}