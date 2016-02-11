/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.systemmanagerstore.DataAccess;

import br.com.systemmanagerstore.DomainModel.Produto;
import br.com.systemmanagerstore.Repository.ProdutoRepositorio;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;
import javax.ejb.Singleton;


/**
 *
 * @author arley
 */

@Singleton
public class ProdutoDAO extends DAOGenerico<Produto> implements ProdutoRepositorio {

    public ProdutoDAO() {
        super(Produto.class);
    }

    @Override
    public List<Produto> Buscar(Produto filtro) {
        if (filtro != null) {
            return this.Like("nome", filtro.getNome())
                    .Like("nome", filtro.getDescricao())
                    .IgualA("id", filtro.getId())
                    .OrderBy("nome", "ASC").Buscar();

        }
        return this.Buscar();
    }
}
