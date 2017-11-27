package com.yonyou.microservice.filter.service;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.yonyou.microservice.filter.entity.Filter;
import com.yonyou.microservice.filter.mapper.FilterMapper;

@Service
public class ScanService {
	private static Logger logger=Logger.getLogger(ScanService.class);
	
	@Autowired
	private FilterMapper filter;
	
    @Value("${groovy.filter.path}")
    private String groovyPath;
	
	public ScanService(){
		logger.info("--ScanService 对象创建");
	}
	
	@Scheduled(cron = "0 */1 * * * ?") 
	public void scanDb() throws ParseException{
		logger.info("--ScanService ,scanDb 执行");
		List<Filter> filters=filter.selectAll();
		File file1 = new File(groovyPath+"pre") ;
		File[] preFiles =file1.listFiles();
		cleanFile(filters,preFiles);
		File file2 = new File(groovyPath+"post") ;
		File[] postFiles =file2.listFiles();
		cleanFile(filters,postFiles);
		for(Filter f:filters){
			logger.info("--,scanDb,script:"+f.getScript());
			File oldfile = new File(groovyPath+f.getType()+"/"+f.getName()+".groovy"); 
			updateFile(f,oldfile);
		}
	}
	private void cleanFile(List<Filter> filters,File[] files){
		for(File f:files){
			if(!hasFile(filters,f.getName())){
				f.delete();
			}
		}
	}
	private boolean hasFile(List<Filter> filters,String fn){
		for(Filter fr:filters){
			if(fn.equals(fr.getName()+".groovy")){
				return true;
			}
		}
		return false;
	}
	private void updateFile(Filter f,File oldfile){
		if(oldfile.exists()){
			logger.info("--scanDb，文件已经存在,"+f.getName()+".groovy");
			long l1= oldfile.lastModified();
			Date d=new Date(l1);
			DateFormat df = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
			long l2=f.getUpdateDate().getTime();//df.parse(.toString())
			if(l1<l2){
				logger.info("--scanDb，需要更新script");
				oldfile.delete();
				newFile(oldfile,f);
			}
		}else{
			logger.info("--scanDb，新建文件,"+f.getName()+".groovy");
			newFile(oldfile,f);
		}
	}
	public void newFile(File oldfile,Filter f) {
		try {
			FileWriter resultFile = new FileWriter(oldfile);   
			PrintWriter myFile = new PrintWriter(resultFile);   
			myFile.println(f.getScript()); 
			oldfile.setLastModified(f.getUpdateDate().getTime());
			resultFile.close(); 
		} catch (Exception e) {
			logger.error("--scanDb error,"+e.getMessage());
		} 
	}
}
