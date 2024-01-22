package org.fhtw.server.thread;


import com.thoughtworks.xstream.XStream;
import org.apache.http.HttpStatus;
import org.fhtw.dto.*;
import org.fhtw.exception.AssociateNotFoundException;
import org.fhtw.exception.AuthenticationException;
import org.fhtw.exception.ManagerNotfoundException;
import org.fhtw.service.*;
import org.fhtw.util.JwtTokenUtil;


import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.io.*;
import java.net.Socket;
import java.text.ParseException;

/**
 * The ClientHandler is responsible for handling the communication between the clients and the server.
 * It implements the Runnable interface which allows it to run on a different thread.
 *
 * @author Muhammed
 * It uses {@link CredentialService} to validate the user's credentials,
 * {@link TaskService} to handle task-related requests,
 * {@link AssociateService} to handle associate-related requests,
 * {@link JwtTokenUtil} to handle JWT token-related requests,
 * {@link XStream} for serialization and deserialization,
 * and {@link EntityManager} for database persistence.
 */
public class ClientHandler implements Runnable {

    private final Socket clientSocket;

    private final AssociateService associateService;
    private final TaskService taskService;
    private final CredentialService credentialService;
    private final JwtTokenUtil jwtTokenUtil;
    private final XStream xstream;

    /**
     * Initializes a new instance of the ClientHandler class.
     *
     * @param socket            The client's socket.
     * @param credentialService The CredentialService instance to handle user's credentials
     * @param taskService       The TaskService instance to handle task-related requests
     * @param associateService  The AssociateService instance to handle associate-related requests
     * @param jwtTokenUtil      The JwtTokenUtil instance to handle JWT token-related requests
     * @param xstream           The XStream instance to handle serialization and deserialization
     */
    public ClientHandler(Socket socket, CredentialService credentialService, TaskService taskService, AssociateService associateService, JwtTokenUtil jwtTokenUtil, XStream xstream) {
        this.clientSocket = socket;
        this.credentialService = credentialService;
        this.taskService = taskService;
        this.associateService = associateService;
        this.jwtTokenUtil = jwtTokenUtil;
        this.xstream = xstream;
    }

