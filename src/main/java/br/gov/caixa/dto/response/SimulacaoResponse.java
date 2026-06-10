package br.gov.caixa.dto.response;

import java.math.BigDecimal;
import java.util.List;

public record SimulacaoResponse(
        String coSimulacao,
        BigDecimal valorInicial,
        BigDecimal taxaJurosMensal,
        Integer prazoMeses,
        BigDecimal valorTotalFinal,
        BigDecimal valorTotalJuros
) {
}
