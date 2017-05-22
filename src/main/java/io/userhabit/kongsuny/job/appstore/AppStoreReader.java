package io.userhabit.kongsuny.job.appstore;

import io.userhabit.kongsuny.model.AppStoreJsonModel;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.support.AbstractItemCountingItemStreamItemReader;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.ClassUtils;

import java.util.List;


/**
 * Created by kongsuny on 2017. 5. 18..
 */
@StepScope
@Component
public class AppStoreReader extends AbstractItemCountingItemStreamItemReader<AppStoreJsonModel> {

    @Value("#{jobExecutionContext['DETAIL_JSON_DATA']}")
    private List<AppStoreJsonModel> detailJsons;

    public AppStoreReader() {
        super.setName(ClassUtils.getShortName(AppStoreReader.class));
    }

    @Override
    protected AppStoreJsonModel doRead() throws Exception {
        int count = super.getCurrentItemCount() - 1;
        AppStoreJsonModel site = detailJsons.get(count);
        return site;
    }

    @Override
    protected void doOpen() throws Exception {
        super.setMaxItemCount(detailJsons.size());
    }

    @Override
    protected void doClose() throws Exception {

    }
}
