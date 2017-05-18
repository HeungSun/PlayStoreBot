package io.userhabit.kongsuny.database;

import io.userhabit.kongsuny.model.AppInfoModel;

import java.util.List;

/**
 * Created by kongsuny on 2017. 5. 18..
 */
public interface AppInfoDao {
    public List<AppInfoModel> getAppInfos() throws Exception;
    public boolean saveAppInfo(AppInfoModel appInfoModel) throws Exception;
}
