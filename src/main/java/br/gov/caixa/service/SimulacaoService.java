package br.gov.caixa.service;

import br.gov.caixa.dto.response.SimulacaoResponse;
import br.gov.caixa.mapper.SimulacaoMapper;
import br.gov.caixa.respository.SimulacaoRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.util.List;

@ApplicationScoped
public class SimulacaoService {

    private final SimulacaoRepository simulacaoRepository;
    private final SimulacaoMapper simulacaoMapper;

    @Inject
    public SimulacaoService(SimulacaoRepository simulacaoRepository, SimulacaoMapper simulacaoMapper){
        this.simulacaoRepository = simulacaoRepository;
        this.simulacaoMapper = simulacaoMapper;
    }

    public List<SimulacaoResponse> listarTodos(){
        return simulacaoRepository
                .listAll()
                .stream()
                .map(simulacaoMapper::toResponse)
                .toList();
    }
}
