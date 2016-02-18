/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.systemmanagerstore.Presentation.Controllers;

import br.com.systemmanagerstore.DomainModel.Funcionario;
import br.com.systemmanagerstore.Presentation.Utility.Exception.CpfInvalidoException;
import br.com.systemmanagerstore.Presentation.Utility.Exception.LoginInvalidoException;
import br.com.systemmanagerstore.Presentation.Utility.Exception.SenhaInvalidaException;
import br.com.systemmanagerstore.Presentation.Utility.ValidadorCPF;
import br.com.systemmanagerstore.Repository.FuncionarioRepositorio;
import br.com.systemmanagerstore.Utility.MensagemTela;
import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.bean.ManagedBean;
import javax.inject.Named;
import org.primefaces.event.FlowEvent;

/**
 *
 * @author ARLEY
 */
@ManagedBean
@Named(value = "funcionarioController")
@SessionScoped
public class FuncionarioController extends ControllerGenerico<Funcionario> implements Serializable {

    @EJB
    FuncionarioRepositorio funcionarioLocal;

    private boolean skip;

    private String senha1, senha2, email;

    public FuncionarioController() {
        senha1 = "";
        senha2 = "";
        email = "";
    }

    @PostConstruct
    public void init() {
        setDao(funcionarioLocal);
        setPaginaEdicao("FuncionarioEditar.xhtml");
        setPaginaListagem("FuncionarioListagemListagem.xtml");
        setEntidade(new Funcionario());
        setFiltro(new Funcionario());
    }

    public boolean isSkip() {
        return skip;
    }

    public void setSkip(boolean skip) {
        this.skip = skip;
    }

    public String getSenha1() {
        return senha1;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setSenha1(String senha1) {
        this.senha1 = senha1;
    }

    public String getSenha2() {
        return senha2;
    }

    public void setSenha2(String senha2) {
        this.senha2 = senha2;
    }

    @Override
    public void limparCampos() {
        this.setEntidade(new Funcionario());
    }

    @Override
    public void limparEntidade() {
        this.setEntidade(new Funcionario());
    }

    @Override
    public void limpar() {
        this.setEntidade(new Funcionario());
        this.setFiltro(new Funcionario());
        this.senha1 = "";
        this.senha2 = "";
        this.email = "";
    }

    public String onFlowProcess(FlowEvent event) {
        if (validaCPF()) {
            try {
                funcionarioLocal.verificaCPfExistente(this.getEntidade().getCpf());
                if (skip) {
                    skip = false;   //reset in case user goes back
                    return "confirm";
                } else {
                    return event.getNewStep();
                }
            }catch(CpfInvalidoException cie){
                MensagemTela.MensagemErro(cie.getMessage(), "CPF Invalido");
                return event.getOldStep();
            }

        } else {
            MensagemTela.MensagemErro("CPF Invalido!", "CPF Inexistente!");
            return event.getOldStep();
        }
    }

    public void comparaSenha() throws SenhaInvalidaException {
        if (!senha1.equals("") || !senha2.equals("")) {
            if (!senha1.equals(senha2)) {
                throw new SenhaInvalidaException("Ás senhas não se conhecide!");
            }

        } else {
            throw new SenhaInvalidaException("Campos senha ou compo repita a senha não preenchidos!");
        }
    }

    public boolean validaCPF() {
        return ValidadorCPF.validaCPF(getEntidade().getCpf());
    }

    public void salvarDados() {
        try {
            comparaSenha();
            this.getEntidade().setSenhaCriptografada(senha1);
            this.funcionarioLocal.verifcaLoginExitente(email);
            this.getEntidade().setEmail(email);
            this.salvar();
            this.limpar();
        } catch (SenhaInvalidaException eie) {
            MensagemTela.MensagemErro("Senha Invalida!", eie.getMessage());
        } catch (LoginInvalidoException lie) {
            MensagemTela.MensagemErro("Email Invalido!", lie.getMessage());
        }
    }
}
