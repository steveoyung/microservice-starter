package com.yonyou.microservice.gate.ratelimit.config;

import javax.servlet.http.HttpServletRequest;

/**
 * 
 * @author daniell
 *
 */
public class DefaultUserPrincipal implements IUserPrincipal {
    @Override
    public String getName(HttpServletRequest request) {
        if(request.getUserPrincipal()==null) {
            return null;
        }
        return request.getUserPrincipal().getName();
    }
}
