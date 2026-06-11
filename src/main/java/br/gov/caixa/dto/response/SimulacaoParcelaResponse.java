package br.gov.caixa.dto.response;

import java.math.BigDecimal;

public record SimulacaoParcelaResponse(
        Integer nuMes,
        BigDecimal saldoInicial,
        BigDecimal jurosIncidentes,
        BigDecimal saldoFinal
) { }
