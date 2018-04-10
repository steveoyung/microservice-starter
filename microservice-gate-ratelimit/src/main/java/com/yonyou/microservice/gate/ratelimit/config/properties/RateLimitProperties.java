
package com.yonyou.microservice.gate.ratelimit.config.properties;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

/**
 * 
 * @author daniell
 *
 */
@Data
@Validated
@NoArgsConstructor
@ConfigurationProperties(RateLimitProperties.PREFIX)
public class RateLimitProperties {

    public static final String PREFIX = "zuul.ratelimit";

    private Policy defaultPolicy;
    @NotNull
    private Map<String, Policy> policies = Maps.newHashMap();
    private boolean behindProxy;
    private boolean enabled;
    @NotNull
    @Value("${spring.application.name:rate-limit-application}")
    private String keyPrefix;
    @NotNull
    private Repository repository = Repository.IN_MEMORY;

    public enum Repository {
    	/* 存储于redis  */
        REDIS, 
    	/* 存储于consul  */
        CONSUL, 
    	/* 存储于jpa  */
        JPA, 
    	/* 存储于memory  */
        IN_MEMORY
    }

    public Optional<Policy> getPolicy(String key) {
        return Optional.ofNullable(policies.getOrDefault(key, defaultPolicy));
    }

    @Data
    @NoArgsConstructor
    public static class Policy {

        @NotNull
        private Long refreshInterval = TimeUnit.MINUTES.toSeconds(1L);
        @NotNull
        private Long limit;
        @NotNull
        private List<Type> type = Lists.newArrayList();

    	/**
         * 定义限流的方法
         */
        public enum Type {
        	//根据请求来源ip限流
            ORIGIN, 
            //根据请求用户限流
            USER, 
            //根据请求url限流
            URL
        }
    }
}