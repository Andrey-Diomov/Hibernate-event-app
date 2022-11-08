package by.diomov.event.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.*;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.transaction.PlatformTransactionManager;
import javax.sql.DataSource;
import java.util.Objects;

@Configuration
@Import({RepositoryHibernateConfig.class})
@ComponentScan("by.diomov.event")
@PropertySource("classpath:dev.properties")
public class TestSpringHibernateConfig {
    private static final String URL = "url";
    private static final String USER = "user";
    private static final String DRIVER = "driver";
    private static final String PASSWORD = "password";

    @Autowired
    private Environment env;

    @Bean
    @Profile("dev")
    public DataSource dataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(
                Objects.requireNonNull(env.getProperty(DRIVER)));
        dataSource.setUrl(env.getProperty(URL));
        dataSource.setUsername(env.getProperty(USER));
        dataSource.setPassword(env.getProperty(PASSWORD));
        return dataSource;
    }

    @Bean("transactionalManager")
    public PlatformTransactionManager platformTransactionManager() {
        return new DataSourceTransactionManager(dataSource());
    }
}