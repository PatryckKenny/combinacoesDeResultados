package com.example.combinacoesderesultados.controller;

import com.example.combinacoesderesultados.exception.ErrorResponse;
import com.example.combinacoesderesultados.model.Placar;
import com.example.combinacoesderesultados.model.PlacarTimes;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import com.example.combinacoesderesultados.service.verifyService;
import org.springframework.http.ResponseEntity;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
public class CalculaCombControllerTest {
    @Mock
    private verifyService verifyService;

    @InjectMocks
    private CalculaCombController calculaCombController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testVerifyValidPlacar() throws ErrorResponse {
        calculaCombController = new CalculaCombController(verifyService);

        Placar placar = new Placar("15x3");

        PlacarTimes pontuacaoTime = new PlacarTimes();
        pontuacaoTime.setTime1(15);
        pontuacaoTime.setTime2(3);

        when(verifyService.tratandoPlacar(placar.getScore())).thenReturn(pontuacaoTime);
        when(verifyService.combinacoes(pontuacaoTime)).thenReturn(1);

        ResponseEntity<String> response = calculaCombController.verify(placar);

        assertEquals(ResponseEntity.ok("1"), response);
    }

    @Test
    public void testVerifyNegativeScore() throws ErrorResponse {
        calculaCombController = new CalculaCombController(verifyService);

        Placar placar = new Placar();
        placar.setScore("-15x3");

        PlacarTimes pontuacaoTime = new PlacarTimes();
        pontuacaoTime.setTime1(-1);
        pontuacaoTime.setTime2(2);

        when(verifyService.tratandoPlacar(placar.getScore())).thenReturn(pontuacaoTime);

        ResponseEntity<String> response = calculaCombController.verify(placar);

        assertEquals(ResponseEntity.badRequest().body("Não é possível calcular com valor negativo"), response);
    }

    @Test
    public void testVerifyNoCombinations() throws ErrorResponse {
        calculaCombController = new CalculaCombController(verifyService);

        Placar placar = new Placar();
        placar.setScore("15x1");

        PlacarTimes pontuacaoTime = new PlacarTimes();
        pontuacaoTime.setTime1(1);
        pontuacaoTime.setTime2(1);

        when(verifyService.tratandoPlacar(placar.getScore())).thenReturn(pontuacaoTime);
        when(verifyService.combinacoes(pontuacaoTime)).thenReturn(0);

        ResponseEntity<String> response = calculaCombController.verify(placar);

        assertEquals(ResponseEntity.ok("Não existe combinações possíveis para esse placar"), response);
    }

    @Test
    public void testVerifyErrorResponse() throws ErrorResponse {
        calculaCombController = new CalculaCombController(verifyService);
        ErrorResponse e;

        Placar placar = new Placar();
        placar.setScore("15x3q");

        when(verifyService.tratandoPlacar(placar.getScore())).thenThrow(new ErrorResponse());

        ResponseEntity<String> response = calculaCombController.verify(placar);

        assertNotNull(response);
    }
}
