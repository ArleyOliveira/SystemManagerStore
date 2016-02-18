/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package br.com.systemmanagerstore.Repository;

import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author petronio
 */

public interface Repositorio<T> {
    public boolean Salvar(T obj);
    public T Abrir(Long id);
    public boolean Apagar(T obj);
    public List<T> Buscar(T filtro);
}
