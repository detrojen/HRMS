package com.hrms.backend.repositories.GameSchedulingRepositories;

import com.hrms.backend.entities.GameSchedulingEntities.SlotRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface SlotRequestRepository extends JpaRepository<SlotRequest,Long>, JpaSpecificationExecutor<SlotRequest> {
    @Procedure(name = "getActiveRequestCount")
    int getActiveRequestCount(Long employeeId, Long gameTypeId);

    @Query(value = "select count(*) from SlotRequest join GameSlot on GameSlot.id = SlotRequest.gameSlot_id where GameSlot.gameType_id = :gameTypeId and requestedBy_id = :employeeId and SlotRequest.status = 'Confirm' and FORMAT(slotDate, 'yyyy-MM-dd') = FORMAT(GETDATE(),'yyyy-MM-dd')", nativeQuery = true)
    int getTodaysConsumedSlotCount(Long employeeId, Long gameTypeId);

    SlotRequest findByGameSlot_IdAndStatus(Long slotId, String status);

    List<SlotRequest> findAllByGameSlot_IdAndStatusOrderByCreatedAt(Long slotId,String status);

    @Query(value = "select top 1 SlotRequest.* from SlotRequest join EmployeeWiseGameInterest on EmployeeWiseGameInterest.employee_id = SlotRequest.requestedBy_id where gameSlot_id = :slotId and gameType_id = :gameTypeId and SlotRequest.status = 'On hold' order by SlotRequest.createdAt asc, EmployeeWiseGameInterest.currentCyclesSlotConsumed asc", nativeQuery = true)
    SlotRequest getTopCandidate(Long slotId, Long gameTypeId);

}
