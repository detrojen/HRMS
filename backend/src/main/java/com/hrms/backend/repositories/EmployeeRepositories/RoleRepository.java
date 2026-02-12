package com.hrms.backend.repositories.EmployeeRepositories;

import com.hrms.backend.entities.EmployeeEntities.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role,Long> {
}
