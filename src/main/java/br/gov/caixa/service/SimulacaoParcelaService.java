package br.gov.caixa.service;

import br.gov.caixa.entity.Tb01Simulacao;
import br.gov.caixa.entity.Tb02PKSimulacaoParcela;
import br.gov.caixa.entity.Tb02SimulacaoParcela;
import br.gov.caixa.repository.SimulacaoParcelaRepository;
import io.quarkus.logging.Log;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
public class SimulacaoParcelaService {

    private final SimulacaoParcelaRepository simulacaoParcelaRepository;

    @Inject
    public SimulacaoParcelaService(SimulacaoParcelaRepository simulacaoParcelaRepository) {
        this.simulacaoParcelaRepository = simulacaoParcelaRepository;
    }

    public List<Tb02SimulacaoParcela> gerarParcelas(Tb01Simulacao simulacao) {
        Log.info("Gerando parcelas da simulação: " + simulacao.coSimulacao);
        List<Tb02SimulacaoParcela> parcelas = new ArrayList<>();
        BigDecimal saldoInicial = simulacao.valorInicial;

        for (int i = 1; i <= simulacao.prazoMeses; i++) {
            Log.info("Gerarndo parcela do MÊS " + i);
            BigDecimal jurosIncidentes = saldoInicial.multiply(simulacao.taxaJurosMensal).divide(BigDecimal.valueOf(100), MathContext.DECIMAL128).setScale(2, RoundingMode.HALF_UP);;
            BigDecimal saldoFinal = saldoInicial.add(jurosIncidentes).setScale(2, RoundingMode.HALF_UP);;

            Tb02SimulacaoParcela parcela = new Tb02SimulacaoParcela();
            parcela.pkSimulacaoParcela = new Tb02PKSimulacaoParcela(simulacao, i);
            parcela.saldoInicial = saldoInicial;
            parcela.jurosIncidentes = jurosIncidentes;
            parcela.saldoFinal = saldoFinal;

            parcelas.add(parcela);
            saldoInicial = saldoFinal;
        }

        salvarParcelas(parcelas);
        return parcelas;
    }

    @Transactional
    public void salvarParcelas(List<Tb02SimulacaoParcela> parcelas){
        simulacaoParcelaRepository.persist(parcelas);
    }

}
