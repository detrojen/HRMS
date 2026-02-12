package com.hrms.backend.repositories.GameSchedulingRepositories;

import com.hrms.backend.entities.GameSchedulingEntities.GameType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface GameTypeRepository extends JpaRepository<GameType,Long>, JpaSpecificationExecutor<GameType> {
}
