/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.systemmanagerstore.DataAccess;

import br.com.systemmanagerstore.DomainModel.Compra;
import br.com.systemmanagerstore.Repository.CompraRepositorio;
import java.util.List;
import javax.ejb.Singleton;

/**
 *
 * @author arley
 */
@Singleton
public class CompraDAO extends DAOGenerico<Compra> implements CompraRepositorio {

    public CompraDAO() {
        super(Compra.class);
    }
    @Override
        public List<Compra> Buscar(Compra filtro) {
        return this.Buscar();
    }
}
