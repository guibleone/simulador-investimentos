package br.gov.caixa.dto.response;

import org.eclipse.microprofile.openapi.annotations.media.Schema;

import java.math.BigDecimal;

@Schema(name = "SimulacaoResponse", description = "Resposta da simulação de empréstimo")
public record SimulacaoResponse(
        @Schema(description = "Código da simulação", examples = "123e4567-e89b-12d3-a456-426614174000")
        String coSimulacao,

        @Schema(description = "Valor inicial do empréstimo", examples = "10000.00")
        BigDecimal valorInicial,

        @Schema(description = "Taxa de juros mensal em porcentagem", examples = "1.5")
        BigDecimal taxaJurosMensal,

        @Schema(description = "Prazo em meses para pagamento", examples = "36")
        Integer prazoMeses,

        @Schema(description = "Valor total a ser pago ao final do prazo", examples = "11500.00")
        BigDecimal valorTotalFinal,

        @Schema(description = "Valor total de juros a ser pago ao final do prazo", examples = "1500.00")
        BigDecimal valorTotalJuros
) {
}
