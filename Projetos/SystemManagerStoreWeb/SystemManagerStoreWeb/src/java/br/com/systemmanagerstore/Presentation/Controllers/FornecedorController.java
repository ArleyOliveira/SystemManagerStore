/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.systemmanagerstore.Presentation.Controllers;

import br.com.systemmanagerstore.DomainModel.Fornecedor;
import br.com.systemmanagerstore.DomainModel.Pessoa;
import br.com.systemmanagerstore.Repository.FornecedorRepositorio;
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
@Named(value = "fornecedorController")
@SessionScoped
public class FornecedorController extends ControllerGenerico<Fornecedor> implements Serializable {

    /**
     * Creates a new instance of FornecedorController
     */
    
    @EJB
    FornecedorRepositorio fornecedorLocal;
    
    public FornecedorController() {
    }

    @PostConstruct
    public void init() {
        setDao(fornecedorLocal);
        setPaginaEdicao("FornecedorEditar.xhtml");
        setPaginaListagem("FornecedorListagem.xtml");
        this.setEntidade(new Fornecedor());
        this.setFiltro(new Fornecedor());
    }
    
    @Override
    public void limparCampos() {
        this.setEntidade(new Fornecedor());
    }

    @Override
    public void limparEntidade() {
        this.setEntidade(new Fornecedor());
    }

    @Override
    public void limpar() {
        this.setEntidade(new Fornecedor());
        this.setFiltro(new Fornecedor());
    }
    
}
