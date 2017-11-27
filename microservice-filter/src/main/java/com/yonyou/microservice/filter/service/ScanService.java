package com.yonyou.microservice.filter.service;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.yonyou.microservice.filter.entity.Filter;
import com.yonyou.microservice.filter.mapper.FilterMapper;

@Service
public class ScanService {
	private static Logger logger=Logger.getLogger(ScanService.class);
	
	@Autowired
	private FilterMapper filter;
	
	public ScanService(){
		logger.info("--ScanService 对象创建");
	}
	
	@Scheduled(cron = "0 */1 * * * ?") 
	public void scanDb(){
		logger.info("--ScanService ,scanDb 执行");
		List<Filter> filters=filter.selectAll();
		for(Filter f:filters){
			logger.info("--script:"+f.getScript());
		}
	}
}
