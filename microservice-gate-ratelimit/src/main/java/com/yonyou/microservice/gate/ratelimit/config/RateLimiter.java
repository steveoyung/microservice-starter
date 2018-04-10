package com.yonyou.microservice.gate.ratelimit.config;

import com.yonyou.microservice.gate.ratelimit.config.properties.RateLimitProperties.Policy;

/**
 * 
 * @author daniell
 *
 */
public interface RateLimiter {

	/**
	 * 取得请求的流量使用情况
     * 
     * @param policy 用于判断是否启用限流
     * @param key 每个限流请求的唯一标识
     * @return 流量使用情况
     */
    Rate consume(Policy policy, String key);
}
