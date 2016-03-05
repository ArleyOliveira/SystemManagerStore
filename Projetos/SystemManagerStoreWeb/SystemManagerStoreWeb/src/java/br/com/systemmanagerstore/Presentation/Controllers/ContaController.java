/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.systemmanagerstore.Presentation.Controllers;

import br.com.systemmanagerstore.DomainModel.Conta;
import br.com.systemmanagerstore.DomainModel.ItemVenda;
import br.com.systemmanagerstore.Repository.ContaRepositorio;
import java.io.ByteArrayOutputStream;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
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
@Named(value = "contaController")
@SessionScoped
public class ContaController extends ControllerGenerico<Conta> implements Serializable {

    /**
     * Creates a new instance of ContaController
     */
    @EJB
    ContaRepositorio contaLocal;

    public ContaController() {
    }

    @PostConstruct
    public void init() {
        setDao(contaLocal);
        setPaginaEdicao("ContaEditar.xhtml");
        setPaginaListagem("ContaListagem.xtml");
        this.setEntidade(new Conta());
        this.setFiltro(new Conta());
    }

    @Override
    public void limparCampos() {
        this.setEntidade(new Conta());
        this.setFiltro(new Conta());
    }

    @Override
    public void limparEntidade() {
        this.setEntidade(new Conta());
    }

    @Override
    public void limpar() {
        this.setEntidade(new Conta());
        this.setFiltro(new Conta());
    }

    public void gerarPdf(int opcao) {

        List<ItemVenda> itens = this.getEntidade().getVenda().getItens();

        JRBeanCollectionDataSource ds = new JRBeanCollectionDataSource(itens);

        HashMap<String, Object> parameters = new HashMap<>();

        parameters.put("operador", this.getEntidade().getVenda().getFuncionario());

        parameters.put("empresa", "AJ Confecções");
        parameters.put("cnpj", "000-000-000/33");

        parameters.put("data", this.getEntidade().getData());
        parameters.put("valor", this.getEntidade().getValor());

        parameters.put("cliente", this.getEntidade().getVenda().getCliente());

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
                    response.setHeader("Content-Disposition", "inline; filename=\"Compra-" + this.getEntidade().getVenda().getDataFormatadaNomeRelatorio() + "-" + this.getEntidade().getVenda().getCliente().getNome() + ".pdf\"");
                } else {
                    response.setHeader("Content-Disposition", "attachment; filename=\"Compra-" + this.getEntidade().getVenda().getDataFormatadaNomeRelatorio() + "-" + this.getEntidade().getVenda().getCliente().getNome() + ".pdf\"");
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
}
