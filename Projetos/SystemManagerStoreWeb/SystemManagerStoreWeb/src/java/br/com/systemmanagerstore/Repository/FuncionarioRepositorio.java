/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.systemmanagerstore.Repository;

import br.com.systemmanagerstore.DomainModel.Funcionario;
import br.com.systemmanagerstore.Presentation.Utility.Exception.CpfInvalidoException;
import br.com.systemmanagerstore.Presentation.Utility.Exception.LoginInvalidoException;



/**
 *
 * @author arley
 */
public interface FuncionarioRepositorio extends PessoaRepositorioGenerico<Funcionario>{
   
    public void verifcaLoginExitente(String email) throws LoginInvalidoException;
}

