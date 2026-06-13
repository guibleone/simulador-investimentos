package br.gov.caixa.service;

import br.gov.caixa.dto.request.DadosSimulacaoRequest;
import br.gov.caixa.entity.Tb01Simulacao;
import br.gov.caixa.entity.Tb02SimulacaoParcela;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import java.math.BigDecimal;
import java.util.List;

@QuarkusTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@DisplayName("Testes do serviço de geração de parcelas")
public class SimulacaoParcelaTest {

   private final SimulacaoParcelaService simulacaoParcelaService;
   private final SimulacaoService simulacaoService;

    @Inject
    public SimulacaoParcelaTest(SimulacaoParcelaService simulacaoParcelaService, SimulacaoService simulacaoService) {
        this.simulacaoParcelaService = simulacaoParcelaService;
        this.simulacaoService = simulacaoService;
    }

    private static final DadosSimulacaoRequest SIMULACAO_REQUEST_PADRAO = new DadosSimulacaoRequest(
            BigDecimal.valueOf(1000.00),
            BigDecimal.valueOf(2),
            3
    );

    @Test
    @Order(1)
    @DisplayName("Deve gerar parcelas com o número correto")
    void gerarParcelas_deveGerarQuantidadeCorretaDeParcelas() {
        // Arrange
        Tb01Simulacao simulacao = simulacaoService.salvarSimulacao(
                SIMULACAO_REQUEST_PADRAO,
                BigDecimal.valueOf(1061.21),
                BigDecimal.valueOf(61.21)
        );

        // Act
        List<Tb02SimulacaoParcela> parcelas = simulacaoParcelaService.gerarParcelas(simulacao);

        // Assert
        Assertions.assertEquals(3, parcelas.size());
    }

    @Test
    @Order(2)
    @DisplayName("Deve calcular juros incidentes corretamente para primeira parcela")
    void gerarParcelas_deveCalcularJurosCorretamentePrimeiraParcela() {
        // Arrange
        Tb01Simulacao simulacao = simulacaoService.salvarSimulacao(
                SIMULACAO_REQUEST_PADRAO,
                BigDecimal.valueOf(1061.21),
                BigDecimal.valueOf(61.21)
        );

        // Act
        List<Tb02SimulacaoParcela> parcelas = simulacaoParcelaService.gerarParcelas(simulacao);

        // Assert
        Tb02SimulacaoParcela primeiraParcela = parcelas.getFirst();
        Assertions.assertEquals(0, primeiraParcela.saldoInicial.compareTo(BigDecimal.valueOf(1000.00)));
        Assertions.assertEquals(0, primeiraParcela.jurosIncidentes.compareTo(BigDecimal.valueOf(20.00)));
        Assertions.assertEquals(0, primeiraParcela.saldoFinal.compareTo(BigDecimal.valueOf(1020.00)));
    }

    @Test
    @Order(3)
    @DisplayName("Deve calcular juros corretamente para segunda parcela (compostos)")
    void gerarParcelas_deveCalcularJurosCorretamenteSegundaParcela() {
        // Arrange
        Tb01Simulacao simulacao = simulacaoService.salvarSimulacao(
                SIMULACAO_REQUEST_PADRAO,
                BigDecimal.valueOf(1061.21),
                BigDecimal.valueOf(61.21)
        );

        // Act
        List<Tb02SimulacaoParcela> parcelas = simulacaoParcelaService.gerarParcelas(simulacao);

        // Assert
        Tb02SimulacaoParcela segundaParcela = parcelas.get(1);
        Assertions.assertEquals(0, segundaParcela.saldoInicial.compareTo(BigDecimal.valueOf(1020.00)));
        Assertions.assertEquals(0, segundaParcela.jurosIncidentes.compareTo(BigDecimal.valueOf(20.40)));
        Assertions.assertEquals(0, segundaParcela.saldoFinal.compareTo(BigDecimal.valueOf(1040.40)));
    }

    @Test
    @Order(4)
    @DisplayName("Deve calcular juros corretamente para terceira parcela (compostos)")
    void gerarParcelas_deveCalcularJurosCorretamenteTerceiraParcela() {
        // Arrange
        Tb01Simulacao simulacao = simulacaoService.salvarSimulacao(
                SIMULACAO_REQUEST_PADRAO,
                BigDecimal.valueOf(1061.21),
                BigDecimal.valueOf(61.21)
        );

        // Act
        List<Tb02SimulacaoParcela> parcelas = simulacaoParcelaService.gerarParcelas(simulacao);

        // Assert
        Tb02SimulacaoParcela terceiraParcela = parcelas.get(2);
        Assertions.assertEquals(0, terceiraParcela.saldoInicial.compareTo(BigDecimal.valueOf(1040.40)));
        Assertions.assertEquals(0, terceiraParcela.jurosIncidentes.compareTo(BigDecimal.valueOf(20.81)));
        Assertions.assertEquals(0, terceiraParcela.saldoFinal.compareTo(BigDecimal.valueOf(1061.21)));
    }

    @Test
    @Order(5)
    @DisplayName("Deve validar saldo final total corresponde ao valor total final da simulação")
    void gerarParcelas_deveTerSaldoFinalCorreto() {
        // Arrange
        Tb01Simulacao simulacao = simulacaoService.salvarSimulacao(
                SIMULACAO_REQUEST_PADRAO,
                BigDecimal.valueOf(1061.21),
                BigDecimal.valueOf(61.21)
        );

        // Act
        List<Tb02SimulacaoParcela> parcelas = simulacaoParcelaService.gerarParcelas(simulacao);
        Tb02SimulacaoParcela ultimaParcela = parcelas.getLast();

        // Assert
        Assertions.assertEquals(0, ultimaParcela.saldoFinal.compareTo(simulacao.valorTotalFinal));
    }

