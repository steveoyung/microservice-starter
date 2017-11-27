package com.yonyou.microservice.filter.mapper;

import org.springframework.transaction.annotation.Transactional;

import com.yonyou.microservice.filter.entity.Filter;

import tk.mybatis.mapper.common.Mapper;

@Transactional("groovyTrans")
public interface FilterMapper extends Mapper<Filter> {

}