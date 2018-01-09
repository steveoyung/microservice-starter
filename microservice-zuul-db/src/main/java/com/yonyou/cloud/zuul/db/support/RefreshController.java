package com.yonyou.cloud.zuul.db.support;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.netflix.zuul.filters.discovery.DiscoveryClientRouteLocator;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.yonyou.cloud.zuul.db.route.StoreProxyRouteLocator;
/**
 * 
 * @author joy
 */
@RestController
public class RefreshController {
	private Logger logger=Logger.getLogger(RefreshController.class);
//	@Autowired
//	RouteMapper routeMapper;

	@Autowired
	DiscoveryClientRouteLocator locator;

	@RequestMapping("/routes/refresh")
	@ResponseBody
	public Map refresh(){
		((StoreProxyRouteLocator)locator).forceRefresh();

    	Map<String,String> map=new HashMap();
    	map.put("message", "ok");
		return map;
	}

}
