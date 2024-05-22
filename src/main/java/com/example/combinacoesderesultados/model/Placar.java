package com.example.combinacoesderesultados.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Placar {

    @Schema(description = "Resultado da partida", example = "15x3")
    String score;
}
