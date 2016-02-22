/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.systemmanagerstore.Presentation.Controllers;

import br.com.systemmanagerstore.DomainModel.Produto;
import br.com.systemmanagerstore.Repository.ProdutoRepositorio;
import br.com.systemmanagerstore.Utility.MensagemTela;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;

/**
 *
 * @author ARLEY
 */
@ManagedBean
@Named(value = "produtoController")
@SessionScoped
public class ProdutoController extends ControllerGenerico<Produto> implements Serializable {

    /**
     * Creates a new instance of ProdutoController
     */
    
    @EJB
    ProdutoRepositorio produtoLocal;
    
    private int quatidadeEstoque;
    
    
    
    @PostConstruct
    public void init() {
        setDao(produtoLocal);
        setPaginaEdicao("ProdutoEditar.xhtml");
        setPaginaListagem("ProdutoListagem.xtml");
        this.setEntidade(new Produto());
        this.setFiltro(new Produto());
    }
    
    public ProdutoController() {
        this.quatidadeEstoque = 0;
    }

    public int getQuatidadeEstoque() {
        return quatidadeEstoque;
    }

    public void setQuatidadeEstoque(int quatidadeEstoque) {
        this.quatidadeEstoque = quatidadeEstoque;
    }

    @Override
    public void limparCampos() {
        this.setEntidade(new Produto());
    }

    @Override
    public void limparEntidade() {
        this.setEntidade(new Produto());
    }

    @Override
    public void limpar() {
        this.setEntidade(new Produto());
        this.setFiltro(new Produto());
    }
    
    public List<Produto> getAutoComplete(String nome) {
        Produto produto = new Produto();
        produto.setNome(nome);
        produto.setDescricao(nome);
        return produtoLocal.Buscar(produto);
    }
    
    public void atualizaEstoque(){
        if(this.quatidadeEstoque > 0){
            this.getEntidade().atualizaEstoque(quatidadeEstoque);
            this.editar();
            this.quatidadeEstoque = 0;
        }else{
            MensagemTela.MensagemErro("Erro!", "A quantidade a ser adicionada do produto deve ser maior que 0");
        }
    }
}
