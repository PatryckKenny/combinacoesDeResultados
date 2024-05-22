package com.example.combinacoesderesultados;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@OpenAPIDefinition(info = @Info(title = "Calculador de resultados de partida de Futebol Americano", version = "1", description = "API desenvolvida para testes do OpenApi"))
@EnableAsync
public class CombinacoesDeResultadosApplication {

    public static void main(String[] args) {
        SpringApplication.run(CombinacoesDeResultadosApplication.class, args);
    }

}
