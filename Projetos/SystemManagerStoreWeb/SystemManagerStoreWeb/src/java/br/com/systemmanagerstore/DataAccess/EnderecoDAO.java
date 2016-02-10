/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.systemmanagerstore.DataAccess;

import br.com.systemmanagerstore.DomainModel.Endereco;
import br.com.systemmanagerstore.Repository.EnderecoRepositorio;
import java.util.List;

/**
 *
 * @author ARLEY
 */
public class EnderecoDAO extends DAOGenerico<Endereco> implements EnderecoRepositorio{

    public EnderecoDAO() {
        super(Endereco.class);
    }

    @Override
    public List<Endereco> Buscar(Endereco filtro) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
