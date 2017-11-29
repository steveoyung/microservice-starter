package com.yonyou.microservice.filter.service;

import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.lang.reflect.Field;
import java.text.ParseException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.filters.FilterRegistry;
import com.yonyou.microservice.filter.entity.Filter;
import com.yonyou.microservice.filter.mapper.FilterMapper;

@Service
public class ScanService {
	private static Logger logger=Logger.getLogger(ScanService.class);
	
	@Autowired
	private FilterMapper filter;
	
    @Value("${groovy.filter.path}")
    private String groovyPath;
	
    @Value("${spring.application.name}")
    private String serviceName;
    
    
	public ScanService(){
		logger.info("--ScanService 对象创建");
	}
	@Scheduled(cron = "0 */1 * * * ?") 
	public void scanDb() throws ParseException{
		Filter e=new Filter();
		e.setServiceName(serviceName);
		List<Filter> filters=filter.select(e);
		logger.info("--ScanService ,scanDb 执行");
//		List<Filter> filters=filter.selectAll();//从db读取所有的filter
		File file1 = new File(groovyPath+"pre") ;
		File[] preFiles =file1.listFiles();
		cleanFile(filters,preFiles);//删除不在db中的本地pre文件
		File file2 = new File(groovyPath+"post") ;
		File[] postFiles =file2.listFiles();
		cleanFile(filters,postFiles);//删除不在db中的本地post文件
		for(Filter f:filters){
			File oldfile = new File(groovyPath+f.getType()+"/"+f.getName()+".groovy"); 
			updateFile(f,oldfile);//根据db数据创建或更新filter文件
		}
	}
	private void cleanFile(List<Filter> filters,File[] files){
		for(File f:files){
			if(!hasFile(filters,f.getName())){
				logger.info("--cleanFile ,清除文件,"+f.getName()+".groovy");
				f.delete();
//				String cl=f.getName().substring(0,f.getName().indexOf(".groovy"));
//				logger.info("--cleanFile ,卸载类,"+cl);
//				String key=null;
//				try {
//					key = getKey(cl,FilterRegistry.instance());
//				} catch (Exception e) {
//					logger.error(e.getMessage());
//				} 
//				if(key!=null){
//					FilterRegistry.instance().remove(key);
//				}
			}
		}
	}
	private String getKey(String name,Object bean) throws IllegalArgumentException, IllegalAccessException{
		Class userCla = (Class) bean.getClass();  
	       Field[] fs = userCla.getDeclaredFields();  
	       for(int i = 0 ; i < fs.length; i++){  
	           Field f = fs[i];  
	           if(f.getName().equals("filters")){
		           f.setAccessible(true); //设置些属性是可以访问的  
		           Object val = f.get(bean);//得到此属性的值   
		           ConcurrentHashMap<String, ZuulFilter> m=(ConcurrentHashMap<String, ZuulFilter>)val;
		           for(Map.Entry<String, ZuulFilter> entry: m.entrySet()) {  
		        	   if(entry.getKey().contains(name)){
		        		   return entry.getKey();
		        	   }
		          } 
	           } 
	       }  
	    return "";
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
			long l1= oldfile.lastModified();
			long l2=f.getUpdateDate().getTime();//df.parse(.toString())
			if(l1<l2){
				logger.info("--updateFile，需要更新script,先删除"+f.getName()+".groovy");
				oldfile.delete();
				newFile(oldfile,f);
			}else{
				logger.info("--updateFile，文件不需要更新,"+f.getName()+".groovy");
			}
		}else{
			logger.info("--updateFile，新建文件,"+f.getName()+".groovy");
			newFile(oldfile,f);
		}
	}
	public void newFile(File oldfile,Filter f) {
		try {
			logger.info("--,newFile,script:"+f.getScript());
			FileWriter resultFile = new FileWriter(oldfile);   
			PrintWriter myFile = new PrintWriter(resultFile);   
			myFile.println(f.getScript()); 
			oldfile.setLastModified(f.getUpdateDate().getTime());
			resultFile.close(); 
		} catch (Exception e) {
			logger.error("--newFile 创建文件失败,"+e.getMessage());
		} 
	}
}
