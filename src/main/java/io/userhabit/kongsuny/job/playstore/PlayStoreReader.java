package io.userhabit.kongsuny.job.playstore;

import io.userhabit.kongsuny.model.PlayStoreBaseModel;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.support.AbstractItemCountingItemStreamItemReader;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.ClassUtils;

import java.util.List;

/**
 * Created by kongsuny on 2017. 5. 8..
 */
@StepScope
@Component
public class PlayStoreReader extends AbstractItemCountingItemStreamItemReader<PlayStoreBaseModel> {

    @Value("#{jobExecutionContext['DETAIL_URL_DATA']}")
    private List<PlayStoreBaseModel> detailUrls;

    public PlayStoreReader() {
        super.setName(ClassUtils.getShortName(PlayStoreReader.class));
    }

    @Override
    protected PlayStoreBaseModel doRead() throws Exception {
        int count = super.getCurrentItemCount() - 1;
        PlayStoreBaseModel item = detailUrls.get(count);
        return item;
    }

    @Override
    protected void doOpen() throws Exception {
        super.setMaxItemCount(detailUrls.size());
    }

    @Override
    protected void doClose() throws Exception {

    }
}
