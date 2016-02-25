/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.systemmanagerstore.Presentation.Utility;

/**
 *
 * @author ARLEY
 */
public class QuantidadeProdutoInvalidoException extends RuntimeException{
    public QuantidadeProdutoInvalidoException(String msg){
        super(msg);
    }
}
