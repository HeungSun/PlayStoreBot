package io.userhabit.kongsuny.job.playstore;

import io.userhabit.kongsuny.model.AppInfoModel;
import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by kongsuny on 2017. 5. 8..
 */
@Component
public class Writer implements ItemWriter<AppInfoModel> {
    @Override
    public void write(List<? extends AppInfoModel> items) throws Exception {
        for (AppInfoModel msg : items) {
            System.out.println("AppInfoModel : " + msg.getTitle() + ":" + msg.getDownLoads());
        }
    }
}
