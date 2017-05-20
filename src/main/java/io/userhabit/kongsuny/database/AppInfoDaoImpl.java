package io.userhabit.kongsuny.database;

import io.userhabit.kongsuny.model.AppInfoModel;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.jdbc.ReturningWork;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate4.HibernateTemplate;
import org.springframework.stereotype.Component;

import javax.swing.text.html.parser.ContentModel;
import javax.transaction.Transactional;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
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
        session.doReturningWork(new ReturningWork<Object>() {
            @Override
            public Object execute(Connection conn) throws SQLException
            {
                try(Statement stmt = conn.createStatement()) {
                    stmt.executeQuery("SET NAMES utf8mb4");
                }

                return null;
            }
        });
        Criteria criteria = session.createCriteria(AppInfoModel.class);
        List<AppInfoModel> appInfoModelList = (List<AppInfoModel>) criteria.setReadOnly(true).list();

        return appInfoModelList;
    }

    @Override
    public boolean saveAppInfo(AppInfoModel appInfoModel) throws Exception {
        Session session = hibernateTemplate.getSessionFactory().getCurrentSession();
        session.doReturningWork(new ReturningWork<Object>() {
            @Override
            public Object execute(Connection conn) throws SQLException
            {
                try(Statement stmt = conn.createStatement()) {
                    stmt.executeQuery("SET NAMES utf8mb4");
                }

                return null;
            }
        });
        session.save(appInfoModel);

        if (appInfoModel.getId() > 0) {
            return true;
        }
        return false;
    }
}
