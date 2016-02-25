/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.systemmanagerstore.Presentation.Controllers;

import br.com.systemmanagerstore.DomainModel.ItemVenda;
import br.com.systemmanagerstore.DomainModel.Venda;
import br.com.systemmanagerstore.Presentation.Utility.ItemInvalidoException;
import br.com.systemmanagerstore.Presentation.Utility.ProdutoExitenteException;
import br.com.systemmanagerstore.Presentation.Utility.QuantidadeProdutoInvalidoException;
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

    public void verificarProdutoExistente() throws ProdutoExitenteException {
        for (ItemVenda item : this.getEntidade().getItens()) {
            if (item.getProduto().equals(i.getProduto())) {
                throw new ProdutoExitenteException("Este produto já foi adicionado na lista!");
            }
        }
    }

    public void verificaQuantidadeAdicionada() throws QuantidadeProdutoInvalidoException {
        if (i.getQuantidade() <= 0) {
            throw new QuantidadeProdutoInvalidoException("Quantidade invalida!");
        } else if (i.getQuantidade() > i.getProduto().getEstoque()) {
            throw new QuantidadeProdutoInvalidoException("Quantidade insufiente no estoque!");
        }

    }

    public void verificaItemPrenchido() throws ItemInvalidoException {
        if (i == null || i.getProduto() == null) {
            throw new ItemInvalidoException("Campos obrigátorio não preenchidos!");
        }
    }

    public void addProduto() {
        try {
            this.verificaItemPrenchido();
            this.verificarProdutoExistente();
            this.verificaQuantidadeAdicionada();    
            this.getEntidade().add(i);
            this.limparItem();
        } catch (ProdutoExitenteException pee) {
            MensagemTela.MensagemErro("Produto exitente!", pee.getMessage());
        } catch(QuantidadeProdutoInvalidoException qpei){
            MensagemTela.MensagemErro("Quantidade Invalida", qpei.getMessage());
        }catch(ItemInvalidoException iie){
            MensagemTela.MensagemErro("Campos não preenchidos!", iie.getMessage());
        }
    }

    public void excluirItem() {
        this.getEntidade().remove(i);
        this.limparItem();
    }

    public void editarItem() {
        this.getEntidade().remove(i);
    }

    public void atualizaEstoqueProduto() {
        for (ItemVenda i : this.getEntidade().getItens()) {
            i.getProduto().setEstoque(i.getProduto().getEstoque() - i.getQuantidade());
            produtoLocal.Salvar(i.getProduto());
        }
    }

    public void addValorItem() {
        this.i.setValor(i.getProduto().getValor());
    }

}
