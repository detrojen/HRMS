package com.hrms.backend.controllers;

import com.hrms.backend.dtos.globalDtos.PageableDto;
import com.hrms.backend.dtos.requestDto.job.*;
import com.hrms.backend.dtos.responseDtos.job.CreateJobResponseDto;
import com.hrms.backend.dtos.responseDtos.GlobalResponseDto;
import com.hrms.backend.dtos.responseDtos.job.CvReviewResponseDto;
import com.hrms.backend.dtos.responseDtos.job.JobApplicationResponseDto;
import com.hrms.backend.services.JobListingServices.JobApplicationService;
import com.hrms.backend.services.JobListingServices.JobService;
import com.hrms.backend.services.JobListingServices.JobWiseCvReviewerService;
import com.hrms.backend.utils.FileUtility;
import jakarta.mail.MessagingException;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.net.MalformedURLException;

@RestController
public class JobController {
    private final JobService jobService;
    private final JobApplicationService jobApplicationService;
    private final JobWiseCvReviewerService jobWiseCvReviewerService;
    @Autowired
    public JobController(JobService jobService, JobApplicationService jobApplicationService, JobWiseCvReviewerService jobWiseCvReviewerService){
        this.jobService = jobService;
        this.jobApplicationService = jobApplicationService;
        this.jobWiseCvReviewerService = jobWiseCvReviewerService;
    }

    @PostMapping(value = "/jobs")
    public ResponseEntity<GlobalResponseDto<CreateJobResponseDto>> createJob(@RequestPart(value = "jobDocument") MultipartFile jobDocument , @RequestPart(value = "jobDetail") @Valid CreateJobRequestDto jobDetail ){
        String jdPath = FileUtility.Save(jobDocument, "jds");
        CreateJobResponseDto responseDto = jobService.createJob(jobDetail,jdPath);
        return  ResponseEntity.ok().body(
                new GlobalResponseDto<>(responseDto)
        );
    }

    @GetMapping("/jobs")
    public ResponseEntity<GlobalResponseDto<Page<CreateJobResponseDto>>> jobList(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int limit
            ){
        PageableDto pageParams = new PageableDto(page,limit);
        Page<CreateJobResponseDto> jobs = jobService.getJobList(pageParams);
        return ResponseEntity.ok().body(
                new GlobalResponseDto<>(jobs)
        );
    }
    @GetMapping("/jobs/job-applications")
    public ResponseEntity<GlobalResponseDto<Page<JobApplicationResponseDto>>> jobApplicationList(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int limit
    ){
        PageableDto pageParams = new PageableDto(page,limit);
        Page<JobApplicationResponseDto> jobApplications = jobApplicationService.getJobApplications(pageParams);
        return ResponseEntity.ok().body(
                new GlobalResponseDto<>(jobApplications)
        );
    }
    @GetMapping("/jobs/refered-job-applications")
    public ResponseEntity<GlobalResponseDto<Page<JobApplicationResponseDto>>> referedJobApplicationList(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int limit
    ){
        PageableDto pageParams = new PageableDto(page,limit);
        Page<JobApplicationResponseDto> jobApplications = jobApplicationService.getReferedJobApplications(pageParams);
        return ResponseEntity.ok().body(
                new GlobalResponseDto<>(jobApplications)
        );
    }

    @GetMapping("/jobs/{jobId}")
    public ResponseEntity<GlobalResponseDto<CreateJobResponseDto>> jobById(@PathVariable Long jobId){
        var job = jobService.getJobById(jobId);
        return ResponseEntity.ok().body(
                new GlobalResponseDto<>(job)
        );
    }


    @PostMapping("/jobs/{jobId}/refer")
    public ResponseEntity<GlobalResponseDto<?>> referJob(@PathVariable Long jobId, @RequestPart @Valid ReferJobRequestDto applicantsDetail, @NotNull(message = "cv must be uploaded") @RequestPart MultipartFile cv) throws MalformedURLException, MessagingException, FileNotFoundException {
        String cvpath = FileUtility.Save(cv, "cvs");
        jobService.referJobTo(jobId,applicantsDetail,cvpath);
        return ResponseEntity.ok().body(new GlobalResponseDto<>(null, "Job refered successfull.",HttpStatus.OK));

    }
    @PostMapping("/jobs/{jobId}/share")
    public ResponseEntity<GlobalResponseDto<?>> shareJob(@PathVariable Long jobId, @RequestBody @Valid ShareJobRequestDto requestDto) throws MessagingException, MalformedURLException, FileNotFoundException {
        jobService.shareJob(jobId,requestDto);
        return ResponseEntity.ok().body(new GlobalResponseDto<>(null, "Job refered successfull.",HttpStatus.OK));

    }

    @GetMapping("/jobs/job-applications/{jobApplicationId}")
    public ResponseEntity<GlobalResponseDto<JobApplicationResponseDto>> getJobApplicationById(@PathVariable Long jobApplicationId){
        JobApplicationResponseDto responseDto = jobApplicationService.getJobApplicationById(jobApplicationId);
        return ResponseEntity.ok().body(new GlobalResponseDto<>(responseDto));
    }

    @PatchMapping("/jobs/job-applications/{jobApplicationId}")
    public ResponseEntity<GlobalResponseDto<JobApplicationResponseDto>> reviewJobApplication(@PathVariable Long jobApplicationId,@RequestBody ReviewJobApplicationRequestDto requestDto){
        JobApplicationResponseDto responseDto = jobApplicationService.reviewJobApplication(jobApplicationId, requestDto);
        return ResponseEntity.ok().body(new GlobalResponseDto<>(responseDto));
    }

    @PostMapping("/jobs/job-applications/{jobApplicationId}/cv-review")
    public ResponseEntity<GlobalResponseDto<CvReviewResponseDto>> reviewJobApplication(@PathVariable Long jobApplicationId, @RequestBody @Valid CvReviewRequestDto requestDto ){
        CvReviewResponseDto responseDto = jobWiseCvReviewerService.reviewJobApplication(jobApplicationId,requestDto);
        return  ResponseEntity.ok().body(
                new GlobalResponseDto<>(responseDto)
        );
    }
}
