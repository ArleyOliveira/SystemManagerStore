/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.systemmanagerstore.Presentation.Converters;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;

/**
 *
 * @author ARLEY
 */
public abstract class ConverterGenerico<T> implements Converter{

    @Override
    public Object getAsObject(FacesContext fc, UIComponent uic, String value) {
        if (value != null && !value.isEmpty()) {
            T  entidade = (T) uic.getAttributes().get(value);
            return entidade;
        }
        return null;
    }

    @Override
    public abstract String getAsString(FacesContext fc, UIComponent uic, Object object);
    
}
