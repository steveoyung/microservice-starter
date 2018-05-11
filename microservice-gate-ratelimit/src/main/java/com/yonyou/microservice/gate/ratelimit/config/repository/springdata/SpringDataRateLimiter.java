package com.yonyou.microservice.gate.ratelimit.config.repository.springdata;

import com.yonyou.microservice.gate.ratelimit.config.Rate;
import com.yonyou.microservice.gate.ratelimit.config.repository.AbstractRateLimiter;
import lombok.RequiredArgsConstructor;

/**
 * In memory rate limiter configuration for dev environment.
 * @author daniell
 *
 */
 
@RequiredArgsConstructor
public class SpringDataRateLimiter extends AbstractRateLimiter {

    private final IRateLimiterRepository repository;

    @Override
    protected Rate getRate(String key) {
        return this.repository.findOne(key);
    }

    @Override
    protected void saveRate(Rate rate) {
        this.repository.save(rate);
    }

}
