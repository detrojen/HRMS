package com.hrms.backend.services.JobListingServices;

import com.hrms.backend.dtos.responseDtos.job.CvReviewerWithNameOnlyDto;
import com.hrms.backend.entities.EmployeeEntities.Employee;
import com.hrms.backend.entities.JobListingEntities.Job;
import com.hrms.backend.entities.JobListingEntities.JobWiseCvReviewer;
import com.hrms.backend.repositories.JobListingRepositories.JobWiseCvReviewerRepository;
import com.hrms.backend.services.EmployeeServices.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class JobWiseCvReviewerService {
    private final JobWiseCvReviewerRepository jobWiseCvReviewerRepository;
    private final EmployeeService employeeService;
    @Autowired
    public JobWiseCvReviewerService(JobWiseCvReviewerRepository jobWiseCvReviewerRepository, EmployeeService employeeService){
        this.jobWiseCvReviewerRepository = jobWiseCvReviewerRepository;
        this.employeeService = employeeService;
    }

    public CvReviewerWithNameOnlyDto assignJobToReviewer(Long employeeId, Job job){
        JobWiseCvReviewer jobWiseCvReviewer = new JobWiseCvReviewer();
        Employee reviewer = employeeService.getReference(employeeId);
        jobWiseCvReviewer.setJob(job);
        jobWiseCvReviewer.setReviewer(reviewer);
        jobWiseCvReviewer =jobWiseCvReviewerRepository.save(jobWiseCvReviewer);
        CvReviewerWithNameOnlyDto dto = new CvReviewerWithNameOnlyDto(jobWiseCvReviewer.getId(), reviewer.getFirstName(), reviewer.getLastName());
        return dto;
    }
}
