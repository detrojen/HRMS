package com.hrms.backend.repositories.TravelRepositories;

import com.hrms.backend.entities.TravelEntities.TravelDocument;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TravelDocumentRepository extends JpaRepository<TravelDocument,Long> {
    Optional<TravelDocument> getByIdAndUploadedBy_Id(Long documentId, Long employeeId);
}
