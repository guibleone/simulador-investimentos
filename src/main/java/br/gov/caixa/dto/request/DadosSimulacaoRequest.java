package br.gov.caixa.dto.request;

import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record DadosSimulacaoRequest(
        @NotNull
        BigDecimal valorInicial,
        @NotNull
        Long taxaJurosMensal,
        @NotNull
        Integer prazoMeses
) {
}
