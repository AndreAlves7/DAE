package pt.ipleiria.estg.dei.ei.dae.backend;

import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;

import java.util.List;

@Stateless
public abstract class AbstractBean<T> {

    private final Class<T> entityClass;

    public AbstractBean(Class<T> entityClass) {
        this.entityClass = entityClass;
    }

    @PersistenceContext(unitName = "yourPersistenceUnit")
    private EntityManager em;

    protected EntityManager getEntityManager() {
        return em;
    }

    public void create(T entity) {
        em.persist(entity);
    }

    public T find(Object id) {
        return em.find(entityClass, id);
    }

    public List<T> findAll() {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<T> cq = cb.createQuery(entityClass);
        Root<T> rootEntry = cq.from(entityClass);
        CriteriaQuery<T> all = cq.select(rootEntry);
        return em.createQuery(all).getResultList();
    }

    public T update(T entity) {
        return em.merge(entity);
    }

    public void remove(T entity) {
        em.remove(em.merge(entity));
    }
}
