package br.gov.caixa.dto.response;

import java.util.List;

public record ResultadoSimulacaoResponse (
        SimulacaoResponse simulacao,
        List<SimulacaoParcelaResponse> parcelas
) {
}
