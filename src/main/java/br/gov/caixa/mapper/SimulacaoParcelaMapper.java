package br.gov.caixa.mapper;

import br.gov.caixa.dto.response.SimulacaoParcelaResponse;
import br.gov.caixa.entity.Tb02SimulacaoParcela;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "jakarta-cdi")
public interface SimulacaoParcelaMapper {
    @Mapping(target = "nuMes", source = "pkSimulacaoParcela.nuMes")
    SimulacaoParcelaResponse toResponse(Tb02SimulacaoParcela simulacaoParcela);
}
