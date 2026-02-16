package com.hrms.backend.repositories.TravelRepositories;

import com.hrms.backend.entities.TravelEntities.TravelWiseEmployeeWiseDocument;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface TravelWiseEmployeeWiseDocumentRepository extends JpaRepository<TravelWiseEmployeeWiseDocument, Long>, JpaSpecificationExecutor<TravelWiseEmployeeWiseDocument> {
}
