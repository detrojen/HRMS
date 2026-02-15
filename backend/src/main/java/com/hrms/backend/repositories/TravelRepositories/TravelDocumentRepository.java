package com.hrms.backend.repositories.TravelRepositories;

import com.hrms.backend.entities.TravelEntities.TravelDocument;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TravelDocumentRepository extends JpaRepository<TravelDocument,Long> {
}
