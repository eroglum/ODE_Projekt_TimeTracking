package org.fhtw.client;


import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.converters.basic.NullConverter;
import com.thoughtworks.xstream.core.TreeMarshallingStrategy;
import com.thoughtworks.xstream.security.AnyTypePermission;
import org.fhtw.dto.*;


import java.io.*;
import java.net.Socket;
import java.text.ParseException;
import java.util.Scanner;

/**
 * The {@link TcpClient} class is a simple TCP client that connects to a server, sends various requests and receives responses in XML format.
 * It uses {@link XStream} library to convert the requests and responses objects to and from XML strings.
 * The class uses {@link Socket} to establish a connection to the server and {@link DataInputStream} and {@link DataOutputStream}
 * to send and receive data over the network.
 * It uses {@link Scanner} to read input from the user.
 * <p>
 * The class contains a single method {@link TcpClient#main(String[])} that is the entry point of the program.
 * The main method reads the username and password from the user and sends a {@link SignInRequest} object to the server.
 * It then enters a loop to read commands from the user and send the corresponding requests to the server.
 * Depending on the user's role, it displays a list of available commands for either an associate or a manager,
 * and it uses a switch statement to process the command and call the appropriate method to send the request.
 * The response from the server is then read and parsed into the corresponding response object and displayed to the user.
 * If the user's role is not associate or manager the user will be notified and the program will exit.
 */
public class TcpClient {

