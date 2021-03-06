/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.systemmanagerstore.DataAccess;

import br.com.systemmanagerstore.DomainModel.Conta;
import br.com.systemmanagerstore.Repository.ContaRepositorio;
import java.util.List;
import javax.ejb.Singleton;

/**
 *
 * @author ARLEY
 */
@Singleton
public class ContaDAO extends DAOGenerico<Conta> implements ContaRepositorio{

    public ContaDAO() {
        super(Conta.class);
    }

    @Override
    public List<Conta> Buscar(Conta filtro) {
        return this.Buscar();
    }
    
}
