/*
 * Copyright 2012-2017 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.yonyou.microservice.gate.ratelimit.config;

import com.yonyou.microservice.gate.ratelimit.config.properties.RateLimitProperties.Policy;

/**
 * @author Marcos Barbero
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
