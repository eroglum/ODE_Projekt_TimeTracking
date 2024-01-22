package org.fhtw.service;

import org.apache.http.HttpStatus;
import org.fhtw.dto.*;
import org.fhtw.entity.Task;
import org.fhtw.repository.TaskRepository;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;

/**
 * The {@link TaskService} class provides methods for performing operations on tasks, such as retrieving tasks by associate id and adding new tasks.
 * It uses a {@link TaskRepository} object to interact with the repository layer and perform database operations.
 *
 * <p> The class contains a single constructor {@link TaskService#TaskService(TaskRepository)} that takes a {@link TaskRepository} object as a parameter
 * and sets it to the {@link TaskService#taskRepository} field. This repository object is used to perform database operations.
 * <p> The class contains a method {@link TaskService#retrieveTasksByAssociateId(GetAssociateTasksRequest, Long, javax.persistence.EntityManager)} that takes a {@link GetAssociateTasksRequest}, an associate id and an {@link EntityManager} object as parameters
 * and returns a {@link GetAssociateTasksResponse} object that contains a list of tasks by searching for them in the database based on the associate id, start and end date.
 * <p> The class contains a method {@link TaskService#addTask(AddTaskRequest, Long, javax.persistence.EntityManager)} that takes a {@link AddTaskRequest}, an associate id and an {@link EntityManager} object as parameters
 * and returns a {@link AddTaskResponse} object that contains a message indicating that the task was added successfully.
 * <p> The class contains a method {@link TaskService#copyTasks(List)} that takes a {@link List} of {@link Task} as a parameter and return a list of {@link TaskDto}
 * that contain the task's data.
 */
public class TaskService {

    private final TaskRepository taskRepository;


    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    public void deleteTask(Long taskId, EntityManager em) {
        taskRepository.deleteTask(taskId, em);
    }

    public GetAssociateTasksResponse retrieveTasksByAssociateId(GetAssociateTasksRequest getAssociateTasksRequest, Long associateId, EntityManager em) {
        GetAssociateTasksResponse getAssociateTasksResponse = new GetAssociateTasksResponse();
        List<Task> tasks = taskRepository.retrieveTasksByAssociateId(getAssociateTasksRequest.getStartDate(), getAssociateTasksRequest.getEndDate(), associateId, em);
        if (tasks.isEmpty()) {
            getAssociateTasksResponse.setTasks(null);
            getAssociateTasksResponse.setRequestSucceeded(true);
            getAssociateTasksResponse.setMessage("Empty list: Not tasks found for the period: " + getAssociateTasksRequest.getStartDate() + " and " + getAssociateTasksRequest.getEndDate());
            getAssociateTasksResponse.setStatus(HttpStatus.SC_NO_CONTENT);
            return getAssociateTasksResponse;
        }
        List<TaskDto> taskDtos = copyTasks(tasks);
        getAssociateTasksResponse.setTasks(taskDtos);
        getAssociateTasksResponse.setMessage(getAssociateTasksResponse.getTasks().size() + " Tasks found for the period: " + getAssociateTasksRequest.getStartDate() + " and " + getAssociateTasksRequest.getEndDate());
        getAssociateTasksResponse.setRequestSucceeded(true);
        getAssociateTasksResponse.setStatus(HttpStatus.SC_OK);
        return getAssociateTasksResponse;
    }

    public AddTaskResponse addTask(AddTaskRequest addTaskRequest, Long associateId, EntityManager em) throws Exception {
        taskRepository.addTask(addTaskRequest, associateId, em);
        AddTaskResponse addTaskResponse = new AddTaskResponse();
        addTaskResponse.setStatus(HttpStatus.SC_CREATED);
        addTaskResponse.setMessage("added new Task");
        addTaskResponse.setRequestSucceeded(true);
        return addTaskResponse;
    }


    public static List<TaskDto> copyTasks(List<Task> tasks) {
        List<TaskDto> taskDtos = new ArrayList<>();
        for (Task task : tasks) {
            TaskDto taskDto = new TaskDto();
            taskDto.setEmployeeTask(task.getEmployeeTask());
            taskDto.setEmployeeDateFrom(task.getEmployeeDateFrom());
            taskDto.setEmployeeHoursSpent(task.getEmployeeHoursSpent());
            taskDtos.add(taskDto);
        }
        return taskDtos;
    }
}

    /*
    public GetAllAssociatesByManagerIdResponse GetAllTasksFromManagerAssociates(GetAllAssociatesByManagerIdRequest getManagerAssociatesTasksRequest,Long managerId, EntityManager em){
        GetAllAssociatesByManagerIdResponse getManagerAssociatesTasksResponse = new GetAllAssociatesByManagerIdResponse();
        List<Associate> associates = associateRepository.findAssociatesByManagerId(managerId,em);
        List<GetAllAssociatesByManagerIdResponse.AssociatesDto> AssociatesDto = copyAssociates(associates);
        for (GetAllAssociatesByManagerIdResponse.AssociatesDto associatesDto : AssociatesDto) {
            List<TaskDto> taskDtos = copyTasks(taskRepository.retrieveTasksByAssociateId(getManagerAssociatesTasksRequest.getStartDate(), getManagerAssociatesTasksRequest.getEndDate(), associatesDto.getAssociateId(), em));
            associatesDto.setTasks(taskDtos);
        }

        return getManagerAssociatesTasksResponse;
    }
     */




/*

    public Task saveTask(Task task) {
        return taskRepository.save(task);
    }

    public void deleteEmployee(Long id) {
        taskRepository.deleteById(id);
    }

    public List<Task> getTasks() {
        return taskRepository.findAll();
    }

    public Task getTask(Long id) {
        Optional<Task> task = taskRepository.findById(id);
        return unwrapEmployee(task, id);
    }



    public Task updateTask(Long taskId, Task updatedTask) {
        Task task = taskRepository.findById(taskId).orElse(null);
        if (task != null) {
            task.setEmployeeTask(updatedTask.getEmployeeTask());
            task.setEmployeeDateFrom(updatedTask.getEmployeeDateFrom());
            task.setEmployeeHoursSpent(updatedTask.getEmployeeHoursSpent());
            taskRepository.save(task);
            return task;
        }
        return null;
    }

    static Task unwrapEmployee(Optional<Task> entity, Long id) {
        if (entity.isPresent()) return entity.get();
        else throw new EmployeeNotFoundException(id);
    }
 */