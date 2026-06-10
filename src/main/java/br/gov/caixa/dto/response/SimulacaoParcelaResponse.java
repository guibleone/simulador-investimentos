package br.gov.caixa.dto.response;

import java.math.BigDecimal;

public record SimulacaoParcelaResponse(
        Integer nu_mes,
        BigDecimal saldoInicial,
        BigDecimal jurosIncidentes,
        BigDecimal saldoFinal
) { }
