package io.userhabit.kongsuny.job.playstore;

import org.springframework.batch.item.ItemWriter;

import java.util.List;

/**
 * Created by kongsuny on 2017. 5. 8..
 */
public class Writer implements ItemWriter<String> {
    @Override
    public void write(List<? extends String> items) throws Exception {
        for(String msg : items) {
            System.out.println("Writer test : " + msg);
        }
    }
}
