package com.yonyou.microservice.gate.ratelimit;

import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * 
 * @author daniell
 *
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Import(RateLimitAutoConfiguration.class)
@Documented
@Inherited
public @interface EnableOpsGateRateLimit {
}
