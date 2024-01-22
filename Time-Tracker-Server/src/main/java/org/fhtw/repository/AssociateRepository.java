package org.fhtw.repository;

import org.fhtw.entity.Associate;
import org.fhtw.entity.Credential;
import org.fhtw.entity.Manager;

import javax.persistence.EntityManager;
import java.util.List;


public interface AssociateRepository {
    void addAssociate(Associate associate, Manager manager, Credential associateCredential, Credential managerCredential, EntityManager em);

    List<Associate> findAssociatesByManagerId(Long managerId, EntityManager em);

    Associate findAssociateById(Long id, EntityManager entityManager);
}
