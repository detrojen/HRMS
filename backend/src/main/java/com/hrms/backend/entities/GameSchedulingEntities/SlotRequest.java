package com.hrms.backend.entities.GameSchedulingEntities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.hrms.backend.entities.EmployeeEntities.Employee;
import jakarta.persistence.*;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Entity
@Data
@NamedStoredProcedureQuery(name ="getActiveRequestCount", procedureName = "sp_activeRequestCount",parameters = {
        @StoredProcedureParameter(name = "employeeId", type = Long.class),
        @StoredProcedureParameter(name = "gameTypeId", type = Long.class),
        @StoredProcedureParameter(name = "reqDate", type = LocalDate.class)
})
@EntityListeners(AuditingEntityListener.class)
public class SlotRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String status;

    @ManyToOne()
//    @JsonIgnore
    private Employee requestedBy;

    @ManyToOne()
    @JsonIgnore
    private GameSlot gameSlot;

    @CreatedDate
    private Date createdAt;
    @LastModifiedDate
    private  Date updatedAt;
    @OneToMany(mappedBy = "slotRequest")
//    @JsonIgnore
    private List<SlotRequestWiseEmployee> slotRequestWiseEmployee;

}
