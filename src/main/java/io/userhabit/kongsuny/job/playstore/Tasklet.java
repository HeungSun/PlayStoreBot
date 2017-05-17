package io.userhabit.kongsuny.job.playstore;

import io.userhabit.kongsuny.model.PlayStoreSiteModel;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * Created by HeungSun-AndBut on 2017. 5. 10..
 */
@Component
public class Tasklet implements org.springframework.batch.core.step.tasklet.Tasklet {

    private static final String SITE_URL = "https://play.google.com/store";

    //순서 대로 : 인기 유료 , 최고 매출 , 인기 앱, 신규 인기 앱
    private String[] mSiteList = {"https://play.google.com/store/apps/collection/topselling_paid"};//, "https://play.google.com/store/apps/collection/topgrossing",
//            "https://play.google.com/store/apps/collection/topselling_free", "https://play.google.com/store/apps/collection/topselling_new_free"};

    private String LOAD_CONTENT_NUM = "60";
    Map<String, PlayStoreSiteModel> sites = new HashMap<>();
    private int mCurrentContent = 0;

    @Override
    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {

        CloseableHttpClient httpclient = HttpClients.createDefault();

        System.out.println("registed site search start : " + mSiteList.length);
        try {
            for (int i = 0; i < mSiteList.length; i++) {

                String site = mSiteList[i];

                System.out.println("search start site : " + site);

                //TODO 끝인지 어떻게 알수있을까? 임시로 아래와 같이 적용 후에 수정
                boolean duplicated = false;
                while (!duplicated) {
                    HttpPost httpPost = new HttpPost(site);

                    List<NameValuePair> postParams = new ArrayList<NameValuePair>();
                    postParams.add(new BasicNameValuePair("start", String.valueOf(mCurrentContent)));
                    postParams.add(new BasicNameValuePair("num", LOAD_CONTENT_NUM));
                    postParams.add(new BasicNameValuePair("cctcss", "square-cover"));
                    postParams.add(new BasicNameValuePair("cllayout", "NORMAL"));
                    postParams.add(new BasicNameValuePair("ipf", "1"));
                    postParams.add(new BasicNameValuePair("xhr", "1"));
                    postParams.add(new BasicNameValuePair("token", "66r4OI_TKfX-3naObfXIYbT0ljQ:1494425852938"));

                    httpPost.setEntity(new UrlEncodedFormEntity(postParams));

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

                    Thread.sleep(5000);
                    String responseBody = httpclient.execute(httpPost, responseHandler);
                    duplicated = getAppUrlFromBody(site, responseBody);

                    mCurrentContent += Integer.valueOf(LOAD_CONTENT_NUM);

                }
            }
        } catch (Exception e) {

        }

        System.out.println("final check app count : " + sites.size());

        List<PlayStoreSiteModel> realSites = new ArrayList<>(sites.values());
        chunkContext.getStepContext().getStepExecution().getJobExecution().getExecutionContext().put("DETAIL_URL_DATA", realSites);

        return RepeatStatus.FINISHED;
    }

    private boolean getAppUrlFromBody(String category, String body) {

        Document doc = Jsoup.parse(body);

        Elements links = doc.select("div.details a.card-click-target").next();

        System.out.println("links :" + links.size());

        String url = "";

        for (int j = 0; j < links.size(); j++) {
            Element link = links.get(j);
            url = link.attr("href");
            if (sites.containsKey(url) && category.equals(sites.get(url).getCategory())) {
                //Already check the page
                return true;
            }
            sites.put(url, new PlayStoreSiteModel(category, url));

            System.out.println("add url :" + url);
        }

        System.out.println(category + " : result size :" + sites.size());

        return false;
    }
}