    /**
     * The main method of the {@link ClientHandler} runnable, which handles incoming client connections.
     * This method reads and writes data to the client socket and communicates with the associated service classes
     * in order to validate credentials, perform actions, and return responses.
     * Communication with the client is done through the input and output streams of the socket,
     * using the XStream library for serialization and deserialization of Java objects to and from XML.
     * The method also manages the lifecycle of the JPA EntityManager, which is used for database operations.
     */
    public void run() {
        try {
            // Get the input and output streams for the socket
            DataOutputStream out = new DataOutputStream(clientSocket.getOutputStream());
            DataInputStream in = new DataInputStream(clientSocket.getInputStream());

            // Persistence unit config
            EntityManagerFactory emf = Persistence.createEntityManagerFactory("ODE_Projekt");
            EntityManager em = emf.createEntityManager();

            SignInResponse signInResponse;
            SignInRequest signInRequest;
            boolean employeeFound = false;
            while (true) {
                // Read the credentials from the client
                String SignInRequestXml = in.readUTF();
                // map the xml request into AssociateSignInRequest Class
                signInRequest = (SignInRequest) xstream.fromXML(SignInRequestXml);
                try {
                    // Validate the credentials
                    signInResponse = credentialService.findAssociateByUsernameAndPassword(signInRequest, em);
                    employeeFound = true;
                    // serialize the sign-in Response into xml and send it to Client
                    String responseJson = xstream.toXML(signInResponse);
                    out.writeUTF(responseJson);
                    break;
                } catch (AssociateNotFoundException ignored) {
                }
                try {
                    // Validate the credentials
                    signInResponse = credentialService.findManagerByUsernameAndPassword(signInRequest, em);
                    employeeFound = true;
                    // serialize the sign-in Response into xml and send it to Client
                    String responseJson = xstream.toXML(signInResponse);
                    out.writeUTF(responseJson);
                    break;
                } catch (ManagerNotfoundException ignored) {
                }
                if (!employeeFound) {
                    SignInResponse exceptionResponse = new SignInResponse();
                    exceptionResponse.setMessage("The username '" + signInRequest.getUsername() + "' and the password '" + signInRequest.getPassword() + "' of the Associate does not exist in our records");
                    exceptionResponse.setStatus(HttpStatus.SC_NOT_FOUND);
                    exceptionResponse.setRequestSucceeded(false);
                    String responseJson = xstream.toXML(exceptionResponse);
                    out.writeUTF(responseJson);
                }
            }
            String username = signInRequest.getUsername();
            Long employeeId = signInResponse.getEmployeeId();
            String role = signInResponse.getRole();

            while (true) {
                String requestXml = in.readUTF();
                if (requestXml.contains("UpdateCredentialRequest")) {
                    UpdateCredentialRequest updateCredentialRequest = (UpdateCredentialRequest) xstream.fromXML(requestXml);
                    try {
                        if (jwtTokenUtil.validateToken(updateCredentialRequest.getToken(), username)) {
                            UpdateCredentialResponse updateCredentialResponse = credentialService.updateCredential(updateCredentialRequest, employeeId, role, em);
                            String responseJson = xstream.toXML(updateCredentialResponse);
                            out.writeUTF(responseJson);
                        }
                    } catch (AuthenticationException e) {
                        UpdateCredentialResponse exceptionResponse = new UpdateCredentialResponse();
                        exceptionResponse.setMessage(e.getMessage());
                        exceptionResponse.setStatus(HttpStatus.SC_UNAUTHORIZED);
                        exceptionResponse.setRequestSucceeded(false);
                        // serialize the sign-in Response into xml and send it to Client
                        String responseJson = xstream.toXML(exceptionResponse);
                        out.writeUTF(responseJson);
                    }
                } else if (requestXml.contains("ExitRequest")) {
                    out.close();
                    in.close();
                    em.close();
                    emf.close();
                    break;
                }
                else if (requestXml.contains("DeleteTaskRequest")) {
                    DeleteTaskRequest deleteTaskRequest = (DeleteTaskRequest) xstream.fromXML(requestXml);
                    try {
                        taskService.deleteTask(deleteTaskRequest.getTaskId(), em);
                        System.out.println("Task successfully deleted. Task ID: " + deleteTaskRequest.getTaskId());
                    } catch (Exception e) {
                        System.err.println("Error deleting task. Task ID: " + deleteTaskRequest.getTaskId() + ". Error: " + e.getMessage());
                    }
                }

                if (role.equals("associate")) {
                    if (requestXml.contains("AddTaskRequest")) {
                        try {
                            AddTaskRequest taskRequest = (AddTaskRequest) xstream.fromXML(requestXml);
                            if (jwtTokenUtil.validateToken(taskRequest.getToken(), username)) {
                                try {
                                    AddTaskResponse addTaskResponse = taskService.addTask(taskRequest, employeeId, em);
                                    String responseJson = xstream.toXML(addTaskResponse);
                                    out.writeUTF(responseJson);
                                } catch (Exception exception) {
                                    AddTaskResponse exceptionResponse = new AddTaskResponse();
                                    exceptionResponse.setMessage(exception.getLocalizedMessage());
                                    String responseJson = xstream.toXML(exceptionResponse);
                                    out.writeUTF(responseJson);
                                }
                            }
                        } catch (AuthenticationException e) {
                            AddTaskResponse exceptionResponse = new AddTaskResponse();
                            exceptionResponse.setMessage(e.getMessage());
                            exceptionResponse.setStatus(HttpStatus.SC_UNAUTHORIZED);
                            exceptionResponse.setRequestSucceeded(false);
                            // serialize the sign-in Response into xml and send it to Client
                            String responseJson = xstream.toXML(exceptionResponse);
                            out.writeUTF(responseJson);
                        }
                    } else if (requestXml.contains("GetAssociateTasksRequest")) {
                        try {
                            GetAssociateTasksRequest getAssociateTasksRequest = (GetAssociateTasksRequest) xstream.fromXML(requestXml);
                            if (jwtTokenUtil.validateToken(getAssociateTasksRequest.getToken(), username)) {
                                GetAssociateTasksResponse getAssociateTasksResponse = taskService.retrieveTasksByAssociateId(getAssociateTasksRequest, employeeId, em);
                                String responseJson = xstream.toXML(getAssociateTasksResponse);
                                out.writeUTF(responseJson);
                            }
                        } catch (AuthenticationException e) {
                            GetAssociateTasksResponse exceptionResponse = new GetAssociateTasksResponse();
                            exceptionResponse.setMessage(e.getMessage());
                            exceptionResponse.setStatus(HttpStatus.SC_UNAUTHORIZED);
                            exceptionResponse.setRequestSucceeded(false);
                            // serialize the sign-in Response into xml and send it to Client
                            String responseJson = xstream.toXML(exceptionResponse);
                            out.writeUTF(responseJson);
                        }
                    }
                } else if (role.equals("manager")) {
                    if (requestXml.contains("GetAllAssociatesByManagerIdRequest")) {
                        try {
                            GetAllAssociatesByManagerIdRequest getAllAssociatesByManagerIdRequest = (GetAllAssociatesByManagerIdRequest) xstream.fromXML(requestXml);
                            if (jwtTokenUtil.validateToken(getAllAssociatesByManagerIdRequest.getToken(), username)) {
                                GetAllAssociatesByManagerIdResponse getAllAssociatesByManagerIdResponse = associateService.GetAllAssociatesByManagerId(getAllAssociatesByManagerIdRequest, employeeId, em);
                                String responseJson = xstream.toXML(getAllAssociatesByManagerIdResponse);
                                out.writeUTF(responseJson);
                            }
                        } catch (AuthenticationException e) {
                            GetAllAssociatesByManagerIdResponse exceptionResponse = new GetAllAssociatesByManagerIdResponse();
                            exceptionResponse.setMessage(e.getMessage());
                            exceptionResponse.setStatus(HttpStatus.SC_UNAUTHORIZED);
                            exceptionResponse.setRequestSucceeded(false);
                            // serialize the sign-in Response into xml and send it to Client
                            String responseJson = xstream.toXML(exceptionResponse);
                            out.writeUTF(responseJson);
                        }
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}


