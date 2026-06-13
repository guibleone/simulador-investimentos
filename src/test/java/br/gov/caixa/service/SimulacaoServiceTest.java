package br.gov.caixa.service;

import br.gov.caixa.dto.request.DadosSimulacaoRequest;
import br.gov.caixa.dto.response.SimulacaoParcelaResponse;
import br.gov.caixa.dto.response.SimulacaoResponse;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import jakarta.ws.rs.NotFoundException;
import org.junit.jupiter.api.*;

import java.math.BigDecimal;
import java.util.List;

@QuarkusTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class SimulacaoServiceTest {

    private final SimulacaoService simulacaoService;

    private static final String CO_SIMULACAO = "a1b2c3d4-e5f6-7a8b-9c0d-1e2f3a4b5c6d";
    private static final DadosSimulacaoRequest SIMULACAO_REQUEST = new DadosSimulacaoRequest(
            BigDecimal.valueOf(1000.00),
            BigDecimal.valueOf(2),
            3
    );

    @Inject
    public SimulacaoServiceTest(SimulacaoService simulacaoService){
        this.simulacaoService = simulacaoService;
    }

    @Test
    @Order(1)
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
    @Order(2)
    @DisplayName("Deve encontrar uma simulação existente pelo código")
    void encontrarSimulacao_deveRetornarSimulacaoExistente() {
        // Act
        SimulacaoResponse resultado = simulacaoService.encontrarSimulacao(CO_SIMULACAO).simulacao();

        // Assert
        Assertions.assertEquals(CO_SIMULACAO, resultado.coSimulacao());
    }

    @Test
    @Order(3)
    @DisplayName("Deve retornar as parcelas corretas para uma simulação existente")
    void encontrarSimulacao_deveRetornarParcelasCorretas() {
        // Act
        List<SimulacaoParcelaResponse> parcelas = simulacaoService.encontrarSimulacao(CO_SIMULACAO).parcelas();

        // Assert
        Assertions.assertEquals(3, parcelas.size());
    }

    @Test
    @Order(4)
    @DisplayName("Deve lançar NotFoundException ao tentar encontrar uma simulação inexistente")
    void encontrarSimulacao_deveLancarNotFoundExceptionParaSimulacaoInexistente() {
        // Act & Assert
        Assertions.assertThrows(NotFoundException.class, () -> simulacaoService.encontrarSimulacao("codigo-inexistente"));
    }

    @Test
    @Order(5)
    @DisplayName("Deve criar uma simulação e retornar o resultado correto")
    void realizarSimulacao_deveCriarSimulacaoERetornarResultadoCorreto() {
        // Act
        var resultado = simulacaoService.realizarSimulacao(SIMULACAO_REQUEST);

        // Assert
        Assertions.assertNotNull(resultado);
        Assertions.assertEquals(SIMULACAO_REQUEST.valorInicial(), resultado.simulacao().valorInicial());
        Assertions.assertEquals(SIMULACAO_REQUEST.taxaJurosMensal(), resultado.simulacao().taxaJurosMensal());
        Assertions.assertEquals(SIMULACAO_REQUEST.prazoMeses(), resultado.simulacao().prazoMeses());
    }

    @Test
    @Order(6)
    @DisplayName("Deve calcular o valor total final e o valor total de juros corretamente")
    void realizarSimulacao_deveCalcularValorTotalFinalEValorTotalJurosCorretamente() {
        // Act
        var resultado = simulacaoService.realizarSimulacao(SIMULACAO_REQUEST);

        // Assert
        Assertions.assertEquals(1061.21, resultado.simulacao().valorTotalFinal().doubleValue());
        Assertions.assertEquals(61.21, resultado.simulacao().valorTotalJuros().doubleValue());
    }

    @Test
    @Order(7)
    @DisplayName("Deve gerar as parcelas corretamente para a simulação realizada")
    void realizarSimulacao_deveGerarParcelasCorretamente() {
        // Act
        var resultado = simulacaoService.realizarSimulacao(SIMULACAO_REQUEST);

        // Assert
        Assertions.assertEquals(3, resultado.parcelas().size());
    }

}
