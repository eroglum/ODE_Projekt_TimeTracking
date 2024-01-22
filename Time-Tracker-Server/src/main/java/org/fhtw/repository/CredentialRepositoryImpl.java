package org.fhtw.repository;

import org.fhtw.dto.SignInRequest;
import org.fhtw.dto.UpdateCredentialRequest;
import org.fhtw.entity.Credential;
import org.fhtw.exception.AssociateNotFoundException;
import org.fhtw.exception.ManagerNotfoundException;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

public class CredentialRepositoryImpl implements CredentialRepository {

    public CredentialRepositoryImpl() {
    }

    @Override
    public Long findAssociateByUsernameAndPassword(SignInRequest associateSignInRequest, EntityManager em) {
        TypedQuery<Long> query = em.createQuery(
                "SELECT e.employeeId FROM Associate e " +
                        "JOIN e.credential c " +
                        "WHERE c.username = :username AND c.password = :password", Long.class);

        query.setParameter("username", associateSignInRequest.getUsername());
        query.setParameter("password", associateSignInRequest.getPassword());
        Long id;
        try {
            id = query.getSingleResult();
            return id;
        } catch (NoResultException ex) {
            throw new AssociateNotFoundException(associateSignInRequest.getUsername(), associateSignInRequest.getPassword());
        }
    }

    @Override
    public Long findManagerByUsernameAndPassword(SignInRequest managerSignInRequest, EntityManager em) {
        TypedQuery<Long> query = em.createQuery(
                "SELECT m.managerId FROM Manager m " +
                        "JOIN m.credential c " +
                        "WHERE c.username = :username AND c.password = :password", Long.class);
        query.setParameter("username", managerSignInRequest.getUsername());
        query.setParameter("password", managerSignInRequest.getPassword());
        Long id;
        try {
            id = query.getSingleResult();
            return id;
        } catch (NoResultException ex) {
            throw new ManagerNotfoundException(managerSignInRequest.getUsername(), managerSignInRequest.getPassword());
        }
    }

    @Override
    public void updateCredentialAssociate(UpdateCredentialRequest updateCredentialRequest, Long associateID, EntityManager em) {
        TypedQuery<Credential> credentialTypedQuery = em.createQuery("SELECT a.credential FROM Associate a " + "WHERE a.employeeId= :employee_id", Credential.class);
        credentialTypedQuery.setParameter("employee_id", associateID);
        //Associate associateToFind = em.find(Associate.class, associateID);
        Credential credential;
        try {
            credential = credentialTypedQuery.getSingleResult();
            //credential.setEmployee(associateToFind);
            credential.setPassword(updateCredentialRequest.getNewPassword());
            em.getTransaction().begin();
            em.merge(credential);
            em.getTransaction().commit();
        } catch (NoResultException ex) {
            throw new AssociateNotFoundException(ex.getMessage());
        }
    }

    @Override
    public void updateCredentialManager(UpdateCredentialRequest updateCredentialRequest, Long managerId, EntityManager em) {
        TypedQuery<Credential> credentialTypedQuery = em.createQuery("SELECT m.credential FROM Manager m " + "WHERE m.managerId= :manager_id", Credential.class);
        credentialTypedQuery.setParameter("manager_id", managerId);
        //Manager managerToFind = em.find(Manager.class, managerId);
        Credential credential;
        try {
            credential = credentialTypedQuery.getSingleResult();
            //credential.setManager(managerToFind);
            credential.setPassword(updateCredentialRequest.getNewPassword());
            em.getTransaction().begin();
            em.merge(credential);
            em.getTransaction().commit();
        } catch (NoResultException ex) {
            throw new ManagerNotfoundException(ex.getMessage());
        }
    }

}






