/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.systemmanagerstore.Presentation.Converters;

import br.com.systemmanagerstore.DomainModel.Produto;
import br.com.systemmanagerstore.Repository.ProdutoRepositorio;
import com.sun.prism.impl.ManagedResource;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.FacesConverter;
import javax.inject.Named;

/**
 *
 * @author ARLEY
 */
@FacesConverter("produtoConverter")
@Named("produtoConverter")
public class ProdutoConverter extends ConverterGenerico<Produto>{

    @EJB
    ProdutoRepositorio produtoLocal;
    
    public ProdutoConverter(){
    }
    
    @Override
    public String getAsString(FacesContext fc, UIComponent uic, Object object) {
        if (object instanceof Produto) {
            Produto entity= (Produto) object;
            if (entity != null && entity instanceof Produto && entity.getId() != null) {
                uic.getAttributes().put( entity.getId().toString(), entity);
                return entity.getId().toString();
            }
        }
        return ""; 
    }
}
