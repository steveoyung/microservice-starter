package com.yonyou.microservice.filter.mapper;

import org.springframework.transaction.annotation.Transactional;

import com.yonyou.microservice.filter.entity.Filter;

import tk.mybatis.mapper.common.Mapper;
/**
 * 
 * @author joy
 */
@Transactional(value="groovyTrans",rollbackFor=(Exception.class))
public interface FilterMapper extends Mapper<Filter> {

}