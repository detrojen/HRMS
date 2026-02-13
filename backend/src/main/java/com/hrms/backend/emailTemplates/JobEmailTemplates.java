package com.hrms.backend.emailTemplates;

import com.hrms.backend.entities.JobListingEntities.Job;
import com.hrms.backend.entities.JobListingEntities.JobApplication;

public class JobEmailTemplates {
    public static String shareJob(Job job) {

        return "Job title :- " + job.getTitle() + "\n\nJob description :- \n" + job.getDescription()
                +"\n\nI am attaching job description document as well.";
    }
    public static String referJob(JobApplication jobApplication) {
        String referrerDetails = "Refered by:- " + jobApplication.getReferedBy().getId() + " : " + jobApplication.getReferedBy().getFullName();
        String freindsDetail = "I am refering to my friend "
                + jobApplication.getApplicantsName()
                + " for " + jobApplication.getJob().getTitle() + " job.\n"
                + "\nother details:-\n" + jobApplication.getDetails()
                + "\nemail- " + jobApplication.getApplicantsEmail()
                + "\nphone- " + jobApplication.getApplicantsPhone()
                + "\n\nI am attaching cv behalf of " + jobApplication.getApplicantsName();

        return referrerDetails + freindsDetail;

    }
}
