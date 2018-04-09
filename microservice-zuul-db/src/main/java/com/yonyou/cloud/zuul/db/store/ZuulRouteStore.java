package com.yonyou.cloud.zuul.db.store;

import java.util.List;

import org.springframework.cloud.netflix.zuul.filters.ZuulProperties;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * 
 * @author joy
 */
public interface ZuulRouteStore {
    /**
     * 读取所有的路由信息
     * @return zuul的路由集合
     */
    List<ZuulProperties.ZuulRoute> findAll();
}
