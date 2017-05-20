package io.userhabit.kongsuny.common;

import io.userhabit.kongsuny.job.JobCompletionListener;
import io.userhabit.kongsuny.job.appstore.AppStoreProcessor;
import io.userhabit.kongsuny.job.appstore.AppStoreReader;
import io.userhabit.kongsuny.job.appstore.AppStoreTasklet;
import io.userhabit.kongsuny.job.playstore.*;
import io.userhabit.kongsuny.model.AppInfoModel;
import io.userhabit.kongsuny.model.PlayStoreSiteModel;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


/**
 * Created by kongsuny on 2017. 5. 8..
 */

@Configuration
@EnableBatchProcessing
public class Config {

    public static final String PLAY_STORE_JOB = "playStoreJob";
    private static final String PLAY_STORE_STEP_LIST_PAGE_JOB = "playStoreListJob";
    private static final String PLAY_STORE_STEP_DETAIL_PAGE_JOB = "playStoreDetailPageJob";
    public static final String APP_STORE_JOB = "appStoreJob";
    private static final String APP_STORE_STEP_LIST_PAGE_JOB = "appStoreListJob";
    private static final String APP_STORE_STEP_DETAIL_PAGE_JOB = "appStoreDetailPageJob";

    @Autowired
    public JobBuilderFactory jobBuilderFactory;
    @Autowired
    public StepBuilderFactory stepBuilderFactory;
    @Autowired
    public JobCompletionListener jobCompletionListener;

    @Autowired
    PlayStoreReader playStoreReader;
    @Autowired
    PlayStoreWriter playStoreWriter;
    @Autowired
    PlayStoreTasklet playStoreTasklet;
    @Autowired
    PlayStoreProcessor playStoreProcessor;
    @Autowired
    AppStoreProcessor appStoreProcessor;
    @Autowired
    AppStoreReader appStoreReader;
    @Autowired
    AppStoreTasklet appStoreTasklet;

    //AppStore
    @Bean
    public Job appStoreJob() {
        return jobBuilderFactory.get(APP_STORE_JOB)
                .start(appStoreListJob()).on("COMPLETED").to(appStoreDetailPageJob())
                .build()
                .listener(listener())
                .build();
    }

    public Step appStoreListJob() {
        return stepBuilderFactory.get(APP_STORE_STEP_LIST_PAGE_JOB)
                .tasklet(appStoreTasklet)
                .build();
    }

    public Step appStoreDetailPageJob() {
        return stepBuilderFactory.get(APP_STORE_STEP_DETAIL_PAGE_JOB).<String, AppInfoModel>chunk(10)
                .reader(appStoreReader)
                .processor(appStoreProcessor)
                .writer(playStoreWriter)
                .build();
    }

    //playStore
    @Bean
    public Job playStoreJob() {
        return jobBuilderFactory.get(PLAY_STORE_JOB)
                .start(playStoreListJob()).on("COMPLETED").to(playStoreDetailPageJob())
                .build()
                .listener(listener())
                .build();
    }

    public Step playStoreListJob() {
        return stepBuilderFactory.get(PLAY_STORE_STEP_LIST_PAGE_JOB)
                .tasklet(playStoreTasklet)
                .build();
    }

    public Step playStoreDetailPageJob() {
        return stepBuilderFactory.get(PLAY_STORE_STEP_DETAIL_PAGE_JOB).<PlayStoreSiteModel, AppInfoModel>chunk(10)
                .reader(playStoreReader)
                .processor(playStoreProcessor)
                .writer(playStoreWriter)
                .build();
    }

    @Bean
    JobExecutionListener listener() {
        return jobCompletionListener;
    }


}