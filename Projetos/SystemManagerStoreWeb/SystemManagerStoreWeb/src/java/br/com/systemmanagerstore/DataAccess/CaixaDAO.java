/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.systemmanagerstore.DataAccess;

import br.com.systemmanagerstore.DomainModel.Caixa;
import br.com.systemmanagerstore.Repository.CaixaRepositorio;
import java.util.List;
import javax.ejb.Singleton;

/**
 *
 * @author ARLEY
 */
@Singleton
public class CaixaDAO extends DAOGenerico<Caixa> implements CaixaRepositorio{

    public CaixaDAO() {
        super(Caixa.class);
    }

    
    // Modifcar busca para o caixa
    @Override
    public List<Caixa> Buscar(Caixa filtro) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
