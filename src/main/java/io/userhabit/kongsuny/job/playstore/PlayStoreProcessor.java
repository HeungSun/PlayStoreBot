package io.userhabit.kongsuny.job.playstore;

import io.userhabit.kongsuny.model.AppInfoModel;
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
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by kongsuny on 2017. 5. 8..
 */
@StepScope
@Component
public class PlayStoreProcessor implements ItemProcessor<PlayStoreSiteModel, AppInfoModel> {

    private static final String BASE_APP_INFO_DETAIL_PAGE_ADDRESS = "https://play.google.com";

    //css itemprop string value
    //업데이트 날짜
    private static final String UPDATE_DATE = "datePublished";
    //설치 수
    private static final String DOWNLOADS = "numDownloads";
    //현재 버전
    private static final String CURRENT_VERSION = "softwareVersion";
    //지원되는 버전
    private static final String OPERATION_VERSION = "operatingSystems";

    @Override
    public AppInfoModel process(PlayStoreSiteModel item) throws Exception {

        AppInfoModel appInfo = null;

        CloseableHttpClient httpclient = HttpClients.createDefault();

        try {
            HttpPost httpPost = new HttpPost(BASE_APP_INFO_DETAIL_PAGE_ADDRESS + item.getUrl());

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
            //item category : 플레이스토어에서 얻은 원청 사이트 - 유료앱 순위
            appInfo = getDetailAppInfoFromBody(responseBody);

        } catch (Exception e) {
            System.out.println("playstore processor error : " + e.getMessage());
        }
        return appInfo;
    }

    private AppInfoModel getDetailAppInfoFromBody(String body) {

        AppInfoModel appInfoModel = null;
        Document doc = Jsoup.parse(body);

        Elements image = doc.select("div.cover-container img.cover-image");
        String imageStr = image.attr("src");

        Elements title = doc.select("h1.document-title div.id-app-title");
        String titleStr = title.text();

        Elements genre = doc.select("div.left-info a.category");
        String genreStr = genre.text();

        Elements company = doc.select("div.left-info a.primary");
        String companyStr = company.text();

        //업데이트 날짜, 크기 설치수 현재 버전 지원되는 Android 버전 개발자
        Elements links = doc.select("div.details-section-contents div.meta-info");

        String updateDate = "";
        String downloads = "";
        String currentVersion = "";
        String operationVersion = "";

        for (int j = 0; j < links.size(); j++) {
            Elements list = links.get(j).select("div.content");
            String prop = list.attr("itemprop");
            if(prop.equals(UPDATE_DATE)) {
                updateDate = list.text();
            } else if(prop.equals(DOWNLOADS)) {
                downloads = list.text();
            } else if(prop.equals(CURRENT_VERSION)) {
                currentVersion = list.text();
            } else if(prop.equals(OPERATION_VERSION)) {
                operationVersion = list.text();
            }
        }

        appInfoModel = new AppInfoModel();
        appInfoModel.setImageUrl(imageStr);
        appInfoModel.setTitle(titleStr);
        appInfoModel.setGenre(genreStr);
        appInfoModel.setUpdateDate(updateDate);
        appInfoModel.setCompany(companyStr);
        appInfoModel.setDownLoads(downloads);
        appInfoModel.setCurrentVersion(currentVersion);
        appInfoModel.setOperationVersion(operationVersion);

        return appInfoModel;
    }
}
