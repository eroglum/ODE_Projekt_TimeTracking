package org.fhtw.service;


import org.apache.http.HttpStatus;
import org.fhtw.dto.SignInRequest;
import org.fhtw.dto.SignInResponse;
import org.fhtw.dto.UpdateCredentialRequest;
import org.fhtw.dto.UpdateCredentialResponse;
import org.fhtw.exception.AssociateNotFoundException;
import org.fhtw.repository.CredentialRepository;
import org.fhtw.util.JwtTokenUtil;


import javax.persistence.EntityManager;

/**
 * The {@link CredentialService} class provides methods for performing operations on credentials, such as sign in and update the credentials.
 * It uses a {@link CredentialRepository} object to interact with the repository layer and perform database operations, also it uses {@link JwtTokenUtil} to generate token.
 *
 * <p> The class contains a single constructor {@link CredentialService#CredentialService(CredentialRepository, JwtTokenUtil)} that takes a {@link CredentialRepository} and {@link JwtTokenUtil} object as a parameter
 * and sets it to the {@link CredentialService#credentialRepository} and {@link CredentialService#jwtTokenUtil} fields. This repository object is used to perform database operations, the jwtTokenUtil is used to generate token for each user.
 * <p> The class contains a method {@link CredentialService#findAssociateByUsernameAndPassword(SignInRequest, javax.persistence.EntityManager)} that takes a {@link SignInRequest} and an {@link EntityManager} object as parameters
 * and returns an {@link SignInResponse} object if the user is found, otherwise it throws an exception.
 * <p> The class contains a method {@link CredentialService#findManagerByUsernameAndPassword(SignInRequest, javax.persistence.EntityManager)} that takes a {@link SignInRequest} and an {@link EntityManager} object as parameters
 * and returns an {@link SignInResponse} object if the manager is found, otherwise it throws an exception.
 * <p> The class contains a method {@link CredentialService#updateCredential(UpdateCredentialRequest, Long, String, javax.persistence.EntityManager)} that takes {@link UpdateCredentialRequest}, an employee id and role and an {@link EntityManager} object as parameters
 * and returns an {@link UpdateCredentialResponse} object if the credentials is updated successfully, otherwise it throws an exception.
 */
public class CredentialService {
    private final CredentialRepository credentialRepository;
    private final JwtTokenUtil jwtTokenUtil;


    public CredentialService(CredentialRepository credentialRepository, JwtTokenUtil jwtTokenUtil) {
        this.credentialRepository = credentialRepository;
        this.jwtTokenUtil = jwtTokenUtil;
    }

    public SignInResponse findAssociateByUsernameAndPassword(SignInRequest signInRequest, EntityManager em) {
        SignInResponse signInResponse = new SignInResponse();
        try {
            Long associateId = credentialRepository.findAssociateByUsernameAndPassword(signInRequest, em);
            String token = jwtTokenUtil.generateToken(signInRequest.getUsername());
            signInResponse.setStatus(HttpStatus.SC_OK);
            signInResponse.setMessage("associate: " + signInRequest.getUsername() + " signed in");
            signInResponse.setRequestSucceeded(true);
            signInResponse.setEmployeeId(associateId);
            signInResponse.setJwtToken(token);
            signInResponse.setRole("associate");
            return signInResponse;
        } catch (AssociateNotFoundException exception) {
            throw new AssociateNotFoundException(exception.getMessage());
        }
    }

    public SignInResponse findManagerByUsernameAndPassword(SignInRequest signInRequest, EntityManager em) {
        SignInResponse signInResponse = new SignInResponse();
        try {
            Long associateId = credentialRepository.findManagerByUsernameAndPassword(signInRequest, em);
            String token = jwtTokenUtil.generateToken(signInRequest.getUsername());
            signInResponse.setStatus(HttpStatus.SC_OK);
            signInResponse.setMessage("manager: " + signInRequest.getUsername() + " signed in");
            signInResponse.setRequestSucceeded(true);
            signInResponse.setEmployeeId(associateId);
            signInResponse.setJwtToken(token);
            signInResponse.setRole("manager");
            return signInResponse;
        } catch (AssociateNotFoundException exception) {
            throw new AssociateNotFoundException(exception.getMessage());
        }
    }

    public UpdateCredentialResponse updateCredential(UpdateCredentialRequest updateCredentialRequest, Long employeeId, String role, EntityManager em) {
        UpdateCredentialResponse updateCredentialResponse = new UpdateCredentialResponse();
        try {
            if (role.equals("associate")) {
                credentialRepository.updateCredentialAssociate(updateCredentialRequest, employeeId, em);
            } else
                credentialRepository.updateCredentialManager(updateCredentialRequest, employeeId, em);

            updateCredentialResponse.setStatus(HttpStatus.SC_OK);
            updateCredentialResponse.setMessage("password updated");
            updateCredentialResponse.setRequestSucceeded(true);
            return updateCredentialResponse;
        } catch (Exception e) {
            updateCredentialResponse.setStatus(HttpStatus.SC_NOT_FOUND);
            updateCredentialResponse.setMessage(e.getMessage());
            updateCredentialResponse.setRequestSucceeded(false);
            return updateCredentialResponse;
        }

    }
}

