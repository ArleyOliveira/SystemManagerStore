/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.systemmanagerstore.Presentation.Converters;

import br.com.systemmanagerstore.DomainModel.Pessoa;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.FacesConverter;
import javax.inject.Named;

/**
 *
 * @author ARLEY
 */
@FacesConverter("pessoaConverter")
@Named("pessoaConverter")
public class PessoaConverter extends ConverterGenerico<Pessoa>{

    @Override
    public String getAsString(FacesContext fc, UIComponent uic, Object object) {
        if (object instanceof Pessoa) {
            Pessoa entity = (Pessoa) object;
            if (entity != null && entity instanceof Pessoa && entity.getId() != null) {
                uic.getAttributes().put( entity.getId().toString(), entity);
                return entity.getId().toString();
            }
        }
        return ""; 
    }
    
}
