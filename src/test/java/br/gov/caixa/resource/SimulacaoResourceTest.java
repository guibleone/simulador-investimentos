package br.gov.caixa.resource;

import br.gov.caixa.dto.request.DadosSimulacaoRequest;
import br.gov.caixa.dto.response.SimulacaoResponse;
import io.quarkus.test.common.http.TestHTTPEndpoint;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.when;
import static org.hamcrest.CoreMatchers.is;

@QuarkusTest
@TestHTTPEndpoint(SimulacaoResource.class)
public class SimulacaoResourceTest {

    @Test
    @DisplayName("Deve listar todas as simulações e retornar os dados corretos")
    void listarTodas_deveRetornar200(){
    SimulacaoResponse simulacaoResponse = new SimulacaoResponse(
            "a1b2c3d4-e5f6-7a8b-9c0d-1e2f3a4b5c6d",
            BigDecimal.valueOf(1000.00f),
            BigDecimal.valueOf(0.02),
            3,
            BigDecimal.valueOf(1061.21),
            BigDecimal.valueOf(61.21)
    );

        when().get()
                .then()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .body("size()", is(1))
                .body("[0].coSimulacao", is(simulacaoResponse.coSimulacao()))
                // Convertemos o BigDecimal para floatValue() para bater com o tipo lido pelo REST Assured
                .body("[0].valorInicial", is(simulacaoResponse.valorInicial().floatValue()))
                .body("[0].taxaJurosMensal", is(simulacaoResponse.taxaJurosMensal().floatValue()))
                .body("[0].prazoMeses", is(simulacaoResponse.prazoMeses()))
                .body("[0].valorTotalFinal", is(simulacaoResponse.valorTotalFinal().floatValue()))
                .body("[0].valorTotalJuros", is(simulacaoResponse.valorTotalJuros().floatValue()));
    }

    @Test
    @DisplayName("Deve realizar uma simulação de investimento e retornar os resultados corretos")
    void realizarSimulacao_deveRetornar201(){
        DadosSimulacaoRequest requestBody = new DadosSimulacaoRequest(
                BigDecimal.valueOf(1000.00f),
                BigDecimal.valueOf(0.02),
                12
        );

        given()
                .contentType(ContentType.JSON)
                .body(requestBody)
                .when().post("/investimento")
                .then()
                .statusCode(201)
                .contentType(ContentType.JSON)
                .body("simulacao.valorInicial", is( 1000.0f))
                .body("simulacao.taxaJurosMensal", is(0.02f))
                .body("simulacao.prazoMeses", is(12))
                .body("simulacao.valorTotalFinal", is(1002.40f))
                .body("simulacao.valorTotalJuros", is(2.40f));
    }

    @Test
    @DisplayName("Deve retornar 400 Bad Request para dados de simulação inválidos")
    void realizarSimulacao_dadosInvalidos_deveRetornar400() {
        DadosSimulacaoRequest requestBody = new DadosSimulacaoRequest(
                BigDecimal.valueOf(-1000.00f), // Valor inicial negativo
                BigDecimal.valueOf(0.02),
                12
        );

        given()
                .contentType(ContentType.JSON)
                .body(requestBody)
                .when().post("/investimento")
                .then()
                .statusCode(400);
    }

    @Test
    @DisplayName("Deve detalhar uma simulação existente e retornar os dados corretos")
    void detalharSimulacao_deveRetornar200() {
        String coSimulacao = "a1b2c3d4-e5f6-7a8b-9c0d-1e2f3a4b5c6d";

        when().get("/" + coSimulacao)
                .then()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .body("simulacao.coSimulacao", is(coSimulacao))
                .body("parcelas.size()", is(3));
    }

    @Test
    @DisplayName("Deve retornar 404 Not Found para simulação inexistente")
    void detalharSimulacao_inexistente_deveRetornar404() {
        String coSimulacaoInexistente = "non-existent-simulation-id";

        when().get("/" + coSimulacaoInexistente)
                .then()
                .statusCode(404);
    }
}