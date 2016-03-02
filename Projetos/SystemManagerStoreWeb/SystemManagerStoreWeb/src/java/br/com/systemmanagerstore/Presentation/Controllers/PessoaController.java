/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.systemmanagerstore.Presentation.Controllers;

import br.com.systemmanagerstore.DomainModel.Fornecedor;
import br.com.systemmanagerstore.DomainModel.Pessoa;
import br.com.systemmanagerstore.Presentation.Utility.Exception.CpfInvalidoException;
import br.com.systemmanagerstore.Presentation.Utility.ValidadorCPF;
import br.com.systemmanagerstore.Repository.PessoaRepositorio;
import br.com.systemmanagerstore.Utility.MensagemTela;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
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

    public void salvarDados() {
        try {
            if (!ValidadorCPF.validaCPF(this.getEntidade().getCpf())) {
                throw new CpfInvalidoException("Cpf invalido!");
            }
            pessoaLocal.verificaCPfExistente(this.getEntidade().getCpf());
            this.salvar();
        } catch (CpfInvalidoException cie) {
            MensagemTela.MensagemErro("Cpf invalido", cie.getMessage());
        }

    }

    public void editarDados() {
        try {
            if (!ValidadorCPF.validaCPF(this.getEntidade().getCpf())) {
                throw new CpfInvalidoException("Cpf invalido!");
            }
            this.editar();
        } catch (CpfInvalidoException cie) {
            MensagemTela.MensagemErro("Cpf invalido", cie.getMessage());
        }
    }

    public List<Pessoa> getAutoComplete(String texto) {
        Pessoa pessoa = new Pessoa();
        pessoa.setNome(texto);
        pessoa.setCpf(texto);
        return pessoaLocal.Buscar(pessoa);
    }
}
