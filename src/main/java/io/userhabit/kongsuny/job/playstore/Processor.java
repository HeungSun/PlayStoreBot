package io.userhabit.kongsuny.job.playstore;

import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

/**
 * Created by kongsuny on 2017. 5. 8..
 */
@Component
public class Processor implements ItemProcessor<String, String> {
    @Override
    public String process(String item) throws Exception {
        return item.toUpperCase();
    }
}
