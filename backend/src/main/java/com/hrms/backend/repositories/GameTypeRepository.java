package com.hrms.backend.repositories;

import com.hrms.backend.entities.GameType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface GameTypeRepository extends JpaRepository<GameType,Long>, JpaSpecificationExecutor<GameType> {
}
