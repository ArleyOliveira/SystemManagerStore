/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package br.com.systemmanagerstore.DataAccess;


import br.com.systemmanagerstore.Repository.Repositorio;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

/**
 *
 * @author petronio
 */
public abstract class DAOGenerico<T> implements Repositorio<T>{

    EntityManagerFactory factory = Persistence.createEntityManagerFactory("SistemManagerStorePU");
    protected EntityManager manager = factory.createEntityManager();
    Class classe;
    
    public DAOGenerico(Class t){
        this.classe = t;
    }
    

    public boolean Salvar(T obj) {
          
        EntityTransaction t = manager.getTransaction();
        
        try {            

            t.begin();

            manager.persist(obj);

            t.commit();
            return true;
        
        } catch (Exception e){
            t.rollback();
            return false;
        }
    }

    public T Abrir(Long id) {
        return (T) manager.find(classe, id);
    }


    public boolean Apagar(T obj) {
         EntityTransaction t = manager.getTransaction();
        
        try {            

            t.begin();

            manager.remove(obj);

            t.commit();
            return true;
        
        } catch (Exception e){
            t.rollback();
            return false;
        }
    }

   
    public abstract List<T> Buscar(T filtro);
    
}
