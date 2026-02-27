package com.hrms.backend.services.JobListingServices;

import com.hrms.backend.dtos.globalDtos.JwtInfoDto;
import com.hrms.backend.dtos.requestDto.job.CvReviewRequestDto;
import com.hrms.backend.dtos.responseDtos.job.CvReviewResponseDto;
import com.hrms.backend.dtos.responseDtos.job.CvReviewerWithNameOnlyDto;
import com.hrms.backend.dtos.responseDtos.job.JobWiseCvReviewerDto;
import com.hrms.backend.entities.EmployeeEntities.Employee;
import com.hrms.backend.entities.JobListingEntities.CvReview;
import com.hrms.backend.entities.JobListingEntities.Job;
import com.hrms.backend.entities.JobListingEntities.JobApplication;
import com.hrms.backend.entities.JobListingEntities.JobWiseCvReviewer;
import com.hrms.backend.exceptions.InvalidActionException;
import com.hrms.backend.repositories.JobListingRepositories.CvReviewRepository;
import com.hrms.backend.repositories.JobListingRepositories.JobWiseCvReviewerRepository;
import com.hrms.backend.services.EmployeeServices.EmployeeService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class JobWiseCvReviewerService {
    private final CvReviewRepository cvReviewRepository;
    private final JobWiseCvReviewerRepository jobWiseCvReviewerRepository;
    private final EmployeeService employeeService;
    private final JobApplicationService jobApplicationService;
    private final ModelMapper modelMapper;
    @Autowired
    public JobWiseCvReviewerService(JobWiseCvReviewerRepository jobWiseCvReviewerRepository, EmployeeService employeeService, CvReviewRepository cvReviewRepository, JobApplicationService jobApplicationService, ModelMapper modelMapper){
        this.jobWiseCvReviewerRepository = jobWiseCvReviewerRepository;
        this.employeeService = employeeService;
        this.cvReviewRepository = cvReviewRepository;
        this.jobApplicationService = jobApplicationService;
        this.modelMapper = modelMapper;
    }

    public JobWiseCvReviewerDto assignJobToReviewer(Long employeeId, Job job){
        JobWiseCvReviewer jobWiseCvReviewer = new JobWiseCvReviewer();
        Employee reviewer = employeeService.getReference(employeeId);
        jobWiseCvReviewer.setJob(job);
        jobWiseCvReviewer.setReviewer(reviewer);
        jobWiseCvReviewer =jobWiseCvReviewerRepository.save(jobWiseCvReviewer);
        JobWiseCvReviewerDto dto = modelMapper.map(jobWiseCvReviewer,JobWiseCvReviewerDto.class);
        return dto;
    }

    public CvReviewResponseDto reviewJobApplication(Long jobApplicationId, CvReviewRequestDto requestDto){
        JwtInfoDto jwtInfoDto = (JwtInfoDto) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        JobApplication jobApplication = jobApplicationService.getReference(jobApplicationId);
        JobWiseCvReviewer cvReviewer = jobWiseCvReviewerRepository.findByJob_IdAndReviewer_Id(jobApplication.getJob().getId(),jwtInfoDto.getUserId()).orElseThrow(()->new InvalidActionException("You are not reviewer for the job"));

        CvReview cvReview = cvReviewRepository.findByJobApplication_IdAndCvReviewer_Id(jobApplicationId,cvReviewer.getId()).orElse( new CvReview());
        cvReview.setCvReviewer(cvReviewer);
        cvReview.setReview(requestDto.getReview());
        cvReview.setJobApplication(jobApplication);
        cvReview = cvReviewRepository.save(cvReview);
        return modelMapper.map(cvReview,CvReviewResponseDto.class);
    }
}
