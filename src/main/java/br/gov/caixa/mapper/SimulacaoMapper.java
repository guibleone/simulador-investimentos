package br.gov.caixa.mapper;

import br.gov.caixa.dto.response.SimulacaoResponse;
import br.gov.caixa.entity.Tb01Simulacao;
import org.mapstruct.Mapper;

@Mapper(componentModel = "jakarta-cdi")
public interface SimulacaoMapper {
    SimulacaoResponse toResponse(Tb01Simulacao simulacao);
}
