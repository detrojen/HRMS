package com.hrms.backend.repositories;

import com.hrms.backend.dtos.responseDtos.GameSlotResponseDto;
import com.hrms.backend.entities.GameSlot;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface GameSlotRepository extends JpaRepository<GameSlot,Long>, JpaSpecificationExecutor<GameSlot> {

    List<GameSlot> findAllByGameType_IdAndSlotDateIs(Long gameTypeId,Date slotDate);
}
