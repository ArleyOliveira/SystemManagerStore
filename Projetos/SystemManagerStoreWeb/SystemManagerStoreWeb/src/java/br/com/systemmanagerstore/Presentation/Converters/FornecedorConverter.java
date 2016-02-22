/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.systemmanagerstore.Presentation.Converters;

import br.com.systemmanagerstore.DomainModel.Fornecedor;
import br.com.systemmanagerstore.DomainModel.Produto;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.FacesConverter;
import javax.inject.Named;

/**
 *
 * @author ARLEY
 */
@FacesConverter("fornecedorConverter")
@Named("fornecedorConverter")
public class FornecedorConverter extends ConverterGenerico<Fornecedor>{

    @Override
    public String getAsString(FacesContext fc, UIComponent uic, Object object) {
        if (object instanceof Fornecedor) {
            Fornecedor entity= (Fornecedor) object;
            if (entity != null && entity instanceof Fornecedor && entity.getId() != null) {
                uic.getAttributes().put( entity.getId().toString(), entity);
                return entity.getId().toString();
            }
        }
        return ""; 
    }
    
}
