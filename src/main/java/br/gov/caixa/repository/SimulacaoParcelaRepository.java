package br.gov.caixa.repository;

import br.gov.caixa.entity.Tb02PKSimulacaoParcela;
import br.gov.caixa.entity.Tb02SimulacaoParcela;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class SimulacaoParcelaRepository implements PanacheRepositoryBase<Tb02SimulacaoParcela, Tb02PKSimulacaoParcela> {
}
