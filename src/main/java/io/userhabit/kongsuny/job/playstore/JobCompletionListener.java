package io.userhabit.kongsuny.job.playstore;

import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.listener.JobExecutionListenerSupport;

/**
 * Created by kongsuny on 2017. 5. 8..
 */
public class JobCompletionListener extends JobExecutionListenerSupport {

    @Override
    public void afterJob(JobExecution jobExecution) {
        super.afterJob(jobExecution);
        if(jobExecution.getStatus() == BatchStatus.COMPLETED) {
            System.out.println("BATCH JOB COMPLETED SUCCESSFULLY");
        }
    }

    @Override
    public void beforeJob(JobExecution jobExecution) {
        super.beforeJob(jobExecution);
        System.out.println("BEFORE JOB hello");
    }
}