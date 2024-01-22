package org.fhtw.exception;

public class AssociateNotFoundException extends RuntimeException {
    public AssociateNotFoundException(String message) {
        super(message);
    }

    public AssociateNotFoundException(Long associateId) {
        super("The employee id '" + associateId + "' does not exist in our records");
    }

    public AssociateNotFoundException(String username, String password) {
        super("The username '" + username + "' and the password '" + password + "' of the Associate does not exist in our records");
    }

}
