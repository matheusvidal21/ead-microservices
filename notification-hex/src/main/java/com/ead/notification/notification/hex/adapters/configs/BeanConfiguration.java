package com.ead.notification.notification.hex.adapters.configs;

import com.ead.notification.notification.hex.NotificationHexApplication;
import com.ead.notification.notification.hex.core.ports.NotificationPersistencePort;
import com.ead.notification.notification.hex.core.services.NotificationServicePortImpl;
import ma.glasnost.orika.MapperFacade;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.impl.DefaultMapperFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackageClasses = NotificationHexApplication.class)
public class BeanConfiguration {

    @Bean
    NotificationServicePortImpl notificationServicePortImpl(NotificationPersistencePort persistence) {
        return new NotificationServicePortImpl(persistence);
    }

    @Bean
    public MapperFactory mapperFactory() {
        DefaultMapperFactory factory = new DefaultMapperFactory.Builder().build();
        return factory;
    }

    @Bean
    public MapperFacade mapperFacade(MapperFactory mapperFactory) {
        return mapperFactory.getMapperFacade();
    }
}
