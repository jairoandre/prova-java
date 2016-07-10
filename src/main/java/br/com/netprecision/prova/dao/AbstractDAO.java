package br.com.netprecision.prova.dao;

import org.hibernate.Criteria;
import org.hibernate.Session;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public abstract class AbstractDAO<T> implements Serializable {

    EntityManagerFactory emf = Persistence.createEntityManagerFactory("persistenceUnit");
    EntityManager em = emf.createEntityManager();

    private Class<T> type;

    public AbstractDAO(Class<T> type) {
        this.type = type;
    }

    public T create(T t) {
        EntityTransaction tx = em.getTransaction();
        try{
            tx.begin();
            em.persist(t);
            em.flush();
            em.refresh(t);
            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        } finally {
            em.close();
            emf.close();
        }
        return t;
    }

    public T find(Object id) {
        return em.find(type, id);
    }

    public void delete(Object id) {
        EntityTransaction tx = em.getTransaction();
        try{
            tx.begin();
            Object ref = em.getReference(type, id);
            em.remove(ref);
            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        } finally {
            em.close();
            emf.close();
        }
    }

    public T update(T item) {
        EntityTransaction tx = em.getTransaction();
        try{
            tx.begin();
            item = em.merge(item);
            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        } finally {
            em.close();
            emf.close();
        }
        return item;
    }

    public List<T> findWithNamedQuery(String namedQuery) {
        List<T> list = new ArrayList<>();
        try {
            list = em.createNamedQuery(namedQuery).getResultList();
        } catch (Exception e) {
            System.out.println("Erro inesperado");
        } finally {
            em.close();
            emf.close();
        }
        return list;
    }

    public Criteria getCriteria() {
        Session sess = getEm().unwrap(Session.class);
        return sess.createCriteria(type);
    }

    public EntityManager getEm() {
        return em;
    }

}
