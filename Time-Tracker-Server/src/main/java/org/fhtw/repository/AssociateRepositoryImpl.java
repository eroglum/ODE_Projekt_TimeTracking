package org.fhtw.repository;

import org.fhtw.entity.Associate;
import org.fhtw.entity.Credential;
import org.fhtw.entity.Manager;
import org.fhtw.exception.AssociateNotFoundException;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import java.util.List;

public class AssociateRepositoryImpl implements AssociateRepository {

    public AssociateRepositoryImpl() {
    }

    @Override
    public void addAssociate(Associate associate, Manager manager, Credential associateCredential, Credential managerCredential, EntityManager em) {
        try {
            associate.setCredentials(associateCredential);
            manager.setCredential(managerCredential);
            associate.setManager(manager);
            managerCredential.setManager(manager);
            associateCredential.setEmployee(associate);
            em.getTransaction().begin();
            em.persist(associate);
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (em.getTransaction() != null)
                em.getTransaction().rollback();
            ex.printStackTrace();
        }
    }

    @Override
    public List<Associate> findAssociatesByManagerId(Long managerId, EntityManager em) {
        TypedQuery<Associate> associateQuery = em.createQuery(
                "SELECT a FROM Associate a " +
                        "WHERE a.manager.managerId = :managerId", Associate.class);
        associateQuery.setParameter("managerId", managerId);
        return associateQuery.getResultList();
    }

    @Override
    public Associate findAssociateById(Long id, EntityManager em) {
        String query = "SELECT e FROM Associate e WHERE e.employeeId = :Employee_ID";
        TypedQuery<Associate> tq = em.createQuery(query, Associate.class);
        tq.setParameter("Employee_ID", id);
        Associate associate;
        try {
            associate = tq.getSingleResult();
            return associate;
        } catch (NoResultException ex) {
            throw new AssociateNotFoundException(id);
        }
    }
}
