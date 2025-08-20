package com.ratelimiter;

import java.util.Collections;
import java.util.Map;


public class RateLimiterConfig {
    private ApiLimit defaultLimit;
    private Map<String, ApiLimit> apis;


    public RateLimiterConfig(ApiLimit defaultLimit, Map<String, ApiLimit> apis) {
        this.defaultLimit = defaultLimit;
        this.apis = apis == null ? Collections.emptyMap() : Map.copyOf(apis);
    }


    public ApiLimit getDefault() { return defaultLimit; }
    public Map<String, ApiLimit> getApis() { return apis; }


    public ApiLimit resolve(String apiId) {
        return apis.getOrDefault(apiId, defaultLimit);
    }
}
