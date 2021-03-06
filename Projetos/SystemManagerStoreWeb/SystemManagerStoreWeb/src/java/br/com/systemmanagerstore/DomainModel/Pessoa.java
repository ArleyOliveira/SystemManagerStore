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
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author ARLEY
 */
@Entity
@Table(name = "pessoas")
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "DTYPE", discriminatorType = DiscriminatorType.STRING, length = 32)
@DiscriminatorValue("pessoa")
public class Pessoa implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false, length = 500)
    private String nome;

    @Column(unique = true, length = 14, nullable = true)
    private String cpf;

    @Column(unique = true, length = 20, nullable = true)
    private String rg;

    @Column(nullable = false, length = 1)
    private char sexo;

    @Column()
    private boolean status;

    @Column()
    private boolean f;

    @Temporal(TemporalType.DATE)
    private Date dataNascimento;

    @OneToOne(cascade = CascadeType.ALL, optional = true)
    Telefone telefone;

    @Column(precision = 5, scale = 2, nullable = true)
    private BigDecimal debito;

    private String DTYPE;

    public String getRg() {
        return rg;
    }

    public void setRg(String rg) {
        this.rg = rg;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;

    }

    public boolean isF() {
        return f;
    }

    public void setF(boolean f) {
        this.f = f;
    }

    public BigDecimal getDebito() {
        return debito;
    }

    public void setDebito(BigDecimal valor) {
        this.debito = valor;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public char getSexo() {
        return sexo;
    }

    public void setSexo(char sexo) {
        this.sexo = sexo;
    }

    public Date getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(Date dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public Telefone getTelefone() {
        return telefone;
    }

    public void setTelefone(Telefone telefone) {
        this.telefone = telefone;
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
        if (!(object instanceof Pessoa)) {
            return false;
        }
        Pessoa other = (Pessoa) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    public String getStatusFormatado() {
        if (this.status) {
            return "Positivo";
        }
        return "Negativo";
    }

    @Override
    public String toString() {
        if (cpf.equals("") || cpf == null || nome.equals("") || nome == null) {
            return "";
        }
        else{
            return this.cpf + " - " + this.nome;
        }
    }

    public Pessoa() {
        this.debito = new BigDecimal("0.00");
        this.telefone = new Telefone();
        this.status = true;
        this.f = true;
    }

    public String getSexoFormatado() {
        if (this.sexo == 'M') {
            return "Masc.";
        }
        return "Femi.";
    }

    public int getIdade() {
        return (new Date()).getYear() - this.dataNascimento.getYear();
    }
    
    public String getDataNascimentoFormatada() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        return sdf.format(dataNascimento);
    }
}
