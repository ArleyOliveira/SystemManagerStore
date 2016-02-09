/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.systemmanagerstore.DomainModel;

import java.io.Serializable;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author ARLEY
 */
@Entity
@Table(name = "caixas")
public class Caixa implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @OneToMany(cascade = CascadeType.ALL)
    private List<Liquidacao> liquidacoes;

    @OneToMany(cascade = CascadeType.ALL)
    private List<EntradaDeCaixa> entradas;

    @OneToMany(cascade = CascadeType.ALL)
    private List<SaidaDeCaixa> saidas;

    @Column(precision = 6, scale = 2, nullable = false)
    private BigDecimal valorIncial;

    @Column(precision = 6, scale = 2, nullable = false)
    private BigDecimal valorFinal;

    private boolean status;

    @Temporal(TemporalType.DATE)
    private Date data;

    public Long getId() {
        return id;
    }

    public Caixa() {
        this.valorIncial = new BigDecimal(0.00);
        this.valorFinal = this.valorIncial;
        this.status = true;
        this.entradas = new LinkedList<>();
        this.saidas = new LinkedList<>();
        this.liquidacoes = new LinkedList<>();
        this.data = new Date();
    }

    public List<Liquidacao> getLiquidacoes() {
        return liquidacoes;
    }

    public void setLiquidacoes(List<Liquidacao> liquidacoes) {
        this.liquidacoes = liquidacoes;
    }

    public List<EntradaDeCaixa> getEntradas() {
        return entradas;
    }

    public void setEntradas(List<EntradaDeCaixa> entradas) {
        this.entradas = entradas;
    }

    public List<SaidaDeCaixa> getSaidas() {
        return saidas;
    }

    public void setSaidas(List<SaidaDeCaixa> saidas) {
        this.saidas = saidas;
    }

    public BigDecimal getValorIncial() {
        return valorIncial;
    }

    public void setValorIncial(BigDecimal valorIncial) {
        this.valorIncial = valorIncial;
    }

    public BigDecimal getValorFinal() {
        return valorFinal;
    }

    public void setValorFinal(BigDecimal valorFinal) {
        this.valorFinal = valorFinal;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
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
        if (!(object instanceof Caixa)) {
            return false;
        }
        Caixa other = (Caixa) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.com.systemmanagerstore.DomainModel.Caixa[ id=" + id + " ]";
    }

    public String getDataFormatada() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        return sdf.format(data);
    }

    public void addLiquidacao(Liquidacao liquidacao) {
        this.liquidacoes.add(liquidacao);
        this.valorFinal.add(liquidacao.getValor());
    }

    public void addEntrada(EntradaDeCaixa entrada) {
        this.entradas.add(entrada);
        this.valorFinal.add(entrada.getValor());
    }
    
    public void addSaida(SaidaDeCaixa saida){
        this.saidas.add(saida);
        this.valorFinal.subtract(saida.getValor());
    }
}
