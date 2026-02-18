package com.hrms.backend.dtos.responseDtos.employee;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class EmployeeMinDetailsDto {
    private  Long id;
    private  String firstName;
    private  String lastName;
    private String email;
    private String designation;
    public EmployeeMinDetailsDto(Long id, String firstName, String lastName, String email, String designation){
        this.id= id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.designation = designation;
    }
}
