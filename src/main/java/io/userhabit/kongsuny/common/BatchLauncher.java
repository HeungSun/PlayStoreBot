package io.userhabit.kongsuny.common;

import org.springframework.batch.core.*;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import javax.batch.operations.JobExecutionAlreadyCompleteException;
import java.util.Map;

/**
 * Created by kongsuny on 2017. 5. 8..
 */
public class BatchLauncher implements ApplicationContextAware {

    private ApplicationContext applicationContext;
    private Map<String, String> params;
    private String jobName;

    @Autowired
    private JobLauncher jobLauncher;

    public void setJobName(String jobName) {
        this.jobName = jobName;
    }
    public void setParams(Map<String, String> params) { this.params = params;}

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    public void run() throws JobParametersInvalidException, JobExecutionAlreadyCompleteException,
            JobExecutionAlreadyRunningException, JobInstanceAlreadyCompleteException , JobRestartException {

        System.out.println("BatchLauncher");

        JobParametersBuilder builder = new JobParametersBuilder();
        Job job = (Job) applicationContext.getBean(jobName);
        JobExecution execution = jobLauncher.run(job, builder.toJobParameters());
        execution.getStatus().name();
    }
}
