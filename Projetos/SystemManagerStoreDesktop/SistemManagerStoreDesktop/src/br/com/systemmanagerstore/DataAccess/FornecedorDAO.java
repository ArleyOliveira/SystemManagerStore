/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.systemmanagerstore.DataAccess;

import br.com.systemmanagerstore.DomainModel.Fornecedor;
import br.com.systemmanagerstore.Repository.FornecedorRepositorio;
import java.util.List;
import javax.persistence.Query;


/**
 *
 * @author Arley
 */

public class FornecedorDAO extends DAOGenerico<Fornecedor> implements FornecedorRepositorio{

    public FornecedorDAO() {
        super(Fornecedor.class);
    }

    @Override
    public List<Fornecedor> Buscar(Fornecedor filtro) {
        if(filtro != null){
            return this.Like("nome", filtro.getNome())
                   .IgualA("id", filtro.getId())
                    .Buscar();   
        }
        return this.Buscar();
    }

    @Override
    public Fornecedor Abrir(String cnpj) {
        Query consulta = manager.createQuery("select f from Fornecedor f where p.cpf =:p0");
        return (Fornecedor) consulta
                .setParameter("p0", cnpj)
                .getSingleResult();
    }
}
