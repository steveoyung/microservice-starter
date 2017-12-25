package com.yonyou.microservice.filter.groovy;

import java.io.File;
import java.io.FilenameFilter;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Service;

import com.netflix.zuul.FilterFileManager;
import com.netflix.zuul.FilterLoader;
import com.netflix.zuul.groovy.GroovyCompiler;
import com.netflix.zuul.groovy.GroovyFileFilter;
import com.netflix.zuul.monitoring.MonitoringHelper;
/**
 * 
 * @author joy
 */
@Service
public class GroovyLoadLineRunner implements CommandLineRunner {
	private Logger logger = Logger.getLogger(GroovyLoadLineRunner.class);
 
    @Value("${groovy.filter.path}")
    private String groovyPath;
    @Value("${groovy.filter.interval:300}")
    private int interval;

	public void initDir(){
		logger.info("--initDir,"+groovyPath);
		File file1 = new File(groovyPath+"pre") ;
		if(!file1.exists()){
			file1.mkdirs();
		}
		File file2 = new File(groovyPath+"post") ;
		if(!file2.exists()){
			file2.mkdirs();
		}
	}
    @Override
    public void run(String... args) throws Exception {
    	initDir();
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