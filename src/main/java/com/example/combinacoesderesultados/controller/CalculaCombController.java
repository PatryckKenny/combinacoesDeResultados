package com.example.combinacoesderesultados.controller;

import com.example.combinacoesderesultados.exception.ErrorResponse;
import com.example.combinacoesderesultados.model.Placar;
import com.example.combinacoesderesultados.model.PlacarTimes;
import com.example.combinacoesderesultados.service.verifyService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping()
@RequiredArgsConstructor
@Tag(name = "CalculaCombController")
public class CalculaCombController {

    final verifyService verifyService;

    @Operation(summary = "Calcular quantidade de possíveis resultado a partir de um placar", method = "POST")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Calculo realizado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Parametro inválido"),
            @ApiResponse(responseCode = "500", description = "Erro ao calcular a combinação de resultados")
    })
    @PostMapping("verify")
    public ResponseEntity<String> verify(@RequestBody Placar placar){

        PlacarTimes pontuacaoTime = null;
        try {
            pontuacaoTime = verifyService.tratandoPlacar(placar.getScore());

        if (pontuacaoTime.getTime1() < 0 || pontuacaoTime.getTime2() < 0) {
            return ResponseEntity.badRequest().body("Não é possível calcular com valor negativo");
        }

        Integer resultado = verifyService.combinacoes(pontuacaoTime);
        return ResponseEntity.ok(resultado == 0 ? "Não existe combinações possíveis para esse placar": resultado.toString());
        } catch (ErrorResponse| StackOverflowError e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }


}
