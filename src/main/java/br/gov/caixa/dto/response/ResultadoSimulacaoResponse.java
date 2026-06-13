package br.gov.caixa.dto.response;

import org.eclipse.microprofile.openapi.annotations.media.Schema;

import java.util.List;

@Schema(name = "ResultadoSimulacaoResponse", description = "Resultado completo da simulação de empréstimo, incluindo detalhes da simulação e das parcelas")
public record ResultadoSimulacaoResponse (
        @Schema(description = "Detalhes da simulação de empréstimo")
        SimulacaoResponse simulacao,

        @Schema(description = "Lista de parcelas detalhadas da simulação")
        List<SimulacaoParcelaResponse> parcelas
) {
}
