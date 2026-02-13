package com.hrms.backend.services.JobListingServices;

import com.hrms.backend.dtos.requestDto.CreateJobRequestDto;
import com.hrms.backend.dtos.globalDtos.PageableDto;
import com.hrms.backend.dtos.requestDto.ShareJobRequestDto;
import com.hrms.backend.dtos.responseDtos.CreateJobResponseDto;
import com.hrms.backend.dtos.responseDtos.CvReviewerWithNameOnlyDto;
import com.hrms.backend.emailTemplates.JobEmailTemplates;
import com.hrms.backend.entities.EmployeeEntities.Employee;
import com.hrms.backend.entities.JobListingEntities.Job;
import com.hrms.backend.repositories.JobListingRepositories.JobRepository;
import com.hrms.backend.services.EmailServices.EmailService;
import com.hrms.backend.services.EmployeeServices.EmployeeService;
import com.hrms.backend.specs.JobSpecs;
import com.hrms.backend.utils.EmailUtility;
import jakarta.mail.MessagingException;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.net.MalformedURLException;
import java.util.Arrays;
import java.util.stream.Collectors;

@Service
public class JobService {
    private final JobRepository jobRepository;
    private final ModelMapper modelMapper;
    private final EmployeeService employeeService;
    private final JobWiseCvReviewerService jobWiseCvReviewerService;
    private final EmailService emailService;
    @Autowired
    public JobService(JobRepository jobRepository, ModelMapper modelMapper, EmployeeService employeeService,JobWiseCvReviewerService jobWiseCvReviewerService, EmailService emailService){
        this.jobRepository = jobRepository;
        this.modelMapper = modelMapper;
        this.employeeService = employeeService;
        this.jobWiseCvReviewerService = jobWiseCvReviewerService;
        this.emailService = emailService;
    }

    @Transactional
    public CreateJobResponseDto createJob(CreateJobRequestDto requestDto,String jdPath){
        Job job = modelMapper.map(requestDto,Job.class);
        job.setStatus("Hiring");
        job.setSkills( Arrays.stream(requestDto.getSkills()).reduce((val,next)->val + "," +next).orElseThrow(()->new RuntimeException("server error while concating skills")));
        job.setJdPath(jdPath);
        Employee hrOwner = employeeService.getReference(requestDto.getHrOwnerId());
        job.setHrOwner(hrOwner);
        Job savedJob = jobRepository.save(job);
        CreateJobResponseDto jobResponseDto = modelMapper.map(savedJob,CreateJobResponseDto.class);
        CvReviewerWithNameOnlyDto[] reviewers = Arrays.stream(requestDto.getReviewerIds()).map(reviewerId->jobWiseCvReviewerService.assignJobToReviewer(reviewerId, savedJob)).collect(Collectors.toUnmodifiableList()).toArray(new CvReviewerWithNameOnlyDto[]{});
        jobResponseDto.setReviewers(reviewers);
        return jobResponseDto;
    }

    public Page<CreateJobResponseDto> getJobList(PageableDto pageParams) {
        Specification<Job> jobByStatusSpec = JobSpecs.getJobByStatus("Hiring");
        Pageable pageable = PageRequest.of(pageParams.getPageNumber(),pageParams.getLimit());
//        Pag<Job> jobs = jobRepository.findAll()
        Page<Job> jobs = jobRepository.findAll(jobByStatusSpec,pageable);
        return jobs.map(job->modelMapper.map(job,CreateJobResponseDto.class));
    }

    public Job getRef(Long id){
        return jobRepository.getReferenceById(id);
    }

    public CreateJobResponseDto getJobById(Long id){
        Job job = jobRepository.findById(id).orElseThrow(()->new RuntimeException("job not found"));
        return modelMapper.map(job,CreateJobResponseDto.class);
    }

    public void shareJob(Long jobId, ShareJobRequestDto requestDto) throws MessagingException, MalformedURLException {
        Job job = jobRepository.findById(jobId).orElseThrow(()->new RuntimeException("job not found"));
        String emailBody = JobEmailTemplates.shareJob(job);
        System.out.println(emailBody);
        emailService.shareJob("Job opening",requestDto.getEmail(),emailBody, job.getJdPath());
    }
}
