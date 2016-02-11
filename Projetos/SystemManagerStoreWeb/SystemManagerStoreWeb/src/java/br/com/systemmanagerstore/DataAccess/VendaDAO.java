/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
// */
package br.com.systemmanagerstore.DataAccess;

import br.com.systemmanagerstore.DomainModel.Venda;
import br.com.systemmanagerstore.Repository.VendaRepositorio;
import java.util.List;
import javax.ejb.Singleton;

/**
 *
 * @author arley
 */
@Singleton
public class VendaDAO extends DAOGenerico<Venda> implements VendaRepositorio {

    public VendaDAO() {
        super(Venda.class);
    }

    @Override
    public List<Venda> Buscar(Venda filtro) {

        if (filtro != null) {
            if (filtro.cliente != null) {
                return this.IgualA("PESSOA_ID", filtro.getCliente().getId()).Buscar();
            } else {
                return this.IgualA("id", filtro.getId())
                        .OrderBy("data", "ASC").Buscar();
            }
        }
        return this.Buscar();
    }

}
