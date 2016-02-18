/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.systemmanagerstore.DataAccess;

import br.com.systemmanagerstore.DomainModel.Pessoa;
import br.com.systemmanagerstore.Presentation.Utility.Exception.CpfInvalidoException;
import br.com.systemmanagerstore.Repository.PessoaRepositorioGenerico;
import java.util.List;
import javax.persistence.NoResultException;
import javax.persistence.Query;

/**
 *
 * @author ARLEY
 */
public class PessoaGenericoDAO<T> extends DAOGenerico<T> implements PessoaRepositorioGenerico<T> {

    public PessoaGenericoDAO(Class t) {
        super(t);
    }

    @Override
    public List<T> Buscar(T filtro) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void verificaCPfExistente(String cpf) throws CpfInvalidoException {
        Query consulta = manager.createQuery("select p from Pessoa p where p.cpf =:p0");
        Pessoa pessoa = null;

        try {
            pessoa = (Pessoa) consulta
                    .setParameter("p0", cpf)
                    .getSingleResult();
        } catch (NoResultException nre) {

        }

        if (pessoa != null) {
            throw new CpfInvalidoException("Cpf já foi cadastrado!");
        }
    }

}
