package com.hrms.backend.repositories.GameSchedulingRepositories;

import com.hrms.backend.entities.GameSchedulingEntities.SlotRequestWiseEmployee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface SlotRequestWiseEmployeeRepository extends JpaRepository<SlotRequestWiseEmployee,Long>, JpaSpecificationExecutor<SlotRequestWiseEmployee> {
}
