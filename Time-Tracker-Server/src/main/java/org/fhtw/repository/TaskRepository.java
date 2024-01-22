package org.fhtw.repository;

import org.fhtw.dto.AddTaskRequest;
import org.fhtw.entity.Task;

import javax.persistence.EntityManager;
import java.util.List;


public interface TaskRepository {
    List<Task> retrieveTasksByAssociateId(String startDate,String endDate, Long associateId, EntityManager em) ;

    void addTask(AddTaskRequest addTaskRequest, Long associateId, EntityManager em) throws Exception;

    void deleteTask(Long taskId, EntityManager em);
}
