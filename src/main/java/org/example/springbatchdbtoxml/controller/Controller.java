package org.example.springbatchdbtoxml.controller;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class Controller {

    private final JobLauncher jobBuilder;

    private final Job job;


    public Controller(JobLauncher jobBuilder, @Qualifier("manyToManyJob") Job job) {
        this.jobBuilder = jobBuilder;
        this.job = job;
    }

    @GetMapping("/start")
    public void convert() throws Exception {
        var jobParametest = new JobParametersBuilder();
        jobParametest.addString("JobID", String.valueOf(System.currentTimeMillis()));

        jobBuilder.run(job, jobParametest.toJobParameters());

    }
}
