package br.gov.caixa.service;

import br.gov.caixa.dto.request.DadosSimulacaoRequest;
import br.gov.caixa.dto.response.ResultadoSimulacaoResponse;
import br.gov.caixa.dto.response.SimulacaoResponse;
import br.gov.caixa.entity.Tb01Simulacao;
import br.gov.caixa.entity.Tb02SimulacaoParcela;
import br.gov.caixa.mapper.SimulacaoMapper;
import br.gov.caixa.mapper.SimulacaoParcelaMapper;
import br.gov.caixa.respository.SimulacaoRepository;
import io.quarkus.logging.Log;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.NotFoundException;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.List;

@ApplicationScoped
public class SimulacaoService {

    private final SimulacaoRepository simulacaoRepository;
    private final SimulacaoParcelaService simulacaoParcelaService;
    private final SimulacaoMapper simulacaoMapper;
    private final SimulacaoParcelaMapper simulacaoParcelaMapper;

    @Inject
    public SimulacaoService(SimulacaoRepository simulacaoRepository, SimulacaoMapper simulacaoMapper, SimulacaoParcelaService simulacaoParcelaService, SimulacaoParcelaMapper simulacaoParcelaMapper){
        this.simulacaoRepository = simulacaoRepository;
        this.simulacaoMapper = simulacaoMapper;
        this.simulacaoParcelaService = simulacaoParcelaService;
        this.simulacaoParcelaMapper = simulacaoParcelaMapper;
    }

    public List<SimulacaoResponse> listarTodos(){
        return simulacaoRepository
                .listAll()
                .stream()
                .map(simulacaoMapper::toResponse)
                .toList();
    }

    public ResultadoSimulacaoResponse encontrarSimulacao(String coSimulacao){
        Tb01Simulacao simulacao = simulacaoRepository.findByIdOptional(coSimulacao)
                .orElseThrow(()-> new NotFoundException("Simulação com código " + coSimulacao + " não encontrada."));
        return new ResultadoSimulacaoResponse(
                simulacaoMapper.toResponse(simulacao),
                simulacao.parcelas.stream().map(simulacaoParcelaMapper::toResponse).toList()
        );
    }

    public ResultadoSimulacaoResponse realizarSimulacao(DadosSimulacaoRequest simulacaoRequest){
        Log.info("Realizando simulação para valor inicial: " + simulacaoRequest.valorInicial() + ", taxa de juros mensal: " + simulacaoRequest.taxaJurosMensal() + "%, prazo: " + simulacaoRequest.prazoMeses() + " meses.");

        BigDecimal valorTotalFinal = calcularValorTotalFinal(simulacaoRequest);
        BigDecimal valorTotalJuros = calcularValorTotalJuros(valorTotalFinal, simulacaoRequest.valorInicial());

        Tb01Simulacao simulacao = salvarSimulacao(simulacaoRequest, valorTotalFinal, valorTotalJuros);
        List<Tb02SimulacaoParcela> parcelas = simulacaoParcelaService.gerarParcelas(simulacao);

        Log.info("Simulação realizada com sucesso: " + simulacao.coSimulacao);

        return new ResultadoSimulacaoResponse(
                simulacaoMapper.toResponse(simulacao),
                parcelas.stream().map(simulacaoParcelaMapper::toResponse).toList()
        );
    }

    @Transactional
    public Tb01Simulacao salvarSimulacao(DadosSimulacaoRequest simulacaoRequest, BigDecimal valorTotalFinal, BigDecimal valorTotalJuros){
        Tb01Simulacao simulacao = new Tb01Simulacao();
        simulacao.valorInicial = simulacaoRequest.valorInicial();
        simulacao.taxaJurosMensal = simulacaoRequest.taxaJurosMensal();
        simulacao.prazoMeses = simulacaoRequest.prazoMeses();
        simulacao.valorTotalFinal = valorTotalFinal;
        simulacao.valorTotalJuros = valorTotalJuros;
        simulacaoRepository.persist(simulacao);
        return simulacao;
    }

    private BigDecimal calcularValorTotalFinal(DadosSimulacaoRequest simulacaoRequest){
        BigDecimal taxaJurosMensalDecimal = simulacaoRequest.taxaJurosMensal().divide(BigDecimal.valueOf(100), MathContext.DECIMAL128);
        Log.info("Taxa de juros mensal em decimal: " + taxaJurosMensalDecimal);
        BigDecimal valorTotalFinal = simulacaoRequest.valorInicial().multiply(BigDecimal.ONE.add(taxaJurosMensalDecimal).pow(simulacaoRequest.prazoMeses(), MathContext.DECIMAL128));
        return valorTotalFinal.setScale(2, RoundingMode.HALF_UP);
    }

    private BigDecimal calcularValorTotalJuros(BigDecimal valorTotalFinal, BigDecimal valorInicial){
        return valorTotalFinal.subtract(valorInicial);
    }
}
