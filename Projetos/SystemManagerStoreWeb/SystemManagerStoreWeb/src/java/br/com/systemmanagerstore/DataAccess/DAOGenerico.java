package br.com.systemmanagerstore.DataAccess;

import br.com.systemmanagerstore.Repository.Repositorio;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;


/**
 *
 * @author petronio
 */
public abstract class DAOGenerico<T> implements Repositorio<T> {

    @PersistenceContext(name = "SystemManagerStorePU")
    protected EntityManager manager;
    Class classe;
    String where = "";
    String orderby = "";
    String jpql = "select c from ";
    Map<String, Object> parametros = new HashMap<>();

    public DAOGenerico(Class t) {
        this.classe = t;
    }

    @Override
    public boolean Salvar(T obj) {

        try {
            manager.merge(obj);
            manager.flush();
            return true;

        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public T Abrir(Long id) {
        return (T) manager.find(classe, id);
    }

    @Override
    public boolean Apagar(T obj) {

        try {
            T objRemove = manager.merge(obj);
            manager.remove(objRemove);
            manager.flush();
            return true;

        } catch (Exception e) {
            return false;
        }
    }

    public DAOGenerico<T> OrderBy(String campo, String order) {

        if (campo != null) {
            if (orderby.length() > 0) {
                orderby += ",";
            }

            orderby += "c." + campo + " " + order;
        }
        return this;
    }

    public DAOGenerico<T> IgualA(String campo, Object valor) {

        if (valor == null || valor.toString().isEmpty()) {
            return this;
        }

        if (where.length() > 0) {
            where += " and ";
        }

        if (valor != null) {
            where = where + "c." + campo + " = :" + campo;
            parametros.put(campo, valor);
        }

        return this;
    }

    public DAOGenerico<T> Like(String campo, String valor) {

        if (valor == null || valor.isEmpty()) {
            return this;
        }

        if (where.length() > 0) {
            where += " and ";
        }

        if (valor != null) {
            where = where + "c." + campo + " like '%" + valor + "%'";
        }

        return this;
    }
    
    
    public DAOGenerico<T> Diferente(String campo, Object valor) {

        if (valor == null || valor.toString().isEmpty()) {
            return this;
        }

        if (where.length() > 0) {
            where += " and ";
        }

        if (valor != null) {
            where = where + "c." + campo + " != :" + campo;
            parametros.put(campo, valor);
        }

        return this;
    }

    public List<T> Buscar() {
        try {

            jpql += classe.getSimpleName() + " c";

            if (where.length() > 0) {
                jpql = jpql + " where " + where;
            }

            if (orderby.length() > 0) {
                jpql = jpql + " order by " + orderby;
            }

            Query consulta = manager.createQuery(jpql);

            for (String parametro : parametros.keySet()) {
                consulta.setParameter(parametro, parametros.get(parametro));
            }
            return consulta.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            where = "";
            jpql = "select c from ";
            orderby = "";
            parametros = new HashMap<>();
        }
    }

}
