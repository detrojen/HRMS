package com.hrms.backend.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.util.Collection;
import java.util.Date;
import java.util.List;

@Entity
@Data
@NamedStoredProcedureQuery(name ="getActiveRequestCount", procedureName = "sp_activeRequestCount",parameters = {
        @StoredProcedureParameter(name = "employeeId", type = Long.class)
})
public class SlotRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String status;

    @ManyToOne()
    private Employee requestedBy;

    @ManyToOne()
    private GameSlot gameSlot;

    @CreatedDate
    private Date createdAt;
    @LastModifiedDate
    private  Date updatedAt;
    @OneToMany(mappedBy = "slotRequest")
    @JsonIgnore
    private List<SlotRequestWiseEmployee> slotRequestWiseEmployee;

}
