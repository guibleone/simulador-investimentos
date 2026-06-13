package br.gov.caixa.service;

import br.gov.caixa.dto.response.SimulacaoParcelaResponse;
import br.gov.caixa.dto.response.SimulacaoResponse;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import jakarta.ws.rs.NotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

@QuarkusTest
public class SimulacaoServiceTest {

    private final SimulacaoService simulacaoService;

    private static final String CO_SIMULACAO = "a1b2c3d4-e5f6-7a8b-9c0d-1e2f3a4b5c6d";

    @Inject
    public SimulacaoServiceTest(SimulacaoService simulacaoService){
        this.simulacaoService = simulacaoService;
    }

    @Test
    @DisplayName("Deve retornar simulações cadastradas inicialmente")
    void listarTodos_deveRetornarListaDeSimulacoes() {
        // Act
        List<SimulacaoResponse> resultado = simulacaoService.listarTodos();
        SimulacaoResponse simulacao = resultado.getFirst();

        // Assert
        Assertions.assertEquals(1, resultado.size());
        Assertions.assertEquals(CO_SIMULACAO, simulacao.coSimulacao());
        Assertions.assertEquals(1000.00, simulacao.valorInicial().doubleValue());
        Assertions.assertEquals(0.02, simulacao.taxaJurosMensal().doubleValue());
        Assertions.assertEquals(3, simulacao.prazoMeses());
        Assertions.assertEquals(1061.21, simulacao.valorTotalFinal().doubleValue());
        Assertions.assertEquals(61.21, simulacao.valorTotalJuros().doubleValue());
    }
    @Test
    @DisplayName("Deve encontrar uma simulação existente pelo código")
    void encontrarSimulacao_deveRetornarSimulacaoExistente() {
        // Act
        SimulacaoResponse resultado = simulacaoService.encontrarSimulacao(CO_SIMULACAO).simulacao();

        // Assert
        Assertions.assertEquals(CO_SIMULACAO, resultado.coSimulacao());
    }

    @Test
    @DisplayName("Deve retornar as parcelas corretas para uma simulação existente")
    void encontrarSimulacao_deveRetornarParcelasCorretas() {
        // Act
        List<SimulacaoParcelaResponse> parcelas = simulacaoService.encontrarSimulacao(CO_SIMULACAO).parcelas();

        // Assert
        Assertions.assertEquals(3, parcelas.size());
    }

    @Test
    @DisplayName("Deve lançar NotFoundException ao tentar encontrar uma simulação inexistente")
    void encontrarSimulacao_deveLancarNotFoundExceptionParaSimulacaoInexistente() {
        // Act & Assert
        Assertions.assertThrows(NotFoundException.class, () -> simulacaoService.encontrarSimulacao("codigo-inexistente"));
    }
}
