package com.hrms.backend.repositories.TravelRepositories;

import com.hrms.backend.entities.TravelEntities.Travel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.Optional;

public interface TravelRepository extends JpaRepository<Travel,Long>, JpaSpecificationExecutor<Travel> {
    boolean existsByIdAndTravelWiseEmployees_Employee_Manager_IdOrTravelWiseEmployees_Employee_Id(Long travelId,Long managerId, Long employeeId);
    Optional<Travel> getByIdAndTravelDocuments_Id(Long travelId, Long documentId);

    @Modifying
    @Query("update Travel t set t.status='STARTED' where t.status = 'INITIATED' and t.startDate = FUNCTION('FORMAT', function('GETDATE'), 'yyyy-MM-dd')")
    int updateTravelStatusToStart( );
    @Modifying
    @Query("update Travel t set t.status='ENDED' where t.status ='INITIATED' and t.endDate = FUNCTION('FORMAT', FUNCTION('DATEADD',day, -1,function('GETDATE')), 'yyyy-MM-dd')")
    int updateTravelStatusToEnd( );
}
