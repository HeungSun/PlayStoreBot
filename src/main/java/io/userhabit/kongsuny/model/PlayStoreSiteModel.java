package io.userhabit.kongsuny.model;

import java.io.Serializable;

/**
 * Created by kongsuny on 2017. 5. 10..
 */
public class PlayStoreSiteModel implements Serializable {
    private static final long serialVersionUID = 123L;

    private String mUrl;
    private String mCategory;

    public PlayStoreSiteModel(String mCategory, String mUrl) {
        this.mCategory = mCategory;
        this.mUrl = mUrl;
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