package com.hrms.backend.controllers;

import com.hrms.backend.dtos.globalDtos.PageableDto;
import com.hrms.backend.dtos.requestDto.job.CreateJobRequestDto;
import com.hrms.backend.dtos.requestDto.job.ReferJobRequestDto;
import com.hrms.backend.dtos.requestDto.job.ShareJobRequestDto;
import com.hrms.backend.dtos.responseDtos.job.CreateJobResponseDto;
import com.hrms.backend.dtos.responseDtos.GlobalResponseDto;
import com.hrms.backend.services.JobListingServices.JobApplicationService;
import com.hrms.backend.services.JobListingServices.JobService;
import com.hrms.backend.utils.FileUtility;
import jakarta.mail.MessagingException;
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
    @Autowired
    public JobController(JobService jobService, JobApplicationService jobApplicationService){
        this.jobService = jobService;
        this.jobApplicationService = jobApplicationService;
    }

    @PostMapping(value = "/jobs")
    public ResponseEntity<GlobalResponseDto<CreateJobResponseDto>> createJob(@RequestPart(value = "jobDocument") MultipartFile jobDocument , @RequestPart(value = "jobDetail") CreateJobRequestDto jobDetail ){
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

    @GetMapping("/jobs/{jobId}")
    public ResponseEntity<GlobalResponseDto<CreateJobResponseDto>> jobById(@PathVariable Long jobId){
        var job = jobService.getJobById(jobId);
        return ResponseEntity.ok().body(
                new GlobalResponseDto<>(job)
        );
    }


    @PostMapping("/jobs/{jobId}/refer")
    public ResponseEntity<GlobalResponseDto<?>> referJob(@PathVariable Long jobId, @RequestPart ReferJobRequestDto applicantsDetail, @RequestPart MultipartFile cv) throws MalformedURLException, MessagingException, FileNotFoundException {
        String cvpath = FileUtility.Save(cv, "cvs");
        jobApplicationService.referJobTo(jobId,applicantsDetail,cvpath);
        return ResponseEntity.ok().body(new GlobalResponseDto<>(null, "Job refered successfull.",HttpStatus.OK));

    }
    @PostMapping("/jobs/{jobId}/share")
    public ResponseEntity<GlobalResponseDto<?>> shareJob(@PathVariable Long jobId, @RequestBody ShareJobRequestDto requestDto) throws MessagingException, MalformedURLException, FileNotFoundException {
        jobService.shareJob(jobId,requestDto);
        return ResponseEntity.ok().body(new GlobalResponseDto<>(null, "Job refered successfull.",HttpStatus.OK));

    }
}
