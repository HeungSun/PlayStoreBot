package io.userhabit.kongsuny.job.playstore;

import io.userhabit.kongsuny.model.PlayStoreSiteModel;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.apache.http.util.TextUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by HeungSun-AndBut on 2017. 5. 10..
 */
@Component
public class Tasklet implements org.springframework.batch.core.step.tasklet.Tasklet {

    private static final String SITE_URL = "https://play.google.com/store";
    private String[] mSiteList = {"https://play.google.com/store/apps/collection/recommended_for_you_POPULAR_APPS_GAMES?clp=ygICEAQ%3D:S:ANO1ljJTyak"};

    @Override
    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {

        CloseableHttpClient httpclient = HttpClients.createDefault();

        List<PlayStoreSiteModel> sites = new ArrayList<>();

        try {

            for (int i = 0; i < mSiteList.length; i++) {

                String site = mSiteList[i];
                HttpGet httpget = new HttpGet(site);

                ResponseHandler<String> responseHandler = new ResponseHandler<String>() {

                    @Override
                    public String handleResponse(HttpResponse response)
                            throws ClientProtocolException, IOException {
                        int status = response.getStatusLine().getStatusCode();

                        if (status >= 200 && status < 300) {
                            HttpEntity entity = response.getEntity();
                            return entity != null ? EntityUtils.toString(entity) : null;
                        } else {
                            throw new ClientProtocolException("Unexpected response status: " + status);
                        }
                    }
                };

                String responseBody = httpclient.execute(httpget, responseHandler);

                Document doc = Jsoup.parse(responseBody);

                Elements links = doc.select("a.card-click-target").next();

                System.out.println("links :" + links.size());

                String url = "";

                for (int j = 0; j < links.size(); j++) {
                    Element link = links.get(0);
                    url = link.attr("href");
                    System.out.println("url :" + url);
                    if (!TextUtils.isEmpty(url)) {
                        sites.add(new PlayStoreSiteModel(site, url));
                    }
                }

                System.out.println(site + " : result size :" + sites.size());
            }
        } catch (Exception e) {

        }


        return null;
    }
}