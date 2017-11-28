/**
 * Copyright (c) 2015 the original author or authors
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
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


//@ComponentScan("com.yonyou.cloud.zuul.db")
@Configuration
//@AutoConfigureAfter({ZuulMybatisConfiguration.class})
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
