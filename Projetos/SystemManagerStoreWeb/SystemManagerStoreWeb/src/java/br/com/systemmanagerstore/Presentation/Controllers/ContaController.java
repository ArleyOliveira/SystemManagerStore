/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.systemmanagerstore.Presentation.Controllers;

import br.com.systemmanagerstore.DomainModel.Conta;
import br.com.systemmanagerstore.Repository.ContaRepositorio;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;

/**
 *
 * @author ARLEY
 */
@ManagedBean
@Named(value = "contaController")
@SessionScoped
public class ContaController extends ControllerGenerico<Conta> implements Serializable {

    /**
     * Creates a new instance of ContaController
     */
    @EJB
    ContaRepositorio contaLocal;

    public ContaController() {
    }

    @PostConstruct
    public void init() {
        setDao(contaLocal);
        setPaginaEdicao("ContaEditar.xhtml");
        setPaginaListagem("ContaListagem.xtml");
        this.setEntidade(new Conta());
        this.setFiltro(new Conta());
    }

    @Override
    public void limparCampos() {
        this.setEntidade(new Conta());
        this.setFiltro(new Conta());
    }

    @Override
    public void limparEntidade() {
        this.setEntidade(new Conta());
    }

    @Override
    public void limpar() {
        this.setEntidade(new Conta());
        this.setFiltro(new Conta());
    }

}
