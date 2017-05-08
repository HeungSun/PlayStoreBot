package io.userhabit.kongsuny.job.playstore;

import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;

/**
 * Created by kongsuny on 2017. 5. 8..
 */
public class Reader implements ItemReader<String> {

    private String[] message = { "sample"};
    private int count =0;

    @Override
    public String read() throws Exception,
            UnexpectedInputException, ParseException, NonTransientResourceException {

        return null;
    }
}
