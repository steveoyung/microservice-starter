package com.yonyou.cloud.zuul.db.support;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.yonyou.cloud.zuul.db.mapper.RouteMapper;
import com.yonyou.cloud.zuul.db.route.StoreProxyRouteLocator;

@RestController
public class RefreshController {
	private Logger logger=Logger.getLogger(RefreshController.class);
//	@Autowired
//	RouteMapper routeMapper;

	@Autowired
	StoreProxyRouteLocator locator;

	@RequestMapping("/routes/refresh")
	public String refresh(){
		locator.forceRefresh();
		return "ok";
	}

}
