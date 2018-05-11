package com.yonyou.microservice.gate.ratelimit.config.repository;

import static java.util.concurrent.TimeUnit.SECONDS;

import java.util.Date;

import com.yonyou.microservice.gate.ratelimit.config.Rate;
import com.yonyou.microservice.gate.ratelimit.config.RateLimiter;
import com.yonyou.microservice.gate.ratelimit.config.properties.RateLimitProperties.Policy;

/** 
 * Abstract implementation for {@link RateLimiter}.
 * @author daniell
 *
 */
  
public abstract class AbstractRateLimiter implements RateLimiter {
	/**
	 * 根据key获取流量使用情况
	 * 
     * @param key 每个限流请求的唯一标识
     * @return 流量使用情况
     */
    protected abstract Rate getRate(String key);
	/**
	 * 保存流量信息到内存、redis、jpa等
     * @param rate 流量使用情况
     */
    protected abstract void saveRate(Rate rate);

    @Override
    public synchronized Rate consume(final Policy policy, final String key) {
        Rate rate = this.create(policy, key);
        this.updateRate(rate);
        this.saveRate(rate);
        return rate;
    }

    private Rate create(final Policy policy, final String key) {
        Rate rate = this.getRate(key);
        if (isExpired(rate)) {

            final Long limit = policy.getLimit();
            final Long refreshInterval = SECONDS.toMillis(policy.getRefreshInterval());
            final Date expiration = new Date(System.currentTimeMillis() + refreshInterval);

            rate = new Rate(key, limit, refreshInterval, expiration);
        }
        return rate;
    }

    private void updateRate(final Rate rate) {
        if (rate.getReset() > 0) {
            Long reset = rate.getExpiration().getTime() - System.currentTimeMillis();
            rate.setReset(reset);
        }
        rate.setRemaining(Math.max(-1, rate.getRemaining() - 1));
    }

    private boolean isExpired(final Rate rate) {
        return rate == null || (rate.getExpiration().getTime() < System.currentTimeMillis());
    }
}
