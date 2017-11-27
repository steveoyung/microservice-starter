package com.yonyou.microservice.filter.groovy;

import org.apache.log4j.Logger;
import java.io.FilenameFilter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.netflix.zuul.FilterFileManager;
import com.netflix.zuul.FilterLoader;
import com.netflix.zuul.groovy.GroovyCompiler;
import com.netflix.zuul.monitoring.MonitoringHelper;

import com.netflix.zuul.groovy.GroovyFileFilter;

public class GroovyLoadLineRunner implements CommandLineRunner {
	private Logger logger = Logger.getLogger(GroovyLoadLineRunner.class);
 
    @Value("${groovy.filter.path}")
    private String groovyPath;//"D:\\Del\\groovy\\"
    @Value("${groovy.filter.interval:300}")
    private int interval;
    
    @Override
    public void run(String... args) throws Exception {
        MonitoringHelper.initMocks();
        FilterLoader.getInstance().setCompiler(new GroovyCompiler());
        try {
        	FilenameFilter filter=new GroovyFileFilter();
            FilterFileManager.setFilenameFilter(filter);
            logger.info(groovyPath);
            FilterFileManager.init(interval, groovyPath + "pre", groovyPath + "post");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}