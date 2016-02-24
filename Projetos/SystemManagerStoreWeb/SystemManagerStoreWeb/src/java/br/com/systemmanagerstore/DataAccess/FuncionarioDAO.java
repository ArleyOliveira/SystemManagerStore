/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.systemmanagerstore.DataAccess;

import br.com.systemmanagerstore.DomainModel.Funcionario;
import br.com.systemmanagerstore.DomainModel.Pessoa;
import br.com.systemmanagerstore.Presentation.Utility.Exception.CpfInvalidoException;
import br.com.systemmanagerstore.Presentation.Utility.Exception.LoginInvalidoException;
import br.com.systemmanagerstore.Repository.FuncionarioRepositorio;
import java.util.List;
import javax.ejb.Singleton;
import javax.persistence.NoResultException;
import javax.persistence.Query;

/**
 *
 * @author arley
 */
@Singleton
public class FuncionarioDAO extends PessoaGenericoDAO<Funcionario> implements FuncionarioRepositorio {

    public FuncionarioDAO() {
        super(Funcionario.class);
    }

    @Override
    public List<Funcionario> Buscar(Funcionario filtro) {
        if (filtro != null) {
            return this.Like("nome", filtro.getNome())
                    .IgualA("id", filtro.getId())
                    .IgualA("cpf", filtro.getCpf())
                    .OrderBy("nome", "ASC").Buscar();

        }
        return this.Buscar();
    }

    @Override
    public void verifcaLoginExitente(String email) throws LoginInvalidoException {
        Query consulta = manager.createQuery("select p from Funcionario p where p.email =:p0");
        Funcionario funcionario = null;

        try {
            funcionario = (Funcionario) consulta
                    .setParameter("p0", email)
                    .getSingleResult();
        } catch (NoResultException nre) {

        }

        if (funcionario != null) {
            throw new LoginInvalidoException("Login existente!");
        }
    }

    @Override
    public Funcionario autenticar(String email, String senha) throws LoginInvalidoException {
        try {
            Query consulta = manager.createQuery("select f from Funcionario f where f.email =:p0 and f.senha =:p1");
            Funcionario funcionario = null;
            funcionario = (Funcionario) consulta
                    .setParameter("p0", email)
                    .setParameter("p1", senha)
                    .getSingleResult();
            return funcionario;
        } catch (NoResultException nre) {
            throw new LoginInvalidoException("Login ou senha invalidos!");
        } catch(IllegalArgumentException iae){
            throw new LoginInvalidoException("Erro desconhecido, contacte com o adminstrador!");
        }
    }
}
