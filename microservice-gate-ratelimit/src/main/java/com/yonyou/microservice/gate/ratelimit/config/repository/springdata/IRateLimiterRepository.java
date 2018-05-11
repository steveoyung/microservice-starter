package com.yonyou.microservice.gate.ratelimit.config.repository.springdata;

import com.yonyou.microservice.gate.ratelimit.config.Rate;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
/** 
 * 定义jpa存储接口，供SpringDataRateLimiter限流类使用
 * @author daniell
 *
 */
public interface IRateLimiterRepository extends CrudRepository<Rate, String> {

}
