package com.hrms.backend.repositories.TravelRepositories;

import com.hrms.backend.entities.TravelEntities.ExpenseWiseDocument;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface ExpenseWiseDocumentRepository extends JpaRepository<ExpenseWiseDocument,Long>, JpaSpecificationExecutor<ExpenseWiseDocument> {
    int countByExpense_Travel_Id(Long travelId);

}
