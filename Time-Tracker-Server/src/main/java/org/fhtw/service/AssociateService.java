package org.fhtw.service;


import org.apache.http.HttpStatus;
import org.fhtw.dto.*;
import org.fhtw.entity.Associate;
import org.fhtw.repository.AssociateRepository;


import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;

/**
 * The {@link AssociateService} class provides methods for performing operations on associates, such as searching for associates by manager id.
 * It uses an {@link AssociateRepository} object to interact with the repository layer and perform database operations.
 * <p> The class contains a single constructor  that takes a {@link AssociateRepository} object as a parameter
 * and sets it to the {@link AssociateService#associateRepository} field. This repository object is used to perform database operations.
 * <p> The class contains a method {@link AssociateService#findAssociateById(Long, javax.persistence.EntityManager)} that takes an associate id and an {@link EntityManager} object as parameters
 * and returns an {@link Associate} object by searching for it in the database.
 * <p> The class contains a method {@link AssociateService#GetAllAssociatesByManagerId(GetAllAssociatesByManagerIdRequest, Long, javax.persistence.EntityManager)} that takes a {@link GetAllAssociatesByManagerIdRequest}, an manager id and an {@link EntityManager} object as parameters
 * and returns a {@link GetAllAssociatesByManagerIdResponse} object that contains a list of associates by searching for them in the database based on the manager id.
 * <p> The class contains a method {@link AssociateService#copyAssociates(List)}  that takes a {@link List} of {@link Associate} as a parameter and return a list of {@link AssociateDto}
 * that contain the associate's data.
 */

public class AssociateService {
    private final AssociateRepository associateRepository;


    public AssociateService(AssociateRepository associateRepository) {
        this.associateRepository = associateRepository;

    }

    public Associate findAssociateById(Long id, EntityManager em) {
        return associateRepository.findAssociateById(id, em);
    }

    public GetAllAssociatesByManagerIdResponse GetAllAssociatesByManagerId(GetAllAssociatesByManagerIdRequest getAllAssociatesByManagerIdRequest, Long managerId, EntityManager em)  {
        GetAllAssociatesByManagerIdResponse getAllAssociatesByManagerIdResponse = new GetAllAssociatesByManagerIdResponse();
        List<Associate> associates = associateRepository.findAssociatesByManagerId(managerId, em);
        if (associates.isEmpty()) {
            getAllAssociatesByManagerIdResponse.setRequestSucceeded(true);
            getAllAssociatesByManagerIdResponse.setStatus(HttpStatus.SC_NO_CONTENT);
            getAllAssociatesByManagerIdResponse.setMessage("Empty list: This manager does not have any associates");
            return getAllAssociatesByManagerIdResponse;
        }
        List<AssociateDto> associateDtos = copyAssociates(associates);
        getAllAssociatesByManagerIdResponse.setAssociates(associateDtos);
        System.out.println(associateDtos);
        getAllAssociatesByManagerIdResponse.setRequestSucceeded(true);
        getAllAssociatesByManagerIdResponse.setStatus(HttpStatus.SC_OK);
        getAllAssociatesByManagerIdResponse.setMessage("returned all tasks from the manager " + associateDtos.size() + " associates");
        return getAllAssociatesByManagerIdResponse;
    }

    /**
     * This method is used to convert the List of {@link Associate} entities to a List of {@link AssociateDto}
     *
     * @param associates List of associate entities
     * @return List of associate DTOs
     */
    public static List<AssociateDto> copyAssociates(List<Associate> associates) {
        List<AssociateDto> associateDtos = new ArrayList<>();
        for (Associate associate : associates) {
            AssociateDto associateDto = new AssociateDto();
            associateDto.setAssociateName(associate.getEmployeeName());
            associateDto.setTasks(TaskService.copyTasks(associate.getTasks()));
            associateDto.setAssociateId(associate.getEmployeeId());
            associateDtos.add(associateDto);
        }
        return associateDtos;
    }

}
