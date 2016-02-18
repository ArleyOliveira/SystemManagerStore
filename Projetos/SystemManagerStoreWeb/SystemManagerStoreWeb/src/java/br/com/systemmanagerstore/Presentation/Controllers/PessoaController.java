/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.systemmanagerstore.Presentation.Controllers;

import br.com.systemmanagerstore.DomainModel.Pessoa;
import br.com.systemmanagerstore.Repository.PessoaRepositorio;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;



/**
 *
 * @author ARLEY
 */
@Named(value = "pessoaController")
@SessionScoped
public class PessoaController extends ControllerGenerico<Pessoa> implements Serializable {

    /**
     * Creates a new instance of PessoaController
     */
    public PessoaController() {
    }
    
    @EJB
    PessoaRepositorio pessoaLocal;
    
    @PostConstruct
    public void init() {
        setDao(pessoaLocal);
        setPaginaEdicao("PessoaEditar.xhtml");
        setPaginaListagem("PessoaListagem.xtml");
        setEntidade(new Pessoa());
        setFiltro(new Pessoa());
    }

    @Override
    public void limparCampos() {
        this.setEntidade(new Pessoa());
    }

    @Override
    public void limparEntidade() {
        this.setEntidade(new Pessoa());
    }

    @Override
    public void limpar() {
        this.setEntidade(new Pessoa());
        this.setFiltro(new Pessoa());
    }

}
