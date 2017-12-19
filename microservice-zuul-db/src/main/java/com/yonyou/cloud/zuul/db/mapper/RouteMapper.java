package com.yonyou.cloud.zuul.db.mapper;

import java.util.List;

import org.apache.ibatis.annotations.ResultType;
import org.apache.ibatis.annotations.Select;
import org.springframework.transaction.annotation.Transactional;

import com.yonyou.cloud.zuul.db.entity.RouteEntity;

import tk.mybatis.mapper.common.Mapper;
 

@Transactional("zuulTrans")
public interface RouteMapper extends Mapper<RouteEntity>{

    @Select(" SELECT id,path,service_id,url,strip_prefix,retryable from gate_routes order by path desc" )
    @ResultType(RouteEntity.class)
    public List<RouteEntity> findAll(); 
     
}
 


 
