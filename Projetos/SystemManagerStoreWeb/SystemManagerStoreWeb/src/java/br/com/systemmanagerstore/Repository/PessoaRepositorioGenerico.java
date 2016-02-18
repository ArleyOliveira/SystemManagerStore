/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.systemmanagerstore.Repository;

import br.com.systemmanagerstore.Presentation.Utility.Exception.CpfInvalidoException;

/**
 *
 * @author ARLEY
 */
public interface PessoaRepositorioGenerico<T> extends Repositorio<T>{
    public void verificaCPfExistente(String cpf) throws CpfInvalidoException; 
}
