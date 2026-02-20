package com.hrms.backend.repositories.GameSchedulingRepositories;

import com.hrms.backend.entities.GameSchedulingEntities.GameSlot;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Repository
public interface GameSlotRepository extends JpaRepository<GameSlot,Long>, JpaSpecificationExecutor<GameSlot> {

    List<GameSlot> findAllByGameType_IdAndSlotDateIs(Long gameTypeId, LocalDate slotDate);
    @Query(value = "execute sp_createSlotsOfAllGameType",nativeQuery = true)
    void createGameSlots();
}
