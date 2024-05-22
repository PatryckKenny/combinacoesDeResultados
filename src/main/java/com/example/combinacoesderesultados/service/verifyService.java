package com.example.combinacoesderesultados.service;

import com.example.combinacoesderesultados.exception.ErrorResponse;
import com.example.combinacoesderesultados.model.PlacarTimes;
import com.example.combinacoesderesultados.enums.PontosEnum;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class verifyService {

    public Integer combinacoes(PlacarTimes pontuacaoTime) throws ErrorResponse{

        PontosEnum[] pontos = PontosEnum.values();
        AtomicInteger combinacaoTime1 = new AtomicInteger(0);
        AtomicInteger combinacaoTime2 = new AtomicInteger(0);
        List<Callable<Void>> tasks = new ArrayList<>();


        // Criando um Executor com duas thread
        ExecutorService executorService = Executors.newFixedThreadPool(2);

        //thread para calcular possíveis resultados do time 1
        tasks.add(() -> {
            try {
                testarResultado(pontos, pontuacaoTime.getTime1(), 0, combinacaoTime1);
                return null;
            } catch (StackOverflowError e) {

                throw new ErrorResponse("Erro ao tentar calcular placares: "+e.toString(), HttpStatus.INTERNAL_SERVER_ERROR);
            }

        });

        //thread para calcular possíveis resultados do time 1
        tasks.add(() -> {
            try {

                testarResultado(pontos, pontuacaoTime.getTime2(), 0, combinacaoTime2);
                return null;
            } catch (StackOverflowError e) {
                throw new ErrorResponse("Erro ao tentar calcular placares: "+e.toString(), HttpStatus.INTERNAL_SERVER_ERROR);
            }
        });

        List<Future<Void>> futures = null;
        try {

            futures = executorService.invokeAll(tasks);

            // Esperando todas as tarefas serem concluídas
            for (Future<Void> future : futures) {
                try {
                    future.get();
                } catch (ExecutionException e) {
                    throw new ErrorResponse(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
                }
            }
            // Realizando a multiplicação do resultado do time1 com o time2
            Integer combinacoes = combinacaoTime1.get() * combinacaoTime2.get();

            return combinacoes;

        } catch (InterruptedException e) {
            throw new ErrorResponse(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        } finally {
            executorService.shutdown();
        }
    }

    public PlacarTimes tratandoPlacar(String placar) throws ErrorResponse {
        try {
            // Dividir a string no caractere 'x'
            String[] parts = placar.split("x");

            return new PlacarTimes(Integer.parseInt(parts[0]), Integer.parseInt(parts[1]));
        } catch (NumberFormatException e) {
            throw new ErrorResponse("Valor invalido", HttpStatus.BAD_REQUEST);
        }

    }

    public static void testarResultado(PontosEnum[] pontos, Integer pontuacaoTime, Integer index, AtomicInteger combinacoes) {
        if (pontuacaoTime == 0) {
            combinacoes.incrementAndGet();
            return;
        }

        if (pontuacaoTime < 0 || index == pontos.length) {
            return;
        }


        // Incluir o valor atual do ponto
        testarResultado(pontos, pontuacaoTime - pontos[index].getValue(), index, combinacoes);

        // Excluir o valor atual do ponto e passar para o próximo
        testarResultado(pontos, pontuacaoTime, index + 1, combinacoes);
    }
}