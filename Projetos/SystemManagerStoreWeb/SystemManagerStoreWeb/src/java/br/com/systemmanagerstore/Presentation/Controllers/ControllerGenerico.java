/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.systemmanagerstore.Presentation.Controllers;

import br.com.systemmanagerstore.Repository.Repositorio;
import br.com.systemmanagerstore.Utility.MensagemTela;
import java.io.IOException;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.context.FacesContext;



/**
 *
 * @author ARLEY
 */
public abstract class ControllerGenerico<T> {

    /**
     * Creates a new instance of ControllerGenerico
     */
    public ControllerGenerico() {
    }

    Class classe;
    
    protected Repositorio<T> dao;

    private T entidade;

    private T filtro;

    private String paginaEdicao, paginaListagem;

    public abstract void limparCampos();

    public abstract void limparEntidade();

    public void salvar() {
        if (dao.Salvar(entidade)) {
            this.limparCampos();
            MensagemTela.MensagemSucesso("Sucesso!", "Registro salvo com sucesso!");
        } else {
            MensagemTela.MensagemErro("Falha!", "Erro ao salvar o registro. Contacte o administrador do sistema!");
        }
    }

    public String apagar() {
        if (dao.Apagar(entidade)) {
            MensagemTela.MensagemSucesso("Sucesso!", "Registro deletado com sucesso!");
            return "FuncionarioListagem.xhtml";
        } else {
            MensagemTela.MensagemErro("Falha!", "Erro ao apagar o registro. Contacte o administrador do sistema!");
            return "";
        }
    }

    public void filtrar() {

    }

    public Class getClasse() {
        return classe;
    }

    public void setClasse(Class classe) {
        this.classe = classe;
    }

    public Repositorio getDao() {
        return dao;
    }

    public void setDao(Repositorio dao) {
        this.dao = dao;
    }

    public T getEntidade() {
        return entidade;
    }

    public void setEntidade(T entidade) {
        this.entidade = entidade;
    }

    public T getFiltro() {
        return filtro;
    }

    public void setFiltro(T filtro) {
        this.filtro = filtro;
    }

    public abstract void limpar();

    public List<T> getListagemGeral() {
        limpar();
        return getListagem();
    }

    public List<T> getListagem() {
        return dao.Buscar(getFiltro());
    }

    public String getPaginaEdicao() {
        return paginaEdicao;
    }

    public void setPaginaEdicao(String paginaEdicao) {
        this.paginaEdicao = paginaEdicao;
    }

    public String getPaginaListagem() {
        return paginaListagem;
    }

    public void setPaginaListagem(String paginaListagem) {
        this.paginaListagem = paginaListagem;
    }

    public void redirect(String url) throws IOException {
        FacesContext.getCurrentInstance().getExternalContext().redirect(url);
    }
}
