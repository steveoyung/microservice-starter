package com.yonyou.microservice.filter.config;

import org.apache.log4j.Logger;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import com.yonyou.microservice.filter.groovy.GroovyLoadLineRunner;

/**
 * 
 * @author joy
 */
@Configuration
@ComponentScan("com.yonyou.microservice.filter")
public class FilterConfiguration  {
	private Logger logger=Logger.getLogger(FilterConfiguration.class);

	@Bean
    public GroovyLoadLineRunner runner() {
    	logger.info("--GroovyLoadLineRunner.runner");
        return new GroovyLoadLineRunner();
    }
}
