package io.userhabit.kongsuny.model;

/**
 * Created by kongsuny on 2017. 5. 10..
 */
public class PlayStoreSiteModel {
    private String mUrl;
    private String mCategory;

    public PlayStoreSiteModel(String mUrl, String mCategory) {
        this.mUrl = mUrl;
        this.mCategory = mCategory;
    }

    public String getUrl() {
        return mUrl;
    }

    public void setUrl(String mUrl) {
        this.mUrl = mUrl;
    }

    public String getCategory() {
        return mCategory;
    }

    public void setCategory(String mCategory) {
        this.mCategory = mCategory;
    }
}