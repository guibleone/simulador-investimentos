package br.gov.caixa.repository;

import br.gov.caixa.entity.Tb01Simulacao;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class SimulacaoRepository implements PanacheRepositoryBase<Tb01Simulacao, String> {
}
