//package com.yonyou.microservice.gate.ratelimit.config.repository;
//
//import com.yonyou.microservice.gate.ratelimit.config.Rate;
//import com.yonyou.microservice.gate.ratelimit.config.RateLimiter;
//import com.yonyou.microservice.gate.ratelimit.config.properties.RateLimitProperties.Policy;
//import lombok.RequiredArgsConstructor;
//import org.springframework.data.redis.core.RedisTemplate;
//
//import static java.util.concurrent.TimeUnit.SECONDS;
//
///**
// * 
// */
//@RequiredArgsConstructor
//public class RedisRateLimiter implements RateLimiter {
//    private final RedisTemplate template;
//
//    @Override
//    @SuppressWarnings("unchecked")
//    public Rate consume(final Policy policy, final String key) {
//        final Long limit = policy.getLimit();
//        final Long refreshInterval = policy.getRefreshInterval();
//        final Long current = this.template.boundValueOps(key).increment(1L);
//        Long expire = this.template.getExpire(key);
//        if (expire == null || expire == -1) {
//            this.template.expire(key, refreshInterval, SECONDS);
//            expire = refreshInterval;
//        }
//        return new Rate(key, Math.max(-1, limit - current), SECONDS.toMillis(expire), null);
//    }
//}
