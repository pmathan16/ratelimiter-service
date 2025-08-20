package com.ratelimiter;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;


public class TokenBucketRateLimiter implements RateLimiter {
    private final RateLimiterConfig config;
    private final ConcurrentMap<String, TokenBucket> buckets = new ConcurrentHashMap<>();


    public TokenBucketRateLimiter(RateLimiterConfig config) {
        this.config = config;
    }


    @Override
    public boolean allow(String userId, String apiId) {
        ApiLimit limit = config.resolve(apiId);
        String key = userId + "|" + apiId;
        TokenBucket bucket = buckets.computeIfAbsent(key,
                k -> new TokenBucket(limit.getCapacity(), limit.getRefillTokensPerSecond()));

        return bucket.tryConsume();
    }
}
