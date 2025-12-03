package com.hainv.booking.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableJpaRepositories(
        basePackages = "com.hainv.booking.repository.booking",
        entityManagerFactoryRef = "bookingEntityManager",
        transactionManagerRef = "bookingTransactionManager"
)
public class BookingDataSourceConfig {

    @Bean
    @ConfigurationProperties("spring.datasource.booking")
    public DataSourceProperties bookingDataSourceProperties() {
        return new DataSourceProperties();
    }

    @Bean
    public DataSource bookingDataSource() {
        return bookingDataSourceProperties().initializeDataSourceBuilder().build();
    }

    @Bean(name = "bookingEntityManager")
    public LocalContainerEntityManagerFactoryBean bookingEntityManager(
            @Qualifier("bookingDataSource") DataSource dataSource) {

        LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
        em.setDataSource(dataSource);
        em.setPackagesToScan("com.hainv.booking.entity.booking");
        em.setJpaVendorAdapter(new HibernateJpaVendorAdapter());

        Map<String, Object> props = new HashMap<>();
        props.put("hibernate.hbm2ddl.auto", "update");
        props.put("hibernate.dialect", "org.hibernate.dialect.PostgreSQLDialect");
        props.put("hibernate.show_sql", "true");
        em.setJpaPropertyMap(props);

        return em;
    }

    @Bean(name = "bookingTransactionManager")
    public JpaTransactionManager bookingTransactionManager(
            @Qualifier("bookingEntityManager") LocalContainerEntityManagerFactoryBean emf) {
        return new JpaTransactionManager(emf.getObject());
    }
}
