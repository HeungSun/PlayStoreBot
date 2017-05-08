package io.userhabit.kongsuny.common;

import io.userhabit.kongsuny.job.playstore.JobCompletionListener;
import io.userhabit.kongsuny.job.playstore.Processor;
import io.userhabit.kongsuny.job.playstore.Reader;
import io.userhabit.kongsuny.job.playstore.Writer;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


/**
 * Created by kongsuny on 2017. 5. 8..
 */

@Configuration
@EnableBatchProcessing
public class Config {
    @Autowired
    public JobBuilderFactory jobBuilderFactory;
    @Autowired
    public StepBuilderFactory stepBuilderFactory;

    @Bean
    public Job ProcessJob() {
        return jobBuilderFactory.get("playstoreJob")
                .incrementer(new RunIdIncrementer()).listener(listener())
                .flow(playstoreJobStep()).end().build();
    }

    @Bean
    Step playstoreJobStep() {
        return stepBuilderFactory.get("playstoreJob-tep1").<String, String> chunk(1)
                .reader(new Reader()).processor(new Processor())
                .writer(new Writer()).build();
    }

    @Bean
    JobExecutionListener listener() {
        return new JobCompletionListener();
    }



}
