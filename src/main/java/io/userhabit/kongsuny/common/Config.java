package io.userhabit.kongsuny.common;

import io.userhabit.kongsuny.job.playstore.*;
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

    private static final String PLAY_STORE_JOB = "playstoreJob";
    private static final String PLAY_STORE_STEP_LIST_PAGE_JOB = "playstroeListJob";
    private static final String PLAY_STORE_STEP_DETAIL_PAGE_JOB = "detailPageJob";


    @Autowired
    public JobBuilderFactory jobBuilderFactory;
    @Autowired
    public StepBuilderFactory stepBuilderFactory;

    @Autowired
    Reader playstoreReader;
    @Autowired
    Writer playstoreWriter;
    @Autowired
    Tasklet playstoreTasklet;
    @Autowired
    Processor playstoreProcessor;

    @Bean
    public Job ProcessJob() {
        return jobBuilderFactory.get(PLAY_STORE_JOB)
                .start(playstroeListJob()).on("COMPLETED").to(detailPageJob())
                .build()
                .listener(playstorelistener())
                .build();
    }

    @Bean
    public Step playstroeListJob() {
        return stepBuilderFactory.get(PLAY_STORE_STEP_LIST_PAGE_JOB)
                .tasklet(playstoreTasklet)
                .build();
    }

    @Bean
    Step detailPageJob() {
        return stepBuilderFactory.get(PLAY_STORE_STEP_DETAIL_PAGE_JOB).<String, String>chunk(10)
                .reader(playstoreReader)
                .processor(playstoreProcessor)
                .writer(playstoreWriter)
                .build();
    }

    @Bean
    JobExecutionListener playstorelistener() {
        return new JobCompletionListener();
    }


}