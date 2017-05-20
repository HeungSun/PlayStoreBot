package io.userhabit.kongsuny.job;

import io.userhabit.kongsuny.ExcelGenerater;
import io.userhabit.kongsuny.database.AppInfoDao;
import io.userhabit.kongsuny.model.AppInfoModel;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.listener.JobExecutionListenerSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

import static io.userhabit.kongsuny.common.Config.APP_STORE_JOB;
import static io.userhabit.kongsuny.common.Config.PLAY_STORE_JOB;

/**
 * Created by kongsuny on 2017. 5. 8..
 */
@Component
public class JobCompletionListener extends JobExecutionListenerSupport {

    @Autowired
    private AppInfoDao appInfoDao;

    private boolean isCompleteAppStore;
    private boolean isCompletePlayStore;

    @Override
    public void afterJob(JobExecution jobExecution) {
        super.afterJob(jobExecution);
        try {
            String jobName = jobExecution.getJobInstance().getJobName();
            if (jobExecution.getStatus() == BatchStatus.COMPLETED) {
                if (jobName.equals(PLAY_STORE_JOB)) {
                    isCompletePlayStore = true;
                } else if (jobName.equals(APP_STORE_JOB)) {
                    isCompleteAppStore = true;
                }
                System.out.println("JOB COMPLETE : " + jobName);
            }

            if (isCompleteAppStore && isCompletePlayStore) {
                List<AppInfoModel> list = appInfoDao.getAppInfos();
                ExcelGenerater excel = new ExcelGenerater();
                excel.saveExcelFile(list);
                System.out.println("All Job COMPLETE");
            }
        } catch (Exception e) {

        }
    }

    @Override
    public void beforeJob(JobExecution jobExecution) {
        super.beforeJob(jobExecution);
    }
}