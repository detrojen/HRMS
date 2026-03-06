package com.hrms.backend.repositories.TravelRepositories;

import com.hrms.backend.entities.TravelEntities.TravelWiseEmployeeWiseDocument;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;

public interface TravelWiseEmployeeWiseDocumentRepository extends JpaRepository<TravelWiseEmployeeWiseDocument, Long>, JpaSpecificationExecutor<TravelWiseEmployeeWiseDocument> {
    Optional<TravelWiseEmployeeWiseDocument> getByIdAndUploadedBy_Id(Long documentId,Long employeeId);
    int countByTravel_id(Long travelId);

}
