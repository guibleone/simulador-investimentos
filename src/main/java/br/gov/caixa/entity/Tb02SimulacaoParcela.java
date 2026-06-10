package br.gov.caixa.entity;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
@Table(name = "tb02_simulacao_parcela")
public class Tb02SimulacaoParcela extends PanacheEntityBase {

    @EmbeddedId
    public Tb02PKSimulacaoParcela pkSimulacaoParcela;

    @Column(name = "saldo_inicial", nullable = false)
    public BigDecimal saldoInicial;

    @Column(name = "juros_incidentes", nullable = false)
    public BigDecimal jurosIncidentes;

    @Column(name = "saldo_final", nullable = false)
    public BigDecimal saldoFinal;
}