    public static void main(String[] args) throws IOException, ParseException {
        // Xstream Config
        XStream xstream = new XStream();
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
        xstream.registerConverter(new NullConverter(), XStream.PRIORITY_VERY_HIGH);
        xstream.setMarshallingStrategy(new TreeMarshallingStrategy());

        // IO config
        Socket socket = new Socket("localhost", 8888);
        DataOutputStream out = new DataOutputStream(socket.getOutputStream());
        DataInputStream in = new DataInputStream(socket.getInputStream());
        Scanner scanner = new Scanner(System.in);

        // Send the signInRequest to the server
        // read the response from the Server
        // If the login was successful, enter a loop to read commands from the user
        // map the xml response into AssociateSignInResponse Class
        // Send the signInRequest to the server
        // Read the username and password from the user
        System.out.print("Username: ");
        String username = scanner.nextLine();
        System.out.print("Password: ");
        String password = scanner.nextLine();
        SignInRequest signInRequest = new SignInRequest();
        signInRequest.setUsername(username);
        signInRequest.setPassword(password);
        String xml = xstream.toXML(signInRequest);
        out.writeUTF(xml);
        String userToken;
        String employeeRole;
        while (true) {
            String serverResponseXml = in.readUTF();
            SignInResponse signInResponse = (SignInResponse) xstream.fromXML(serverResponseXml);
            if (signInResponse.isRequestSucceeded()) {
                userToken = signInResponse.getJwtToken();
                employeeRole = signInResponse.getRole();
                System.out.println("Server response: ");
                System.out.println(signInResponse);
                break;
            }
            System.out.println(signInResponse);
        }

        while (true) {
            if (employeeRole.equals("associate")) {
                System.out.print("You are an associate: Enter a command (ADD_TASK or GET_TASKS or UPDATE_PASSWORD or EXIT): ");
                String input = scanner.nextLine();
                if (input.equals("ADD_TASK")) {
                    try {
                        // Read the task data from the user
                        System.out.print("Enter a task description: ");
                        String description = scanner.nextLine();
                        System.out.print("Enter the date (yyyy-MM-dd): ");
                        String date = scanner.nextLine();
                        System.out.print("Enter the number of hours spent: ");
                        double hours = scanner.nextDouble();
                        scanner.nextLine(); // consume newline left-over
                        AddTaskRequest addTaskRequest = new AddTaskRequest(userToken, description, date, hours);
                        String request = xstream.toXML(addTaskRequest);
                        out.writeUTF(request);
                        String serverResponseXml = in.readUTF();
                        AddTaskResponse addTaskResponse = (AddTaskResponse) xstream.fromXML(serverResponseXml);
                        System.out.println("Server response: ");
                        System.out.println(addTaskResponse);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }

                } else if (input.equals("GET_TASKS")) {
                    try {
                        System.out.print("Enter a startDate (yyyy-MM-dd): ");
                        String startDate = scanner.nextLine();
                        System.out.print("Enter the endDate (yyyy-MM-dd): ");
                        String endDate = scanner.nextLine();
                        GetAssociateTasksRequest getAssociateTasksRequest = new GetAssociateTasksRequest(startDate, endDate, userToken);
                        String request = xstream.toXML(getAssociateTasksRequest);
                        out.writeUTF(request);
                        String serverResponseXml = in.readUTF();
                        GetAssociateTasksResponse getAssociateTasksResponse = (GetAssociateTasksResponse) xstream.fromXML(serverResponseXml);
                        System.out.println("Server response: ");
                        System.out.println(getAssociateTasksResponse);
                    } catch (ParseException | IllegalArgumentException e) {
                        e.printStackTrace();
                    }

                } else if (input.equals("UPDATE_PASSWORD")) {
                    // Read the username and password from the user
                    System.out.print("Enter the new password: ");
                    String newPassword = scanner.nextLine();
                    UpdateCredentialRequest updateCredentialRequest = new UpdateCredentialRequest(newPassword, userToken);
                    String request = xstream.toXML(updateCredentialRequest);
                    out.writeUTF(request);
                    String serverResponseXml = in.readUTF();
                    UpdateCredentialResponse updateCredentialResponse = (UpdateCredentialResponse) xstream.fromXML(serverResponseXml);
                    System.out.println("Server response: ");
                    System.out.println(updateCredentialResponse);
                } else if (exit(xstream, socket, out, in, scanner, input)) break;

            } else if (employeeRole.equals("manager")) {
                System.out.print("You are a manager: Enter a command (GET_ASSOCIATE_TASKS or UPDATE_PASSWORD or EXIT): ");
                String input = scanner.nextLine();
                if (input.equals("GET_ASSOCIATE_TASKS")) {
                        GetAllAssociatesByManagerIdRequest getAllAssociatesByManagerIdRequest = new GetAllAssociatesByManagerIdRequest(userToken);
                        String request = xstream.toXML(getAllAssociatesByManagerIdRequest);
                        out.writeUTF(request);
                        String serverResponseXml = in.readUTF();
                        GetAllAssociatesByManagerIdResponse getAssociateTasksResponse = (GetAllAssociatesByManagerIdResponse) xstream.fromXML(serverResponseXml);
                        System.out.println(getAssociateTasksResponse);
                } else if (input.equals("UPDATE_PASSWORD")) {
                    // Read the username and password from the user
                    System.out.print("Enter the new password: ");
                    String newPassword = scanner.nextLine();
                    UpdateCredentialRequest updateCredentialRequest = new UpdateCredentialRequest(newPassword, userToken);
                    String request = xstream.toXML(updateCredentialRequest);
                    out.writeUTF(request);
                    String serverResponseXml = in.readUTF();
                    UpdateCredentialResponse updateCredentialResponse = (UpdateCredentialResponse) xstream.fromXML(serverResponseXml);
                    System.out.println("Server response: ");
                    System.out.println(updateCredentialResponse);
                } else {
                    if (exit(xstream, socket, out, in, scanner, input)) break;
                }
            }
        }
    }

    private static boolean exit(XStream xstream, Socket socket, DataOutputStream out, DataInputStream in, Scanner scanner, String input) throws IOException {
        if (input.equals("EXIT")) {
            ExitRequest exitRequest = new ExitRequest();
            String request = xstream.toXML(exitRequest);
            out.writeUTF(request);
            scanner.close();
            out.close();
            in.close();
            socket.close();
            return true;
        }
        return false;
    }
}
