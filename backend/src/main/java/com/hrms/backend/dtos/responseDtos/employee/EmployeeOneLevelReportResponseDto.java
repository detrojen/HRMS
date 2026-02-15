package com.hrms.backend.dtos.responseDtos.employee;

import lombok.Data;

import java.util.List;
import java.util.stream.Collectors;

@Data
public class EmployeeOneLevelReportResponseDto {
    @Data
    private class EmployeeWithManager{
        private Long id;
        private String firstName;
        private EmployeeWithManager manager;

        public EmployeeWithManager(List<EmployeeWithManagerIdDto> employees, Long currentEmployeeId){
            EmployeeWithManagerIdDto current = employees.stream().filter(e->e.getId() == currentEmployeeId).findFirst().orElseThrow(()->new RuntimeException());
            this.id = current.getId();
            this.firstName = current.getFirstName();
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
    private EmployeeWithManager manager;

    public EmployeeOneLevelReportResponseDto(List<EmployeeWithManagerIdDto> employees, Long startingId){
        oneLevelDown = employees.stream().filter(e->e.getManagerId()==startingId).map(e->new EmployeeWithManagerIdDto(e.getId(),e.getFirstName(),e.getManagerId())).collect(Collectors.toUnmodifiableList());
        EmployeeWithManagerIdDto current = employees.stream().filter(e->e.getId() == startingId).findFirst().orElseThrow(()->new RuntimeException());
        id = current.getId();
        firstName = current.getFirstName();
        if(current.getManagerId() == null || current.getManagerId() == 0){
            manager = null;
        }else{
            manager = new EmployeeWithManager(employees, current.getManagerId());
        }
    }
}


