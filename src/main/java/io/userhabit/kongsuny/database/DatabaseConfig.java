package io.userhabit.kongsuny.database;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.hibernate4.HibernateTemplate;
import org.springframework.orm.hibernate4.LocalSessionFactoryBuilder;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.Properties;

/**
 * Created by kongsuny on 2017. 5. 18..
 */
@SuppressWarnings("ALL")
@Configuration
@EnableTransactionManagement
public class DatabaseConfig {
//    @Value("${spring.jpa.showSql}")
//    private boolean showSql;

    @Bean
    @Autowired
    public HibernateTemplate hibernateTemplate(DataSource dataSource) {
        return new HibernateTemplate(sessionFactory(dataSource));
    }

    @Bean
    public SessionFactory sessionFactory(DataSource dataSource) {
        return new LocalSessionFactoryBuilder(dataSource)
                .scanPackages("io.userhabit.kongsuny")
                .buildSessionFactory();
    }

    @Bean
    @Autowired
    public LocalContainerEntityManagerFactoryBean entityManagerFactoryBean(DataSource dataSource) {
        LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
        em.setDataSource(dataSource);
        em.setPackagesToScan("io.userhabit.kongsuny");

        // persistence 설정
        Properties properties = new Properties();
        properties.put("hibernate.show_sql", "true");
        properties.put("hibernate.dialect", "org.hibernate.dialect.MySQLDialect");
        properties.put("hibernate.id.new_generator_mappings", "false");
        properties.put("hibernate.hbm2ddl.auto", "create");
        properties.put("hibernate.connection.useUnicode", "true");
        properties.put("hibernate.connection.CharSet", "utf8mb4");
        properties.put("hibernate.connection.characterEncoding", "utf8mb4");

        // 각 구현체의 프로퍼티 확장 및 설정
        JpaVendorAdapter jpaVendorAdapter = new HibernateJpaVendorAdapter();
        em.setJpaVendorAdapter(jpaVendorAdapter);
        em.setJpaProperties(properties);
        return em;
    }
}
