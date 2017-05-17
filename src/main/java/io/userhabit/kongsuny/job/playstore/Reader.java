package io.userhabit.kongsuny.job.playstore;

import io.userhabit.kongsuny.model.PlayStoreSiteModel;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;
import org.springframework.batch.item.support.AbstractItemCountingItemStreamItemReader;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.ClassUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * Created by kongsuny on 2017. 5. 8..
 */
@StepScope
@Component
public class Reader extends AbstractItemCountingItemStreamItemReader<PlayStoreSiteModel> {

    @Value("#{jobExecutionContext['DETAIL_URL_DATA']}")
    private List<PlayStoreSiteModel> detailUrls;

    public Reader() {
        super.setName(ClassUtils.getShortName(Reader.class));
    }

    @Override
    protected PlayStoreSiteModel doRead() throws Exception {
        int count = super.getCurrentItemCount() - 1;
        PlayStoreSiteModel item = detailUrls.get(count);

        System.out.println("read : " + item.getUrl());
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
