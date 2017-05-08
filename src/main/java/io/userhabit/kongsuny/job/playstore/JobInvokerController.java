package io.userhabit.kongsuny.job.playstore;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by kongsuny on 2017. 5. 8..
 * web으로 표시할경우?
 */
public class JobInvokerController {
//    @Autowired
//    JobLauncher jobLauncher;
//
//    @Autowired
//    Job processJob;
//
//    @RequestMapping("/invokejob")
//    public String handle() throws Exception {
//        JobParameters jobParameters = new JobParametersBuilder().addLong("time", System.currentTimeMillis())
//                .toJobParameters();
//        jobLauncher.run(processJob, jobParameters);
//
//        return "Batch job has been invoked";
//    }
}
