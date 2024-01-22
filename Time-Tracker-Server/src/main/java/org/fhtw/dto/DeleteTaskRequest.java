package org.fhtw.dto;

public class DeleteTaskRequest {
    private Long taskId;

    public DeleteTaskRequest() {
    }

    public DeleteTaskRequest(Long taskId) {
        this.taskId = taskId;
    }

    public Long getTaskId() {
        return taskId;
    }

    public void setTaskId(Long taskId) {
        this.taskId = taskId;
    }
}
