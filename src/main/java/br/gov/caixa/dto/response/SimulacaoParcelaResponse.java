package br.gov.caixa.dto.response;

import org.eclipse.microprofile.openapi.annotations.media.Schema;

import java.math.BigDecimal;

@Schema(name = "SimulacaoParcelaResponse", description = "Detalhes de cada parcela da simulação de empréstimo" )
public record SimulacaoParcelaResponse(
        @Schema(description = "Número da parcela e mês", examples = "1")
        Integer nuMes,

        @Schema(description = "Saldo devedor no início do mês", examples = "10000.00")
        BigDecimal saldoInicial,

        @Schema(description = "Valor dos juros incidentes na parcela", examples = "150.00")
        BigDecimal jurosIncidentes,

        @Schema(description = "Saldo devedor no final do mês após o pagamento da parcela", examples = "9850.00")
        BigDecimal saldoFinal
) { }
