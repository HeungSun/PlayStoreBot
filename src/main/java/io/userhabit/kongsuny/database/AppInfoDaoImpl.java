package io.userhabit.kongsuny.database;

import io.userhabit.kongsuny.model.AppInfoModel;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.HibernateTemplate;
import org.springframework.stereotype.Component;

import javax.swing.text.html.parser.ContentModel;
import javax.transaction.Transactional;
import java.util.List;

/**
 * Created by kongsuny on 2017. 5. 18..
 */
@Transactional
@Component
public class AppInfoDaoImpl implements AppInfoDao {

    @Autowired
    private HibernateTemplate hibernateTemplate;

    @Override
    public List<AppInfoModel> getAppInfos() throws Exception {
        Session session = hibernateTemplate.getSessionFactory().getCurrentSession();
        Criteria criteria = session.createCriteria(ContentModel.class);
        List<AppInfoModel> appInfoModelList = (List<AppInfoModel>) criteria.setReadOnly(true).list();

        return appInfoModelList;
    }

    @Override
    public boolean saveAppInfo(AppInfoModel appInfoModel) throws Exception {
        Session session = hibernateTemplate.getSessionFactory().getCurrentSession();
        session.save(appInfoModel);

        if (appInfoModel.getId() > 0) {
            return true;
        }
        return false;
    }
}
