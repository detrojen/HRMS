package com.hrms.backend.dtos.responseDtos.employee;

import com.hrms.backend.exceptions.ItemNotFoundExpection;
import lombok.Data;

import java.util.List;


@Data
public class EmployeeOneLevelReportResponseDto {
    @Data
    private class EmployeeWithManager{
        private Long id;
        private String firstName;
        private String lastName;
        private String designation;
        private EmployeeWithManager manager;

        public EmployeeWithManager(List<EmployeeWithManagerIdDto> employees, Long currentEmployeeId){
            EmployeeWithManagerIdDto current = employees.stream().filter(e->e.getId().equals(currentEmployeeId)).findFirst().orElseThrow(()->new ItemNotFoundExpection("employee not found"));
            this.id = current.getId();
            this.firstName = current.getFirstName();
            this.lastName = current.getLastName();
            this.designation = current.getDesignation();
            if(current.getManagerId() == null || current.getManagerId() == 0) {
                this.manager = null;
            }else{
                this.manager = new EmployeeWithManager(employees, current.getManagerId());
            }
        }
    }
    private List<EmployeeWithManagerIdDto> oneLevelDown;
    private Long id;
    private String firstName;
    private String lastName;
    private String designation;
    private EmployeeWithManager manager;

    public EmployeeOneLevelReportResponseDto(List<EmployeeWithManagerIdDto> employees, Long startingId){
        oneLevelDown = employees.stream().filter(e->e.getManagerId()!=null &&e.getManagerId().equals(startingId)).map(e->new EmployeeWithManagerIdDto(e.getId(),e.getFirstName(),e.getLastName(), e.getDesignation(), e.getManagerId())).toList();
        EmployeeWithManagerIdDto current = employees.stream().filter(e->e.getId().equals(startingId)).findFirst().orElseThrow(()->new ItemNotFoundExpection(""));
        id = current.getId();
        firstName = current.getFirstName();
        lastName = current.getLastName();
        designation = current.getDesignation();
        if(current.getManagerId() == null || current.getManagerId() == 0){
            manager = null;
        }else{
            manager = new EmployeeWithManager(employees, current.getManagerId());
        }
    }
}


