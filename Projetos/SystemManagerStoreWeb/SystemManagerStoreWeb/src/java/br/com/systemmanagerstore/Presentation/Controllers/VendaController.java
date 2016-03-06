/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.systemmanagerstore.Presentation.Controllers;

import br.com.systemmanagerstore.DomainModel.Conta;
import br.com.systemmanagerstore.DomainModel.ItemVenda;
import br.com.systemmanagerstore.DomainModel.Venda;
import br.com.systemmanagerstore.Presentation.Utility.Config;
import br.com.systemmanagerstore.Presentation.Utility.Exception.ClienteInvalidoException;
import br.com.systemmanagerstore.Presentation.Utility.ItemInvalidoException;
import br.com.systemmanagerstore.Presentation.Utility.ProdutoExitenteException;
import br.com.systemmanagerstore.Presentation.Utility.QuantidadeProdutoInvalidoException;
import br.com.systemmanagerstore.Repository.ContaRepositorio;
import br.com.systemmanagerstore.Repository.PessoaRepositorio;
import br.com.systemmanagerstore.Repository.ProdutoRepositorio;
import br.com.systemmanagerstore.Repository.VendaRepositorio;
import br.com.systemmanagerstore.Utility.MensagemTela;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.servlet.ServletContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.JRPdfExporter;

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

    @EJB
    ContaRepositorio contaLocal;

    @EJB
    PessoaRepositorio pessoaLocal;

    private ItemVenda i;

    private Conta conta;

    private boolean opcoesPosConfirme;

    public VendaController() {
        this.i = new ItemVenda();
        this.conta = new Conta();
    }

    @PostConstruct
    public void init() {
        setDao(vendaLocal);
        setPaginaEdicao("VendaEditar.xhtml");
        setPaginaListagem("VendaListagem.xtml");
        this.setEntidade(new Venda());
        this.setFiltro(new Venda());
        this.opcoesPosConfirme = false;
    }

    public ItemVenda getI() {
        return i;
    }

    public boolean isOpcoesPosConfirme() {
        return opcoesPosConfirme;
    }

    public void setOpcoesPosConfirme(boolean opcoesPosConfirme) {
        this.opcoesPosConfirme = opcoesPosConfirme;
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
        } catch (QuantidadeProdutoInvalidoException qpei) {
            MensagemTela.MensagemErro("Quantidade Invalida", qpei.getMessage());
        } catch (ItemInvalidoException iie) {
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

    public void addDebitoCliente() {
        double valorVenda = this.getEntidade().getValor().doubleValue();
        valorVenda = valorVenda + this.getEntidade().getCliente().getDebito().doubleValue();
        BigDecimal valorAtualizado = new BigDecimal(String.valueOf(valorVenda));
        this.getEntidade().getCliente().setDebito(valorAtualizado);
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

    public Conta getConta() {
        return conta;
    }

    public void setConta(Conta conta) {
        this.conta = conta;
    }

    public void preencherConta() {
        this.conta = new Conta(this.getEntidade(), true);
    }

    public void comfirmar() {
        try {
            this.verificaClienteExistente();
            this.addDebitoCliente();
            this.getEntidade().setFuncionario(Config.getFuncionarioLogado());
            this.preencherConta();
            this.salvar();
        } catch (ClienteInvalidoException cie) {
            MensagemTela.MensagemErro("Erro!", cie.getMessage());
        }
    }

    @Override
    public void salvar() {
        if (contaLocal.Salvar(this.conta)) {
            this.pessoaLocal.Salvar(this.getEntidade().getCliente());
            this.atualizaEstoqueProduto();
            MensagemTela.MensagemSucesso("Sucesso!", "Registro salvo com sucesso!");
            this.limparCampos();
            try {
                this.redirect("VendaConfirmada.xhtml");
            } catch (IOException ioe) {
                MensagemTela.MensagemErro("Falha!", "Erro desconhecido! Contacte com o adminstrado.");
            }
            this.opcoesPosConfirme = true;
        } else {
            MensagemTela.MensagemErro("Falha!", "Erro ao salvar o registro. Contacte o administrador do sistema!");
        }

    }

    public void verificaClienteExistente() throws ClienteInvalidoException {
        if (this.getEntidade().getCliente() == null) {
            throw new ClienteInvalidoException("Cliente não informado!");
        }
    }

    public String irParaTelaConfirmacao() {
        if (this.getEntidade().getItens().isEmpty()) {
            MensagemTela.MensagemErro("Erro!", "Nenhum item foi adicionado!");
            return "";
        }
        return "VendaAPrazoCadastro.xhtml";
    }
    
    

    public void isEmptyItem() {
        if(this.getEntidade().getItens().isEmpty())
            try {
                redirect("VendaCadastro.xhtml");
        } catch (IOException ex) {
            MensagemTela.MensagemErro("Falha!", "Erro desconhecido. Contacte com administrador.");
        }
    }

    public void gerarPdf(int opcao) {

        List<ItemVenda> itens = this.conta.getVenda().getItens();

        JRBeanCollectionDataSource ds = new JRBeanCollectionDataSource(itens);

        HashMap<String, Object> parameters = new HashMap<>();

        parameters.put("operador", this.conta.getVenda().getFuncionario());

        parameters.put("empresa", "AJ Confecções");
        parameters.put("cnpj", "000-000-000/33");

        parameters.put("data", this.conta.getData());
        parameters.put("valor", this.conta.getValor());

        parameters.put("cliente", this.conta.getVenda().getCliente());

        try {

            FacesContext facesContext = FacesContext.getCurrentInstance();

            facesContext.responseComplete();

            ServletContext scontext = (ServletContext) facesContext.getExternalContext().getContext();

            JasperPrint jasperPrint = JasperFillManager
                    .fillReport(scontext.getRealPath("/resources/relatorios/venda.jasper"), parameters, ds);

            ByteArrayOutputStream baos = new ByteArrayOutputStream();

            JRPdfExporter exporter = new JRPdfExporter();

            exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);

            exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, baos);

            exporter.exportReport();

            byte[] bytes = baos.toByteArray();

            if (bytes != null && bytes.length > 0) {

                HttpServletResponse response = (HttpServletResponse) facesContext.getExternalContext().getResponse();

                response.setContentType("application/pdf");
                if (opcao == 1) {
                    response.setHeader("Content-Disposition", "inline; filename=\"Compra-" + this.conta.getVenda().getDataFormatadaNomeRelatorio() + "-" + this.conta.getVenda().getCliente().getNome() + ".pdf\"");
                } else {
                    response.setHeader("Content-Disposition", "attachment; filename=\"Compra-" + this.conta.getVenda().getDataFormatadaNomeRelatorio() + "-" + this.conta.getVenda().getCliente().getNome() + ".pdf\"");
                }
                response.setContentLength(bytes.length);

                ServletOutputStream outputStream = response.getOutputStream();

                outputStream.write(bytes, 0, bytes.length);

                outputStream.flush();

                outputStream.close();

            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void limparConta(){
        this.conta = null;
    }
    
    public void irATelaDeVenda(){
        limparCampos();
        limparConta();
        try {
            this.redirect("VendaCadastro.xhtml");
        } catch (IOException ex) {
            MensagemTela.MensagemErro("Falha!", "Erro ao redirecionar.");
        }
    }
}
