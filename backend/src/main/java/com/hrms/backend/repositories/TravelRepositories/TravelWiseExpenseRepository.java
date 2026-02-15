package com.hrms.backend.repositories.TravelRepositories;

import com.hrms.backend.entities.TravelEntities.TravelWiseExpense;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TravelWiseExpenseRepository extends JpaRepository<TravelWiseExpense , Long> {
}
