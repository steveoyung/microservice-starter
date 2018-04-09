package com.yonyou.cloud.zuul.db.support;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.netflix.zuul.ZuulProxyAutoConfiguration;
import org.springframework.cloud.netflix.zuul.filters.ZuulProperties;
import org.springframework.cloud.netflix.zuul.filters.discovery.DiscoveryClientRouteLocator;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import com.yonyou.cloud.zuul.db.config.ZuulMybatisConfiguration;
import com.yonyou.cloud.zuul.db.route.StoreProxyRouteLocator;
import com.yonyou.cloud.zuul.db.store.ZuulRouteStore;

/**
 * 
 * @author joy
 */
@Configuration
public class ZuulProxyStoreConfiguration extends ZuulProxyAutoConfiguration {
	private Logger logger=Logger.getLogger(ZuulProxyStoreConfiguration.class);

    @Autowired
    private ZuulRouteStore zuulRouteStore;

    @Autowired
    private DiscoveryClient discovery;

    @Autowired
    private ZuulProperties zuulProperties;

    @Autowired
    private ServerProperties server;

    public ZuulProxyStoreConfiguration(){
    	logger.info("--ZuulProxyStoreConfiguration()");
    }
    @Override
    public DiscoveryClientRouteLocator discoveryRouteLocator() {
    	logger.info("--ZuulProxyStoreConfiguration.routeLocator");
        return new StoreProxyRouteLocator(server.getServletPath(),discovery, zuulProperties, zuulRouteStore);
    }
}
