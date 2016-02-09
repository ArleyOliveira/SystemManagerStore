/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.systemmanagerstore.DomainModel;

import java.io.Serializable;
import java.math.BigDecimal;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import org.eclipse.persistence.jpa.config.Cascade;

/**
 *
 * @author ARLEY
 */
@Entity
@Table(name = "Liquidacoes")
public class Liquidacao implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    @ManyToOne(optional = false)
    private Conta conta;
    
    @Column(precision = 6, scale = 2)
    private BigDecimal valor;
    
    @Column(nullable = true, length = 500)
    private String observacao;

    public Liquidacao(){
        this.valor = new BigDecimal("0.00");
    }
    
    public Liquidacao(Conta conta, BigDecimal valorPago, String observacao){
        this.conta = conta;
        this.valor = valorPago;
        this.observacao = observacao;
    }
    
    public Liquidacao(Conta conta, BigDecimal valorPago){
        this.conta = conta;
        this.valor = valorPago;
    }
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Liquidacao)) {
            return false;
        }
        Liquidacao other = (Liquidacao) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.com.systemmanagerstore.DomainModel.Liquidacao[ id=" + id + " ]";
    }

    public Conta getConta() {
        return conta;
    }

    public void setConta(Conta conta) {
        this.conta = conta;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }

    public String getObservacao() {
        return observacao;
    }

    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }
    
}
