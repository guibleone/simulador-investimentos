package br.gov.caixa.entities;

import jakarta.persistence.*;

import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class Tb02PKSimulacaoParcela implements Serializable {

    @ManyToOne
    @JoinColumn(name ="co_simulacao" )
    private Tb01Simulacao simulacao;
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer nu_mes;

    public Tb02PKSimulacaoParcela(){}

    public Tb02PKSimulacaoParcela(Tb01Simulacao simulacao, Integer nu_mes) {
        this.simulacao = simulacao;
        this.nu_mes = nu_mes;
    }

    public Tb01Simulacao getSimulacao() {
        return simulacao;
    }

    public void setSimulacao(Tb01Simulacao simulacao) {
        this.simulacao = simulacao;
    }

    public Integer getNu_mes() {
        return nu_mes;
    }

    public void setNu_mes(Integer nu_mes) {
        this.nu_mes = nu_mes;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Tb02PKSimulacaoParcela that = (Tb02PKSimulacaoParcela) o;
        return Objects.equals(simulacao, that.simulacao) && Objects.equals(nu_mes, that.nu_mes);
    }

    @Override
    public int hashCode() {
        return Objects.hash(simulacao, nu_mes);
    }
}
