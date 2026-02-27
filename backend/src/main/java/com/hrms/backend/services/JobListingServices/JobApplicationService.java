package com.hrms.backend.services.JobListingServices;

import com.hrms.backend.dtos.globalDtos.JwtInfoDto;
import com.hrms.backend.dtos.globalDtos.PageableDto;
import com.hrms.backend.dtos.requestDto.job.ReferJobRequestDto;
import com.hrms.backend.dtos.requestDto.job.ReviewJobApplicationRequestDto;
import com.hrms.backend.dtos.responseDtos.job.JobApplicationResponseDto;
import com.hrms.backend.emailTemplates.JobEmailTemplates;
import com.hrms.backend.entities.EmployeeEntities.Employee;
import com.hrms.backend.entities.JobListingEntities.Job;
import com.hrms.backend.entities.JobListingEntities.JobApplication;
import com.hrms.backend.exceptions.InvalidActionException;
import com.hrms.backend.exceptions.ItemNotFoundExpection;
import com.hrms.backend.repositories.JobListingRepositories.JobApplicationRepository;
import com.hrms.backend.services.EmailServices.EmailService;
import com.hrms.backend.services.EmployeeServices.EmployeeService;
import com.hrms.backend.specs.JobApplicationSpecs;
import jakarta.mail.MessagingException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.io.FileNotFoundException;
import java.net.MalformedURLException;
import java.util.stream.Collectors;

@Service
public class JobApplicationService {
    private final JobApplicationRepository jobApplicationRepository;
    private final ModelMapper modelMapper;
    private final EmployeeService employeeService;
    private final EmailService emailService;
    @Autowired
    public JobApplicationService(JobApplicationRepository jobApplicationRepository, ModelMapper modelMapper, EmployeeService employeeService, EmailService emailService){
        this.jobApplicationRepository = jobApplicationRepository;
        this.modelMapper = modelMapper;
        this.employeeService = employeeService;
        this.emailService = emailService;
    }

    public JobApplication referJobTo(Job job,ReferJobRequestDto requestDto, String cvPath) throws MalformedURLException, MessagingException, FileNotFoundException {
        JobApplication jobApplication = modelMapper.map(requestDto,JobApplication.class);
        JwtInfoDto jwtinfo = (JwtInfoDto) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
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

    public Page<JobApplicationResponseDto> getJobApplications(PageableDto pageParams){
        JwtInfoDto jwtInfoDto = (JwtInfoDto) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Pageable pageable = PageRequest.of(pageParams.getPageNumber(),pageParams.getLimit(), Sort.by("createdAt").descending());
        Page<JobApplication> jobApplications;
        if(jwtInfoDto.getRoleTitle().equals("HR")){
            jobApplications = jobApplicationRepository.findAll(pageable);
        }else{
            Specification<JobApplication> specs = JobApplicationSpecs.hasAssignedToReview(jwtInfoDto.getUserId());
            jobApplications = jobApplicationRepository.findAll(specs,pageable);

        }
        return jobApplications.map(application->modelMapper.map(application, JobApplicationResponseDto.class));
    }

    public Page<JobApplicationResponseDto> getReferedJobApplications(PageableDto pageParams){
        JwtInfoDto jwtInfoDto = (JwtInfoDto) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Pageable pageable = PageRequest.of(pageParams.getPageNumber(),pageParams.getLimit(), Sort.by("createdAt").descending());

        Specification<JobApplication> specs = JobApplicationSpecs.hasRefered(jwtInfoDto.getUserId());
        Page<JobApplication> jobApplications = jobApplicationRepository.findAll(specs,pageable);

        return jobApplications.map(application-> {
            var model = modelMapper.map(application, JobApplicationResponseDto.class);
            model.setCvReviews(null);
            model.getJob().setReviewers(null);
            return model;
        });
    }

    public JobApplicationResponseDto getJobApplicationById(Long jobApplicationId){
        Specification<JobApplication> specs = JobApplicationSpecs.hasId(jobApplicationId);
        JwtInfoDto jwtInfoDto = (JwtInfoDto) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(!jwtInfoDto.getRoleTitle().equals("HR")){
            specs =specs.and(JobApplicationSpecs.hasAssignedToReview(jwtInfoDto.getUserId()));
        }
        JobApplication application = jobApplicationRepository.findBy(specs,q->q.first()).orElseThrow(()->new InvalidActionException("Either job not found or you have no access to see details"));//.orElseThrow(()->new ItemNotFoundExpection("Job not found"));
        if(application.getStatus().equals("pending")){
            application.setStatus("in review");
            jobApplicationRepository.save(application);
        }
        application.getCvReviews();
        return modelMapper.map(application,JobApplicationResponseDto.class);
    }

    public JobApplicationResponseDto reviewJobApplication(Long jobApplicationId , ReviewJobApplicationRequestDto review){
        JobApplication application = jobApplicationRepository.findById(jobApplicationId).orElseThrow(()->new ItemNotFoundExpection("Job not found"));
        JwtInfoDto jwtinfo = (JwtInfoDto) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Employee reviewedBy = employeeService.getReference(jwtinfo.getUserId());
        application.setStatus(review.getStatus());
        application.setRemark(review.getStatus());
        application.setReviewedBy(reviewedBy);
        jobApplicationRepository.save(application);
        return modelMapper.map(application, JobApplicationResponseDto.class);
    }
    public JobApplication getReference(Long jobApplicationId){
        return jobApplicationRepository.getReferenceById(jobApplicationId);
    }

}
