package org.fhtw.repository;

import org.fhtw.dto.AddTaskRequest;
import org.fhtw.entity.Associate;
import org.fhtw.entity.Task;
import org.fhtw.exception.AssociateNotFoundException;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;

public class TaskRepositoryImpl implements TaskRepository {
    @Override
    public List<Task> retrieveTasksByAssociateId(String startDate, String endDate, Long associateId, EntityManager em) {
        String query;
        if (endDate == null) {
            query = "SELECT t FROM Task t " +
                    "WHERE t.associate.employeeId = :employeeId " +
                    "AND t.employeeDateFrom = :startDate";
            TypedQuery<Task> taskQuery = em.createQuery(query, Task.class);
            taskQuery.setParameter("employeeId", associateId);
            taskQuery.setParameter("startDate", startDate);
            return taskQuery.getResultList();
        } else {
            query = "SELECT t FROM Task t " +
                    "WHERE t.associate.employeeId = :employeeId " +
                    "AND t.employeeDateFrom BETWEEN :startDate AND :endDate";
            TypedQuery<Task> taskQuery = em.createQuery(query, Task.class);
            taskQuery.setParameter("employeeId", associateId);
            taskQuery.setParameter("startDate", startDate);
            taskQuery.setParameter("endDate", endDate);
            return taskQuery.getResultList();
        }
    }

    @Override
    public void addTask(AddTaskRequest addTaskRequest, Long associateId, EntityManager em) throws Exception {
        Associate associateToFind = em.find(Associate.class, associateId);
        if (associateToFind == null) {
            throw new AssociateNotFoundException(associateId);
        }
        Task taskNew = new Task();
        taskNew.setEmployeeTask(addTaskRequest.getEmployeeTask());
        taskNew.setEmployeeDateFrom(addTaskRequest.getEmployeeDateFrom());
        taskNew.setEmployeeHoursSpent(addTaskRequest.getEmployeeHoursSpent());
        taskNew.setEmployee(associateToFind);
        try {
            em.getTransaction().begin();
            em.persist(taskNew);
            em.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
            em.getTransaction().rollback();
            throw new Exception(e);

        }
    }
    public void deleteTask(Long taskId, EntityManager em) {
        Task task = em.find(Task.class, taskId);
        if (task != null) {
            em.getTransaction().begin();
            em.remove(task);
            em.getTransaction().commit();
        }
    }

}
