/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.systemmanagerstore.Repository;

import br.com.systemmanagerstore.DomainModel.Fornecedor;
import javax.ejb.Local;



/**
 *
 * @author Arley
 */
@Local
public interface FornecedorRepositorio extends Repositorio<Fornecedor>{
    public Fornecedor Abrir(String cnpj);
}
