package com.yonyou.cloud.zuul.db.mapper;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.yonyou.cloud.zuul.db.entity.RouteEntity;

import tk.mybatis.mapper.common.Mapper;
 

@Transactional("zuulTrans")
public interface RouteMapper extends Mapper<RouteEntity>{

    	public List<RouteEntity> findAll(); 
     
    }
 


 
