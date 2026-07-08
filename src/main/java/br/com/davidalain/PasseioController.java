package br.com.davidalain;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@RestController
public class PasseioController {

    @GetMapping("/calcular")
    public Map<String, Object> calcularPasseio(@RequestParam(defaultValue = "6") int dimensao) {
        long timeIni = System.currentTimeMillis();

        final Tabuleiro tabuleiro = new Tabuleiro(dimensao);

        IntStream.range(0, dimensao * dimensao)
                .mapToObj(i -> tabuleiro.getPosicaoOrdenada(i).posicaoEspelhoOriginal(dimensao))
                .collect(Collectors.toSet())
                .forEach(tabuleiro::encontrarPasseioDoCavalo7);

        IntStream.range(0, dimensao * dimensao)
                .sorted()
                .parallel()
                .mapToObj(tabuleiro::getPosicaoOrdenada)
                .forEach(tabuleiro::encontrarPasseioDoCavalo7);

        long timeEnd = System.currentTimeMillis();

        int somaTotal = tabuleiro.getMapaPosicaoInicialQuantidadeSolucoes()
                .values()
                .stream()
                .reduce(0, Integer::sum);

        return Map.of(
                "dimensaoTabuleiro", dimensao + "x" + dimensao,
                "totalSolucoes", somaTotal,
                "tempoExecucaoMs", (timeEnd - timeIni),
                "status", "Sucesso"
        );
    }
}