package br.gov.caixa.dto.request;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import java.math.BigDecimal;

@Schema(description = "Dados necessários para realizar uma simulação de financiamento")
public record DadosSimulacaoRequest(
        @NotNull @Min(value = 1, message = "O valor inicial deve ser maior que zero")
        @Schema(description = "Valor inicial do financiamento", examples = "150000.00", required = true)
        BigDecimal valorInicial,

        @NotNull
        @Schema(description = "Taxa de juros mensal em percentual", examples = "1.5", required = true)
        BigDecimal taxaJurosMensal,

        @NotNull @Min(value = 1, message = "O prazo deve ser maior que zero")
        @Max(value = 144, message = "O prazo máximo de meses é 144")
        @Schema(description = "Prazo da simulação em meses", examples = "360", required = true)
        Integer prazoMeses
) {
}