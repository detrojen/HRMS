package com.hrms.backend.controllers;

import com.hrms.backend.dtos.globalDtos.PageableDto;
import com.hrms.backend.dtos.requestDto.CreateJobRequestDto;
import com.hrms.backend.dtos.requestDto.ShareJobRequestDto;
import com.hrms.backend.dtos.responseDtos.CreateJobResponseDto;
import com.hrms.backend.dtos.responseDtos.GlobalResponseDto;
import com.hrms.backend.services.JobListingServices.JobService;
import com.hrms.backend.utils.FileUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class JobController {
    private final JobService jobService;

    @Autowired
    public JobController(JobService jobService){
        this.jobService = jobService;
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

    @PostMapping("/jobs/{jobId}/share")
    public ResponseEntity<GlobalResponseDto<?>> shareJob(@PathVariable Long jobId, @RequestBody ShareJobRequestDto requestDto){
        return ResponseEntity.ok().body(new GlobalResponseDto<>(null, "Job shared successfull.",HttpStatus.OK));
    }
}
