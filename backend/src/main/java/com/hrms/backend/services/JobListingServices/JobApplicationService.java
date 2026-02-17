package com.hrms.backend.services.JobListingServices;

import com.hrms.backend.dtos.globalDtos.JwtInfoDto;
import com.hrms.backend.dtos.requestDto.job.ReferJobRequestDto;
import com.hrms.backend.emailTemplates.JobEmailTemplates;
import com.hrms.backend.entities.EmployeeEntities.Employee;
import com.hrms.backend.entities.JobListingEntities.Job;
import com.hrms.backend.entities.JobListingEntities.JobApplication;
import com.hrms.backend.repositories.JobListingRepositories.JobApplicationRepository;
import com.hrms.backend.services.EmailServices.EmailService;
import com.hrms.backend.services.EmployeeServices.EmployeeService;
import jakarta.mail.MessagingException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.io.FileNotFoundException;
import java.net.MalformedURLException;
import java.util.stream.Collectors;

@Service
public class JobApplicationService {
    private final JobApplicationRepository jobApplicationRepository;
    private final JobService jobService;
    private final ModelMapper modelMapper;
    private final EmployeeService employeeService;
    private final EmailService emailService;
    @Autowired
    public JobApplicationService(JobApplicationRepository jobApplicationRepository,JobService jobService, ModelMapper modelMapper, EmployeeService employeeService, EmailService emailService){
        this.jobService = jobService;
        this.jobApplicationRepository = jobApplicationRepository;
        this.modelMapper = modelMapper;
        this.employeeService = employeeService;
        this.emailService = emailService;
    }

    public JobApplication referJobTo(Long jobId,ReferJobRequestDto requestDto, String cvPath) throws MalformedURLException, MessagingException, FileNotFoundException {
        JobApplication jobApplication = modelMapper.map(requestDto,JobApplication.class);
        JwtInfoDto jwtinfo = (JwtInfoDto) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Job job = jobService.getRef(jobId);
        jobApplication.setJob(job);
        Employee referdBy = employeeService.getReference(jwtinfo.getUserId());
        jobApplication.setReferedBy(referdBy);
        jobApplication.setCvPath(cvPath);
        jobApplication = jobApplicationRepository.save(jobApplication);
        String emailBody = JobEmailTemplates.referJob(jobApplication);
        emailService.refferJob(
                "Refering for job " + jobApplication.getJob().getTitle(),
                new String[]{jobApplication.getJob().getHrOwner().getEmail()},
                jobApplication.getJob().getCvReviewers().stream().map(reviewer->reviewer.getReviewer().getEmail()).collect(Collectors.toUnmodifiableList()).toArray(new String[]{}),
                emailBody,jobApplication.getCvPath()
        );
        return jobApplication;
    }
}
