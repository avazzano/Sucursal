package com.sucursal;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
@EntityScan(basePackages = {"com.sucursal.model"})  // scan JPA entities
@ComponentScan(basePackages = { "com.sucursal.*" })
@EnableJpaRepositories("com.sucursal.repository")
@TestPropertySource(locations = {"classpath:application.properties"})
class SucursalApplicationTests {

	@Test
	void contextLoads() {
	}
	
	@Bean
	  public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
	      return new PropertySourcesPlaceholderConfigurer();
	  }

}
