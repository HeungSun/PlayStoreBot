package io.userhabit.kongsuny.model;

import java.io.Serializable;

/**
 * Created by kongsuny on 2017. 5. 22..
 */
public class AppStoreJsonModel implements Serializable {

    private static final long serialVersionUID = 123L;

    private String mBndleId;
    private String mJson;


    public AppStoreJsonModel(String mBndleId, String mJson) {
        this.mBndleId = mBndleId;
        this.mJson = mJson;
    }

    public String getBndleId() {
        return mBndleId;
    }

    public void setBndleId(String mBndleId) {
        this.mBndleId = mBndleId;
    }

    public String getJson() {
        return mJson;
    }

    public void setJson(String mJson) {
        this.mJson = mJson;
    }
}
