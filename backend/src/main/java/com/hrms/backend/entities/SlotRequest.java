package com.hrms.backend.entities;

import jakarta.persistence.*;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.util.Collection;
import java.util.Date;

@Entity
@Data
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
    private Collection<SlotRequestWiseEmployee> slotRequestWiseEmployee;

    public Collection<SlotRequestWiseEmployee> getSlotRequestWiseEmployee() {
        return slotRequestWiseEmployee;
    }

    public void setSlotRequestWiseEmployee(Collection<SlotRequestWiseEmployee> slotRequestWiseEmployee) {
        this.slotRequestWiseEmployee = slotRequestWiseEmployee;
    }
}
