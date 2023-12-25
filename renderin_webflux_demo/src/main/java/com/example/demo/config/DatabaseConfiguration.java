package com.example.demo.config;


import com.example.demo.dao.converter.GeometryReadConverter;
import io.r2dbc.spi.ConnectionFactories;
import io.r2dbc.spi.ConnectionFactory;
import io.r2dbc.spi.ConnectionFactoryOptions;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.r2dbc.config.AbstractR2dbcConfiguration;
import org.springframework.data.r2dbc.convert.R2dbcCustomConversions;
import org.springframework.data.r2dbc.dialect.DialectResolver;

import java.util.List;

@Configuration
public class DatabaseConfiguration extends AbstractR2dbcConfiguration {

    @Override
    public ConnectionFactory connectionFactory() {
        return ConnectionFactories.get(
                ConnectionFactoryOptions.builder()
                        .option(ConnectionFactoryOptions.DRIVER, "postgresql")
                        .option(ConnectionFactoryOptions.HOST, "localhost")
                        .option(ConnectionFactoryOptions.USER, "postgis")
                        .option(ConnectionFactoryOptions.PASSWORD, "postgis")
                        .option(ConnectionFactoryOptions.DATABASE, "postgis")
                        .build()
        );
    }

    @Bean
    public R2dbcCustomConversions r2dbcCustomConversions(ConnectionFactory connectionFactory){
        var dialect = DialectResolver.getDialect(connectionFactory);
        return R2dbcCustomConversions.of( dialect, List.of(new GeometryReadConverter()));
    }
}
