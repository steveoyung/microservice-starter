package com.yonyou.cloud.zuul.db.store;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.netflix.zuul.filters.ZuulProperties;

import com.yonyou.cloud.zuul.db.entity.RouteEntity;
import com.yonyou.cloud.zuul.db.mapper.RouteMapper;
/**
 * 
 * @author joy
 */
public class MysqlZuulRouteStore implements ZuulRouteStore {
	private Logger logger=Logger.getLogger(MysqlZuulRouteStore.class);
//	@Autowired
//	@Qualifier("zuulDataSource")
//	public DataSource dataSource;
	
	@Autowired
    private RouteMapper dao;

	
    
    public MysqlZuulRouteStore() {
    }

    @Override
    public List<ZuulProperties.ZuulRoute> findAll() {
    	logger.info("--List<ZuulProperties.ZuulRoute> findAll()，从数据库加载路由");
    	List<ZuulProperties.ZuulRoute> zs=new ArrayList();
    	List<RouteEntity> list=dao.findAll();
    	for(RouteEntity e:list){
    		ZuulProperties.ZuulRoute zl=new ZuulProperties.ZuulRoute();
    		zl.setId(e.getId());
    		zl.setPath(e.getPath());
    		zl.setRetryable(e.getRetryable());
    		zl.setServiceId(e.getServiceId());
    		zl.setStripPrefix(e.getStripPrefix());
    		zl.setUrl(e.getUrl());
    		zs.add(zl);
    	}
        return zs;
    }

//    @Repository
//    public interface RouteDao extends JpaRepository<ZuulProperties.ZuulRoute, String>{
//    	
//
//    	@Query("select * from gate_routes")
//    	public List<ZuulProperties.ZuulRoute> findAll(); 
//     
//    }

}
