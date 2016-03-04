/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.systemmanagerstore.DomainModel;

import br.com.systemmanagerstore.Presentation.Utility.BigDecimalFormater;
import java.io.Serializable;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
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
import org.apache.derby.client.am.Decimal;

/**
 *
 * @author ARLEY
 */
@Entity
@Table(name = "contas")
public class Conta implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(precision = 6, scale = 2, nullable = false)
    private BigDecimal valor;

    @Column(precision = 6, scale = 2, nullable = false)
    private BigDecimal valorAtual;

    private boolean status;

    @OneToOne(optional = false, cascade = CascadeType.ALL)
    private Venda venda;

    @Temporal(TemporalType.DATE)
    private Date data;

    public Conta(Venda venda, boolean status) {
        this.venda = venda;
        this.valor = venda.getValor();
        this.status = status;
        if (status) {
            this.valorAtual = this.valor;
        }
        this.data = venda.getData();
    }

    public Conta() {
        this.valor = new BigDecimal(0.00);
        this.data = new Date();
        this.status = true;
    }

    public Conta(Venda venda, BigDecimal valorPago, boolean status) {
        this.valor = venda.getValor();
        this.valorAtual = this.valor.subtract(valorPago);
        this.data = venda.getData();
    }

    public Long getId() {
        return id;
    }

    public Venda getVenda() {
        return venda;
    }

    public void setVenda(Venda venda) {
        this.venda = venda;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public BigDecimal getValorAtual() {
        return valorAtual;
    }

    public void setValorAtual(BigDecimal valorAtual) {
        this.valorAtual = valorAtual;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
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
        if (!(object instanceof Conta)) {
            return false;
        }
        Conta other = (Conta) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.com.systemmanagerstore.DomainModel.Conta[ id=" + id + " ]";
    }

    public String getDataFormatada() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        return sdf.format(data);
    }
    
    public BigDecimal getValorFormatado() {
        return BigDecimalFormater.casasDecimais(2, this.valor);
    }
    
    public String getStatusFormatado(){
        if(this.status)
            return "Não liquidada";
         return "Pago";
    }
}
