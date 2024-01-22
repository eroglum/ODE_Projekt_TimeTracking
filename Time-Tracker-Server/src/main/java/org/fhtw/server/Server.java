package org.fhtw.server;


import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.converters.basic.NullConverter;
import com.thoughtworks.xstream.core.TreeMarshallingStrategy;
import com.thoughtworks.xstream.security.AnyTypePermission;
import org.fhtw.dto.*;
import org.fhtw.repository.AssociateRepositoryImpl;
import org.fhtw.repository.CredentialRepositoryImpl;
import org.fhtw.repository.TaskRepositoryImpl;
import org.fhtw.service.AssociateService;
import org.fhtw.service.CredentialService;
import org.fhtw.service.TaskService;
import org.fhtw.server.thread.ClientHandler;
import org.fhtw.util.JwtTokenUtil;


import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * The {@code Server} class is responsible for creating a server that listens to incoming client connections on port 8888.
 * Once a client connects, the server creates a new thread, instantiates a {@link ClientHandler} object, and runs the thread
 * with the client handler object as its target. The {@link ClientHandler} class handles all communication and logic for the client.
 * The server also configures XStream for XML serialization and deserialization of data that is sent and received from clients.
 * Additionally, JWT token utility is also provided to the {@link CredentialService} for token generation and validation.
 */
public class Server {
    /**
     * The main method creates a new {@link ServerSocket} and binds it to port 8888. The server enters an infinite loop
     * that waits for incoming client connections. When a client connects, the server creates a new thread, instantiates a
     * {@link ClientHandler} object, and runs the thread with the client handler object as its target. This allows the server
     * to handle multiple clients simultaneously.
     *
     * @param args command line arguments.
     */
    public static void main(String[] args) {
        ServerSocket server = null;
        try {
            // server is listening on port 1234
            server = new ServerSocket(8888);
            server.setReuseAddress(true);
            // running infinite loop for getting client request
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
            xstream.alias("org.fhtw.dto.TaskDto", TaskDto.class);

            xstream.registerConverter(new NullConverter(), XStream.PRIORITY_VERY_HIGH);
            xstream.setMarshallingStrategy(new TreeMarshallingStrategy());

            JwtTokenUtil jwtTokenUtil = new JwtTokenUtil();

            CredentialRepositoryImpl credentialRepository = new CredentialRepositoryImpl();
            CredentialService credentialService = new CredentialService(credentialRepository, jwtTokenUtil);

            TaskRepositoryImpl taskRepository = new TaskRepositoryImpl();
            TaskService taskService = new TaskService(taskRepository);

            AssociateRepositoryImpl associateRepository = new AssociateRepositoryImpl();
            AssociateService associateService = new AssociateService(associateRepository);

            while (true) {
                // socket object to receive incoming client requests
                Socket client = server.accept();
                // Displaying that new client is connected to server
                System.out.println("New client connected: " + client.getInetAddress().getHostAddress());
                // create a new thread object
                ClientHandler clientSock = new ClientHandler(client, credentialService, taskService, associateService, jwtTokenUtil, xstream);
                // This thread will handle the client separately
                new Thread(clientSock).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        } finally {
            if (server != null) {
                try {
                    server.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
