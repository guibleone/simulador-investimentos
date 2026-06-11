package br.gov.caixa.entity;

import jakarta.persistence.*;

import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class Tb02PKSimulacaoParcela implements Serializable {

    @ManyToOne
    @JoinColumn(name ="co_simulacao" )
    private Tb01Simulacao simulacao;
    private Integer nuMes;

    public Tb02PKSimulacaoParcela(){}

    public Tb02PKSimulacaoParcela(Tb01Simulacao simulacao, Integer nuMes) {
        this.simulacao = simulacao;
        this.nuMes = nuMes;
    }

    public Tb01Simulacao getSimulacao() {
        return simulacao;
    }

    public void setSimulacao(Tb01Simulacao simulacao) {
        this.simulacao = simulacao;
    }

    public Integer getNuMes() {
        return nuMes;
    }

    public void setNuMes(Integer nuMes) {
        this.nuMes = nuMes;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Tb02PKSimulacaoParcela that = (Tb02PKSimulacaoParcela) o;
        return Objects.equals(simulacao, that.simulacao) && Objects.equals(nuMes, that.nuMes);
    }

    @Override
    public int hashCode() {
        return Objects.hash(simulacao, nuMes);
    }
}
