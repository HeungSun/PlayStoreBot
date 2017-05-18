package io.userhabit.kongsuny.job.playstore;

import io.userhabit.kongsuny.database.AppInfoDao;
import io.userhabit.kongsuny.model.AppInfoModel;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by kongsuny on 2017. 5. 8..
 */
@Component
public class Writer implements ItemWriter<AppInfoModel> {

    @Autowired
    private AppInfoDao appInfoDao;


    @Override
    public void write(List<? extends AppInfoModel> items) throws Exception {
        for (AppInfoModel item : items) {
            Calendar cal = Calendar.getInstance();
            Date now = cal.getTime();
            item.setAppInfoUpdateDate(now);

            try {
                appInfoDao.saveAppInfo(item);
            } catch (Exception e) {
            }
        }
    }
}
