package com.hrms.backend.repositories.TravelRepositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExpenseCategoryRepository extends JpaRepository<com.hrms.backend.entities.TravelEntities.ExpenseCategory,Long> {
}
