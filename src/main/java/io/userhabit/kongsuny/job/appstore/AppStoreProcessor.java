package io.userhabit.kongsuny.job.appstore;

import io.userhabit.kongsuny.model.AppInfoModel;
import io.userhabit.kongsuny.model.AppStoreJsonModel;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

/**
 * Created by kongsuny on 2017. 5. 18..
 */
@StepScope
@Component
public class AppStoreProcessor implements ItemProcessor<AppStoreJsonModel, AppInfoModel> {

    @Override
    public AppInfoModel process(AppStoreJsonModel appStoreJson) throws Exception {

        AppInfoModel appInfoModel = null;
        String json = appStoreJson.getJson();

        String updateDate = "";
        String downloads = "자료없음";
        String currentVersion = "자료없음";
        String operationVersion = "자료없음";
        String genre = "";
        String title = "";
        String image = "";
        String company = "";

        try {
            JSONParser jsonParser = new JSONParser();
            JSONObject parent = (JSONObject) jsonParser.parse(json);

            //currentVersion
            genre = getAttributeJson((JSONObject)parent.get("category"), "label");
            updateDate = getAttributeJson((JSONObject)parent.get("im:releaseDate"), "label");
            company = ((JSONObject)parent.get("im:artist")).get("label").toString();
            title = ((JSONObject)parent.get("im:name")).get("label").toString();
            image = ((JSONObject)((JSONArray)parent.get("im:image")).get(0)).get("label").toString();

        } catch (Exception e) {
            System.out.println("appStore processor error : " + e.getMessage());
        }

        appInfoModel = new AppInfoModel();
        appInfoModel.setImageUrl(image);
        appInfoModel.setTitle(title);
        appInfoModel.setGenre(genre);
        appInfoModel.setUpdateDate(updateDate);
        appInfoModel.setCompany(company);
        appInfoModel.setDownLoads(downloads);
        appInfoModel.setCurrentVersion(currentVersion);
        appInfoModel.setOperationVersion(operationVersion);
        appInfoModel.setUniqueId(appStoreJson.getBndleId());
        return appInfoModel;
    }

    private String getAttributeJson(JSONObject json, String detailAttr) {
        JSONObject attribute = (JSONObject)json.get("attributes");
        return attribute.get(detailAttr).toString();
    }
}
