/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.systemmanagerstore.Presentation.Controllers;

import br.com.systemmanagerstore.DomainModel.ItemVenda;
import br.com.systemmanagerstore.DomainModel.Venda;
import br.com.systemmanagerstore.Repository.ProdutoRepositorio;
import br.com.systemmanagerstore.Repository.VendaRepositorio;
import br.com.systemmanagerstore.Utility.MensagemTela;
import java.io.Serializable;
import java.math.BigDecimal;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.bean.ManagedBean;
import javax.inject.Named;

/**
 *
 * @author ARLEY
 */
@ManagedBean
@Named(value = "vendaController")
@SessionScoped
public class VendaController extends ControllerGenerico<Venda> implements Serializable {

    /**
     * Creates a new instance of CompraController
     */
    @EJB
    VendaRepositorio vendaLocal;

    @EJB
    ProdutoRepositorio produtoLocal;

    private ItemVenda i;

    public VendaController() {
        i = new ItemVenda();
    }

    @PostConstruct
    public void init() {
        setDao(vendaLocal);
        setPaginaEdicao("VendaEditar.xhtml");
        setPaginaListagem("VendaListagem.xtml");
        this.setEntidade(new Venda());
        this.setFiltro(new Venda());
    }

    public ItemVenda getI() {
        return i;
    }

    public void setI(ItemVenda i) {
        this.i = i;
    }  

    @Override
    public void limparCampos() {
        this.setEntidade(new Venda());
    }

    @Override
    public void limparEntidade() {
        this.setEntidade(new Venda());
    }

    @Override
    public void limpar() {
        this.setEntidade(new Venda());
        this.setFiltro(new Venda());
    }

    public void limparItem() {
        i = new ItemVenda();
    }

    public void addValorCompra(BigDecimal valor) {
        this.getEntidade().getValor().add(valor);
    }

    public void removerValorCompra(BigDecimal valor) {
        this.getEntidade().getValor().subtract(valor);
    }

    public void addProduto() {
        try {
            for (ItemVenda item : this.getEntidade().getItens()) {
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

    public void atualizaEstoqueProduto() {
        for (ItemVenda i : this.getEntidade().getItens()) {
            i.getProduto().setEstoque(i.getProduto().getEstoque() + i.getQuantidade());
            produtoLocal.Salvar(i.getProduto());
        }
    }

    public void addValorItem() {
        this.i.setValor(i.getProduto().getValor());
    }
 
}
