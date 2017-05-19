package pl.bTech.carsappII.utilities;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
import java.util.Properties;

/**
 * @author Kamil Radek<kamil.radek@btech.pl>
 */
@Configuration
@PropertySources({
        @PropertySource(value = "classpath:mysql.properties")
})

public class SqlConfig {

    @Value("${database.host}")
    protected String host;
    @Value("${database.name}")
    protected String databaseName;
    @Value("${database.user}")
    protected String user;
    @Value("${database.password}")
    protected String password;
    @Value("${database.port}")
    protected String port;
    @Value("${database.type}")
    protected String type;
    @Value("${database.timezone}")
    protected String timezone;
    @Value("${database.databaseTestName}")
    protected String databaseTestName;

    @Bean
    public DataSource mysql() {
        String jdbcFormat = String.format("jdbc:%s://%s:%s/%s?serverTimezone=%s&createDatabaseIfNotExist=true",
                type, host, port, databaseTestName, timezone);

        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("com.mysql.jdbc.Driver");
        dataSource.setUrl(jdbcFormat);
        dataSource.setUsername(user);
        dataSource.setPassword(password);

        return dataSource;
    }

    @Bean
    @Autowired
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(DataSource dataSource) {
        LocalContainerEntityManagerFactoryBean entityManagerFactoryBean
                = new LocalContainerEntityManagerFactoryBean();

        entityManagerFactoryBean.setDataSource(dataSource);
        entityManagerFactoryBean.setPackagesToScan("pl.bTech.carsappII.*.entity" // wytrych do wszystkich pakietów
        );
        entityManagerFactoryBean.setPersistenceUnitName("myPersistenceUnit");
        HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        entityManagerFactoryBean.setJpaVendorAdapter(vendorAdapter);

        Properties properties = new Properties();
        properties.put("hibernate.dialect", "org.hibernate.dialect.MySQLDialect");
//        properties.put("hibernate.hbm2ddl.auto", "create"); // usuwa wszystkie dane z bazy
        properties.put("hibernate.hbm2ddl.auto", "update");

        entityManagerFactoryBean.setJpaProperties(properties);
        entityManagerFactoryBean.afterPropertiesSet();

        return entityManagerFactoryBean;
    }

    @Autowired
    @Bean
    @SuppressWarnings("SpringJavaAutowiringInspection")
    public PlatformTransactionManager transactionManager(LocalContainerEntityManagerFactoryBean entityManagerFactory) {
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(entityManagerFactory.getObject());

        return transactionManager;
    }

    @Bean
    public PersistenceExceptionTranslationPostProcessor exceptionTranslation() {
        return new PersistenceExceptionTranslationPostProcessor();
    }

    @Bean
    public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
        return new PropertySourcesPlaceholderConfigurer();
    }
}
