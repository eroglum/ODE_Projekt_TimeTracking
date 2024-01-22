package org.fhtw.exception;

public class ManagerNotfoundException extends RuntimeException {
    public ManagerNotfoundException(String message) {
        super(message);
    }

    public ManagerNotfoundException(Long associateId) {
        super("The employee id '" + associateId + "' does not exist in our records");
    }

    public ManagerNotfoundException(String username, String password) {
        super("The username '" + username + "' and the password '" + password + "' of the manager does not exist in our records");
    }

}
