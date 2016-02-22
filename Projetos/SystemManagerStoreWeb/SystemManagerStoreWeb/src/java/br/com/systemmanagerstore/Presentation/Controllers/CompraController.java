/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.systemmanagerstore.Presentation.Controllers;

import br.com.systemmanagerstore.DomainModel.Compra;
import br.com.systemmanagerstore.DomainModel.ItemCompra;
import br.com.systemmanagerstore.DomainModel.Produto;
import br.com.systemmanagerstore.Repository.CompraRepositorio;
import br.com.systemmanagerstore.Repository.ProdutoRepositorio;
import br.com.systemmanagerstore.Utility.MensagemTela;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.math.BigDecimal;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import org.primefaces.event.SelectEvent;

/**
 *
 * @author ARLEY
 */
@ManagedBean
@Named(value = "compraController")
@SessionScoped
public class CompraController extends ControllerGenerico<Compra> implements Serializable {

    /**
     * Creates a new instance of CompraController
     */
    @EJB
    CompraRepositorio compraLocal;
    
    @EJB
    ProdutoRepositorio produtoLocal;
    
    private ItemCompra i;
    
    public CompraController() {
        i = new ItemCompra();
    }
    
    @PostConstruct
    public void init() {
        setDao(compraLocal);
        setPaginaEdicao("CompraEditar.xhtml");
        setPaginaListagem("CompraListagem.xtml");
        this.setEntidade(new Compra());
        this.setFiltro(new Compra());
    }

    public ItemCompra getI() {
        return i;
    }

    public void setI(ItemCompra i) {
        this.i = i;
    }

    @Override
    public void limparCampos() {
        this.setEntidade(new Compra());
    }

    @Override
    public void limparEntidade() {
        this.setEntidade(new Compra());
    }

    @Override
    public void limpar() {
        this.setEntidade(new Compra());
        this.setFiltro(new Compra());
    }
    
    public void limparItem(){
        i = new ItemCompra();
    }
    
    public void addValorCompra(BigDecimal valor){
        this.getEntidade().getValor().add(valor);
    }
    
    public void removerValorCompra(BigDecimal valor){
        this.getEntidade().getValor().subtract(valor);
    }
    
    public void addProduto() {
        try {
            for (ItemCompra item : this.getEntidade().getItens()) {
                if (item.getProduto().equals(i.getProduto())) {
                    throw new RuntimeException();
                }
            }
            if (i.getProduto() != null && i.getQuantidade() > 0) {
                this.getEntidade().add(i);
                this.getEntidade().setValor((BigDecimal) this.getEntidade().getValor().add(i.getValorTotal()));
                this.limparItem();
                MensagemTela.MensagemSucesso("Sucesso!", "Produto adicionado com sucesso!");
            } else {
                MensagemTela.MensagemErro("Erro!", "Preenchar os campos obrigatorios");
            }
        } catch (RuntimeException e) {
            limparItem();
            MensagemTela.MensagemErro("Erro!", "Este produto já esta na lista!");
        }
    }

    public void excluirItem() {
        this.getEntidade().setValor((BigDecimal) this.getEntidade().getValor().subtract(i.getValorTotal()));
        this.getEntidade().remove(i);
        this.limparItem();
    }

    public void editarItem() {
        this.getEntidade().setValor((BigDecimal) this.getEntidade().getValor().subtract(i.getValorTotal()));
        this.getEntidade().remove(i);
    }
    
    public void atualizaEstoqueProduto(){
        for (ItemCompra i : this.getEntidade().getItens()) { 
            i.getProduto().setEstoque(i.getProduto().getEstoque() + i.getQuantidade());
            produtoLocal.Salvar(i.getProduto());
        }
    }
   
    public void addValorItem(){
        this.i.setValor(i.getProduto().getValor());
    }
}
