package by.diomov.event.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import javax.sql.DataSource;
import java.util.Properties;

@Configuration
@EnableTransactionManagement
@AllArgsConstructor
@PropertySource("classpath:db.properties")
public class RepositoryHibernateConfig {
    private static final String URL = "url";
    private static final String USER = "user";
    private static final String DRIVER = "driver";
    private static final String PASSWORD = "password";
    private static final String POOL_SIZE = "poolsize";

    private final Environment env;

    @Bean
    public LocalSessionFactoryBean sessionFactory() {
        LocalSessionFactoryBean sessionFactory = new LocalSessionFactoryBean();
        sessionFactory.setDataSource(dataSource());
        sessionFactory.setPackagesToScan(new String[]{"by.diomov.event.entity"});
        sessionFactory.setHibernateProperties(hibernateProperties());
        return sessionFactory;
    }

    @Bean
    @Profile("prod")
    public DataSource dataSource() {
        HikariConfig config = new HikariConfig();
        config.setDriverClassName(env.getProperty(DRIVER));
        config.setUsername(env.getProperty(USER));
        config.setPassword(env.getProperty(PASSWORD));
        config.setJdbcUrl(env.getProperty(URL));
        config.setMaximumPoolSize(Integer.parseInt(env.getProperty(POOL_SIZE)));
        return new HikariDataSource(config);
    }

    @Bean
    public PlatformTransactionManager hibernateTransactionManager() {
        HibernateTransactionManager transactionManager
                = new HibernateTransactionManager();
        transactionManager.setSessionFactory(sessionFactory().getObject());
        return transactionManager;
    }

    private final Properties hibernateProperties() {
        Properties hibernateProperties = new Properties();
        //hibernateProperties.setProperty("hibernate.hbm2ddl.auto", "create-drop");
        hibernateProperties.setProperty("hibernate.dialect", "org.hibernate.dialect.PostgreSQLDialect");
        return hibernateProperties;
    }
}
