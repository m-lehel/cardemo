package com.demo.car;

import com.demo.car.KeyValueServiceTestImpl;
import com.demo.car.car.database.KeyValueService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
public class TestConfig {

    @Bean
    @Primary // This ensures this bean is prioritized over other implementations
    public KeyValueService keyValueService() {
        return new KeyValueServiceTestImpl();
    }

}
