package io.userhabit.kongsuny.model;

import java.io.Serializable;

/**
 * Created by kongsuny on 2017. 5. 10..
 */
public class PlayStoreBaseModel implements Serializable {
    private static final long serialVersionUID = 123L;

    private String mPackage;
    private String mCategory;

    public PlayStoreBaseModel(String mCategory, String mPackage) {
        this.mCategory = mCategory;
        this.mPackage = mPackage;
    }


    public String getPackage() {
        return mPackage;
    }

    public void setPackage(String mPackage) {
        this.mPackage = mPackage;
    }

    public String getCategory() {
        return mCategory;
    }

    public void setCategory(String mCategory) {
        this.mCategory = mCategory;
    }
}