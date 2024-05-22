package com.example.combinacoesderesultados.service;

import com.example.combinacoesderesultados.exception.ErrorResponse;
import com.example.combinacoesderesultados.model.Placar;
import com.example.combinacoesderesultados.model.PlacarTimes;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.boot.test.context.SpringBootTest;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class verifyServiceTest {

    @InjectMocks
    private verifyService verifyService;

    @BeforeEach
    public void setUp() {
    }

    @Test
    public void testCombinacoes() throws ErrorResponse {
        PlacarTimes pontuacaoTime = new PlacarTimes(3, 3);

        Integer result = verifyService.combinacoes(pontuacaoTime);

        assertEquals(1, result);
    }

    @Test
    public void testTratandoPlacar() throws ErrorResponse {

        PlacarTimes expected = new PlacarTimes(15, 3);

        Placar placar = new Placar("15x3");
        PlacarTimes result = verifyService.tratandoPlacar(placar.getScore());

        assertEquals(expected.getTime1(), result.getTime1());
        assertEquals(expected.getTime2(), result.getTime2());

    }
}
