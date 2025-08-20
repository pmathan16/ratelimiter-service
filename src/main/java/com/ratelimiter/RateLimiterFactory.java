package com.ratelimiter;

public final class RateLimiterFactory {
    private RateLimiterFactory() {}


    public static RateLimiter tokenBucket(RateLimiterConfig config) {
        return new TokenBucketRateLimiter(config);
    }
}
