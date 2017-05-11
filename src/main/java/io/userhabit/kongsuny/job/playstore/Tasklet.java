package io.userhabit.kongsuny.job.playstore;

import io.userhabit.kongsuny.model.PlayStoreSiteModel;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
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

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

240개 부터는 처음 부터 다시 불러온다
1.왜 다시 불러올까
2.이제 모어 로딩이 끝날때 타이밍을 알아내기

/**
 * Created by HeungSun-AndBut on 2017. 5. 10..
 */
@Component
public class Tasklet implements org.springframework.batch.core.step.tasklet.Tasklet {

    private static final String SITE_URL = "https://play.google.com/store";
//    private String[] mSiteList = {"https://play.google.com/store/apps/collection/recommended_for_you_POPULAR_APPS_GAMES?clp=ygICEAQ%3D:S:ANO1ljJTyak"};
private String[] mSiteList = {"https://play.google.com/store/apps/category/GAME/collection/topselling_paid"};

    private String LOAD_CONTENT_NUM  = "60";
    List<PlayStoreSiteModel> sites = new ArrayList<>();

    private int mCurrentContent = 0;
    @Override
    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {

        CloseableHttpClient httpclient = HttpClients.createDefault();

        try {
            for (int i = 0; i < mSiteList.length; i++) {

                String site = mSiteList[i];

                //TODO 끝인지 어떻게 알수있을까? 임시로 아래와 같이 적용 후에 수정
                int addContent = -1;
                while(addContent != 0) {
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
                    addContent = getAppUrlFromBody(site, responseBody);

                    mCurrentContent += addContent;
                }
            }
        } catch (Exception e) {

        }

        return null;
    }

    private int getAppUrlFromBody(String category, String body) {

        Document doc = Jsoup.parse(body);

        Elements links = doc.select("div.details a.card-click-target").next();

        System.out.println("links :" + links.size());

        String url = "";

        for (int j = 0; j < links.size(); j++) {
            Element link = links.get(j);
            url = link.attr("href");
            System.out.println("url :" + url);
            if (!TextUtils.isEmpty(url)) {
                sites.add(new PlayStoreSiteModel(category, url));
            }
        }

        System.out.println(category + " : result size :" + sites.size());

        return sites.size();
    }
}