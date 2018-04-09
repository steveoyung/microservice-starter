package com.yonyou.cloud.zuul.db.route;

import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.netflix.zuul.filters.ZuulProperties;
import org.springframework.cloud.netflix.zuul.filters.discovery.DiscoveryClientRouteLocator;

import com.yonyou.cloud.zuul.db.store.ZuulRouteStore;
/**
 * 
 * @author joy
 */
public class StoreProxyRouteLocator extends DiscoveryClientRouteLocator {
	private Logger logger=Logger.getLogger(StoreProxyRouteLocator.class);

    private final ZuulRouteStore store;
    private ZuulProperties properties;

    public StoreProxyRouteLocator(String servletPath, DiscoveryClient discovery,
			ZuulProperties properties,
                                  ZuulRouteStore store) {
        super(servletPath, discovery, properties);
        this.store = store;
        this.properties=properties;
        addConfiguredRoutes(properties.getRoutes());
    }
    
    public void forceRefresh() {
    	logger.info("--StoreProxyRouteLocator.forceRefresh");
        addConfiguredRoutes(properties.getRoutes());
    }
    
    @Override
    protected LinkedHashMap<String, ZuulProperties.ZuulRoute> locateRoutes() {
    	logger.info("--StoreProxyRouteLocator.locateRoutes");

        final LinkedHashMap<String, ZuulProperties.ZuulRoute> routesMap = new LinkedHashMap<String, ZuulProperties.ZuulRoute>();
        routesMap.putAll((Map<String, ZuulProperties.ZuulRoute>)super.locateRoutes());
        return routesMap;
    }

    @Override
    protected void addConfiguredRoutes(final Map<String, ZuulProperties.ZuulRoute> routes) {
    	logger.info("--StoreProxyRouteLocator.addConfiguredRoutes");
        for (ZuulProperties.ZuulRoute route : store.findAll()) {
        	routes.put(route.getPath(), route);
        }
    }
}
