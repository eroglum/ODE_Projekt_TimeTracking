/**
 * The AssociateDto class is used to encapsulate the data of an {@link Associate} object
 * in a format that can be easily serialized to xml and sent over the network.
 */
package com.fhtw.taskmanagerclient.model.dto;

//import org.fhtw.entity.Associate;

import java.util.List;

/**
 * DTO (data transfer object) class for {@link }
 * <p>
 * It is used for transferring data between layers and over the network.
 */
public class AssociateDto {
    private String associateName;
    private Long associateId;
    private List<TaskDto> tasks;

    /**
     * No argument constructor
     */
    public AssociateDto() {
    }

    /**
     * Constructor with fields
     *
     * @param associateId   ID of the associate
     * @param associateName name of the associate
     * @param tasks         list of tasks performed by associate
     */
    public AssociateDto(Long associateId, String associateName, List<TaskDto> tasks) {
        this.associateId = associateId;
        this.associateName = associateName;
        this.tasks = tasks;
    }

    /**
     * Getter for associateName
     *
     * @return associateName
     */
    public String getAssociateName() {
        return associateName;
    }

    /**
     * Setter for associateName
     *
     * @param associateName new value for associateName
     */
    public void setAssociateName(String associateName) {
        this.associateName = associateName;
    }

    /**
     * Getter for associateId
     *
     * @return associateId
     */
    public Long getAssociateId() {
        return associateId;
    }

    /**
     * Setter for associateId
     *
     * @param associateId new value for associateId
     */
    public void setAssociateId(Long associateId) {
        this.associateId = associateId;
    }

    /**
     * Getter for tasks
     *
     * @return tasks
     */
    public List<TaskDto> getTasks() {
        return tasks;
    }

    /**
     * Setter for tasks
     *
     * @param tasks new value for tasks
     */
    public void setTasks(List<TaskDto> tasks) {
        this.tasks = tasks;
    }

    @Override
    public String toString() {
        return "AssociateDto{" +
                "associateName='" + associateName + '\'' +
                ", associateId=" + associateId +
                ", tasks=" + tasks +
                '}';
    }
}
