
package com.yonyou.microservice.gate.ratelimit.config.repository;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.yonyou.microservice.gate.ratelimit.config.Rate;

/** 
 * In memory rate limiter configuration for dev environment.
 * @author daniell
 *
 */
 
public class InMemoryRateLimiter extends AbstractRateLimiter {

    private Map<String, Rate> repository = new ConcurrentHashMap<>();

    @Override
    protected Rate getRate(String key) {
        return this.repository.get(key);
    }

    @Override
    protected void saveRate(Rate rate) {
        this.repository.put(rate.getKey(), rate);
    }

}
