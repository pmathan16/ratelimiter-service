package com.ratelimiter;

public interface RateLimiter {
    boolean allow(String userId, String apiId);
}
