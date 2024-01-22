package org.fhtw.repository;

import org.fhtw.dto.SignInRequest;
import org.fhtw.dto.UpdateCredentialRequest;

import javax.persistence.EntityManager;

public interface CredentialRepository {
    Long findAssociateByUsernameAndPassword(SignInRequest signInRequest, EntityManager em);

    Long findManagerByUsernameAndPassword(SignInRequest SignInRequest, EntityManager em);

    void updateCredentialAssociate(UpdateCredentialRequest updateCredentialRequest, Long associateID, EntityManager em);

    void updateCredentialManager(UpdateCredentialRequest updateCredentialRequest, Long managerId, EntityManager em);
}
