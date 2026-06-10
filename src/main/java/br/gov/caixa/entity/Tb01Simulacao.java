package br.gov.caixa.entity;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
@Table(name = "tb01_simulacao")
public class Tb01Simulacao extends PanacheEntityBase {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    public String coSimulacao;

    @Column(name = "valor_inicial", nullable = false)
    public BigDecimal valorInicial;

    @Column(name = "taxa_juros_mensal", nullable = false)
    public BigDecimal taxaJurosMensal;

    @Column(name = "prazo_meses", nullable = false)
    public Integer prazoMeses;

    @Column(name = "valor_total_final")
    public BigDecimal valorTotalFinal;

    @Column(name = "valor_total_juros")
    public BigDecimal valorTotalJuros;
}
