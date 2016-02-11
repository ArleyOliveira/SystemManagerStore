/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.systemmanagerstore.DataAccess;

import br.com.systemmanagerstore.DomainModel.Funcionario;
import br.com.systemmanagerstore.Repository.FuncionarioRepositorio;
import java.util.List;
import javax.ejb.Singleton;

/**
 *
 * @author arley
 */
@Singleton
public class FuncionarioDAO extends DAOGenerico<Funcionario> implements FuncionarioRepositorio{

    public FuncionarioDAO() {
        super(Funcionario.class);
    }

    @Override
    public List<Funcionario> Buscar(Funcionario filtro) {
        if(filtro != null){
            return this.Like("nome", filtro.getNome())
                   .IgualA("id", filtro.getId())
                   .IgualA("cpf", filtro.getCpf())
                   .OrderBy("nome", "ASC").Buscar();
            
        }
        return this.Buscar();
    }
    
}
