package com.fhtw.taskmanagerclient.model;

import com.fhtw.taskmanagerclient.model.dto.*;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.converters.basic.NullConverter;
import com.thoughtworks.xstream.core.TreeMarshallingStrategy;
import com.thoughtworks.xstream.security.AnyTypePermission;

import java.io.*;
import java.net.Socket;
import java.text.ParseException;

/**

 The Client class is responsible for connecting to the server, sending and receiving data, and handling the communication with the server.

 It also uses the XStream library to convert the data sent and received to and from XML format.
 */
public class Client {
    private String token = "";
    private String userRole = "";
    XStream xstream = null;
    private Socket socket;
    /**
     The DataOutputStream of the socket of the client.
     */
    private DataOutputStream out;
    /**

     The DataInputStream of the socket of the client.
     */
    private DataInputStream in;
    /**

     Constructor for the Client class, it creates a new socket and connects to the given host and port number.
     It also initializes the XStream library.
     @param hostName The hostname or IP address to connect to.
     @param portNumber The port number to connect to.
     @throws IOException If the socket cannot be created or connected to the given host and port.
     */
    public Client(String hostName, int portNumber) throws IOException {
        this.socket = new Socket(hostName, portNumber);
        this.out = new DataOutputStream(socket.getOutputStream());
        this.in = new DataInputStream(socket.getInputStream());
        initializeXstream();
    }
    /**
     Returns the current user role
     @return userRole
     */
    public String getUserRole() {
        return userRole;
    }

    /**
     Sends a login request to the server with the given username and password.
     If the login request is successful, it sets the userRole and token of the current client instance.
     @param username The username to login with.
     @param password The password to login with.
     @return true if the login request is successful, false otherwise.
     @throws IOException If there is an issue sending the login request to the server.
     */
    public boolean login(String username, String password) throws IOException {
        SignInRequest signInRequest = new SignInRequest();
        signInRequest.setUsername(username);
        signInRequest.setPassword(password);
        out.writeUTF(xstream.toXML(signInRequest));
        SignInResponse signInResponse = (SignInResponse) xstream.fromXML(in.readUTF());
        if (signInResponse.isRequestSucceeded()) {
            this.userRole = signInResponse.getRole();
            this.token = signInResponse.getJwtToken();
            return true;
        }
        return false;
    }
    /**

     Sends a request to the server to retrieve all tasks of the associates associated with the manager
     @return GetAllAssociatesByManagerIdResponse containing all associates and their tasks
     @throws ParseException
     @throws IOException
     */
    public GetAllAssociatesByManagerIdResponse getAllManagersEmployeesTasks() throws ParseException, IOException {
        GetAllAssociatesByManagerIdRequest request = new GetAllAssociatesByManagerIdRequest(this.token);
        out.writeUTF(xstream.toXML(request));
        GetAllAssociatesByManagerIdResponse response = (GetAllAssociatesByManagerIdResponse) xstream.fromXML(in.readUTF());
        return response;
    }
    /**

     Sends a request to the server to add a task for the associate associated with this client
     @param task the task to add
     @throws IOException
     @throws ParseException
     @returns true if request was successful
     */
    public boolean addTask(TaskDto task) throws IOException, ParseException {
        AddTaskRequest request = new AddTaskRequest (this.token,
                                                    task.getEmployeeTask(),
                                                    task.getEmployeeDateFrom(),
                                                    task.getEmployeeHoursSpent());
        out.writeUTF(xstream.toXML(request));
        String serverResponseXml = in.readUTF();
        AddTaskResponse addTaskResponse = (AddTaskResponse) xstream.fromXML(serverResponseXml);
        return addTaskResponse.isRequestSucceeded();
    }
    /**

     Sends a request to the server to retrieve the tasks of the current user from a specific interval.
     @param startDate the start date of the interval
     @param endDate the end date of the interval
     @return a {@link GetAssociateTasksResponse} object containing the response from the server
     @throws IOException if an I/O error occurs when sending the request
     @throws ParseException if the date format is not valid
     */
    public GetAssociateTasksResponse getTasksInAnInterval(String startDate, String endDate) throws IOException, ParseException {
        GetAssociateTasksRequest getAssociateTasksRequest = new GetAssociateTasksRequest(startDate, endDate, this.token);
        out.writeUTF(xstream.toXML(getAssociateTasksRequest));
        GetAssociateTasksResponse TasksResponse = (GetAssociateTasksResponse) xstream.fromXML(in.readUTF());
        return TasksResponse;
    }
    /**
     * updatePassword - sends a request to the server to update the password for the current user.
     * @param newPassword - the new password the user wants to set
     * @param repeatNewPassword - the repeated new password the user has to confirm
     * @return true if the password was updated successfully, false otherwise.
     */
    public boolean updatePassword(String newPassword, String repeatNewPassword) throws IOException {

        if(!newPassword.equals(repeatNewPassword)) return false;
        UpdateCredentialRequest request  = new UpdateCredentialRequest(newPassword, this.token);
        out.writeUTF(xstream.toXML(request));
        UpdateCredentialResponse response = (UpdateCredentialResponse) xstream.fromXML(in.readUTF());
        if(!response.isRequestSucceeded()) return false;
        return true;
    }
    /**

     Initializes XStream object with necessary aliases and permissions.
     */
    private void initializeXstream(){
        xstream = new XStream();
        xstream.addPermission(AnyTypePermission.ANY);
        xstream.setMode(XStream.XPATH_RELATIVE_REFERENCES);
        xstream.alias("SignInRequest", SignInRequest.class);
        xstream.alias("SignInResponse", SignInResponse.class);
        xstream.alias("AddTaskRequest", AddTaskRequest.class);
        xstream.alias("AddTaskResponse", AddTaskResponse.class);
        xstream.alias("GetAllAssociatesByManagerIdRequest", GetAllAssociatesByManagerIdRequest.class);
        xstream.alias("GetAllAssociatesByManagerIdResponse", GetAllAssociatesByManagerIdResponse.class);
        xstream.alias("GetAssociateTasksRequest", GetAssociateTasksRequest.class);
        xstream.alias("GetAssociateTasksResponse", GetAssociateTasksResponse.class);
        xstream.alias("UpdateCredentialRequest", UpdateCredentialRequest.class);
        xstream.alias("UpdateCredentialResponse", UpdateCredentialResponse.class);
        xstream.alias("ExitRequest", ExitRequest.class);
        xstream.alias("org.fhtw.dto.TaskDto", TaskDto.class);
        xstream.alias("org.fhtw.dto.AssociateDto", AssociateDto.class);
        xstream.registerConverter(new NullConverter(), XStream.PRIORITY_VERY_HIGH);
        xstream.setMarshallingStrategy(new TreeMarshallingStrategy());
    }
    public void deleteTask(String taskId) throws IOException {
        DeleteTaskRequest deleteTaskRequest = new DeleteTaskRequest(taskId);
        out.writeUTF(xstream.toXML(deleteTaskRequest));
        String serverResponseXml = in.readUTF();
    }

}


