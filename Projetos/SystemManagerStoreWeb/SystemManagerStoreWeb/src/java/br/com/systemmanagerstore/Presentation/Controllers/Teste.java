/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.systemmanagerstore.Presentation.Controllers;

import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;

/**
 *
 * @author ARLEY
 */
@Named(value = "teste")
@SessionScoped
public class Teste implements Serializable {

    /**
     * Creates a new instance of Teste
     */
    public Teste() {
    }
    
    
    public boolean valida(){
        return true;
    }
    
    public String urlBase(){
        return "http://localhost:8080/SystemManagerStoreWeb/Login.xhtml";
    }
}
