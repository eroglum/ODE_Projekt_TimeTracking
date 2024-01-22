# Time Tracker Server

## Prerequisites
- JDK 17
- apache-maven-3.8.7
- Docker

## Starting the Application
1. Build the Docker image:
```sh
docker build -t customized-postgre:v1 .
```
**_Note: the docker-image has to be up and running, this docker image contains sample records, without this customized DB the program will not work._**  

You can connect your host computer to the db by typing this command:  
```sh
psql -h localhost -p 8080 -U postgres -d odedb
```
The password for postgres is: `12345678`

2. Run the Docker container:
```sh
docker run -p 8080:5432 --name Time-Tracking-DB -d customized-postgre:v1
```
3. Compile the java project:
```sh
mvn clean install
```
4. Start the java TCP Server:
```sh
java -jar target/TimeTracker-1.0-SNAPSHOT-jar-with-dependencies.jar
```

## Usage of the Software

To use the software, you need to first sign in with a valid username and password. Once you are logged in, you will have
access to the different functions of the software.

## Signing in as an associate

The associate can sign in to the software by providing their username and password.

```java
SignInRequest signInRequest=new SignInRequest();
        signInRequest.setUsername("associate_username");
        signInRequest.setPassword("associate_password");
        String xml=xstream.toXML(signInRequest);
        out.writeUTF(xml);

        String serverResponseXml=in.readUTF();
        SignInResponse signInResponse=(SignInResponse)xstream.fromXML(serverResponseXml);

        if(signInResponse.isRequestSucceeded()){
        // login successful
        String userToken=signInResponse.getJwtToken();
        System.out.println("Associate sign in successful. Token: "+userToken);
        }else{
        System.out.println("Associate sign in failed. Reason: "+signInResponse.getErrorMessage());
        }
```

## Adding a task as an associate

The associate can add a task by providing the task details and their token.

```java
AddTaskRequest addTaskRequest=new AddTaskRequest();
        addTaskRequest.setTask("Complete project report");
        addTaskRequest.setDateFrom("2022-01-01");
        addTaskRequest.setHoursSpent(8);
        addTaskRequest.setJwtToken(userToken);
        xml=xstream.toXML(addTaskRequest);
        out.writeUTF(xml);

        serverResponseXml=in.readUTF();
        AddTaskResponse addTaskResponse=(AddTaskResponse)xstream.fromXML(serverResponseXml);

        if(addTaskResponse.isRequestSucceeded()){
        // task added successfully
        System.out.println("Task added successfully.");
        }else{
        System.out.println("Task addition failed. Reason: "+addTaskResponse.getErrorMessage());
        }
```

## Getting all associates under a manager

A manager can view all associates that are under them by providing their token start-date and end-date for all associates task.

```java
GetAllAssociatesByManagerIdRequest getAllAssociatesByManagerIdRequest=new GetAllAssociatesByManagerIdRequest(userToken);
String request=xstream.toXML(getAllAssociatesByManagerIdRequest);
out.writeUTF(request);
```

## Update the password

A manager and an associate can reset their credential after signing in

```java
UpdateCredentialRequest updateCredentialRequest=new UpdateCredentialRequest();
        updateCredentialRequest.setPassword("newpassword");
        updateCredentialRequest.setJwtToken(userToken);
        String updateCredentialXml=xstream.toXML(updateCredentialRequest);
        out.writeUTF(updateCredentialXml);
```

## Get associate tasks 
An associate can request to retrieve their tasks by providing the user token and start-date and end-date.
```java

System.out.print("Enter a startDate (yyyy-MM-dd): ");
String startDate = scanner.nextLine();
System.out.print("Enter the endDate (yyyy-MM-dd): ");
String endDate = scanner.nextLine();
GetAssociateTasksRequest getAssociateTasksRequest = new GetAssociateTasksRequest(startDate, endDate, userToken);
String request = xstream.toXML(getAssociateTasksRequest);
out.writeUTF(request);
```

### **_Database Tables_**

###### _Credential table_

| credential_id | username      | password         | employee_id | manager_id |
|---------------|---------------|------------------|-------------|------------|
| 1             | johndoe       | password123      | NULL        | 1          |
| 2             | janedoe       | password456      | NULL        | 2          |
| 3             | alicesmith    | password789      | NULL        | 3          |
| 4             | bobsmith      | password101112   | NULL        | 4          |
| 5             | mikejohnson   | password131415   | NULL        | 5          |
| 6             | ashleyjohnson | password161718   | 1           | NULL       |
| 7             | sarahwilliams | password19202122 | 2           | NULL       |
| 8             | mattwilliams  | password232425   | 3           | NULL       |
| 9             | davidsmith    | password262728   | 4           | NULL       |
| 10            | emmawhite     | password29303132 | 5           | NULL       |
| 11            | chrisbrown    | password333334   | 6           | NULL       |
| 12            | katiebrown    | password353637   | 7           | NULL       |
| 13            | jamesdoe      | password383940   | 8           | NULL       |
| 14            | racheladams   | password414243   | 9           | NULL       |

###### _Manager table_

| manager_id | employee_name       | credential_id |
|------------|---------------------|---------------|
| 1          | John Doe            | 1             |
| 2          | Jane Doe            | 2             |
| 3          | Alice Smith         | 3             |
| 4          | Bob Smith           | 4             |
| 5          | Mike Johnson        | 5             |

###### _Associate table_

| employee_id | employee_name   | credential_id | manager_id    | 
|-------------|-----------------|---------------|---------------|
| 1           | ashley johnson  | 6             | 1             |
| 2           | sarah williams  | 7             | 2             |
| 3           | matt williams   | 8             | 3             |
| 4           | david smith     | 9             | 2             |
| 5           | emma white      | 10            | 3             |
| 6           | chris brown     | 11            | 4             |
| 7           | katie brown     | 12            | 5             |
| 8           | james doe       | 13            | 4             |
| 9           | rachel adams    | 14            | 5             |

###### _Task table_

| task_id | employee_task              | employee_date_from | employee_hours_spent | employee_id |
|---------|----------------------------|--------------------|----------------------|-------------|


## Notes
- the server has to be up running, when the client trys to connect

