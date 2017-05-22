package io.userhabit.kongsuny.job.appstore;

import io.userhabit.kongsuny.model.AppStoreJsonModel;
import io.userhabit.kongsuny.model.PlayStoreBaseModel;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by kongsuny on 2017. 5. 18..
 */
@StepScope
@Component
public class AppStoreTasklet implements org.springframework.batch.core.step.tasklet.Tasklet {


    private String mBaseUrl = "https://itunes.apple.com/kr/rss/%s/limit=100/genre=60%02d/json";
    private String[] mCategory = {"newfreeapplications", "newpaidapplications", "topfreeapplications", "topgrossingapplications", "toppaidapplications"};
    private int mGenreMaxNum = 24;
    Map<String, AppStoreJsonModel> sites = new HashMap<>();


    @Override
    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {

        CloseableHttpClient httpclient = HttpClients.createDefault();

        try {
            for (int j = 0; j < mCategory.length; j++) {
                for (int i = 0; i < mGenreMaxNum + 1; i++) {
                    String url = String.format(mBaseUrl, mCategory[j], i);
                    HttpPost httpPost = new HttpPost(url);

                    List<NameValuePair> postParams = new ArrayList<>();
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

                    String responseBody = httpclient.execute(httpPost, responseHandler);
                    getAppUrlFromBody(responseBody);

                    System.out.println(url + " | item count: " + sites.size());
                }

            }

        } catch (Exception e) {
            System.out.println("appStore taklet error : " + e.getMessage());
        }

        System.out.println("final check appstore count : " + sites.size());
        List<AppStoreJsonModel> appJson = new ArrayList<>(sites.values());
        chunkContext.getStepContext().getStepExecution().getJobExecution().getExecutionContext().put("DETAIL_JSON_DATA", appJson);

        return RepeatStatus.FINISHED;
    }

    private void getAppUrlFromBody(String responseBody) {
        try {
            JSONParser jsonParser = new JSONParser();
            JSONObject jsonObj = (JSONObject) ((JSONObject) jsonParser.parse(responseBody)).get("feed");

            JSONArray appList = (JSONArray) jsonObj.get("entry");

            for (int i = 0; i < appList.size(); i++) {
                JSONObject id = (JSONObject) ((JSONObject) appList.get(i)).get("id");
                JSONObject attributes = (JSONObject) id.get("attributes");
                String bundle = attributes.get("im:bundleId").toString();
                if (!sites.containsKey(bundle)) {
                    sites.put(bundle, new AppStoreJsonModel(bundle, appList.get(i).toString()));
                }
            }
        } catch (Exception e) {
            System.out.println("getAppUrlFromBody : " + e.getMessage());
        }
    }


}