    @Test
    @Order(6)
    @DisplayName("Deve gerar parcelas com chave primária (PK) correta")
    void gerarParcelas_deveGerarChavePrimariaCorreta() {
        // Arrange
        Tb01Simulacao simulacao = simulacaoService.salvarSimulacao(
                SIMULACAO_REQUEST_PADRAO,
                BigDecimal.valueOf(1061.21),
                BigDecimal.valueOf(61.21)
        );

        // Act
        List<Tb02SimulacaoParcela> parcelas = simulacaoParcelaService.gerarParcelas(simulacao);

        // Assert
        for (int i = 0; i < parcelas.size(); i++) {
            Assertions.assertNotNull(parcelas.get(i).pkSimulacaoParcela);
            Assertions.assertEquals(i + 1, parcelas.get(i).pkSimulacaoParcela.getNuMes());
            Assertions.assertEquals(simulacao.coSimulacao, parcelas.get(i).pkSimulacaoParcela.getSimulacao().coSimulacao);
        }
    }

    @Test
    @Order(7)
    @DisplayName("Deve gerar parcelas para simulação com taxa diferente")
    void gerarParcelas_deveGerarParcelasComTaxaDiferente() {
        // Arrange
        DadosSimulacaoRequest simulacaoRequest = new DadosSimulacaoRequest(
                BigDecimal.valueOf(5000.00),
                BigDecimal.valueOf(1.5),
                6
        );
        Tb01Simulacao simulacao = simulacaoService.salvarSimulacao(
                simulacaoRequest,
                BigDecimal.valueOf(5482.91),
                BigDecimal.valueOf(482.91)
        );

        // Act
        List<Tb02SimulacaoParcela> parcelas = simulacaoParcelaService.gerarParcelas(simulacao);

        // Assert
        Assertions.assertEquals(6, parcelas.size());
        Assertions.assertEquals(0, parcelas.getFirst().saldoInicial.compareTo(BigDecimal.valueOf(5000.00)));
        Assertions.assertTrue(parcelas.getFirst().jurosIncidentes.compareTo(BigDecimal.ZERO) > 0);
    }

    @Test
    @Order(8)
    @DisplayName("Deve gerar parcelas para simulação com prazo diferente")
    void gerarParcelas_deveGerarParcelasComPrazoDiferente() {
        // Arrange
        DadosSimulacaoRequest simulacaoRequest = new DadosSimulacaoRequest(
                BigDecimal.valueOf(2000.00),
                BigDecimal.valueOf(1.0),
                12
        );
        Tb01Simulacao simulacao = simulacaoService.salvarSimulacao(
                simulacaoRequest,
                BigDecimal.valueOf(2254.65),
                BigDecimal.valueOf(254.65)
        );

        // Act
        List<Tb02SimulacaoParcela> parcelas = simulacaoParcelaService.gerarParcelas(simulacao);

        // Assert
        Assertions.assertEquals(12, parcelas.size());
        Assertions.assertEquals(0, parcelas.getFirst().saldoInicial.compareTo(BigDecimal.valueOf(2000.00)));
    }

    @Test
    @Order(9)
    @DisplayName("Deve garantir que saldo final de uma parcela é saldo inicial da próxima")
    void gerarParcelas_deveTerContinuidadeSaldoEntreParcelas() {
        // Arrange
        Tb01Simulacao simulacao = simulacaoService.salvarSimulacao(
                SIMULACAO_REQUEST_PADRAO,
                BigDecimal.valueOf(1061.21),
                BigDecimal.valueOf(61.21)
        );

        // Act
        List<Tb02SimulacaoParcela> parcelas = simulacaoParcelaService.gerarParcelas(simulacao);

        // Assert
        for (int i = 0; i < parcelas.size() - 1; i++) {
            Assertions.assertEquals(
                    parcelas.get(i).saldoFinal,
                    parcelas.get(i + 1).saldoInicial,
                    "Saldo final da parcela " + (i + 1) + " deve ser igual ao saldo inicial da parcela " + (i + 2)
            );
        }
    }

    @Test
    @Order(10)
    @DisplayName("Deve gerar parcelas com valores não negativos")
    void gerarParcelas_deveGerarValesPositivos() {
        // Arrange
        Tb01Simulacao simulacao = simulacaoService.salvarSimulacao(
                SIMULACAO_REQUEST_PADRAO,
                BigDecimal.valueOf(1061.21),
                BigDecimal.valueOf(61.21)
        );

        // Act
        List<Tb02SimulacaoParcela> parcelas = simulacaoParcelaService.gerarParcelas(simulacao);

        // Assert
        for (Tb02SimulacaoParcela parcela : parcelas) {
            Assertions.assertTrue(parcela.saldoInicial.compareTo(BigDecimal.ZERO) >= 0);
            Assertions.assertTrue(parcela.jurosIncidentes.compareTo(BigDecimal.ZERO) >= 0);
            Assertions.assertTrue(parcela.saldoFinal.compareTo(BigDecimal.ZERO) >= 0);
        }
    }
}
